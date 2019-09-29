package com.proaimltd.web.video.model.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @project: springboot-demo
 * @packageName: com.proaimltd.web.video.model.entity
 * @author: Administrator
 * @date: 2019/9/27 20:23
 * @description：TODO
 *                     // form.set('name', file.name);
// form.set('total', blockCount);
// form.set('index', i);
// form.set('size', file.size);
// form.set('hash', hash);
 */

@Data
public class FileBlockInfo {
	private MultipartFile uploadFile;
	private String md5;
	private String originFileName;
	private int blockCount;
	private String blockMD5;
	private int index;
	private double fileSize;
}
