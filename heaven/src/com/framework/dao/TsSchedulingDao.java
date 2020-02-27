package com.framework.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.framework.entity.TsCompanyEntity;
import com.framework.entity.TsSchedulingEntity;
import com.framework.entity.TsUserEntity;

/**
 * 业务员排班
 * @author GZH
 *@Date s2019-12-16 11:29:58
 */
public interface TsSchedulingDao extends BaseDao<TsSchedulingEntity>{

	List<TsSchedulingEntity> queryListObject(@Param("page")Integer page, @Param("limit")Integer limit);

	void saveYWY(TsSchedulingEntity tsScheduling);

	TsSchedulingEntity queryObjectByUserId(@Param("id")Long id);
	//@Param("tsUserId")
    List<TsSchedulingEntity>  queryObjectByTS(@Param("tsUserId")Long tsUserId);
	
	int update (TsSchedulingEntity tsScheduling);
	
	void delete (@Param("tsUserId")Long tsUserId);
	
	void deleteBatch(Long[] id);
	
	List<TsSchedulingEntity> queryListByDate(String scheduling);

	Long querySchedulingByDate(String scheduling);

	List<Long> queryListUserIdByDate(String dateStr);

	int queryTotalAll();

	int queryTotalByAccount(@Param("ch")String ch);

	List<TsSchedulingEntity> queryObjectByMap(Map<String, Object> map);

	List<TsSchedulingEntity> queryObjectByKey(Map<String, Object> map);

	TsSchedulingEntity[] queryListObjectByTS(@Param("tsUserId")Long tsUserId);

	void deleteById(@Param("id")Long id);

	String[] queryTsScheduling(@Param("scheduling")String scheduling, @Param("tsUserId")Long tsUserId);



	
}





