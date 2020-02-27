package com.framework.service;

import java.util.List;
import java.util.Map;

import com.framework.entity.TsSchedulingEntity;
import com.framework.utils.R;

public interface TsSchedulingService {

	//List<TsSchedulingEntity> queryList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);
	
	void update(Long id);

	void delete(Long tsUserId);

	List<TsSchedulingEntity> queryListObject(Integer page, Integer limit);

	TsSchedulingEntity queryObjectByUserId(Long  id);

	List<TsSchedulingEntity> queryListByDate(String scheduling);

	Long querySchedulingByDate(String dateStr);

	List<Long> queryListUserIdByDate(String dateStr);

	List<TsSchedulingEntity> queryList(Map<String, Object> map);

	int queryTotalAll();

	void checkScheduling(Long long1, String string);

	void checkSchedulingChange(Long long1, String string);

	R addTsScheduling(TsSchedulingEntity schedulingEntity);

	String[] queryTsScheduling(Long tsUserId);

	R changeTsScheduling(String account, String number);

}
