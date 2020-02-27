package com.framework.service;

import com.alibaba.fastjson.JSONArray;
import com.framework.entity.TyServiceEntity;

import java.util.List;
import java.util.Map;

/**
 * 服务
 * 
 * @author R & D
 * @email 
 * @date 2019-03-24 18:53:52
 */
public interface TyServiceService {
	
	TyServiceEntity queryObject(Long id);
	
	List<TyServiceEntity> queryList(Map<String, Object> map);
	
	List<TyServiceEntity> queryObjectByKey(TyServiceEntity tyService);
	
	List<TyServiceEntity> queryObjectByKeySort(TyServiceEntity tyService);
	
	List<TyServiceEntity> queryObjectByKeySortUser(TyServiceEntity tyService);
	
	JSONArray recursive(Long parentId);
	
	List<Long> getRecursiveIds(Long parentId);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TyServiceEntity tyService);
	
	void update(TyServiceEntity tyService);
	
	void updateMainSors(TyServiceEntity tyService);
	
	void updateMainSorsScd(TyServiceEntity tyService);
	
	void updateJiaGe(TyServiceEntity tyService);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
	
	void deleteFwAll(Long[] ids);
}
