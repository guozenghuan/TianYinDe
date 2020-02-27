package com.framework.dao;

import java.util.List;

import com.framework.entity.TyInvitationCommentEntity;

/**
 * 模板评论
 * 
 * @author R & D
 * @email 
 * @date 2019-05-07 00:31:33
 */
public interface TyInvitationCommentDao extends BaseDao<TyInvitationCommentEntity> {
	List<TyInvitationCommentEntity> queryListRand(Long modulId);
}
