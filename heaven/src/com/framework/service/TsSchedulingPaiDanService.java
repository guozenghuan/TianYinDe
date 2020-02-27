package com.framework.service;

import java.util.List;

import com.framework.entity.TsSchedulingEntity;
import com.framework.entity.TsSchedulingPaiDanEntity;

public interface TsSchedulingPaiDanService {

	int queryDate(String dateStr);

	void saveDate(List<TsSchedulingEntity> tsUserIds);

	void deleteDate();

	List<TsSchedulingPaiDanEntity> queryDateMin();

	void addNumber(TsSchedulingPaiDanEntity  tsUserId);

}
