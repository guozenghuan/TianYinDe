package com.framework.dao;

import com.framework.entity.TyStatisticsEntity;

/**
 * 平台信息统计
 * 
 * @author R & D
 * @email 
 * @date 2019-05-14 18:02:32
 */
public interface TyStatisticsDao extends BaseDao<TyStatisticsEntity> {
	/**
	 * 获取当前平台数据
	 * @return
	 */
	TyStatisticsEntity queryByNow();
}
