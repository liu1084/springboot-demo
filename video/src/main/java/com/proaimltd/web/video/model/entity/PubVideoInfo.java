package com.proaimltd.web.video.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class PubVideoInfo {
	private Long id;

	private String originName;

	private String hashName;

	private String username;

	private String fileMimeType;

	private Boolean achTag;

	private String fileMd5;

	private Integer parts;

	private Date createTime;

	private Date updateTime;

	private Boolean isDeleted;

}