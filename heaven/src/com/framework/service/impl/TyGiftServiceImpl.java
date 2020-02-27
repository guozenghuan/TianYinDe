package com.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.framework.dao.TyGiftDao;
import com.framework.entity.TyGiftEntity;
import com.framework.service.TyGiftService;



@Service("tyGiftService")
public class TyGiftServiceImpl implements TyGiftService {
	@Autowired
	private TyGiftDao tyGiftDao;
	
	@Override
	public TyGiftEntity queryObject(Long id){
		return tyGiftDao.queryObject(id);
	}
	
	@Override
	public List<TyGiftEntity> queryObjectByKey(TyGiftEntity tyGift){
		return tyGiftDao.queryObjectByKey(tyGift);
	}
	
	@Override
	public List<TyGiftEntity> queryList(Map<String, Object> map){
		return tyGiftDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tyGiftDao.queryTotal(map);
	}
	
	@Override
	public void save(TyGiftEntity tyGift){
		tyGiftDao.save(tyGift);
	}
	
	@Override
	public void update(TyGiftEntity tyGift){
		tyGiftDao.update(tyGift);
	}
	
	@Override
	public void delete(Long id){
		tyGiftDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		tyGiftDao.deleteBatch(ids);
	}
	
}
