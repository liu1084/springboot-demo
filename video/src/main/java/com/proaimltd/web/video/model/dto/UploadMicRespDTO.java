package com.proaimltd.web.video.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @project: springboot-demo
 * @packageName: com.proaimltd.web.video.model.dto
 * @author: fengkun.zhao
 * @date: 2019/9/12 18:32
 * @description：TODO
 */
@Data
public class UploadMicRespDTO {
	private boolean isSuccess;
	private String hashName;
	private String downloadUrl;
}
