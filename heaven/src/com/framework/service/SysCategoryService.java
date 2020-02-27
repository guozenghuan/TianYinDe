package com.framework.service;

import com.framework.entity.SysCategoryEntity;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import com.framework.utils.R;

/**
 * 类目表
 * 
 * @author R & D
 * @email 
 * @date 2019-11-18 09:52:48
 */
public interface SysCategoryService {
	
	SysCategoryEntity queryObject(Integer id);
	
	List<SysCategoryEntity> queryObjectByKey(SysCategoryEntity sysCategory);
	
	List<SysCategoryEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysCategoryEntity sysCategory);
	
	R saveImg(HttpServletRequest request);

	
	void update(SysCategoryEntity sysCategory);
	
	R updateImg(HttpServletRequest request);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
