package com.framework.service;

import com.framework.entity.Modul10Entity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author R & D
 * @email 
 * @date 2019-05-01 00:32:50
 */
public interface Modul10Service {
	
	Modul10Entity queryObject(Long id);
	
	List<Modul10Entity> queryObjectByKey(Modul10Entity modul10);
	
	List<Modul10Entity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(Modul10Entity modul10);
	
	void update(Modul10Entity modul10);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
