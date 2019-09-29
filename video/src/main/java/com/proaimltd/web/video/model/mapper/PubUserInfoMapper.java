package com.proaimltd.web.video.model.mapper;

import com.proaimltd.web.video.model.entity.PubUserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PubUserInfoMapper {
    int insert(PubUserInfo record);

    int insertSelective(PubUserInfo record);
}