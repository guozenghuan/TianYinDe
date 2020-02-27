package com.framework.service;

import com.framework.entity.TyInvitationCashmoneyEntity;

import java.util.List;
import java.util.Map;

/**
 * 奠金
 * 
 * @author R & D
 * @email 
 * @date 2019-05-07 00:31:33
 */
public interface TyInvitationCashmoneyService {
	
	TyInvitationCashmoneyEntity queryObject(Long id);
	
	List<TyInvitationCashmoneyEntity> queryObjectByKey(TyInvitationCashmoneyEntity tyInvitationCashmoney);
	
	List<TyInvitationCashmoneyEntity> queryList(Map<String, Object> map);
	
	List<TyInvitationCashmoneyEntity> queryListRand(Long modulId);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TyInvitationCashmoneyEntity tyInvitationCashmoney);
	
	void update(TyInvitationCashmoneyEntity tyInvitationCashmoney);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
