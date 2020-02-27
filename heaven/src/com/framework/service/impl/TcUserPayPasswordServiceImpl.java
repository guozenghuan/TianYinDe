package com.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.framework.dao.TcUserPayPasswordDao;
import com.framework.entity.TcUserPayPasswordEntity;
import com.framework.service.TcUserPayPasswordService;
import com.framework.utils.StringUtil;



@Service("tcUserPayPasswordService")
public class TcUserPayPasswordServiceImpl implements TcUserPayPasswordService {
	@Autowired
	private TcUserPayPasswordDao tcUserPayPasswordDao;
	
	@Override
	public TcUserPayPasswordEntity queryObject(Long id){
		return tcUserPayPasswordDao.queryObject(id);
	}
	
	@Override
	public List<TcUserPayPasswordEntity> queryObjectByKey(TcUserPayPasswordEntity tcUserPayPassword){
		return tcUserPayPasswordDao.queryObjectByKey(tcUserPayPassword);
	}
	
	@Override
	public List<TcUserPayPasswordEntity> queryList(Map<String, Object> map){
		return tcUserPayPasswordDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tcUserPayPasswordDao.queryTotal(map);
	}
	
	@Override
	public void save(TcUserPayPasswordEntity tcUserPayPassword){
		tcUserPayPasswordDao.save(tcUserPayPassword);
	}
	
	@Override
	public void update(TcUserPayPasswordEntity tcUserPayPassword){
		tcUserPayPasswordDao.update(tcUserPayPassword);
	}
	
	@Override
	public void delete(Long id){
		tcUserPayPasswordDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		tcUserPayPasswordDao.deleteBatch(ids);
	}

	@Override
	public Boolean setPayPwdFlag(Long userId) {
		TcUserPayPasswordEntity tcUserPayPassword = new TcUserPayPasswordEntity();
		tcUserPayPassword.setUserId(userId);
		
		List<TcUserPayPasswordEntity> list = tcUserPayPasswordDao.queryObjectByKey(tcUserPayPassword);
		if(list.isEmpty() || list.size() <= 0){
			return true;
		}else{
			return false;
		}
	}
	
}
