package com.framework.service;

import com.framework.entity.TcUserWechatEntity;

import java.util.List;
import java.util.Map;

/**
 * 用户微信收款码
 * 
 * @author R & D
 * @email 
 * @date 2019-05-12 10:16:52
 */
public interface TcUserWechatService {
	
	TcUserWechatEntity queryObject(Long id);
	
	List<TcUserWechatEntity> queryObjectByKey(TcUserWechatEntity tcUserWechat);
	
	List<TcUserWechatEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TcUserWechatEntity tcUserWechat);
	
	void update(TcUserWechatEntity tcUserWechat);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
