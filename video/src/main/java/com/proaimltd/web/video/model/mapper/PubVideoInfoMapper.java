package com.proaimltd.web.video.model.mapper;

import com.proaimltd.web.video.model.entity.PubVideoInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface PubVideoInfoMapper {
    int deletePubVideoInfoByPrimaryKey(Long id);

    int insertPubVideoInfo(PubVideoInfo record);

    int insertPubVideoInfoSelective(PubVideoInfo record);

    PubVideoInfo selectPubVideoInfoByPrimaryKey(Long id);

    int updatePubVideoInfoByPrimaryKeySelective(PubVideoInfo record);

    int updatePubVideoInfoByPrimaryKey(PubVideoInfo record);

    PubVideoInfo queryVideoInfoByMD5(String md5);

    int setUplaoded(Long id);

	int deletePubVideoInfoByMD5(String fileMd5);
}