package com.cardpay.pccredit.jnpad.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.cardpay.pccredit.intopieces.web.LocalImageForm;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface JnpadImageBrowseDao {

	public List<LocalImageForm> findLocalImage(@Param("customerId") String id,@Param("productId") String productId);

	//删除图片
	public void deleteImage(@Param("id") String imageId);

	
	
}
