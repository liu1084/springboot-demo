package com.proaimltd.web.video.service;

import com.proaimltd.web.video.config.ZffException;
import com.proaimltd.web.video.model.entity.FileBlockInfo;
import com.proaimltd.web.video.model.entity.dto.VideoMergeReqDTO;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Optional;

public interface IVideoService {
	Optional<Map<String, String>> upload(FileBlockInfo fileBlockInfo) throws ZffException, NoSuchAlgorithmException, IOException;

	void mergeChunks(VideoMergeReqDTO videoMergeReqDTO) throws IOException;
}