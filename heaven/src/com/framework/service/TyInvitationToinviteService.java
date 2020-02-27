package com.framework.service;

import com.framework.entity.TcUserEntity;
import com.framework.entity.TyInvitationToinviteEntity;

import java.util.List;
import java.util.Map;

/**
 * 赴邀
 * 
 * @author R & D
 * @email 
 * @date 2019-05-07 00:31:33
 */
public interface TyInvitationToinviteService {
	
	TyInvitationToinviteEntity queryObject(Long id);
	
	List<TyInvitationToinviteEntity> queryObjectByKey(TyInvitationToinviteEntity tyInvitationToinvite);
	
	List<TyInvitationToinviteEntity> queryList(Map<String, Object> map);
	
	String nameModul(TcUserEntity tcUserEntity);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TyInvitationToinviteEntity tyInvitationToinvite);
	
	void update(TyInvitationToinviteEntity tyInvitationToinvite);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
