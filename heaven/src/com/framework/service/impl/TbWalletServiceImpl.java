package com.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.framework.dao.TbWalletDao;
import com.framework.entity.TbWalletEntity;
import com.framework.service.TbWalletService;



@Service("tbWalletService")
public class TbWalletServiceImpl implements TbWalletService {
	@Autowired
	private TbWalletDao tbWalletDao;
	
	@Override
	public TbWalletEntity queryObject(Long id){
		return tbWalletDao.queryObject(id);
	}
	
	@Override
	public List<TbWalletEntity> queryList(Map<String, Object> map){
		return tbWalletDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tbWalletDao.queryTotal(map);
	}
	
	@Override
	public void save(TbWalletEntity tbWallet){
		tbWalletDao.save(tbWallet);
	}
	
	@Override
	public void update(TbWalletEntity tbWallet){
		tbWalletDao.update(tbWallet);
	}
	
	@Override
	public void delete(Long id){
		tbWalletDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		tbWalletDao.deleteBatch(ids);
	}
	
}
