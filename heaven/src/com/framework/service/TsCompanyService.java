package com.framework.service;

import com.framework.entity.TsCompanyEntity;

import java.util.List;
import java.util.Map;

/**
 * 公司信息
 * 
 * @author R & D
 * @email 
 * @date 2019-03-20 13:52:39
 */
public interface TsCompanyService {
	
	TsCompanyEntity queryObject(Long id);
	
	List<TsCompanyEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TsCompanyEntity tsCompany);
	
	void update(TsCompanyEntity tsCompany);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
