package com.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.framework.dao.TyInvitationCashmoneyDao;
import com.framework.entity.TyInvitationCashmoneyEntity;
import com.framework.service.TyInvitationCashmoneyService;



@Service("tyInvitationCashmoneyService")
public class TyInvitationCashmoneyServiceImpl implements TyInvitationCashmoneyService {
	@Autowired
	private TyInvitationCashmoneyDao tyInvitationCashmoneyDao;
	
	@Override
	public TyInvitationCashmoneyEntity queryObject(Long id){
		return tyInvitationCashmoneyDao.queryObject(id);
	}
	
	@Override
	public List<TyInvitationCashmoneyEntity> queryObjectByKey(TyInvitationCashmoneyEntity tyInvitationCashmoney){
		return tyInvitationCashmoneyDao.queryObjectByKey(tyInvitationCashmoney);
	}
	
	@Override
	public List<TyInvitationCashmoneyEntity> queryList(Map<String, Object> map){
		return tyInvitationCashmoneyDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tyInvitationCashmoneyDao.queryTotal(map);
	}
	
	@Override
	public void save(TyInvitationCashmoneyEntity tyInvitationCashmoney){
		tyInvitationCashmoneyDao.save(tyInvitationCashmoney);
	}
	
	@Override
	public void update(TyInvitationCashmoneyEntity tyInvitationCashmoney){
		tyInvitationCashmoneyDao.update(tyInvitationCashmoney);
	}
	
	@Override
	public void delete(Long id){
		tyInvitationCashmoneyDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		tyInvitationCashmoneyDao.deleteBatch(ids);
	}

	@Override
	public List<TyInvitationCashmoneyEntity> queryListRand(Long modulId) {
		return tyInvitationCashmoneyDao.queryListRand(modulId);
	}
	
}
