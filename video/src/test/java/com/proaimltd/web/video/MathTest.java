package com.proaimltd.web.video;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @project: springboot-demo
 * @packageName: com.proaimltd.web.video
 * @author: fengkun.zhao
 * @date: 2019/9/26 10:12
 * @description：TODO
 */
@Slf4j
public class MathTest {
	@Test
	@Ignore
	public void testCeil() throws IOException {
		final double BLOCK_SIZE = 1_000_000;//1M
		Path path = Paths.get("D:\\Temp File\\Java学习笔记\\nezha.mp4");
		FileInputStream is = new FileInputStream("D:\\Temp File\\Java学习笔记\\nezha.mp4");
//		File file = new File("D:\\Temp File\\Java学习笔记\\nezha.mp4");
		FileChannel fc = is.getChannel();
		double size = fc.size();//文件大小
		int testSize = (int) Math.ceil(size / BLOCK_SIZE);
		Assert.assertEquals(4, testSize);
	}
}
