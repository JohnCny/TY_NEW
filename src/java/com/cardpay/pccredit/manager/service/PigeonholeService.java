package com.cardpay.pccredit.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.manager.dao.PigeonholeDao;

@Service
public class PigeonholeService {
	@Autowired
	private PigeonholeDao pDao;

}
