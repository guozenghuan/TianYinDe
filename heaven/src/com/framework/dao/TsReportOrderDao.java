package com.framework.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.framework.entity.TsReportOrderEntity;

public interface TsReportOrderDao extends BaseDao<TsReportOrderEntity>{

	List<TsReportOrderEntity> queryOrderList(Long tsUserId, Integer limit, Integer page);

	void deleteByUN(@Param("tsUserId")Long tsUserId, @Param("number")String number);

	List<TsReportOrderEntity> queryOrderLists(@Param("limit")Integer limit, @Param("page")Integer page);

	List<TsReportOrderEntity> queryOrderListNoCheck(@Param("limit")Integer limit, @Param("page")Integer page);


	int queryOrderTotalByStatuOne();

	List<TsReportOrderEntity> queryOrderListCheck(@Param("limit")Integer limit, @Param("page")Integer page);

	int queryOrderTotalByStatuTwo();

	int queryOrderTotalByTsUser();

	int queryOrderTotalByUserId(Long tsUserId);

	void updateCheckStatus(Long tsUserId, String number, Long status);
	
	int queryCheckPageByUN(@Param("tsUserId")Long tsUserId,@Param("number") String number);
	
	void save(TsReportOrderEntity tsReportOrderEntity);

	//void updateOrder(@Param("tsUserId")Long tsUserId, @Param("number")String number);

	void updateOrder(@Param("ordertime")String  ordertime,@Param("total") BigDecimal total, 
			@Param("tsUserId")Long tsUserId, @Param("number")String number);

	TsReportOrderEntity queryObjectByUN(@Param("tsUserId")Long tsUserId, @Param("number")String number);
	
}



















