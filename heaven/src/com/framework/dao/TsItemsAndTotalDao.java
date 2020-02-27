package com.framework.dao;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Param;

import com.framework.entity.TsItemsAndTotalEntity;

public interface TsItemsAndTotalDao extends BaseDao<TsItemsAndTotalEntity>{

	void deleteByUN(@Param("tsUserId")Long tsUserId,@Param("number")String number);

	TsItemsAndTotalEntity queryObjectByUN(@Param("tsUserId")Long tsUserId,@Param("number")String number);

	//BigDecimal queryCostTotalSUN(@Param("number")String number);
	
	
	
}
