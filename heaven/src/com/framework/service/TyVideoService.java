package com.framework.service;

import com.framework.entity.TyVideoEntity;
import com.framework.utils.R;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 视频展示
 * 
 * @author R & D
 * @email 
 * @date 2019-04-09 18:41:09
 */
public interface TyVideoService {
	
	TyVideoEntity queryObject(Long id);
	
	List<TyVideoEntity> queryObjectByKey(TyVideoEntity tyVideo);
	
	List<TyVideoEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	R save(HttpServletRequest request);
	
	R update(HttpServletRequest request);
	
	R updateSors(TyVideoEntity tyVideo);
	
	void delete(Long id);
	
	R deleteBatch(Long[] ids);
}
