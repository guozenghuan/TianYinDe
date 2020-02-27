package com.framework.service;

import com.framework.entity.Modul05Entity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author R & D
 * @email 
 * @date 2019-04-28 02:12:11
 */
public interface Modul05Service {
	
	Modul05Entity queryObject(Long id);
	
	List<Modul05Entity> queryObjectByKey(Modul05Entity modul05);
	
	List<Modul05Entity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(Modul05Entity modul05);
	
	void update(Modul05Entity modul05);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
