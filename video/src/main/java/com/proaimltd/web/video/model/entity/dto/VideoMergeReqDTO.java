package com.proaimltd.web.video.model.entity.dto;

import lombok.Data;

/**
 * @project: springboot-demo
 * @packageName: com.proaimltd.web.video.model.entity.dto
 * @author: Administrator
 * @date: 2019/9/29 15:48
 * @description：TODO
 */

@Data
public class VideoMergeReqDTO {
	private String originFileName;
	private String md5;
	private String username;
	private String fileMimeType;
	private int parts;
	private long fileSize;
}
