package com.framework.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.dao.TsSchedulingPaiDanDao;
import com.framework.entity.TsSchedulingEntity;
import com.framework.entity.TsSchedulingPaiDanEntity;
import com.framework.service.TsSchedulingPaiDanService;

@Service("TsSchedulingPaiDanService")
public class TsSchedulingPaiDanServiceImpl implements TsSchedulingPaiDanService{
	@Autowired
	private TsSchedulingPaiDanDao tsSchedulingPaiDanDao;

	@Override
	public int queryDate(String dateStr) {
		int paiDanEntity = 
		tsSchedulingPaiDanDao.queryDate(dateStr);
		
		return paiDanEntity ;
	}

	@Override
	public void saveDate(List<TsSchedulingEntity> tsUserIds) {
		TsSchedulingPaiDanEntity tsUser = new TsSchedulingPaiDanEntity();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		tsUser.setCreatetime(sdf.format(date));
		
		for(int i=0;i<tsUserIds.size();i++){
			TsSchedulingEntity tsUserId = tsUserIds.get(i);
			TsSchedulingPaiDanEntity paiDanEntity = tsSchedulingPaiDanDao.queryObjectByUS(tsUserId.getTsUserId(),tsUserId.getScheduling());
			if(paiDanEntity == null){
				tsUser.setTsUserId(tsUserId.getTsUserId());
				tsUser.setDate(tsUserId.getScheduling());
				tsUser.setNumber(0);
				tsSchedulingPaiDanDao.savePaiDan(tsUser);
			}
		};
	}

	@Override
	public void deleteDate() {
		tsSchedulingPaiDanDao.deleteAll();	
	}

	@Override
	public List<TsSchedulingPaiDanEntity> queryDateMin() {
		List<TsSchedulingPaiDanEntity>  tsUser = 
		tsSchedulingPaiDanDao.queryDateMin();
		return tsUser;
	}

	@Override
	public void addNumber(TsSchedulingPaiDanEntity tsUser) {
		int i = tsUser.getNumber();
		i = i+1;
		tsUser.setNumber(i);
		tsSchedulingPaiDanDao.addNumber(tsUser);
	}

}
