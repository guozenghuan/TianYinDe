package com.framework.service;

import com.framework.entity.TsOrderEntity;

public interface TsOrderService {

	void save(TsOrderEntity order);

	TsOrderEntity info(Long tsUserId, String number);

	int queryPageByUN(Long tsUserId, String number);

	void update(TsOrderEntity order);

}
