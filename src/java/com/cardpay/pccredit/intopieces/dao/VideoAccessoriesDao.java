package com.cardpay.pccredit.intopieces.dao;

import com.cardpay.pccredit.intopieces.model.VideoAccessories;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface VideoAccessoriesDao {
    int deleteByPrimaryKey(Long id);

    int insert(VideoAccessories record);

    int insertSelective(VideoAccessories record);

    VideoAccessories selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VideoAccessories record);

    int updateByPrimaryKey(VideoAccessories record);
}