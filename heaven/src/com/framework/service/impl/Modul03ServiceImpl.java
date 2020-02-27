package com.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.framework.dao.Modul03Dao;
import com.framework.entity.Modul03Entity;
import com.framework.service.Modul03Service;



@Service("modul03Service")
public class Modul03ServiceImpl implements Modul03Service {
	@Autowired
	private Modul03Dao modul03Dao;
	
	@Override
	public Modul03Entity queryObject(Long id){
		return modul03Dao.queryObject(id);
	}
	
	@Override
	public List<Modul03Entity> queryObjectByKey(Modul03Entity modul03){
		return modul03Dao.queryObjectByKey(modul03);
	}
	
	@Override
	public List<Modul03Entity> queryList(Map<String, Object> map){
		return modul03Dao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return modul03Dao.queryTotal(map);
	}
	
	@Override
	public void save(Modul03Entity modul03){
		modul03Dao.save(modul03);
	}
	
	@Override
	public void update(Modul03Entity modul03){
		modul03Dao.update(modul03);
	}
	
	@Override
	public void delete(Long id){
		modul03Dao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		modul03Dao.deleteBatch(ids);
	}
	
}
