package com.framework.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.framework.entity.TsItemEntity;

public interface TsItemDao extends BaseDao<TsItemEntity>{
	
	void deleteByUN(@Param("tsUserId")Long tsUserId,@Param("number")String number);
	
	void save(TsItemEntity tsItem);
    
	int update(TsItemEntity tsItem);
    
    int queryObjectByNumber(@Param("number")String number);
   
    void delete(@Param("number")String number);

	int queryItemsTotal(@Param("tsUserId")Long tsUserId, @Param("number")String number);

	BigDecimal queryCostTotalSUM(@Param("number")String number);

	BigDecimal queryCashTotalSUM(@Param("number")String number);

	BigDecimal  queryAProfitSUM(@Param("number")String number);

	BigDecimal queryBaseDCByUN(@Param("tsUserId")Long tsUserId, @Param("number")String number);

	BigDecimal queryHjrytcByUN(@Param("tsUserId")Long tsUserId, @Param("number")String number);

	List<TsItemEntity> queryAccountList(@Param("tsUserId")Long tsUserId, @Param("number")String number);

	List<TsItemEntity> queryRemakeList(@Param("tsUserId")Long tsUserId, @Param("number")String number);

}
