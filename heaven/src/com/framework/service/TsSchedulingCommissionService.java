package com.framework.service;

import java.util.List;
import java.util.Map;

import com.framework.entity.TsSchedulingCommissionEntity;

public interface TsSchedulingCommissionService {
	
	String[] queryTsAccount();

	int queryTotal(Map<String, Object> map);

	List<TsSchedulingCommissionEntity> queryListByMap(Map<String, Object> map);

	void delete(Long tsUserId, String number);

}
