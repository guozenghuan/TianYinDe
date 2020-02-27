package com.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.framework.dao.Modul07Dao;
import com.framework.entity.Modul07Entity;
import com.framework.service.Modul07Service;



@Service("modul07Service")
public class Modul07ServiceImpl implements Modul07Service {
	@Autowired
	private Modul07Dao modul07Dao;
	
	@Override
	public Modul07Entity queryObject(Long id){
		return modul07Dao.queryObject(id);
	}
	
	@Override
	public List<Modul07Entity> queryObjectByKey(Modul07Entity modul07){
		return modul07Dao.queryObjectByKey(modul07);
	}
	
	@Override
	public List<Modul07Entity> queryList(Map<String, Object> map){
		return modul07Dao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return modul07Dao.queryTotal(map);
	}
	
	@Override
	public void save(Modul07Entity modul07){
		modul07Dao.save(modul07);
	}
	
	@Override
	public void update(Modul07Entity modul07){
		modul07Dao.update(modul07);
	}
	
	@Override
	public void delete(Long id){
		modul07Dao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		modul07Dao.deleteBatch(ids);
	}
	
}
