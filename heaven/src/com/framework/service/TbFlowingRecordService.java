package com.framework.service;

import com.framework.entity.TbFlowingRecordEntity;

import java.util.List;
import java.util.Map;

/**
 * 用户流水
 * 
 * @author R & D
 * @email 
 * @date 2019-03-30 14:52:39
 */
public interface TbFlowingRecordService {
	
	TbFlowingRecordEntity queryObject(Long id);
	
	List<TbFlowingRecordEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TbFlowingRecordEntity tbFlowingRecord);
	
	void update(TbFlowingRecordEntity tbFlowingRecord);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
