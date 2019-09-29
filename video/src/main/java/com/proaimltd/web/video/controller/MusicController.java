package com.proaimltd.web.video.controller;

import com.proaimltd.web.video.model.dto.UploadMicRespDTO;
import com.proaimltd.web.video.service.IMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.MessageUtils;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Optional;

/**
 * @project: springboot-demo
 * @packageName: com.proaimltd.web.video.controller
 * @author: fengkun.zhao
 * @date: 2019/9/12 18:42
 * @description：TODO
 */
@Controller
@RequestMapping("/music")
public class MusicController {
	public static final String uploadingDir = System.getProperty("user.dir") + "/uploadingMusicDir/";

	@Value("${music.file.maxSize}")
	private long musicFileMaxSize;

	@Autowired
	private IMusicService musicService;

	@GetMapping
	public String upload(){
		return("upload");
	}

	@PostMapping(value = "/uploadmusic")
	@ResponseBody
	public UploadMicRespDTO upload(@RequestParam("musicfile") MultipartFile musicFile) {

		long fileSize = musicFile.getSize();
		if (fileSize > musicFileMaxSize) {
			throw new InvalidParameterException("Upload file is more than " + musicFileMaxSize / 1000/ 1000);
		}

		//空指针解决方案Optional
		Optional<MultipartFile> optionalMultipartFile = Optional.ofNullable(musicFile);
		optionalMultipartFile.ifPresent((file)->{
			UploadMicRespDTO musicres = musicService.upload(file);
		});


		return null;
	}

}
