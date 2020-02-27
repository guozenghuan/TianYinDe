package com.framework.service;

import com.framework.entity.Modul07Entity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author R & D
 * @email 
 * @date 2019-04-28 02:12:11
 */
public interface Modul07Service {
	
	Modul07Entity queryObject(Long id);
	
	List<Modul07Entity> queryObjectByKey(Modul07Entity modul07);
	
	List<Modul07Entity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(Modul07Entity modul07);
	
	void update(Modul07Entity modul07);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
