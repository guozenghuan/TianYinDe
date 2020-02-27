package com.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.framework.dao.TyUserServiceDao;
import com.framework.entity.TyUserServiceEntity;
import com.framework.service.TyUserServiceService;



@Service("tyUserServiceService")
public class TyUserServiceServiceImpl implements TyUserServiceService {
	@Autowired
	private TyUserServiceDao tyUserServiceDao;
	
	@Override
	public TyUserServiceEntity queryObject(Long id){
		return tyUserServiceDao.queryObject(id);
	}
	
	@Override
	public List<TyUserServiceEntity> queryObjectByKey(TyUserServiceEntity tyUserService){ 
		List<TyUserServiceEntity> fuwu = tyUserServiceDao.queryObjectByKey(tyUserService); 
		return fuwu;
	}
	
	@Override
	public List<TyUserServiceEntity> queryList(Map<String, Object> map){
		return tyUserServiceDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tyUserServiceDao.queryTotal(map);
	}
	
	@Override
	public void save(TyUserServiceEntity tyUserService){
		tyUserServiceDao.save(tyUserService);
	}
	
	@Override
	public void update(TyUserServiceEntity tyUserService){
		tyUserServiceDao.update(tyUserService);
	}
	
	@Override
	public void delete(Long id){
		tyUserServiceDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		tyUserServiceDao.deleteBatch(ids);
	}
	
}
