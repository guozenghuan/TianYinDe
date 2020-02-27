package com.framework.service;

import com.framework.entity.TyGiftEntity;

import java.util.List;
import java.util.Map;

/**
 * 礼物
 * 
 * @author R & D
 * @email 
 * @date 2019-05-07 00:31:33
 */
public interface TyGiftService {
	
	TyGiftEntity queryObject(Long id);
	
	List<TyGiftEntity> queryObjectByKey(TyGiftEntity tyGift);
	
	List<TyGiftEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TyGiftEntity tyGift);
	
	void update(TyGiftEntity tyGift);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
