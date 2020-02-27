package com.framework.service;

import java.util.Map;

import com.framework.entity.TsItemEntity;
import com.framework.entity.TsItemsAndTotalEntity;

import com.framework.entity.TsOrderEntity;

public interface TsUploadOrderService {

	boolean deleteUploadOrder(Long tsUserId, String number);

	Map<String, Object> infoOrder(Long tsUserId, String number);

	void uploadOrder(TsItemEntity tsItems, TsOrderEntity tsOrder, TsItemsAndTotalEntity itemsAndTotal);

}
