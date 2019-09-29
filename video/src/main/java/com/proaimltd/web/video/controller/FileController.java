package com.proaimltd.web.video.controller;


import com.proaimltd.web.video.config.ZffException;
import com.proaimltd.web.video.model.entity.FileBlockInfo;
import com.proaimltd.web.video.model.entity.dto.VideoMergeReqDTO;
import com.proaimltd.web.video.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Optional;

/**
 * @project: springboot-demo
 * @packageName: com.proaimltd.web.video.controller
 * @author: fengkun.zhao
 * @date: 2019/9/10 15:04
 * @description：TODO
 */

@Controller
@RequestMapping(value = "/file")
@CrossOrigin("*")
public class FileController {

	@Autowired
	private IVideoService videoService;

	/**
	 * 此处没加@ResponseBody，就意味着是要返回一个HTML文件。
	 * 加上@ResponseBody，就意味着是返回一个响应体
	 * @return
	 */
	@GetMapping
	public String upload() {
		return "upload";
	}

	/**
	 * 上传文件
	 * @param uploadFile
	 * @return
	 */

	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public ResponseEntity<Map<String, String>> upload(@RequestParam("file") MultipartFile uploadFile,
													  @RequestParam String md5,
													  @RequestParam String originFileName,
													  @RequestParam int blockCount,
													  @RequestParam int index,
													  @RequestParam long fileSize) {
		try {
			FileBlockInfo fileBlockInfo = generateFileBlockInfo(uploadFile, md5, originFileName, blockCount, index, fileSize);
			Optional<Map<String, String>> resp = videoService.upload(fileBlockInfo);
			return ResponseEntity.of(resp);
		} catch (ZffException ex1) {
			ex1.printStackTrace();
			throw ex1;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private FileBlockInfo generateFileBlockInfo(MultipartFile uploadFile, String md5, String originFileName, int blockCount, int index, long fileSize) {
		FileBlockInfo fileBlockInfo = new FileBlockInfo();
		fileBlockInfo.setUploadFile(uploadFile);
		fileBlockInfo.setMd5(md5);
		fileBlockInfo.setOriginFileName(originFileName);
		fileBlockInfo.setBlockCount(blockCount);
		fileBlockInfo.setIndex(index);
		fileBlockInfo.setFileSize(fileSize);
		return fileBlockInfo;
	}

	@PostMapping(value = "/mergeChunks")
	@ResponseBody
	public ResponseEntity<Map<String, String>> mergeChunks(@RequestBody VideoMergeReqDTO videoMergeReqDTO) {
		videoService.mergeChunks(videoMergeReqDTO);
		return ResponseEntity.of(Optional.empty());
	}
}
