package com.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.framework.dao.Modul10Dao;
import com.framework.entity.Modul10Entity;
import com.framework.service.Modul10Service;



@Service("modul10Service")
public class Modul10ServiceImpl implements Modul10Service {
	@Autowired
	private Modul10Dao modul10Dao;
	
	@Override
	public Modul10Entity queryObject(Long id){
		return modul10Dao.queryObject(id);
	}
	
	@Override
	public List<Modul10Entity> queryObjectByKey(Modul10Entity modul10){
		return modul10Dao.queryObjectByKey(modul10);
	}
	
	@Override
	public List<Modul10Entity> queryList(Map<String, Object> map){
		return modul10Dao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return modul10Dao.queryTotal(map);
	}
	
	@Override
	public void save(Modul10Entity modul10){
		modul10Dao.save(modul10);
	}
	
	@Override
	public void update(Modul10Entity modul10){
		modul10Dao.update(modul10);
	}
	
	@Override
	public void delete(Long id){
		modul10Dao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		modul10Dao.deleteBatch(ids);
	}
	
}
