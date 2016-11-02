package com.cardpay.pccredit.intopieces.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.cardpay.pccredit.intopieces.dao.VideoAccessoriesDao;
import com.cardpay.pccredit.intopieces.model.VideoAccessories;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;

public class VideoAccessoriesService {

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private VideoAccessoriesDao videoAccessoriesDao;

	public void save(VideoAccessories videoAccessories) {
		commonDao.insertObject(videoAccessories);
	}
}
