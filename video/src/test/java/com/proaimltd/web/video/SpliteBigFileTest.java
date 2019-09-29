package com.proaimltd.web.video;

import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.LinkedList;

/**
 * @project: java-core
 * @packageName: com.jim.java.core.io
 * @author: Administrator
 * @date: 2019/9/24 13:34
 * @description：TODO
 */
public class SpliteBigFileTest {
	@Test
	public void testSpliteBigFile() throws IOException, NoSuchAlgorithmException {

		final int BLOCK_SIZE = 1_000_000;//分片上传每次的文件大小为1M;
		int partCounter = 1;
		Instant current = Instant.now(); //UTC
		LinkedList<String> files = new LinkedList<>();
		final String filename = String.valueOf(current.getEpochSecond());
		Path path = Paths.get("C:\\Users\\proaim\\Desktop\\任务\\nezha.mp4");
		try (BufferedInputStream inputStream = new BufferedInputStream(Files.newInputStream(path))) {
			byte[] flush = new byte[BLOCK_SIZE];
			int len = 0;
			while ((len = inputStream.read(flush)) > 0) {
				String destName = String.format("%s_%03d", filename, partCounter++);
				//files.add(destName);
				Path dest = Paths.get("C:\\Users\\proaim\\Desktop\\任务\\" + destName);
				try (BufferedOutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(dest))) {
					outputStream.write(flush, 0, len);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
