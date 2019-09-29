package com.proaimltd.web.video;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @project: springboot-demo
 * @packageName: com.proaimltd.web.video
 * @author: fengkun.zhao
 * @date: 2019/9/24 21:46
 * @description：TODO
 */

@Slf4j
public class RegExpTest {

	@Test
	public void testEmailRegExp() {
		Pattern pattern = Pattern.compile("zff");
		String name = "my name is zff.";
		Matcher matcher = pattern.matcher(name);
		if (matcher.find()) {
			log.debug("找到我的名字了。");
			String myName = matcher.replaceFirst("liujun");
			log.debug(myName);
		}

	}
}
