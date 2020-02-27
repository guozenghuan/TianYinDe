package com.framework.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.framework.entity.TsSchedulingPaiDanEntity;

public interface TsSchedulingPaiDanDao extends BaseDao<TsSchedulingPaiDanEntity>{

	int queryDate(String dateStr);

	void deleteAll();

	void addNumber(TsSchedulingPaiDanEntity  tsUser);

	List<TsSchedulingPaiDanEntity> queryDateMin();

	void savePaiDan(TsSchedulingPaiDanEntity tsUserId);

	TsSchedulingPaiDanEntity queryObjectByUS(@Param("tsUserId")Long tsUserId,@Param("tsScheduling") String tsScheduling);

	void deleteById(@Param("tsUserId")Long tsUserId);

}
