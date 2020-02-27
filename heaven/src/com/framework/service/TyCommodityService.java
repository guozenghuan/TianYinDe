package com.framework.service;

import com.framework.entity.TyCommodityEntity;
import com.framework.utils.R;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * 
 * @author R & D
 * @email 
 * @date 2019-03-23 14:20:58
 */
public interface TyCommodityService {
	
	TyCommodityEntity queryObject(Long id);
	
	List<TyCommodityEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	R save(HttpServletRequest request);
	
	R update(HttpServletRequest request);
	
	void updateStatus(TyCommodityEntity tyCommodity);
	
	void delete(Long id);
	
	R deleteBatch(Long[] ids);
}
