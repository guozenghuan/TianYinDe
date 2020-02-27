package com.framework.service;

import com.framework.entity.Modul04Entity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author R & D
 * @email 
 * @date 2019-04-28 02:12:11
 */
public interface Modul04Service {
	
	Modul04Entity queryObject(Long id);
	
	List<Modul04Entity> queryObjectByKey(Modul04Entity modul04);
	
	List<Modul04Entity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(Modul04Entity modul04);
	
	void update(Modul04Entity modul04);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
