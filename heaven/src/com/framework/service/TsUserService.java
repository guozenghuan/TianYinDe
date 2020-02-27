package com.framework.service;

import com.framework.entity.TsUserEntity;
import com.framework.utils.R;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 业务员账号
 * 
 * @author R & D
 * @email 
 * @date 2019-03-20 13:52:39
 */
public interface TsUserService {
	
	TsUserEntity queryObject(Long id);
	
	List<TsUserEntity> queryObjectByKey(TsUserEntity tsUserEntity);
	
	List<TsUserEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	R save(HttpServletRequest request);
	
	R update(HttpServletRequest requestr);
	
	R updateMy(HttpServletRequest requestr);
	
	void updateNow(TsUserEntity tsUserEntity);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
