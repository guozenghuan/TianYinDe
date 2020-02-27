package com.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.framework.dao.Modul04Dao;
import com.framework.entity.Modul04Entity;
import com.framework.service.Modul04Service;



@Service("modul04Service")
public class Modul04ServiceImpl implements Modul04Service {
	@Autowired
	private Modul04Dao modul04Dao;
	
	@Override
	public Modul04Entity queryObject(Long id){
		return modul04Dao.queryObject(id);
	}
	
	@Override
	public List<Modul04Entity> queryObjectByKey(Modul04Entity modul04){
		return modul04Dao.queryObjectByKey(modul04);
	}
	
	@Override
	public List<Modul04Entity> queryList(Map<String, Object> map){
		return modul04Dao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return modul04Dao.queryTotal(map);
	}
	
	@Override
	public void save(Modul04Entity modul04){
		modul04Dao.save(modul04);
	}
	
	@Override
	public void update(Modul04Entity modul04){
		modul04Dao.update(modul04);
	}
	
	@Override
	public void delete(Long id){
		modul04Dao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		modul04Dao.deleteBatch(ids);
	}
	
}
