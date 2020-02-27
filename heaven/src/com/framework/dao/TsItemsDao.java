package com.framework.dao;

import java.util.List;

import com.framework.entity.TsItemEntity;
 

public interface TsItemsDao{

	int deleteByUN(Long tsUserId, String number);

	List<TsItemEntity> queryListByUN(Long tsUserId, String number);

}
