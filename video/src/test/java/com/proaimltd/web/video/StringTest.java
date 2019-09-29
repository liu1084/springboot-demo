package com.proaimltd.web.video;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * @project: springboot-demo
 * @packageName: com.proaimltd.web.video
 * @author: fengkun.zhao
 * @date: 2019/9/25 23:32
 * @description：TODO
 */
@Slf4j
public class StringTest {
	@Test
	public void testStr() {
		String str1 = "I am ";
		String str2 = null;
		String str3 = str1 + str2;
		Assert.assertEquals("I am null", str3);
	}
}
