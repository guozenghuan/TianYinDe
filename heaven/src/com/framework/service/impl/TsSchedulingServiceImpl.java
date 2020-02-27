package com.framework.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.dao.TsSchedulingDao;
import com.framework.dao.TsSchedulingPaiDanDao;
import com.framework.dao.TyOrderServiceDao;
import com.framework.entity.TsSchedulingEntity;
import com.framework.entity.TsSchedulingPaiDanEntity;
import com.framework.entity.TyOrderServiceEntity;
import com.framework.service.TsSchedulingPaiDanService;
import com.framework.service.TsSchedulingService;
import com.framework.utils.R;

/**
 * 业务员排班
 * @author GZH
 */
@Service
public class TsSchedulingServiceImpl implements TsSchedulingService{
	@Autowired
	private TsSchedulingDao tsSchedulingDao;
	@Autowired
	private TsSchedulingPaiDanDao tsSchedulingPaiDanDao;
	@Autowired
	private TsSchedulingPaiDanService tsSchedulingPaiDanService;
	@Autowired
	private TyOrderServiceDao tyOrderServiceDao;
	
	@Override
	public int queryTotal(Map<String, Object> map) {
		return tsSchedulingDao.queryTotal(map);
	}

	@Override
	public void delete(Long tsUserId) {
		tsSchedulingDao.delete(tsUserId);
	}

	@Override
	public List<TsSchedulingEntity> queryListObject(Integer page, Integer limit) {

		return tsSchedulingDao.queryListObject(page,limit);
	}

	@Override
	public TsSchedulingEntity queryObjectByUserId(Long tsUserId) {
		return tsSchedulingDao.queryObjectByUserId(tsUserId);
	}

	@Override
	public List<TsSchedulingEntity> queryListByDate(String scheduling) {

		return tsSchedulingDao.queryListByDate(scheduling);
	}

	@Override
	public Long querySchedulingByDate(String scheduling) {

		return tsSchedulingDao.querySchedulingByDate(scheduling);
	}

	@Override
	public List<Long> queryListUserIdByDate(String dateStr) {

		return tsSchedulingDao.queryListUserIdByDate(dateStr);
	}

	@Override
	public List<TsSchedulingEntity> queryList(Map<String, Object> map) {

		return tsSchedulingDao.queryList(map);
	}

	@Override
	public int queryTotalAll() {

		return tsSchedulingDao.queryTotalAll();
	}

	@Override
	public void checkScheduling(Long tsUserId, String tsScheduling) {
		//校验今日值班
		TsSchedulingPaiDanEntity paiDanEntity = tsSchedulingPaiDanDao.queryObjectByUS(tsUserId,tsScheduling);
		int dateS = tsSchedulingPaiDanService.queryDate(tsScheduling); 
		if(dateS == 0) {
			tsSchedulingPaiDanService.deleteDate();
		}
		if(paiDanEntity == null){
			TsSchedulingPaiDanEntity paiDan = new TsSchedulingPaiDanEntity();
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			paiDan.setCreatetime(sdf.format(date));
			paiDan.setTsUserId(tsUserId);
			paiDan.setDate(tsScheduling);
			paiDan.setNumber(0);
			tsSchedulingPaiDanDao.savePaiDan(paiDan);	
		}
	}

	@Override
	public void checkSchedulingChange(Long tsUserId, String tsScheduling) {
		//校验今日值班
		tsSchedulingPaiDanDao.deleteById(tsUserId);
	}

	@Override
	public R addTsScheduling(TsSchedulingEntity schedulingEntity) {
		TsSchedulingEntity[]  scheduling = 
				tsSchedulingDao.queryListObjectByTS(schedulingEntity.getTsUserId());
		for(int i = 0 ; i < scheduling.length ; i++) {
			if(scheduling[i].getScheduling().compareTo(schedulingEntity.getScheduling())==0) {
				return R.error("值班日期重复！");
			};
		}

		if(scheduling.length == 1 && scheduling[0].getScheduling().compareTo("")==0) {
			scheduling[0].setScheduling(schedulingEntity.getScheduling());
			scheduling[0].setTsscheduling(schedulingEntity.getTsscheduling());
			scheduling[0].setCreatetime(schedulingEntity.getCreatetime());
			tsSchedulingDao.update(scheduling[0]);
			return R.ok();
		} else {
			schedulingEntity.setAccount(scheduling[0].getAccount());
			schedulingEntity.setCompany(scheduling[0].getCompany());
			schedulingEntity.setName(scheduling[0].getName());
			schedulingEntity.setPhone(scheduling[0].getPhone());
			schedulingEntity.setPortraitUrl(scheduling[0].getPortraitUrl());
			tsSchedulingDao.saveYWY(schedulingEntity);
		}
		return R.ok();
	}

	@Override
	public void update(Long id) {
		TsSchedulingEntity schedulingEntity = tsSchedulingDao.queryObjectByUserId(id);
		TsSchedulingEntity[] entities = 
				tsSchedulingDao.queryListObjectByTS(schedulingEntity.getTsUserId());
		if(entities.length == 1) {
			schedulingEntity.setScheduling("");
			schedulingEntity.setCreatetime(null);
			schedulingEntity.setTsscheduling(null);
			tsSchedulingDao.update(schedulingEntity);
		} else {
			tsSchedulingDao.deleteById(id);
		}

		tsSchedulingPaiDanDao.deleteById(schedulingEntity.getTsUserId());
	}

	@Override
	public String[] queryTsScheduling(Long tsUserId) {	
		//当前日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendat = Calendar.getInstance();
		String scheduling = sdf.format(calendat.getTime());
		String[] account = tsSchedulingDao.queryTsScheduling(scheduling,tsUserId);
		return account;
	}

	@Override
	public R changeTsScheduling(String account, String number) {
		Long tsUserId = null;
		Map<String,Object> map = new HashMap<>();
		map.put("account", account);
		
		TyOrderServiceEntity tyOrderServiceEntity = null;
		try {
		tyOrderServiceEntity = tyOrderServiceDao.queryObjectByUN(number, tsUserId);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("订单异常！");
		}
		
		if(tyOrderServiceEntity != null) {
			List<TsSchedulingEntity> schedulingEntity = tsSchedulingDao.queryObjectByMap(map);
			tyOrderServiceEntity.setTsUserid(schedulingEntity.get(0).getTsUserId());
			tyOrderServiceDao.update(tyOrderServiceEntity);
		}
		
		return null;
	}



}









