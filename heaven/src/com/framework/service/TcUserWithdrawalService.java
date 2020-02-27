package com.framework.service;

import com.framework.entity.TcUserWithdrawalEntity;
import com.framework.utils.R;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 提现申请
 * 
 * @author R & D
 * @email 
 * @date 2019-05-12 10:16:52
 */
public interface TcUserWithdrawalService {
	
	TcUserWithdrawalEntity queryObject(Long id);
	
	List<TcUserWithdrawalEntity> queryObjectByKey(TcUserWithdrawalEntity tcUserWithdrawal);
	
	List<TcUserWithdrawalEntity> queryList(Map<String, Object> map);
	
	R withdrawalByUser(Long userId,BigDecimal money);
	
	R updateSetting(Long id,Integer type,String note);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TcUserWithdrawalEntity tcUserWithdrawal);
	
	void update(TcUserWithdrawalEntity tcUserWithdrawal);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
