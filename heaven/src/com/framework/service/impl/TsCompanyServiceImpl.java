package com.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.framework.dao.TsCompanyDao;
import com.framework.entity.TsCompanyEntity;
import com.framework.service.TsCompanyService;



@Service("tsCompanyService")
public class TsCompanyServiceImpl implements TsCompanyService {
	@Autowired
	private TsCompanyDao tsCompanyDao;
	
	@Override
	public TsCompanyEntity queryObject(Long id){
		return tsCompanyDao.queryObject(id);
	}
	
	@Override
	public List<TsCompanyEntity> queryList(Map<String, Object> map){
		return tsCompanyDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tsCompanyDao.queryTotal(map);
	}
	
	@Override
	public void save(TsCompanyEntity tsCompany){
		tsCompanyDao.save(tsCompany);
	}
	
	@Override
	public void update(TsCompanyEntity tsCompany){
		tsCompanyDao.update(tsCompany);
	}
	
	@Override
	public void delete(Long id){
		tsCompanyDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		tsCompanyDao.deleteBatch(ids);
	}
	
}
