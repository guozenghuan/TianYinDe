package com.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import com.framework.dao.TcUserDao;
import com.framework.entity.TcUserEntity;
import com.framework.service.TcUserService;



@Service("tcUserService")
public class TcUserServiceImpl implements TcUserService {
	@Autowired
	private TcUserDao tcUserDao;
	
	@Override
	public TcUserEntity queryObject(Long id){
		return tcUserDao.queryObject(id);
	}
	
	@Override
	public List<TcUserEntity> queryObjectByKey(TcUserEntity tcUser){
		return tcUserDao.queryObjectByKey(tcUser);
	}
	
	@Override
	public List<TcUserEntity> queryList(Map<String, Object> map){
		return tcUserDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tcUserDao.queryTotal(map);
	}
	
	@Transactional
	@Override
	public void save(TcUserEntity tcUser){
		tcUserDao.save(tcUser);
	}
	
	@Transactional
	@Override
	public void update(TcUserEntity tcUser){
		tcUserDao.update(tcUser);
	}
	
	@Override
	public void delete(Long id){
		tcUserDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		tcUserDao.deleteBatch(ids);
	}

	@Transactional
	@Override
	public synchronized void addWallet(Map<String, Object> map) {
		tcUserDao.addWallet(map);
	}
	
}
