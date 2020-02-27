package com.framework.dao;

import com.framework.entity.TsCompanyEntity;

/**
 * 公司信息
 * 
 * @author R & D
 * @email 
 * @date 2019-03-20 13:52:39
 */
public interface TsCompanyDao extends BaseDao<TsCompanyEntity> {
	TsCompanyEntity queryObject(Long value);
}
