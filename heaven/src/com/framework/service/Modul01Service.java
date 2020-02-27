package com.framework.service;

import com.framework.entity.Modul01Entity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author R & D
 * @email 
 * @date 2019-04-28 02:12:10
 */
public interface Modul01Service {
	
	Modul01Entity queryObject(Long id);
	
	List<Modul01Entity> queryObjectByKey(Modul01Entity modul01);
	
	List<Modul01Entity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(Modul01Entity modul01);
	
	void update(Modul01Entity modul01);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
