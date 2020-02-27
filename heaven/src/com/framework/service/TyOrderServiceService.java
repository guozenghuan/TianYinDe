package com.framework.service;

import com.framework.entity.TyOrderServiceEntity;
import com.framework.utils.R;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 服务订单
 * 
 * @author R & D
 * @email 
 * @date 2019-04-08 16:38:46
 */
public interface TyOrderServiceService {
	
	TyOrderServiceEntity queryObject(Long id);
	
	List<TyOrderServiceEntity> queryObjectByKey(TyOrderServiceEntity tyOrderService);
	
	List<TyOrderServiceEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TyOrderServiceEntity tyOrderService);
	
	void update(TyOrderServiceEntity tyOrderService);
	
	R paiDan(Long orderId,Long tsUserId);
	
	R updatefw(Long orderId,BigDecimal price,String note,String fuwuIds, String deposit, int isCollect);
	
	R updateFuOk(Long orderId);
	
	R updateAuditState(Long id,Integer auditState);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

	List<TyOrderServiceEntity> queryListSure(Map<String, Object> map);

	int queryTotalSure(Map<String, Object> map);

	List<TyOrderServiceEntity> queryListByMap(Map<String, Object> map);

	Map<String, Object> checkDeposit(TyOrderServiceEntity tyOrderCommodity);
}
