package com.framework.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.dao.TsItemDao;
import com.framework.dao.TsItemsAndTotalDao;
import com.framework.dao.TsOrderDao;
import com.framework.dao.TsReportOrderDao;
import com.framework.entity.TsReportOrderEntity;
import com.framework.service.TsReportOrderService;

/**
 * 提交订单
 * @author GZH
 *
 */
@Service
public class TsReportOrderServiceImpl implements TsReportOrderService{
	@Autowired
	private TsReportOrderDao tsReportOrderDao;
	@Autowired
	private TsOrderDao tsOrderDao;
	@Autowired 
	private TsItemDao tsItemDao;
	@Autowired
	private TsItemsAndTotalDao tsItemsAndTotalDao;
	
	@Override
	public List<TsReportOrderEntity> queryOrderList(Long tsUserId, Integer limit, Integer page) {
		//业务员查询审核订单
		//orderList 
		List<TsReportOrderEntity> orderList 
	    = tsReportOrderDao.queryOrderList(tsUserId,limit, page);
		return orderList;
	}

	@Override
	public int queryOrderTotalByUserId(Long tsUserId) {	
		return tsReportOrderDao.queryOrderTotalByUserId(tsUserId);
	}

	@Override
	public void deleteReportOrder(Long tsUserId, String number) {
		tsReportOrderDao.deleteByUN(tsUserId,number);
		tsOrderDao.deleteByUN(tsUserId, number);
		tsItemDao.deleteByUN(tsUserId, number);
		tsItemsAndTotalDao.deleteByUN(tsUserId, number);
		
	}

	@Override
	public void checkReportOrder(Long tsUserId, String number, Long status) {
		tsReportOrderDao.updateCheckStatus(tsUserId,number,status);
	}

	@Override
	public List<TsReportOrderEntity> queryOrderLists(Long tsUserId, 
			Integer limit, Integer page) {
		return tsReportOrderDao.queryOrderLists(limit, page);
	}

	@Override
	public int queryOrderTotalByTsUser() {	
		return tsReportOrderDao.queryOrderTotalByTsUser();
	}

	@Override
	public List<TsReportOrderEntity> queryOrderListNoCheck
	( Integer limit, Integer page) {

		return tsReportOrderDao.queryOrderListNoCheck(limit,page);
	}
	
	@Override
	public List<TsReportOrderEntity> queryOrderListCheck
	(Integer limit, Integer page) {

		return tsReportOrderDao.queryOrderListCheck(limit,page);
	}

	@Override
	public int queryOrderTotalByStatuOne() {

		return tsReportOrderDao.queryOrderTotalByStatuOne();
	}

	@Override
	public int queryOrderTotalByStatuTwo() {

		return tsReportOrderDao.queryOrderTotalByStatuTwo();
	}

	@Override
	public int queryCheckPageByUN(Long tsUserId, String number) {
		
		return tsReportOrderDao.queryCheckPageByUN(tsUserId,number);
	}
}
