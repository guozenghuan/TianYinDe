package com.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.framework.dao.Modul09Dao;
import com.framework.entity.Modul09Entity;
import com.framework.service.Modul09Service;



@Service("modul09Service")
public class Modul09ServiceImpl implements Modul09Service {
	@Autowired
	private Modul09Dao modul09Dao;
	
	@Override
	public Modul09Entity queryObject(Long id){
		return modul09Dao.queryObject(id);
	}
	
	@Override
	public List<Modul09Entity> queryObjectByKey(Modul09Entity modul09){
		return modul09Dao.queryObjectByKey(modul09);
	}
	
	@Override
	public List<Modul09Entity> queryList(Map<String, Object> map){
		return modul09Dao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return modul09Dao.queryTotal(map);
	}
	
	@Override
	public void save(Modul09Entity modul09){
		modul09Dao.save(modul09);
	}
	
	@Override
	public void update(Modul09Entity modul09){
		modul09Dao.update(modul09);
	}
	
	@Override
	public void delete(Long id){
		modul09Dao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		modul09Dao.deleteBatch(ids);
	}
	
}
