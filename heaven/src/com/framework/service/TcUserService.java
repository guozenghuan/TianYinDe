package com.framework.service;

import com.framework.entity.TcUserEntity;
import com.framework.utils.R;

import java.util.List;
import java.util.Map;

/**
 * 客户信息
 * 
 * @author R & D
 * @email 
 * @date 2019-04-03 16:05:31
 */
public interface TcUserService {
	
	TcUserEntity queryObject(Long id);
	
	List<TcUserEntity> queryObjectByKey(TcUserEntity tcUser);
	
	List<TcUserEntity> queryList(Map<String, Object> map);
	
	void addWallet(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TcUserEntity tcUser);
	
	void update(TcUserEntity tcUser);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
