package com.framework.service;

import com.framework.entity.Modul08Entity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author R & D
 * @email 
 * @date 2019-04-28 02:12:11
 */
public interface Modul08Service {
	
	Modul08Entity queryObject(Long id);
	
	List<Modul08Entity> queryObjectByKey(Modul08Entity modul08);
	
	List<Modul08Entity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(Modul08Entity modul08);
	
	void update(Modul08Entity modul08);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
