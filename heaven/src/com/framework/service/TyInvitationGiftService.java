package com.framework.service;

import com.framework.entity.TyInvitationGiftEntity;

import java.util.List;
import java.util.Map;

/**
 * 赠送平台礼物
 * 
 * @author R & D
 * @email 
 * @date 2019-05-07 00:31:33
 */
public interface TyInvitationGiftService {
	
	TyInvitationGiftEntity queryObject(Long id);
	
	List<TyInvitationGiftEntity> queryObjectByKey(TyInvitationGiftEntity tyInvitationGift);
	
	List<TyInvitationGiftEntity> queryList(Map<String, Object> map);
	
	List<TyInvitationGiftEntity> queryListRand(Long modulId);
	
	List<TyInvitationGiftEntity> queryByCode(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TyInvitationGiftEntity tyInvitationGift);
	
	void update(TyInvitationGiftEntity tyInvitationGift);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
