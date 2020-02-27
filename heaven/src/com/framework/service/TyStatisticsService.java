package com.framework.service;

import com.framework.entity.TyStatisticsEntity;

import java.util.List;
import java.util.Map;

/**
 * 平台信息统计
 * 
 * @author R & D
 * @email 
 * @date 2019-05-14 18:02:32
 */
public interface TyStatisticsService {
	
	TyStatisticsEntity queryObject(Long id);
	
	TyStatisticsEntity queryByNow();
	
	List<TyStatisticsEntity> queryObjectByKey(TyStatisticsEntity tyStatistics);
	
	List<TyStatisticsEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TyStatisticsEntity tyStatistics);
	
	void update(TyStatisticsEntity tyStatistics);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
