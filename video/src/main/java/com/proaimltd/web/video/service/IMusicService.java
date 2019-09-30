package com.proaimltd.web.video.service;

import com.proaimltd.web.video.model.dto.UploadMicRespDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @project: springboot-demo
 * @packageName: com.proaimltd.web.video.service
 * @author: fengkun.zhao
 * @date: 2019/9/12 18:33
 * @description：TODO
 */
public interface IMusicService {
	UploadMicRespDTO upload(MultipartFile musicFile);
}
