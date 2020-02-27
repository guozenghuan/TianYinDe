package com.framework.dao;

import java.util.Map;

import com.framework.entity.SysCategoryEntity;

/**
 * 类目表
 * 
 * @author R & D
 * @email 
 * @date 2019-11-18 09:52:48
 */
public interface SysCategoryDao extends BaseDao<SysCategoryEntity> {
	
	int saveImg(Map<String, Object> map);
	
}
