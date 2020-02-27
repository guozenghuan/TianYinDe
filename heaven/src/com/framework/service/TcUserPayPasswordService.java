package com.framework.service;

import com.framework.entity.TcUserPayPasswordEntity;

import java.util.List;
import java.util.Map;

/**
 * 支付密码
 * 
 * @author R & D
 * @email 
 * @date 2019-05-12 11:26:27
 */
public interface TcUserPayPasswordService {
	
	TcUserPayPasswordEntity queryObject(Long id);
	
	List<TcUserPayPasswordEntity> queryObjectByKey(TcUserPayPasswordEntity tcUserPayPassword);
	
	List<TcUserPayPasswordEntity> queryList(Map<String, Object> map);
	
	/**
	 * 判断用户是否设置过支付密码
	 * @param userId
	 * @return
	 */
	Boolean setPayPwdFlag(Long userId);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TcUserPayPasswordEntity tcUserPayPassword);
	
	void update(TcUserPayPasswordEntity tcUserPayPassword);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
