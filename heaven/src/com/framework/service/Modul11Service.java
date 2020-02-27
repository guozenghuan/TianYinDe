package com.framework.service;

import com.framework.entity.Modul11Entity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author R & D
 * @email 
 * @date 2019-05-01 00:32:50
 */
public interface Modul11Service {
	
	Modul11Entity queryObject(Long id);
	
	List<Modul11Entity> queryObjectByKey(Modul11Entity modul11);
	
	List<Modul11Entity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(Modul11Entity modul11);
	
	void update(Modul11Entity modul11);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
