package com.framework.service;

import com.framework.entity.TyOrderCommodityEntity;
import com.framework.utils.R;

import java.util.List;
import java.util.Map;

/**
 * 商品订单
 * 
 * @author R & D
 * @email 
 * @date 2019-03-27 13:52:37
 */
public interface TyOrderCommodityService {
	
	TyOrderCommodityEntity queryObject(Long id);
	
	List<TyOrderCommodityEntity> queryObjectKey(TyOrderCommodityEntity tyOrderCommodity);
	
	List<TyOrderCommodityEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TyOrderCommodityEntity tyOrderCommodity);
	
	void update(TyOrderCommodityEntity tyOrderCommodity);
	
	R sendGoods(TyOrderCommodityEntity tyOrderCommodity);
	
	R refund(Long id);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
