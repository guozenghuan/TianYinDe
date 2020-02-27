package com.framework.service;

import com.framework.entity.Modul03Entity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author R & D
 * @email 
 * @date 2019-04-28 02:12:11
 */
public interface Modul03Service {
	
	Modul03Entity queryObject(Long id);
	
	List<Modul03Entity> queryObjectByKey(Modul03Entity modul03);
	
	List<Modul03Entity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(Modul03Entity modul03);
	
	void update(Modul03Entity modul03);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
