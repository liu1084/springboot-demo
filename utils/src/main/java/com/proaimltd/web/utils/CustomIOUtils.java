package com.proaimltd.web.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

/**
 * @project: springboot-demo
 * @packageName: com.proaimltd.web.utils
 * @author: fengkun.zhao
 * @date: 2019/9/19 15:09
 * @description：TODO
 */
public final class CustomIOUtils {
	public static void inputStreamToFile(InputStream ins, File file) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
			int len = 0;
			byte[] buffer = new byte[8192];
			while ((len = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, len);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null)
					os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (ins != null)
					ins.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	public static void inputStreamToFileWithSlice(InputStream ins, File file) {
		final int BLOCK_SIZE = 1_000_000;
		int partCounter = 1;
		Instant current = Instant.now(); //UTC
		//LinkedList<String> files = new LinkedList<>();
		final String filename = String.valueOf(current.getEpochSecond());
		Path path = Paths.get("C:\\Users\\proaim\\Desktop\\任务\\nezha.mp4");
		try (BufferedInputStream inputStream = new BufferedInputStream(Files.newInputStream(path))) {
			byte[] flush = new byte[BLOCK_SIZE];
			int len = 0;
			while ((len = inputStream.read(flush)) > 0) {
				String destName = String.format("%s_%03d", filename, partCounter++);
				Path dest = Paths.get("C:\\Users\\proaim\\Desktop\\任务\\" + destName);
				try (BufferedOutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(dest))) {
					outputStream.write(flush, 0, len);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
