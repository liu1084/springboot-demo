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
	//上传的文件
	private MultipartFile uploadFile;
	//大文件的MD5
	private String md5;
	//大文件名称
	private String originFileName;
	//文件分片的数量
	private int blockCount;
	//每个分片的MD5
	private String blockMD5;
	//分片的Index, 合并文件的时候会按照此顺序进行
	private int index;
	//分片的大小
	private double fileSize;
}
