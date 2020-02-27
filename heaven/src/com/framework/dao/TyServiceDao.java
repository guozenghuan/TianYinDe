package com.framework.dao;

import java.util.List;

import com.framework.entity.TyServiceEntity;

/**
 * 服务
 * 
 * @author R & D
 * @email 
 * @date 2019-03-24 18:53:52
 */
public interface TyServiceDao extends BaseDao<TyServiceEntity> {
	List<TyServiceEntity> queryObjectByKeySort(TyServiceEntity tyServiceEntity);
	List<TyServiceEntity> queryObjectByKeySortUser(TyServiceEntity tyServiceEntity);
	void deleteFwAll(Long id);
	
	TyServiceEntity shangyiMain(Integer id);
	TyServiceEntity xiayiMain(Integer id);
	
	TyServiceEntity shangyiMainScd(TyServiceEntity tyServiceEntity);
	TyServiceEntity xiayiMainScd(TyServiceEntity tyServiceEntity);
}
