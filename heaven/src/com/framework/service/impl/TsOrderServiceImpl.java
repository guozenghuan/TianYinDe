package com.framework.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.dao.TsOrderDao;
import com.framework.dao.TsReportOrderDao;
import com.framework.dao.TyOrderServiceDao;
import com.framework.entity.TsOrderEntity;
import com.framework.entity.TsReportOrderEntity;
import com.framework.entity.TyOrderServiceEntity;
import com.framework.service.TsOrderService;
import com.framework.service.TsReportOrderService;

@Service
public class TsOrderServiceImpl implements TsOrderService{
	@Autowired
	private TsOrderDao tsOrderDao;
	@Autowired
	private TsReportOrderDao tsReportOrderDao;
//	@Autowired 
//	private TyOrderServiceDao tyOrderServiceDao;
	
	@Override
	public void save(TsOrderEntity order) {
		//保存订单头信息
		tsOrderDao.save(order);
		
		//生成审核订单列表
		
		//业务员Id  tsUserId;
		//用户ID userId;
		//单号   number;  
		TsReportOrderEntity tsReportOrderEntity = new TsReportOrderEntity();
		tsReportOrderEntity.setTsUserId(order.getTsUserId());
		tsReportOrderEntity.setUserId(order.getUserId());
		tsReportOrderEntity.setNumber(order.getNumber());
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		tsReportOrderEntity.setOrdertime(sdf.format(date));
		tsReportOrderEntity.setTotal(order.getTotal());
		tsReportOrderEntity.setStatus((long) 2);//未审核
		/*
		TyOrderServiceEntity tyOrderServiceEntity = new TyOrderServiceEntity();
		tyOrderServiceEntity.setNumber(order.getNumber());
		tyOrderServiceEntity = (TyOrderServiceEntity) tyOrderServiceDao.queryObjectByKey(tyOrderServiceEntity);
		System.out.println("tyOrderSer:"+tyOrderServiceEntity.getCreatetime());
		tsReportOrderEntity.setOrdertime(sdf.format(tyOrderServiceEntity.getCreatetime()));
		*/
		tsReportOrderDao.save(tsReportOrderEntity);
	}

	@Override
	public TsOrderEntity info(Long tsUserId, String number) {
		
		return tsOrderDao.queryObjectByUN(tsUserId,number);
	}

	@Override
	public int queryPageByUN(Long tsUserId, String number) {
		
		return tsOrderDao.queryPageByUN(tsUserId,number);
	}

	@Override
	public void update(TsOrderEntity order) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		tsOrderDao.update(order);
		TsReportOrderEntity tsReportOrderEntity = new TsReportOrderEntity();
		tsReportOrderEntity.setOrdertime(sdf.format(date));
		tsReportOrderEntity.setTotal(order.getTotal());
		tsReportOrderDao.updateOrder(tsReportOrderEntity.getOrdertime(),order.getTotal(),
		order.getTsUserId(),order.getNumber());
		
	}
	
}
