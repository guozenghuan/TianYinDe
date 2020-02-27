package com.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.framework.dao.Modul08Dao;
import com.framework.entity.Modul08Entity;
import com.framework.service.Modul08Service;



@Service("modul08Service")
public class Modul08ServiceImpl implements Modul08Service {
	@Autowired
	private Modul08Dao modul08Dao;
	
	@Override
	public Modul08Entity queryObject(Long id){
		return modul08Dao.queryObject(id);
	}
	
	@Override
	public List<Modul08Entity> queryObjectByKey(Modul08Entity modul08){
		return modul08Dao.queryObjectByKey(modul08);
	}
	
	@Override
	public List<Modul08Entity> queryList(Map<String, Object> map){
		return modul08Dao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return modul08Dao.queryTotal(map);
	}
	
	@Override
	public void save(Modul08Entity modul08){
		modul08Dao.save(modul08);
	}
	
	@Override
	public void update(Modul08Entity modul08){
		modul08Dao.update(modul08);
	}
	
	@Override
	public void delete(Long id){
		modul08Dao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		modul08Dao.deleteBatch(ids);
	}
	
}
