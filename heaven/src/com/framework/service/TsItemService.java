package com.framework.service;

import java.util.List;
import java.util.Map;

import com.framework.entity.TsItemEntity;


public interface TsItemService {

	void saveList(TsItemEntity[] itemsEntity);

	int queryObjectByNumber(String number);

	void updateList(TsItemEntity[] itemsEntity);

	int queryItemsTotal(Long tsUserId, String number);

	List<TsItemEntity> queryItemsList(Map<String, Object> map);

}
