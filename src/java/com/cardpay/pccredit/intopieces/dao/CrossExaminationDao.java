package com.cardpay.pccredit.intopieces.dao;

import com.cardpay.pccredit.intopieces.model.CrossExamination;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface CrossExaminationDao {
    int deleteByPrimaryKey(Long id);

    int insert(CrossExamination record);

    int insertSelective(CrossExamination record);

    CrossExamination selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CrossExamination record);

    int updateByPrimaryKey(CrossExamination record);
}