package com.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.framework.dao.TyStatisticsDao;
import com.framework.entity.TyStatisticsEntity;
import com.framework.service.TyStatisticsService;



@Service("tyStatisticsService")
public class TyStatisticsServiceImpl implements TyStatisticsService {
	@Autowired
	private TyStatisticsDao tyStatisticsDao;
	
	@Override
	public TyStatisticsEntity queryObject(Long id){
		return tyStatisticsDao.queryObject(id);
	}
	
	@Override
	public List<TyStatisticsEntity> queryObjectByKey(TyStatisticsEntity tyStatistics){
		return tyStatisticsDao.queryObjectByKey(tyStatistics);
	}
	
	@Override
	public List<TyStatisticsEntity> queryList(Map<String, Object> map){
		return tyStatisticsDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tyStatisticsDao.queryTotal(map);
	}
	
	@Override
	public void save(TyStatisticsEntity tyStatistics){
		tyStatisticsDao.save(tyStatistics);
	}
	
	@Override
	public void update(TyStatisticsEntity tyStatistics){
		tyStatisticsDao.update(tyStatistics);
	}
	
	@Override
	public void delete(Long id){
		tyStatisticsDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		tyStatisticsDao.deleteBatch(ids);
	}

	@Override
	public TyStatisticsEntity queryByNow() {
		return tyStatisticsDao.queryByNow();
	}
	
}
