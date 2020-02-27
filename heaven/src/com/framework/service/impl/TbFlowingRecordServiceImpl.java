package com.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.framework.dao.TbFlowingRecordDao;
import com.framework.entity.TbFlowingRecordEntity;
import com.framework.service.TbFlowingRecordService;



@Service("tbFlowingRecordService")
public class TbFlowingRecordServiceImpl implements TbFlowingRecordService {
	@Autowired
	private TbFlowingRecordDao tbFlowingRecordDao;
	
	@Override
	public TbFlowingRecordEntity queryObject(Long id){
		return tbFlowingRecordDao.queryObject(id);
	}
	
	@Override
	public List<TbFlowingRecordEntity> queryList(Map<String, Object> map){
		return tbFlowingRecordDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tbFlowingRecordDao.queryTotal(map);
	}
	
	@Override
	public void save(TbFlowingRecordEntity tbFlowingRecord){
		tbFlowingRecordDao.save(tbFlowingRecord);
	}
	
	@Override
	public void update(TbFlowingRecordEntity tbFlowingRecord){
		tbFlowingRecordDao.update(tbFlowingRecord);
	}
	
	@Override
	public void delete(Long id){
		tbFlowingRecordDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		tbFlowingRecordDao.deleteBatch(ids);
	}
	
}
