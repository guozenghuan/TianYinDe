package com.framework.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.dao.TsItemDao;
import com.framework.dao.TsItemsAndTotalDao;
import com.framework.dao.TsOrderDao;
import com.framework.dao.TsReportOrderDao;
import com.framework.dao.TyServiceDao;
import com.framework.entity.TsItemEntity;
import com.framework.entity.TsItemsAndTotalEntity;
import com.framework.entity.TsOrderEntity;
import com.framework.entity.TsReportOrderEntity;
import com.framework.entity.TyServiceEntity;
import com.framework.service.TsItemService;
import com.framework.utils.StringUtil;

@Service
public class TsItemServiceImpl implements TsItemService{
	@Autowired
	private TsItemDao tsItemdao;
	@Autowired
	private TyServiceDao tyServiceDao;
	@Autowired
	private TsOrderDao tsOrderDao;
	@Autowired
	private TsItemsAndTotalDao tsItemsAndTotalDao;
	@Autowired
	private TsReportOrderDao tsReportOrderDao;

	@Override
	public void saveList(TsItemEntity[] itemsEntity) {
		//新增商品列表
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for(TsItemEntity tsItem : itemsEntity){
			tsItem.setCreatetime(sdf.format(date)); 
			TyServiceEntity tyServiceEntity = tyServiceDao.queryObject(tsItem.getItemId()); 
			//业务员价格
			tsItem.setCost(tyServiceEntity.getUnitPrice()); 
			//单价成本
			if(!StringUtil.isEmpty(tyServiceEntity.getName())) {
				BigDecimal unit = new BigDecimal(tyServiceEntity.getName());
				tsItem.setUnit(unit);
			}
			//客户价格(售价)
			tsItem.setPrice(tyServiceEntity.getCostPrice());
			tsItemdao.save(tsItem);
		}
		//更新报表汇总
		TsItemEntity item = itemsEntity[0];
		TsItemsAndTotalEntity itemsAndTotalEntity = new TsItemsAndTotalEntity();
		itemsAndTotalEntity.setTsUserId(item.getTsUserId());
		itemsAndTotalEntity.setNumber(item.getNumber()); 
		
		//(行政审核)审核人员
		itemsAndTotalEntity.setAudit("暂无");
		//（财务）审核状态
		itemsAndTotalEntity.setFinance("未审核");
		//制单日期
		itemsAndTotalEntity.setCreatetime(sdf.format(date));
		
		tsItemsAndTotalDao.deleteByUN(item.getTsUserId(),item.getNumber());
		tsItemsAndTotalDao.save(itemsAndTotalEntity);
		TsReportOrderEntity reportOrderEntity = 
		tsReportOrderDao.queryObjectByUN(item.getTsUserId(), item.getNumber());
		reportOrderEntity.setStatus((long) 2);
		reportOrderEntity.setAuditor(itemsAndTotalEntity.getCostAll());
		reportOrderEntity.setCompanyProfit(itemsAndTotalEntity.getCompanyIncome());
		tsReportOrderDao.update(reportOrderEntity);
	}

	@Override
	public int queryObjectByNumber(String number) {
		return tsItemdao.queryObjectByNumber(number);
	}

	@Override
	public void updateList(TsItemEntity[] itemsEntity) {
		//更新商品列表
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		int i = 1;
		for(TsItemEntity tsItem : itemsEntity){
			if(i==1){
				tsItemdao.delete(tsItem.getNumber());
				i=0;
			}
			tsItem.setCreatetime(sdf.format(date));
			TyServiceEntity tyServiceEntity = tyServiceDao.queryObject(tsItem.getItemId());
			//业务员价格
			tsItem.setCost(tyServiceEntity.getUnitPrice());
			//单价成本
			if(!StringUtil.isEmpty(tyServiceEntity.getName())) {
				BigDecimal unit = new BigDecimal(tyServiceEntity.getName());
				tsItem.setUnit(unit);
			} 
			//客户价格(售价)
			tsItem.setPrice(tyServiceEntity.getCostPrice());
			tsItemdao.save(tsItem); 
		}
		
		//更新报表汇总
		TsItemEntity item = itemsEntity[0];
		TsItemsAndTotalEntity itemsAndTotalEntity = new TsItemsAndTotalEntity();
		itemsAndTotalEntity.setTsUserId(item.getTsUserId());
		itemsAndTotalEntity.setNumber(item.getNumber()); 
		
		//(行政审核)审核人员
		itemsAndTotalEntity.setAudit("暂无");
		//（财务）审核状态
		itemsAndTotalEntity.setFinance("未审核");
		//制单日期
		itemsAndTotalEntity.setCreatetime(sdf.format(date));
		
		tsItemsAndTotalDao.deleteByUN(item.getTsUserId(),item.getNumber());
		tsItemsAndTotalDao.save(itemsAndTotalEntity);
		TsReportOrderEntity reportOrderEntity = 
		tsReportOrderDao.queryObjectByUN(item.getTsUserId(), item.getNumber());
		reportOrderEntity.setStatus((long) 2);
		reportOrderEntity.setAuditor(itemsAndTotalEntity.getCostAll());
		reportOrderEntity.setCompanyProfit(itemsAndTotalEntity.getCompanyIncome());
		tsReportOrderDao.update(reportOrderEntity);

	}

	@Override
	public int queryItemsTotal(Long tsUserId, String number) {

		return tsItemdao.queryItemsTotal(tsUserId,number);
	}

	@Override
	public List<TsItemEntity> queryItemsList(Map<String, Object> map) {

		return tsItemdao.queryList(map);
	}

}
