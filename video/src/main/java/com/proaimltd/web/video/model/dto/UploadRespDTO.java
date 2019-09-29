package com.proaimltd.web.video.model.dto;


import lombok.Getter;
import lombok.Setter;

/**
 * @project: springboot-demo
 * @packageName: com.proaimltd.web.video.model.entity
 * @author: fengkun.zhao
 * @date: 2019/9/10 15:08
 * @description：TODO
 */

@Getter
@Setter
//public class UploadRespDTO extends PubVideoInfo implements Serializable {
public class UploadRespDTO {
	private boolean isSuccess;
	private String hashName;
	private String downloadUrl;
	private String msg;

	public UploadRespDTO(boolean isSuccess) {
		this.isSuccess = isSuccess;
		if (isSuccess != true) {
			System.out.println("文件上传失败");
		}
	}

	public UploadRespDTO(boolean isSuccess, String hashName, String downloadUrl,String msg) {
		this.isSuccess = isSuccess;
		this.hashName = hashName;
		this.downloadUrl = downloadUrl;
		this.msg = msg;
	}

	public UploadRespDTO() {

	}
}
