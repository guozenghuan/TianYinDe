package com.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.framework.dao.TyInvitationCommentDao;
import com.framework.entity.TyInvitationCommentEntity;
import com.framework.service.TyInvitationCommentService;



@Service("tyInvitationCommentService")
public class TyInvitationCommentServiceImpl implements TyInvitationCommentService {
	@Autowired
	private TyInvitationCommentDao tyInvitationCommentDao;
	
	@Override
	public TyInvitationCommentEntity queryObject(Long id){
		return tyInvitationCommentDao.queryObject(id);
	}
	
	@Override
	public List<TyInvitationCommentEntity> queryObjectByKey(TyInvitationCommentEntity tyInvitationComment){
		return tyInvitationCommentDao.queryObjectByKey(tyInvitationComment);
	}
	
	@Override
	public List<TyInvitationCommentEntity> queryList(Map<String, Object> map){
		return tyInvitationCommentDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tyInvitationCommentDao.queryTotal(map);
	}
	
	@Override
	public void save(TyInvitationCommentEntity tyInvitationComment){
		tyInvitationCommentDao.save(tyInvitationComment);
	}
	
	@Override
	public void update(TyInvitationCommentEntity tyInvitationComment){
		tyInvitationCommentDao.update(tyInvitationComment);
	}
	
	@Override
	public void delete(Long id){
		tyInvitationCommentDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		tyInvitationCommentDao.deleteBatch(ids);
	}

	@Override
	public List<TyInvitationCommentEntity> queryListRand(Long modulId) {
		return tyInvitationCommentDao.queryListRand(modulId);
	}
	
}
