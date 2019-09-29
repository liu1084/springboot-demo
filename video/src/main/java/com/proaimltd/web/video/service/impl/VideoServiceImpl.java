
package com.proaimltd.web.video.service.impl;


import com.proaimltd.web.video.config.ZffException;
import com.proaimltd.web.video.model.entity.FileBlockInfo;
import com.proaimltd.web.video.model.entity.PubVideoBlockInfo;
import com.proaimltd.web.video.model.entity.PubVideoInfo;
import com.proaimltd.web.video.model.entity.dto.VideoMergeReqDTO;
import com.proaimltd.web.video.model.mapper.PubVideoBlockInfoMapper;
import com.proaimltd.web.video.model.mapper.PubVideoInfoMapper;
import com.proaimltd.web.video.service.IVideoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @project: springboot-demo
 * @packageName: com.proaimltd.web.video.service.impl
 * @author: fengkun.zhao
 * @date: 2019/9/10 15:17
 * @description：TODO
 */
@Service
@Slf4j
public class VideoServiceImpl implements IVideoService {

	@Value("${logging.isDebug}")
	private Boolean isDebug;

	@Value("${video.file.maxSize}")
	private long videoFileMaxSize;

	@Value("${video.file.allowedMimeTypes}")
	private List<String> allowedMimeTypes;

	@Value("${video.file.location}")
	private String uploadLocation;

	@Autowired
	private PubVideoInfoMapper pubVideoInfoMapper;

	@Autowired
	private PubVideoBlockInfoMapper pubVideoBlockInfoMapper;


	private static final String DOWNLOAD_URL_KEY = "downloadUrl";

	private void valid(MultipartFile uploadFile) {
	}

	private File createOrRetrieve(final String target) throws IOException {
		final Path path = Paths.get(target);

		if (Files.notExists(path)) {
			log.info("Target file \"" + target + "\" will be created.");
			return Files.createFile(Files.createDirectories(path)).toFile();
		}
		log.info("Target file \"" + target + "\" will be retrieved.");
		return path.toFile();
	}

