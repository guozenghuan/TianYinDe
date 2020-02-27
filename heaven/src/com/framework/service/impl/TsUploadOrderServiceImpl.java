package com.framework.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.dao.TsItemDao;
import com.framework.dao.TsItemsAndTotalDao;
import com.framework.dao.TsItemsDao;
import com.framework.dao.TsOrderDao;
import com.framework.entity.TsItemEntity;
import com.framework.entity.TsItemsAndTotalEntity;
import com.framework.entity.TsOrderEntity;
import com.framework.service.TsUploadOrderService;
import com.framework.utils.DateUtils;

/**
 * 提交订单
 */
@Service
public class TsUploadOrderServiceImpl implements TsUploadOrderService{
	@Autowired
	private TsOrderDao tsOrderDao;
	@Autowired
	private TsItemDao tsItemDao;
	@Autowired
	private TsItemsAndTotalDao tsItemsAndTotalDao;

	@Override
	public boolean deleteUploadOrder(Long tsUserId, String number) {
		boolean b = true;
		boolean o = true;
		boolean l = true;
		tsItemDao.deleteByUN(tsUserId,number);
		tsOrderDao.deleteByUN(tsUserId,number);
		tsItemsAndTotalDao.deleteByUN(tsUserId,number);
		
		return b&&o&&l;
	}

	@Override
	public Map<String, Object> infoOrder(Long tsUserId, String number) {
		Map<String, Object> map = new HashMap<>(); 
	
		return map;
	}

	@Override
	public void uploadOrder(TsItemEntity tsItems, TsOrderEntity tsOrder, TsItemsAndTotalEntity itemsAndTotal) {
		//新增提交订单
		Date date = new Date();
		DateUtils dateu = new DateUtils();
		String ceratetime = dateu.format(date, "yyyy-MM-dd HH:mm");
		tsItems.setCreatetime(ceratetime );
		tsOrder.setCreatetime(date);
		itemsAndTotal.setCreatetime(ceratetime);
		
		if(tsOrder.getId() == null){
			tsOrderDao.save(tsOrder);
			//tsItemsDao.save(tsItems);
			tsItemsAndTotalDao.save(itemsAndTotal);
		} else {
		//更新提交的表单
			tsOrderDao.update(tsOrder);
			//tsItemsDao.update(tsItems);
			tsItemsAndTotalDao.update(itemsAndTotal);
		};
		
	}

}












