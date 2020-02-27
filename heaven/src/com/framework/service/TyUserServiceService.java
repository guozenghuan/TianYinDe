package com.framework.service;

import com.framework.entity.TyUserServiceEntity;

import java.util.List;
import java.util.Map;

/**
 * 服务
 * 
 * @author R & D
 * @email 
 * @date 2019-05-11 21:55:12
 */
public interface TyUserServiceService {
	
	TyUserServiceEntity queryObject(Long id);
	
	List<TyUserServiceEntity> queryObjectByKey(TyUserServiceEntity tyUserService);
	
	List<TyUserServiceEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TyUserServiceEntity tyUserService);
	
	void update(TyUserServiceEntity tyUserService);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
