package com.framework.dao;

import com.framework.entity.TyUserServiceEntity;

/**
 * 服务
 * 
 * @author R & D
 * @email 
 * @date 2019-05-11 21:55:12
 */
public interface TyUserServiceDao extends BaseDao<TyUserServiceEntity> {
	int deleteByOrderId(Long orderId);
}
