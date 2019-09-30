package com.proaimltd.web.video.model.mapper;

import com.proaimltd.web.video.model.entity.PubVideoBlockInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PubVideoBlockInfoMapper {
	int deletePubVideoBlockInfoByPrimaryKey(Long id);

	int insertPubVideoBlockInfo(PubVideoBlockInfo record);

	int insertPubVideoBlockInfoSelective(PubVideoBlockInfo record);

	PubVideoBlockInfo selectPubVideoBlockInfoByPrimaryKey(Long id);

	int updatePubVideoBlockInfoByPrimaryKeySelective(PubVideoBlockInfo record);

	int updatePubVideoBlockInfoByPrimaryKey(PubVideoBlockInfo record);

	List<PubVideoBlockInfo> queryPubVideoBlocksByMD5(String md5);

	int deletePubVideoBlockInfoByMD5(String fileMd5);

	void updatePubVideoBlockInfoByMD5(@Param("md5") String md5, @Param("videoId") long videoId);
}