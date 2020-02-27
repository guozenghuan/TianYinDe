package com.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.framework.dao.Modul11Dao;
import com.framework.entity.Modul11Entity;
import com.framework.service.Modul11Service;



@Service("modul11Service")
public class Modul11ServiceImpl implements Modul11Service {
	@Autowired
	private Modul11Dao modul11Dao;
	
	@Override
	public Modul11Entity queryObject(Long id){
		return modul11Dao.queryObject(id);
	}
	
	@Override
	public List<Modul11Entity> queryObjectByKey(Modul11Entity modul11){
		return modul11Dao.queryObjectByKey(modul11);
	}
	
	@Override
	public List<Modul11Entity> queryList(Map<String, Object> map){
		return modul11Dao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return modul11Dao.queryTotal(map);
	}
	
	@Override
	public void save(Modul11Entity modul11){
		modul11Dao.save(modul11);
	}
	
	@Override
	public void update(Modul11Entity modul11){
		modul11Dao.update(modul11);
	}
	
	@Override
	public void delete(Long id){
		modul11Dao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		modul11Dao.deleteBatch(ids);
	}
	
}
