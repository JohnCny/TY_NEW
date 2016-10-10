package com.cardpay.pccredit.intopieces.dao;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.intopieces.filter.CustomerApplicationInfoFilter;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface CustomerApplicationInfoDao {
    int deleteByPrimaryKey(String id);

    int insert(CustomerApplicationInfo record);

    int insertSelective(CustomerApplicationInfo record);

    CustomerApplicationInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CustomerApplicationInfo record);

    int updateByPrimaryKey(CustomerApplicationInfo record);
    
    /*
     * 查询符合条件的记录数
     */
    public int findCountByFilter(CustomerApplicationInfoFilter filter);
    /**
     * 统计进件条数
     * @param status
     * @return
     */
    public int findCustomerApplicationInfoCount(@Param("userId") String userId,@Param("status1") String status,@Param("status2") String status2);
}