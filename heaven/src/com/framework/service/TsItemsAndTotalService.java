package com.framework.service;

import java.math.BigDecimal;

import com.framework.entity.TsItemsAndTotalEntity;
import com.framework.entity.TyOrderServiceEntity;
import com.framework.utils.R;

public interface TsItemsAndTotalService{

	TsItemsAndTotalEntity queryObjectByUN(Long tsUserId, String number);

	R check(Long tsUserId, String number, Long check, String admin, BigDecimal b, String commission);
 
	R checkOrder(TsItemsAndTotalEntity orderServiceEntity,String commission);

}
