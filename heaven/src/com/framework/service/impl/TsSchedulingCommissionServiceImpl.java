package com.framework.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.dao.TsSchedulingCommissionDao;
import com.framework.entity.TsSchedulingCommissionEntity;
import com.framework.service.TsSchedulingCommissionService;

@Service
public class TsSchedulingCommissionServiceImpl implements TsSchedulingCommissionService{
	@Autowired
	TsSchedulingCommissionDao tsSchedulingCommissionDao;
	
	@Override
	public String[] queryTsAccount() {
		
		return tsSchedulingCommissionDao.queryTsAccount();
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		
		return tsSchedulingCommissionDao.queryTotal(map);
	}

	@Override
	public List<TsSchedulingCommissionEntity> queryListByMap(Map<String, Object> map) {
		
		return tsSchedulingCommissionDao.queryListByMap(map);
	}

	@Override
	public void delete(Long tsUserId, String number) {
		tsSchedulingCommissionDao.deleteByUN(tsUserId,number);
		
	}

}
