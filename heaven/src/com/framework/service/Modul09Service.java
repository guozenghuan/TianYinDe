package com.framework.service;

import com.framework.entity.Modul09Entity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author R & D
 * @email 
 * @date 2019-04-28 02:12:11
 */
public interface Modul09Service {
	
	Modul09Entity queryObject(Long id);
	
	List<Modul09Entity> queryObjectByKey(Modul09Entity modul09);
	
	List<Modul09Entity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(Modul09Entity modul09);
	
	void update(Modul09Entity modul09);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
