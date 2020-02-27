package com.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.framework.dao.Modul06Dao;
import com.framework.entity.Modul06Entity;
import com.framework.service.Modul06Service;



@Service("modul06Service")
public class Modul06ServiceImpl implements Modul06Service {
	@Autowired
	private Modul06Dao modul06Dao;
	
	@Override
	public Modul06Entity queryObject(Long id){
		return modul06Dao.queryObject(id);
	}
	
	@Override
	public List<Modul06Entity> queryObjectByKey(Modul06Entity modul06){
		return modul06Dao.queryObjectByKey(modul06);
	}
	
	@Override
	public List<Modul06Entity> queryList(Map<String, Object> map){
		return modul06Dao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return modul06Dao.queryTotal(map);
	}
	
	@Override
	public void save(Modul06Entity modul06){
		modul06Dao.save(modul06);
	}
	
	@Override
	public void update(Modul06Entity modul06){
		modul06Dao.update(modul06);
	}
	
	@Override
	public void delete(Long id){
		modul06Dao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		modul06Dao.deleteBatch(ids);
	}
	
}
