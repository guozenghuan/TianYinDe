package com.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.framework.dao.Modul01Dao;
import com.framework.entity.Modul01Entity;
import com.framework.entity.TyCommodityEntity;
import com.framework.service.Modul01Service;



@Service("modul01Service")
public class Modul01ServiceImpl implements Modul01Service {
	@Autowired
	private Modul01Dao modul01Dao;
	
	@Override
	public Modul01Entity queryObject(Long id){
		return modul01Dao.queryObject(id);
	}
	
	@Override
	public List<Modul01Entity> queryObjectByKey(Modul01Entity modul01){
		return modul01Dao.queryObjectByKey(modul01);
	}
	
	@Override
	public List<Modul01Entity> queryList(Map<String, Object> map){
		return modul01Dao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return modul01Dao.queryTotal(map);
	}
	
	@Override
	public void save(Modul01Entity modul01){
		modul01Dao.save(modul01);
	}
	
	@Override
	public void update(Modul01Entity modul01){
		modul01Dao.update(modul01);
	}
	
	@Override
	public void delete(Long id){
		modul01Dao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		modul01Dao.deleteBatch(ids);
	}
	
}
