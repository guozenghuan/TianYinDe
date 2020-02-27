package com.framework.service;

import com.framework.entity.Modul06Entity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author R & D
 * @email 
 * @date 2019-04-28 02:12:11
 */
public interface Modul06Service {
	
	Modul06Entity queryObject(Long id);
	
	List<Modul06Entity> queryObjectByKey(Modul06Entity modul06);
	
	List<Modul06Entity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(Modul06Entity modul06);
	
	void update(Modul06Entity modul06);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
