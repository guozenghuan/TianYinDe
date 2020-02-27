package com.framework.dao;

import java.util.Map;

import com.framework.entity.TcUserEntity;

/**
 * 客户信息
 * 
 * @author R & D
 * @email 
 * @date 2019-04-03 15:43:14
 */
public interface TcUserDao extends BaseDao<TcUserEntity> {
	/**
	 * 账户增加
	 * @param map
	 */
	int addWallet(Map<String, Object> map);
	/**
	 * 账户扣除
	 * @param map
	 * @return
	 */
	int reduceWallet(Map<String, Object> map);
}
