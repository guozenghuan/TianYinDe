package com.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import com.framework.dao.TbAddressDao;
import com.framework.entity.TbAddressEntity;
import com.framework.service.TbAddressService;



@Service("tbAddressService")
public class TbAddressServiceImpl implements TbAddressService {
	@Autowired
	private TbAddressDao tbAddressDao;
	
	@Override
	public TbAddressEntity queryObject(Long id){
		return tbAddressDao.queryObject(id);
	}
	
	@Override
	public List<TbAddressEntity> queryObjectByKey(TbAddressEntity tbAddress){
		return tbAddressDao.queryObjectByKey(tbAddress);
	}
	
	@Override
	public List<TbAddressEntity> queryList(Map<String, Object> map){
		return tbAddressDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tbAddressDao.queryTotal(map);
	}
	
	@Transactional
	@Override
	public void save(TbAddressEntity tbAddress){
		tbAddressDao.save(tbAddress);
	}
	
	@Override
	public void update(TbAddressEntity tbAddress){
		tbAddressDao.update(tbAddress);
	}
	
	@Override
	public void delete(Long id){
		tbAddressDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		tbAddressDao.deleteBatch(ids);
	}
	
}
