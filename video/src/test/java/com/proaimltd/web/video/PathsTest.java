package com.proaimltd.web.video;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;


/**
 * @project: springboot-demo
 * @packageName: com.proaimltd.web.video
 * @author: fengkun.zhao
 * @date: 2019/9/25 22:51
 * @description：测试JDK8的新特性，Paths和Files的使用方法
 */
@Slf4j
public class PathsTest {
	@Test
	public void testPaths() throws IOException {
		LinkedList<String> fileNames = new LinkedList<>();
		//创建源
		Path path = Paths.get("D:\\Temp File\\Java学习笔记\\nezha.mp4");
		try {
			//选择流
			BufferedInputStream inputStream = new BufferedInputStream(Files.newInputStream(path));
			//写操作
			byte[] flush = new byte[1024 * 1000];
			int parts = 1;
			int len = 0;
			String fileName = path.getFileName().toString();
			while ((len = inputStream.read(flush)) != -1) {
				String destName = String.format("%s_%03d", fileName, parts++);
				fileNames.add(destName);
				Path dest = Paths.get("D:\\Temp File\\Java学习笔记\\", destName);
				try (BufferedOutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(dest))) {
					outputStream.write(flush, 0, len);
					outputStream.flush();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		//合成操作
		//创建源
//		Path destPath = Paths.get("D:\\Temp File\\Java学习笔记\\nezha1.mp4");

		File destPath = new File("D:\\Temp File\\Java学习笔记\\nezha1.mp4");
		if ( destPath.exists()) {
			destPath.delete();
		}
		//选择流
		//操作---循环读取


		//操作网络流  下载百度的源代码
//		BufferedWriter writer = new BufferedWriter(
//				new OutputStreamWriter(
//						new FileOutputStream(destPath), "UTF-8"));
//		for (String fileName : fileNames
//		) {
//			String srcPath = "D:\\Temp File\\Java学习笔记\\"+fileName;
//			try (BufferedReader reader = new BufferedReader(
//					new InputStreamReader(
//							new FileInputStream(srcPath), "GBK"));
//			) {
//				//操作--->读取
//				String msg;
//				while ((msg = reader.readLine()) != null) {
//					System.out.println(msg);
//					writer.write(msg);
//					writer.newLine();
//					writer.flush();
//				}
//			} catch (MalformedURLException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}

		//选择流
		//操作---循环读取
		try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(destPath))) {
			for (String fileName : fileNames
			) {
				Path srcPath = Paths.get("D:\\Temp File\\Java学习笔记\\" + fileName);
				try (BufferedInputStream intputStream = new BufferedInputStream(Files.newInputStream(srcPath))) {
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


		log.debug(String.valueOf(path.getName(1)));
		Assert.assertTrue("Java学习笔记".equals(path.getName(1).toString()));
		Assert.assertEquals(3, path.getNameCount());
		Assert.assertEquals("D:\\", path.getRoot().toString());


	}
}
