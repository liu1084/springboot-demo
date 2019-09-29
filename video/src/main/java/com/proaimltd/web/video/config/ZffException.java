package com.proaimltd.web.video.config;

import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.Errors;

import java.util.Map;
import java.util.Set;

/**
 * @project: springboot-demo
 * @packageName: com.proaimltd.web.video.config
 * @author: fengkun.zhao
 * @date: 2019/9/19 14:06
 * @description：TODO
 */

@Data
public class ZffException extends RuntimeException {

	private Integer code;

	private String message;

	@Setter(AccessLevel.PRIVATE)
	@Getter(AccessLevel.PRIVATE)
	private Gson gson;

	public ZffException(IProStatus status) {
		this.code = status.getCode();
		this.message = status.getMessage();
	}

	public ZffException(IProStatus status, String message) {
		this.code = status.getCode();
		this.message = status.getMessage() + "Cause by: " + message;
	}

	public ZffException(Integer code, String message) {
		this.message = message;
		this.code = code;
	}

	public ZffException(Map<String, String> errors) {
		this.code = -1;
		this.message = gson.toJson(errors);
		// TODO!!
		// Set<String> keys = errors.keySet();

	}

	public ZffException(IProStatus status, Errors errors) {
	}
}
