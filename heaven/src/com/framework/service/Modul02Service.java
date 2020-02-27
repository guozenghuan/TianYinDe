package com.framework.service;

import com.framework.entity.Modul02Entity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author R & D
 * @email 
 * @date 2019-04-28 02:12:11
 */
public interface Modul02Service {
	
	Modul02Entity queryObject(Long id);
	
	List<Modul02Entity> queryObjectByKey(Modul02Entity modul02);
	
	List<Modul02Entity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(Modul02Entity modul02);
	
	void update(Modul02Entity modul02);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
