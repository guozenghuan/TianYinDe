package com.framework.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.framework.entity.TyOrderServiceEntity;

/**
 * 服务订单
 * 
 * @author R & D
 * @email 
 * @date 2019-04-08 16:38:46
 */
public interface TyOrderServiceDao extends BaseDao<TyOrderServiceEntity> {

	List<TyOrderServiceEntity> queryListSure(Map<String, Object> map);

	int queryTotalSure(Map<String, Object> map);
	
	TyOrderServiceEntity queryObjectByUN(@Param("number") String number,@Param("tsUserId")Long tsUserId);

	void updateByNumber(@Param("number")String number);

	int queryTotalByCN(@Param("number") String number,@Param("tsUserId")Long tsUserId);

	List<TyOrderServiceEntity> queryListByMap(Map<String, Object> map);
}
