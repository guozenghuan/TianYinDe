package com.framework.dao;

import org.apache.ibatis.annotations.Param;

import com.framework.entity.TsOrderEntity;

public interface TsOrderDao extends BaseDao<TsOrderEntity>{

	void deleteByUN(@Param("tsUserId")Long tsUserId,@Param("number")String number);

	TsOrderEntity queryObjectByKey(Long id);

	TsOrderEntity queryObjectByUN(@Param("tsUserId")Long tsUserId, 
	@Param("number")String number);

	TsOrderEntity info(Long tsUserId, String number);

	void  orderNumb(Long tsUserId, String number);

	boolean saveOrder(TsOrderEntity order);
   
	void save(TsOrderEntity order);

	int queryPageByUN(@Param("tsUserId")Long tsUserId, @Param("number")String number);

	TsOrderEntity queryObjectByKey(@Param("number")String number);
}
