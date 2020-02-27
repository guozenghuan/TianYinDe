package com.framework.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.framework.entity.TsSchedulingCommissionEntity;

public interface TsSchedulingCommissionDao extends BaseDao<TsSchedulingCommissionEntity>{
	String[] queryTsAccount();

	List<TsSchedulingCommissionEntity> queryListByMap(Map<String, Object> map);

	void deleteByUN(@Param("tsUserId")Long tsUserId,@Param("number") String number);

	TsSchedulingCommissionEntity queryTotalByUN(@Param("tsUserId")Long tsUserId,@Param("number") String number);

	BigDecimal queryMathSUMByUN(@Param("tsUserId")Long tsUserId, @Param("createtime")String createtime);
	
	int update(TsSchedulingCommissionEntity entity);

	TsSchedulingCommissionEntity queryObjectByKey(Map<String, Object> map);

	void updateByNumber(@Param("number")String number);
}
