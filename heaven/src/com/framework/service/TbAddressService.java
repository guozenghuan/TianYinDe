package com.framework.service;

import com.framework.entity.TbAddressEntity;

import java.util.List;
import java.util.Map;

/**
 * 用户收货地址
 * 
 * @author R & D
 * @email 
 * @date 2019-04-10 21:53:39
 */
public interface TbAddressService {
	
	TbAddressEntity queryObject(Long id);
	
	List<TbAddressEntity> queryObjectByKey(TbAddressEntity tbAddress);
	
	List<TbAddressEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TbAddressEntity tbAddress);
	
	void update(TbAddressEntity tbAddress);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
