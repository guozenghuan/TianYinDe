package com.framework.service;

import com.framework.entity.ModulMusicEntity;
import com.framework.utils.R;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * 
 * @author R & D
 * @email 
 * @date 2019-04-28 12:58:09
 */
public interface ModulMusicService {
	
	ModulMusicEntity queryObject(Long id);
	
	List<ModulMusicEntity> queryObjectByKey(ModulMusicEntity modulMusic);
	
	List<ModulMusicEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	R save(HttpServletRequest request);
	
	R update(HttpServletRequest request);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
