package com.framework.dao;

import java.util.List;
import java.util.Map;

import com.framework.entity.TyInvitationGiftEntity;

/**
 * 赠送平台礼物
 * 
 * @author R & D
 * @email 
 * @date 2019-05-07 00:31:33
 */
public interface TyInvitationGiftDao extends BaseDao<TyInvitationGiftEntity> {
	List<TyInvitationGiftEntity> queryListRand(Long modulId);
	List<TyInvitationGiftEntity> queryByCode(Map<String, Object> map);
}
