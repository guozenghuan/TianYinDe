package com.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.framework.dao.Modul02Dao;
import com.framework.entity.Modul02Entity;
import com.framework.service.Modul02Service;



@Service("modul02Service")
public class Modul02ServiceImpl implements Modul02Service {
	@Autowired
	private Modul02Dao modul02Dao;
	
	@Override
	public Modul02Entity queryObject(Long id){
		return modul02Dao.queryObject(id);
	}
	
	@Override
	public List<Modul02Entity> queryObjectByKey(Modul02Entity modul02){
		return modul02Dao.queryObjectByKey(modul02);
	}
	
	@Override
	public List<Modul02Entity> queryList(Map<String, Object> map){
		return modul02Dao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return modul02Dao.queryTotal(map);
	}
	
	@Override
	public void save(Modul02Entity modul02){
		modul02Dao.save(modul02);
	}
	
	@Override
	public void update(Modul02Entity modul02){
		modul02Dao.update(modul02);
	}
	
	@Override
	public void delete(Long id){
		modul02Dao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		modul02Dao.deleteBatch(ids);
	}
	
}
