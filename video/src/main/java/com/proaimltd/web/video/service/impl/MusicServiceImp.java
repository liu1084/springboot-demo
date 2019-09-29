package com.proaimltd.web.video.service.impl;

import com.proaimltd.web.video.model.dto.UploadMicRespDTO;
import com.proaimltd.web.video.service.IMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @project: springboot-demo
 * @packageName: com.proaimltd.web.video.service.impl
 * @author: fengkun.zhao
 * @date: 2019/9/12 18:36
 * @description：TODO
 */


@Service
public class MusicServiceImp implements IMusicService {
	public static final String uploadingDir = System.getProperty("user.dir") + "/uploadingMusicDir/";

	//先应用Dao层
	@Autowired
	private PubMusicInfoMapper pubMusicInfoMapper;

	@Override
	public UploadMicRespDTO upload(MultipartFile musicFile) {
		//将文件写入本地
		File dest = new File(uploadingDir+musicFile.getOriginalFilename());
		try {
			musicFile.transferTo(dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void insert(PubMusicInfo musicInfo) {
		pubMusicInfoMapper.insert(musicInfo);
	}

}
