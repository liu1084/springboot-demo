package com.proaimltd.web.video;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @project: springboot-demo
 * @packageName: com.proaimltd.web.video
 * @author: fengkun.zhao
 * @date: 2019/9/26 14:34
 * @description：TODO
 */
@Slf4j
public class Md5Test {

	File largeFile = new File("C:\\Users\\proaim\\Desktop\\任务\\第11期 经典翻唱回忆杀！决赛冲刺夜全国十一强诞生_超清.mp4");
	File smallFile = new File("C:\\Users\\proaim\\Desktop\\任务\\经典老歌唱不停！好声音勾起满满回忆杀_标清.mp4");

	@Test
//	public void testMd5(MultipartFile uploadFile) throws NoSuchAlgorithmException, IOException {
	public void testMd5(File uploadFile) throws NoSuchAlgorithmException, IOException {
		//生成MD5
		//生成MessageDigest实例
		MessageDigest md = MessageDigest.getInstance("MD5");
		//拿到文件的字节流
		byte[] fileBytes = fileToByteArray(uploadFile);
//		byte[] fileBytes = uploadFile.getBytes();
		//获取hash值
		byte[] hashBytes = md.digest(fileBytes);
		//转换hash值为十六进制值
		StringBuilder sb = new StringBuilder();
		for (byte b : hashBytes) {
			sb.append(String.format("%02x", b));
		}
		String fileMd5 = sb.toString();
		System.out.println(fileMd5);
	}



//	private String getFileMD5(MultipartFile uploadFile) throws NoSuchAlgorithmException, IOException {
//		/**
//		 * 功能描述:
//		 *
//		 * @param: [uploadFile]  要上传的文件
//		 * @return: java.lang.String   返回这一文件的md5值
//		 * @auther: proaim
//		 * @date: 2019/9/25 17:53
//		 */
//		long fileSize = uploadFile.getSize();
//		final int BLOCK_SIZE = 10_000_000;//若文件过大，我们找到前面的10M，生成一个md5的checksum值
//		int partSum;
//
//		//生成MD5
//		//生成MessageDigest实例
//		MessageDigest md = MessageDigest.getInstance("MD5");
//		//拿到文件的字节流
//		byte[] fileBytes = uploadFile.getBytes();
//		//获取hash值
//		byte[] hashBytes = md.digest(fileBytes);
//		//转换hash值为十六进制值
//		StringBuilder sb = new StringBuilder();
//		for (byte b : hashBytes) {
//			sb.append(String.format("%02x", b));
//		}
//		String fileMd5 = sb.toString();
//
//		// Print the hashed text
//		if (isDebug) {
//			log.debug("文件的MD5值： {}", fileMd5);
//		}
//
//		return fileMd5;
//	}

//	if(fileSize>100_000_000){
//			partSum = (int)Math.ceil(fileSize/BLOCK_SIZE);
//			//生成前10M的文件的Md5值
//
//		}else {
//			//生成MD5
//			//拿到文件的字节流
//			byte[] fileBytes = uploadFile.getBytes();
//			//获取hash值
//			byte[] hashBytes = md.digest(fileBytes);
//			//转换hash值为十六进制值
//			StringBuilder sb = new StringBuilder();
//			for (byte b : hashBytes) {
//				sb.append(String.format("%02x", b));
//			}
//			String fileMd5 = sb.toString();
//			return fileMd5;
//		}
//
//		//多线程--->前端分片
//
////		 Print the hashed text
//		if (isDebug) {
////			log.debug("文件的MD5值： {}", fileMd5);
//		}
////
//		return fileMd5;



	//文件转换到字节数组方法
	public static  byte[] fileToByteArray(File file){
		//1.创建源头与目的地
		File src = file;
		byte[] dest = null;
		//2.选择流
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try {
			is = new FileInputStream(src);
			baos = new ByteArrayOutputStream();
			//3.操作（分段读取）
			byte[] flush = new byte[1024*100];//缓冲容器--->一般都是1024个字节这样读取
			int len = -1;//接收长度
			while ((len = is.read(flush))!=-1){
				baos.write(flush,0,len);//写出到字节数组中
			}
			baos.flush();
			return baos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(null != is){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
