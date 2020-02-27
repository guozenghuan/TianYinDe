package com.framework.dao;

import java.util.List;

import com.framework.entity.TyInvitationCashmoneyEntity;

/**
 * 奠金
 * 
 * @author R & D
 * @email 
 * @date 2019-05-07 00:31:33
 */
public interface TyInvitationCashmoneyDao extends BaseDao<TyInvitationCashmoneyEntity> {
	List<TyInvitationCashmoneyEntity> queryListRand(Long modulId);
}
