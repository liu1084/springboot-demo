package com.proaimltd.web.video.config;

import org.apache.commons.lang3.StringUtils;

/**
 * @project: springboot-demo
 * @packageName: com.proaimltd.web.video.config
 * @author: fengkun.zhao
 * @date: 2019/9/19 13:55
 * @description：TODO
 */

public enum EnumVideo implements IProStatus {
	// 用户列表返回失败
	EXCEPTION_VIDEO_NOT_MATCH(1000, "不是指定的文件类型"),
	FAILED_VIDEO_FILE_UPLOAD(1001, "上传文件失败"),
	;

	/**
	 * 错误代码
	 */
	private Integer code;

	/**
	 * 错误信息
	 */
	private String message;

	/**
	 * 构造器
	 * @param code
	 * @param message
	 */
	EnumVideo(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public static String getMessageByCode(String code) {
		for (EnumVideo item : EnumVideo.values()) {
			if (code.equals(item.code)) {
				return item.message;
			}
		}
		return null;
	}

	public static Integer getCodeByMessage(String message) {
		for (EnumVideo item : EnumVideo.values()) {
			if (StringUtils.equals(message, item.message)) {
				return item.code;
			}
		}
		return null;
	}

	@Override
	public Integer getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
}
