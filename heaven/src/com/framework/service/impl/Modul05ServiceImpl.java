package com.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.framework.dao.Modul05Dao;
import com.framework.entity.Modul05Entity;
import com.framework.service.Modul05Service;



@Service("modul05Service")
public class Modul05ServiceImpl implements Modul05Service {
	@Autowired
	private Modul05Dao modul05Dao;
	
	@Override
	public Modul05Entity queryObject(Long id){
		return modul05Dao.queryObject(id);
	}
	
	@Override
	public List<Modul05Entity> queryObjectByKey(Modul05Entity modul05){
		return modul05Dao.queryObjectByKey(modul05);
	}
	
	@Override
	public List<Modul05Entity> queryList(Map<String, Object> map){
		return modul05Dao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return modul05Dao.queryTotal(map);
	}
	
	@Override
	public void save(Modul05Entity modul05){
		modul05Dao.save(modul05);
	}
	
	@Override
	public void update(Modul05Entity modul05){
		modul05Dao.update(modul05);
	}
	
	@Override
	public void delete(Long id){
		modul05Dao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		modul05Dao.deleteBatch(ids);
	}
	
}
