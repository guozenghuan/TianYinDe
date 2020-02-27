package com.framework.service;

import com.framework.entity.TbWalletEntity;

import java.util.List;
import java.util.Map;

/**
 * 客户钱包
 * 
 * @author R & D
 * @email 
 * @date 2019-03-19 15:31:36
 */
public interface TbWalletService {
	
	TbWalletEntity queryObject(Long id);
	
	List<TbWalletEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TbWalletEntity tbWallet);
	
	void update(TbWalletEntity tbWallet);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