	/**
	 * 处理上传的文件块
	 *
	 * @param fileBlockInfo
	 * @throws IOException
	 */
	private void handleUploadBlock(FileBlockInfo fileBlockInfo) throws IOException {
		//接收文件的文件夹
		String target = uploadLocation + "/" + fileBlockInfo.getMd5();
		File currentDirectory = createOrRetrieve(target);
		String fileName = currentDirectory + fileBlockInfo.getMd5();
		//大文件被切割成小文件的每个小文件的命名
		String destName = String.format("%s_%03d", fileName, fileBlockInfo.getIndex());
		Path destPath = Paths.get(destName);
		try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream((File) fileBlockInfo.getUploadFile()));) {
			byte[] buffer = new byte[8192];
			int len = 0;
			while ((len = bufferedInputStream.read(buffer)) != -1) {
				try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(destPath.toFile()))) {
					bufferedOutputStream.write(buffer, 0, len);
					bufferedOutputStream.flush();
				}
			}
		}


	}

	/**
	 * 文件上传块信息入库
	 * @param fileBlockInfo
	 */
	private void addUploadBlockToDB(FileBlockInfo fileBlockInfo) {
		PubVideoBlockInfo pubVideoBlockInfo = new PubVideoBlockInfo();
		pubVideoBlockInfo.setBlockIndex(fileBlockInfo.getIndex());
		pubVideoBlockInfo.setBlockSize(fileBlockInfo.getFileSize());
		pubVideoBlockInfo.setMd5(fileBlockInfo.getMd5());

		pubVideoBlockInfoMapper.insertPubVideoBlockInfo(pubVideoBlockInfo);
	}


	/**
	 * 接收上传得block之前, 先检查一下之前是否上传过此文件
	 * 如果文件不存在, 但是数据库中有记录, 需要删除记录
	 *
	 * @param pubVideoInfo
	 * @param fileBlockInfo
	 */
	private void handleIfFileNotFound(PubVideoInfo pubVideoInfo, FileBlockInfo fileBlockInfo) {
		//先检查已经上传的文件是否真的真的已经存在了
		String target = uploadLocation + "/" + fileBlockInfo.getMd5();
		Path path = Paths.get(target);
		//如果路径不存在
		if (!Files.exists(path)) {
			//数据库记录是否已经存在,如果存在,需要删除
			List<PubVideoBlockInfo> uploadedBlocks = pubVideoBlockInfoMapper.queryPubVideoBlocksByMD5(pubVideoInfo.getFileMd5());
			if (!uploadedBlocks.isEmpty()) {
				//删除记录
				pubVideoBlockInfoMapper.deletePubVideoBlockInfoByMD5(pubVideoInfo.getFileMd5());
				pubVideoInfoMapper.deletePubVideoInfoByMD5(pubVideoInfo.getFileMd5());
			}
		}
	}


	/**
	 * 检查文件块是否已经存在
	 * @param fileBlockInfo
	 * @return
	 */
	private boolean checkBlockExist(FileBlockInfo fileBlockInfo) {
		String target = uploadLocation + "/" + fileBlockInfo.getMd5();
		Path path = Paths.get(target);
		if (!Files.exists(path)) {
			return false;
		}
		//上传的文件块存储路径为: 大文件的MD5/大文件的MD5_{index}
		target += "/" + fileBlockInfo.getMd5() + "_" + fileBlockInfo.getIndex();
		path = Paths.get(target);
		if (!Files.exists(path)) {
			return false;
		}

		return true;
	}


	/**
	 * 处理文件块上传
	 * @param fileBlockInfo
	 * @return
	 * @throws ZffException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Optional<Map<String, String>> upload(FileBlockInfo fileBlockInfo) throws ZffException, NoSuchAlgorithmException, IOException {
		//校验入参
		MultipartFile uploadFile = fileBlockInfo.getUploadFile();
		Map<String, String> errors = validUploadFile(uploadFile);
		if (!errors.isEmpty()) {
			//把map中的错误返给前端
			return Optional.ofNullable(errors);
		}

		// 检查该文件之前是否已经上传过
		PubVideoInfo pubVideoInfo = pubVideoInfoMapper.queryVideoInfoByMD5(fileBlockInfo.getMd5());
		//记录还不存在
		if (pubVideoInfo == null) {
			handleUploadBlock(fileBlockInfo);
			addUploadBlockToDB(fileBlockInfo);
		} else {
			//TODO!!
			//获取登录用户的用户名
			String currentUser = "liujun";
			//接收上传得block之前, 先检查一下之前是否上传过此文件
			//如果文件不存在, 但是数据库中有记录, 需要删除记录
			handleIfFileNotFound(pubVideoInfo, fileBlockInfo);

			Map<String, String> resp = new ConcurrentHashMap<>();
			//已经上传过了,并且已经完成了
			if (pubVideoInfo.getAchTag()) {
				//如果是当前用户上传过的, 直接返回
				if (pubVideoInfo.getUsername().equals(currentUser)) {
					resp.put(DOWNLOAD_URL_KEY, pubVideoInfo.getHashName());
					return Optional.of(resp);
				}

				//如果不是当前用户上传过的, 复制一条数据
				pubVideoInfo.setUsername(currentUser);
				pubVideoInfoMapper.insertPubVideoInfo(pubVideoInfo);
				resp.put(DOWNLOAD_URL_KEY, pubVideoInfo.getHashName());
			} else {
				//已完成的文件块的数目
				int parts = pubVideoInfo.getParts();
				//如果相等, 更新数据库
				if (parts == fileBlockInfo.getBlockCount()) {
					//更新为已完成
					pubVideoInfoMapper.setUplaoded(pubVideoInfo.getId());
					resp.put(DOWNLOAD_URL_KEY, pubVideoInfo.getHashName());
					return Optional.of(resp);
				} else {
					//如果不相等, 接收文件块
					//先检查一下当前文件块是否已经存在
					if (!checkBlockExist(fileBlockInfo)) {
						//如果文件块不存在, 接收文件块,并放在指定的目录下
						handleUploadBlock(fileBlockInfo);
						addUploadBlockToDB(fileBlockInfo);
					} else {
						//如果文件块已经存在, 检查一下文件块的md5是否跟上传的一致
						String target = uploadLocation + "/" + fileBlockInfo.getMd5() + "/" + fileBlockInfo.getMd5() + "_" + fileBlockInfo.getIndex();
						Path path = Paths.get(target);
						File blockFile = path.toFile();
						String currentBlockFileMD5 = getFileMD5(blockFile);

						if (!currentBlockFileMD5.equals(fileBlockInfo.getBlockMD5())) {
							handleUploadBlock(fileBlockInfo);
						}
					}

					//先去数据库查询一下已经上传完成的blocks
					List<PubVideoBlockInfo> uploadedBlocks = pubVideoBlockInfoMapper.queryPubVideoBlocksByMD5(pubVideoInfo.getFileMd5());
					List<Integer> indexes = new ArrayList<>();
					uploadedBlocks.forEach((block) -> {
						indexes.add(block.getBlockIndex());
					});

					//如果当前上传的index在数据库中不存在
					if (!indexes.contains(fileBlockInfo.getIndex())) {
						addUploadBlockToDB(fileBlockInfo);
					}
				}
			}
		}


		Map<String, String> resp = new ConcurrentHashMap<>();
		return Optional.ofNullable(resp);
	}

	@Override
	public void mergeChunks(VideoMergeReqDTO videoMergeReqDTO) throws IOException {
		handleUploadedFile(videoMergeReqDTO);
		handleMergeFile(videoMergeReqDTO);
	}

	private void handleMergeFile(VideoMergeReqDTO videoMergeReqDTO) throws IOException {
		String fileExtension = getFileExtension(videoMergeReqDTO.getFileMimeType());
		Path srcPath = Paths.get(uploadLocation + "/" + videoMergeReqDTO.getMd5());
		Pattern pattern = Pattern.compile(videoMergeReqDTO.getMd5() + "_\\d+");
		List<Path> fileNames = Files.list(srcPath)
				.filter(p -> Files.isRegularFile(p))
				.filter(p -> pattern.asPredicate().test(p.getFileName().toString()))
				.collect(Collectors.toList());

		String target = uploadLocation + "/" + videoMergeReqDTO.getMd5() + "/" + videoMergeReqDTO.getMd5() + "." + fileExtension;
		Path destPath = Paths.get(target);

		try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(destPath.toFile()))) {
			for (Path fileName : fileNames
					) {
				try (BufferedInputStream intputStream = new BufferedInputStream(Files.newInputStream(fileName))) {
					byte[] flush = new byte[1024 * 1000];
					int len = 0;
					while ((len = intputStream.read(flush)) != -1) {
						outputStream.write(flush, 0, len);
						outputStream.flush();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private String getFileExtension(String fileMimeType) {
		//TODO!
		return "mp4";
	}


	private void handleUploadedFile(VideoMergeReqDTO videoMergeReqDTO) {
		PubVideoInfo pubVideoInfo = new PubVideoInfo();
		pubVideoInfo.setUsername(videoMergeReqDTO.getUsername());
		pubVideoInfo.setAchTag(true);
		pubVideoInfo.setFileMd5(videoMergeReqDTO.getMd5());
		pubVideoInfo.setFileMimeType(videoMergeReqDTO.getFileMimeType());
		pubVideoInfo.setParts(videoMergeReqDTO.getParts());
		int current = Instant.now().getNano();
		pubVideoInfo.setHashName(String.valueOf(current));
		pubVideoInfoMapper.insertPubVideoInfo(pubVideoInfo);
	}


	/**
	 * 返回这一文件的md5值
	 *
	 * @param uploadFile
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	private String getFileMD5(File uploadFile) throws NoSuchAlgorithmException, IOException {

		//生成MD5
		//生成MessageDigest实例
		MessageDigest md = MessageDigest.getInstance("MD5");
		//拿到文件的字节流
		byte[] fileBytes = Files.readAllBytes(uploadFile.toPath());
		//获取hash值
		byte[] hashBytes = md.digest(fileBytes);
		//转换hash值为十六进制值
		StringBuilder sb = new StringBuilder();
		for (byte b : hashBytes) {
			sb.append(String.format("%02x", b));
		}
		String fileMd5 = sb.toString();

		// Print the hashed text
		if (isDebug) {
			log.debug("文件的MD5值： {}", fileMd5);
		}

		return fileMd5;
	}

	//文件信息赋值
	private PubVideoInfo setFileInfo(MultipartFile uploadFile) throws NoSuchAlgorithmException, IOException {
		/**
		 * 功能描述:
		 *
		 * @param: [uploadFile]  获取文件信息的那个文件
		 * @return: com.proaimltd.web.video.model.entity.PubVideoInfo
		 * @auther: proaim
		 * @date: 2019/9/25 15:41
		 */
		PubVideoInfo pubVideoInfo = new PubVideoInfo();
		String hash = UUID.randomUUID().toString();
		String orgName = uploadFile.getOriginalFilename();
		pubVideoInfo.setHashName(hash);
		pubVideoInfo.setOriginName(uploadFile.getOriginalFilename());
		String userName = "zff";
		pubVideoInfo.setUsername(userName);
		pubVideoInfo.setFileMimeType(getFileMimeType(uploadFile.getInputStream()));
		//获取文件md5值
