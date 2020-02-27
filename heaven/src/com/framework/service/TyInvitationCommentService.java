package com.framework.service;

import com.framework.entity.TyInvitationCommentEntity;

import java.util.List;
import java.util.Map;

/**
 * 模板评论
 * 
 * @author R & D
 * @email 
 * @date 2019-05-07 00:31:33
 */
public interface TyInvitationCommentService {
	
	TyInvitationCommentEntity queryObject(Long id);
	
	List<TyInvitationCommentEntity> queryObjectByKey(TyInvitationCommentEntity tyInvitationComment);
	
	List<TyInvitationCommentEntity> queryList(Map<String, Object> map);
	
	List<TyInvitationCommentEntity> queryListRand(Long modulId);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TyInvitationCommentEntity tyInvitationComment);
	
	void update(TyInvitationCommentEntity tyInvitationComment);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
