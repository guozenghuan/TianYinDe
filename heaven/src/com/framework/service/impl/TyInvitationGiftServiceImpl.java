package com.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.framework.dao.TyInvitationGiftDao;
import com.framework.entity.TyInvitationGiftEntity;
import com.framework.service.TyInvitationGiftService;



@Service("tyInvitationGiftService")
public class TyInvitationGiftServiceImpl implements TyInvitationGiftService {
	@Autowired
	private TyInvitationGiftDao tyInvitationGiftDao;
	
	@Override
	public TyInvitationGiftEntity queryObject(Long id){
		return tyInvitationGiftDao.queryObject(id);
	}
	
	@Override
	public List<TyInvitationGiftEntity> queryObjectByKey(TyInvitationGiftEntity tyInvitationGift){
		return tyInvitationGiftDao.queryObjectByKey(tyInvitationGift);
	}
	
	@Override
	public List<TyInvitationGiftEntity> queryList(Map<String, Object> map){
		return tyInvitationGiftDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tyInvitationGiftDao.queryTotal(map);
	}
	
	@Override
	public synchronized void save(TyInvitationGiftEntity tyInvitationGift){
		tyInvitationGiftDao.save(tyInvitationGift);
	}
	
	@Override
	public void update(TyInvitationGiftEntity tyInvitationGift){
		tyInvitationGiftDao.update(tyInvitationGift);
	}
	
	@Override
	public void delete(Long id){
		tyInvitationGiftDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		tyInvitationGiftDao.deleteBatch(ids);
	}

	@Override
	public List<TyInvitationGiftEntity> queryListRand(Long modulId) {
		return tyInvitationGiftDao.queryListRand(modulId);
	}

	@Override
	public List<TyInvitationGiftEntity> queryByCode(Map<String, Object> map) {
		return tyInvitationGiftDao.queryByCode(map);
	}
	
}