//		String fileMd5 = ;
		//文件MD5的值从前端拿过来---放进实体类中
//		pubVideoInfo.setFileMd5(fileMd5);

		return pubVideoInfo;
	}




	//文件是否有效检查方法
	private Map<String, String> validUploadFile(MultipartFile uploadFile) {
		/**
		 * 功能描述:
		 *
		 * @param: [uploadFile] 要上传的文件
		 * @return: java.util.Map<java.lang.String, java.lang.String>  map容器存放发现的错误
		 * @auther: proaim
		 * @date: 2019/9/25 17:55
		 */
		Map<String, String> map = new HashMap<>();
		//size check
		//判断上传文件大小有没有超过限制
		//Locale
		long fileSize = uploadFile.getSize();
		if (fileSize == 0) {
			map.put("", "");
		}
		if (fileSize > videoFileMaxSize) {
			//Enum
			map.put("sizeCheck", "false");
//			throw new InvalidParameterException("Upload file is more than " + videoFileMaxSize / 1000 / 1000);
		}
		//mimeType check
		String filename = uploadFile.getOriginalFilename();
		if (StringUtils.isBlank(filename)) {
			throw new InvalidParameterException("Upload file size is zero.");
		}
		//apache Tika
		String typeFile = "";
		try {
			typeFile = this.getFileMimeType(uploadFile.getInputStream());
			if (isDebug) {
				log.debug("file type is: {}", typeFile);
			}
			if (!allowedMimeTypes.contains(typeFile)) {
				if (isDebug) {
					log.debug("file type is not match");
				}
				map.put("typeCheck", "不是指定的文件类型");
			}

		} catch (IOException e) {
			map.put("typeCheck", typeFile);
			e.printStackTrace();
		}
		if (isDebug) {
			log.debug("allowed file type which is uploaded are: {}", allowedMimeTypes);
		}
		return map;
	}

	/**
	 * 获取文件的MIME 类型
	 *
	 * @param inputStream
	 * @return 类型名称
	 * @throws IOException
	 */
	private String getFileMimeType(InputStream inputStream) throws IOException {
		Tika tika = new Tika();
		return tika.detect(inputStream);
	}
}
