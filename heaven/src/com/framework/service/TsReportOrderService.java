package com.framework.service;

import java.util.List;

import com.framework.entity.TsReportOrderEntity;

public interface TsReportOrderService {

	List<TsReportOrderEntity> queryOrderList(Long tsUserId, Integer limit, Integer page01);

	int queryOrderTotalByUserId(Long tsUserId);

	void  deleteReportOrder(Long tsUserId, String number);

	void checkReportOrder(Long tsUserId, String number, Long status);

	List<TsReportOrderEntity> queryOrderLists(Long tsUserId, Integer limit, Integer page01);

	int queryOrderTotalByTsUser();

	List<TsReportOrderEntity> queryOrderListNoCheck( Integer limit, Integer page01);

	List<TsReportOrderEntity> queryOrderListCheck( Integer limit, Integer page);

	int queryOrderTotalByStatuOne();

	int queryOrderTotalByStatuTwo();

	int queryCheckPageByUN(Long tsUserId, String number);
	
	

}
