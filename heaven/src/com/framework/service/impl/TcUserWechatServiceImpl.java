package com.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.framework.dao.TcUserWechatDao;
import com.framework.entity.TcUserWechatEntity;
import com.framework.service.TcUserWechatService;



@Service("tcUserWechatService")
public class TcUserWechatServiceImpl implements TcUserWechatService {
	@Autowired
	private TcUserWechatDao tcUserWechatDao;
	
	@Override
	public TcUserWechatEntity queryObject(Long id){
		return tcUserWechatDao.queryObject(id);
	}
	
	@Override
	public List<TcUserWechatEntity> queryObjectByKey(TcUserWechatEntity tcUserWechat){
		return tcUserWechatDao.queryObjectByKey(tcUserWechat);
	}
	
	@Override
	public List<TcUserWechatEntity> queryList(Map<String, Object> map){
		return tcUserWechatDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tcUserWechatDao.queryTotal(map);
	}
	
	@Override
	public void save(TcUserWechatEntity tcUserWechat){
		tcUserWechatDao.save(tcUserWechat);
	}
	
	@Override
	public void update(TcUserWechatEntity tcUserWechat){
		tcUserWechatDao.update(tcUserWechat);
	}
	
	@Override
	public void delete(Long id){
		tcUserWechatDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		tcUserWechatDao.deleteBatch(ids);
	}
	
}
