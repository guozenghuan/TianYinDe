package com.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.framework.dao.TyInvitationToinviteDao;
import com.framework.entity.TcUserEntity;
import com.framework.entity.TyInvitationToinviteEntity;
import com.framework.service.TyInvitationToinviteService;



@Service("tyInvitationToinviteService")
public class TyInvitationToinviteServiceImpl implements TyInvitationToinviteService {
	@Autowired
	private TyInvitationToinviteDao tyInvitationToinviteDao;
	
	@Override
	public TyInvitationToinviteEntity queryObject(Long id){
		return tyInvitationToinviteDao.queryObject(id);
	}
	
	@Override
	public List<TyInvitationToinviteEntity> queryObjectByKey(TyInvitationToinviteEntity tyInvitationToinvite){
		return tyInvitationToinviteDao.queryObjectByKey(tyInvitationToinvite);
	}
	
	@Override
	public List<TyInvitationToinviteEntity> queryList(Map<String, Object> map){
		return tyInvitationToinviteDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tyInvitationToinviteDao.queryTotal(map);
	}
	
	@Override
	public void save(TyInvitationToinviteEntity tyInvitationToinvite){
		tyInvitationToinviteDao.save(tyInvitationToinvite);
	}
	
	@Override
	public void update(TyInvitationToinviteEntity tyInvitationToinvite){
		tyInvitationToinviteDao.update(tyInvitationToinvite);
	}
	
	@Override
	public void delete(Long id){
		tyInvitationToinviteDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		tyInvitationToinviteDao.deleteBatch(ids);
	}

	@Override
	public String nameModul(TcUserEntity tcUserEntity) {
		TyInvitationToinviteEntity tyInvitationToinvite = new TyInvitationToinviteEntity();
		tyInvitationToinvite.setUserId(tcUserEntity.getId());
		
		List<TyInvitationToinviteEntity> list = tyInvitationToinviteDao.queryObjectByKey(tyInvitationToinvite);
		if(list.isEmpty() || list.size() <= 0){
			return tcUserEntity.getNickname();
		}else{
			return list.get(0).getName();
		}
	}
	
}
