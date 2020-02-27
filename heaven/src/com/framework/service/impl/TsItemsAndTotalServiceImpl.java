package com.framework.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.dao.TsItemDao;
import com.framework.dao.TsItemsAndTotalDao;
import com.framework.dao.TsOrderDao;
import com.framework.dao.TsReportOrderDao;
import com.framework.dao.TsSchedulingCommissionDao;
import com.framework.dao.TsSchedulingDao;
import com.framework.dao.TyOrderServiceDao;
import com.framework.entity.TsItemEntity;
import com.framework.entity.TsItemsAndTotalEntity;
import com.framework.entity.TsOrderEntity;
import com.framework.entity.TsReportOrderEntity;
import com.framework.entity.TsSchedulingCommissionEntity;
import com.framework.entity.TsSchedulingEntity;
import com.framework.entity.TyOrderServiceEntity;
import com.framework.service.TsItemsAndTotalService;
import com.framework.utils.R;
import com.framework.utils.StringUtil;

@Service
public class TsItemsAndTotalServiceImpl implements TsItemsAndTotalService{
	@Autowired
	private TsItemsAndTotalDao tsItemsAndTotalDao; 
	@Autowired
	private TsOrderDao tsOrderDao;
	@Autowired
	private TsItemDao tsItemDao;
	@Autowired
	private TsReportOrderDao tsReportOrderDao;
	@Autowired
	private TyOrderServiceDao tyOrderServiceDao;
	@Autowired
	private TsSchedulingCommissionDao tsSchedulingCommissionDao;
	@Autowired
	private TsSchedulingDao tsSchedulingDao;
	@Autowired
	private TsItemsAndTotalService tsItemsAndTotalService;

	@Override
	public TsItemsAndTotalEntity queryObjectByUN(Long tsUserId, String number) {

		return tsItemsAndTotalDao.queryObjectByUN(tsUserId, number);
	}

	@Override
	public R check(Long tsUserId, String number, Long check, String admin, BigDecimal b, String commission) {
		Map<String,Object> map = new HashMap<>();
		//当前审核状态
		TsReportOrderEntity reportOrderEntity = 
		tsReportOrderDao.queryObjectByUN(tsUserId,number);
		//如果审核，全部先清零所有提成
		if(reportOrderEntity.getStatus() == 0 || check == 1){
			try {
				tsSchedulingCommissionDao.updateByNumber(number);
			} catch (Exception e) {
				e.printStackTrace();
				return R.error("清空数据失败！");
			}
			try {
				tyOrderServiceDao.updateByNumber(number);
			} catch (Exception e) {
				e.printStackTrace();
				return R.error("清空数据失败！");
			} 
		} 
		//基准利率
		BigDecimal Bb = new BigDecimal(100);
		BigDecimal B = b.divide(Bb);
		//业务员提成率
		BigDecimal A = new BigDecimal(0.00);
		if(commission.compareTo("A")==0){
			A = B;
		}else if(commission.compareTo("AA")==0){
			A = new BigDecimal(0.25);
		}else if(commission.compareTo("AAA")==0){
			BigDecimal C = new BigDecimal(0.3);
			A = C.add(B);
		}else if(commission.compareTo("AAAA")==0){
			A = new BigDecimal(0.4);
		}else if(commission.compareTo("AAAAA")==0){
			BigDecimal C = new BigDecimal(0.5);
			BigDecimal n = new BigDecimal(1);
			TsOrderEntity tsOrder =  null ;
			try {
				tsOrder = tsOrderDao.queryObjectByUN(tsUserId, number); 
			} catch (Exception e) {
				e.printStackTrace();
				return R.error("tsOrderDao.queryObjectByUN失败！");
			}
		
			BigDecimal tsNumber = new BigDecimal(0.00);
			if(tsOrder.getTsNumber()>0){
				tsNumber = new BigDecimal(tsOrder.getTsNumber());
				A = B.add(((n.subtract(B)).multiply(C.divide(tsNumber,3,RoundingMode.HALF_UP))));
			}
		}
		TsItemsAndTotalEntity itemsAndTotalEntity = null;
		BigDecimal baseDC = null;
		try {
			itemsAndTotalEntity = tsItemsAndTotalDao.queryObjectByUN(tsUserId, number);
		    baseDC = tsItemDao.queryBaseDCByUN(tsUserId,number);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("ywytc提成计算失败！");
		}
		
		//业务员提成
		BigDecimal ywytc = baseDC.multiply(A);
		
		//审核人员
		itemsAndTotalEntity.setAudit(admin);
		Date date = new Date(); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM");
		if( check == 2){//预审核
			itemsAndTotalEntity.setFinance("预审中");
			//更新      业务员提成                  (通过则有4处)
			itemsAndTotalEntity.setSalCommission(ywytc);
			tsItemsAndTotalDao.update(itemsAndTotalEntity);

		}else if(check == 0){//审核通过
			itemsAndTotalEntity.setFinance("审核通过");
			itemsAndTotalEntity.setSalCommission(ywytc); 

			reportOrderEntity.setStatus((long)0);
			reportOrderEntity.setProfit(ywytc);
			reportOrderEntity.setChecktime(sdf.format(date));
			tsItemsAndTotalDao.update(itemsAndTotalEntity);
			tsReportOrderDao.update(reportOrderEntity);
			List<TsSchedulingEntity> tsSchedulingEntity = tsSchedulingDao.queryObjectByTS(tsUserId);
			TsSchedulingCommissionEntity entity = new TsSchedulingCommissionEntity();

			entity.setTsUserId(tsUserId);
			entity.setCompany(tsSchedulingEntity.get(0).getCompany());
			entity.setName(tsSchedulingEntity.get(0).getName());
			entity.setPhone(tsSchedulingEntity.get(0).getPhone());
			entity.setAccount(tsSchedulingEntity.get(0).getAccount());
			entity.setNumber(number);
			entity.setCreatetime(sd.format(date));
			entity.setCommission(ywytc);	
			TsSchedulingCommissionEntity h = 
					tsSchedulingCommissionDao.queryTotalByUN(tsUserId,number);
			if(StringUtil.isEmpty(h)) {
				tsSchedulingCommissionDao.save(entity);
				BigDecimal c = tsSchedulingCommissionDao.queryMathSUMByUN(tsUserId,sd.format(date));
				entity.setCommissionTotal(c);
				tsSchedulingCommissionDao.update(entity);
			} else {
				tsSchedulingCommissionDao.update(entity);
				BigDecimal c = tsSchedulingCommissionDao.queryMathSUMByUN(tsUserId,sd.format(date));
				entity.setCommissionTotal(c);
				tsSchedulingCommissionDao.update(entity);
			}
		    
			TyOrderServiceEntity orderServiceEntity = null;
			try {
				orderServiceEntity = tyOrderServiceDao.queryObjectByUN(number,tsUserId); 
			    
			} catch (Exception e) {
				e.printStackTrace();
				return R.error("queryObjectByUN错误！");
			} 
			orderServiceEntity.setAuditState(1);
			orderServiceEntity.setCommission(ywytc);
			try {
				tyOrderServiceDao.update(orderServiceEntity); 
			} catch (Exception e) {
				e.printStackTrace();
				return R.error("update orderServiceEntity 错误！");
			}

			//查询操作员  提成 
			List<TsItemEntity> tsSch = null;
			try {
				tsSch = tsItemDao.queryAccountList(tsUserId,number);
			} catch (Exception e) {
				e.printStackTrace();
				return R.error("queryAccountList 错误！");
			}
			if(!StringUtil.isEmpty(tsSch)){
				for(TsItemEntity ts : tsSch){
					String s = ts.getRemark().trim().substring(4);
					String[] sch = s.split(";|；");
					BigDecimal czytc = new BigDecimal(0.00); 
					BigDecimal length = new BigDecimal(sch.length); 
					czytc = ts.getCost().divide(length,2,RoundingMode.HALF_UP);
					//System.out.println("czytc:"+czytc);
					for(int i=0; i<sch.length;i++){
						String ch = sch[i];
						//校验账号的正误  可以拿去优化   tsItem
						int j = tsSchedulingDao.queryTotalByAccount(ch);
						if(j == 0 ){
							//? tsItem优化
						} else {
							//System.out.println(ch+":"+czytc);
							//更新工资处：
							map.put("account", ch);
							map.put("number", itemsAndTotalEntity.getNumber());
							TsSchedulingCommissionEntity tsSchedulingC = null;
							try {
								tsSchedulingC = tsSchedulingCommissionDao.queryObjectByKey(map); 
							} catch (Exception e) {
								e.printStackTrace();
								return R.error("tsSchedulingCommissionDao.queryObjectByKey 错误!");
							} 
							List<TsSchedulingEntity> schedulingE = null;
							try {
								schedulingE =  tsSchedulingDao.queryObjectByMap(map);
							} catch (Exception e) {
								e.printStackTrace();
								return R.error("queryObjectByMap tsSchedulingDao 错误！");
							}

							TsSchedulingCommissionEntity commissionEntity
							= new TsSchedulingCommissionEntity();
							BigDecimal c = new BigDecimal(0.00);
							if(tsSchedulingC == null){ 
								commissionEntity.setAccount(ch);
								commissionEntity.setCommission(czytc);
								commissionEntity.setCompany(schedulingE.get(0).getCompany());
								commissionEntity.setCreatetime(sd.format(date));
								commissionEntity.setName(schedulingE.get(0).getName());
								commissionEntity.setPhone(schedulingE.get(0).getPhone());
								commissionEntity.setTsUserId(schedulingE.get(0).getTsUserId());
								commissionEntity.setNumber(number);

								tsSchedulingCommissionDao.save(commissionEntity);
								c = tsSchedulingCommissionDao.queryMathSUMByUN(commissionEntity.getTsUserId(),sd.format(date));
								commissionEntity.setCommissionTotal(c);
								tsSchedulingCommissionDao.update(commissionEntity);
							} else {
								BigDecimal bbb = tsSchedulingC.getCommission().add(czytc);
								tsSchedulingC.setCommission(bbb);
								tsSchedulingC.setCreatetime(sd.format(date));
								tsSchedulingCommissionDao.update(tsSchedulingC);
								c = tsSchedulingCommissionDao.queryMathSUMByUN(tsSchedulingC.getTsUserId(),sd.format(date));
								tsSchedulingC.setCommissionTotal(c);
								tsSchedulingCommissionDao.update(tsSchedulingC);
							}  
							//更新操作员端：
							//							map.put("tsUserId", tsUserId);
							//							map.put("number", number);
							TyOrderServiceEntity tyOrderService =
									tyOrderServiceDao.queryObjectByUN(number,tsUserId);
							//		System.out.println("无此账号002！"+schedulingE.getTsUserId());
							int t = tyOrderServiceDao.queryTotalByCN(number,schedulingE.get(0).getTsUserId());
							if(t == 0){
								TyOrderServiceEntity tyOrder = new TyOrderServiceEntity(); 
								tyOrder.setTsUserid(schedulingE.get(0).getTsUserId());
								//tyOrder.setUserid(tyOrderService.getUserid());
								tyOrder.setName(tyOrderService.getName());
								tyOrder.setNumber(tyOrderService.getNumber());
								tyOrder.setPrice(tyOrderService.getPrice());
								tyOrder.setScore(tyOrderService.getScore());
								tyOrder.setEvaluate(tyOrderService.getEvaluate());
								tyOrder.setCommission(commissionEntity.getCommissionTotal());
								tyOrder.setCreatetime(date);
								//查询需要
								tyOrder.setStatus(tyOrderService.getServiceStatus());
								tyOrder.setUserStatus(tyOrderService.getUserStatus());
								tyOrder.setAuditState(tyOrderService.getAuditState()); 
								tyOrderServiceDao.save(tyOrder);
							} else { 
								TyOrderServiceEntity tyOrderS =
										tyOrderServiceDao.queryObjectByUN(number,schedulingE.get(0).getTsUserId());

								//查询需要
								tyOrderS.setStatus(tyOrderService.getServiceStatus());
								tyOrderS.setUserStatus(tyOrderService.getUserStatus());
								tyOrderS.setAuditState(tyOrderService.getAuditState()); 
								//System.out.println(c);
								tyOrderS.setCommission(c);
								tyOrderS.setCreatetime(date); 
								tyOrderServiceDao.update(tyOrderS); 
							}

						}
					}
				}
			}
			//业务员提成
			TsSchedulingCommissionEntity hh = 
					tsSchedulingCommissionDao.queryTotalByUN(tsUserId,number); 
			itemsAndTotalEntity.setSalCommission(hh.getCommission());
			itemsAndTotalEntity.setCostJ(ywytc);
			tsItemsAndTotalDao.update(itemsAndTotalEntity);

			//信息源&经办人 
			try {
				tsItemsAndTotalService.checkOrder(itemsAndTotalEntity,commission);
			} catch (Exception e) {
				e.printStackTrace();
				return R.error("checkOrder错误！");
			}

		}else if(check == 1){//审核未通过
			itemsAndTotalEntity.setFinance("审核未通过");
			itemsAndTotalEntity.setSalCommission(ywytc);
			reportOrderEntity.setStatus((long)1);
			reportOrderEntity.setProfit(ywytc);
			reportOrderEntity.setChecktime(sdf.format(date));
			tsItemsAndTotalDao.update(itemsAndTotalEntity);
			tsReportOrderDao.update(reportOrderEntity); 
		}
		return R.ok();
	}

	@Override
	public R checkOrder(TsItemsAndTotalEntity itemsAndTotalEntity,String commission) {
		Map<String,Object> map = new HashMap<>();
		Date date = new Date(); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM");

		TsOrderEntity tsorder = tsOrderDao.queryObjectByUN(itemsAndTotalEntity.getTsUserId(),itemsAndTotalEntity.getNumber());
		//服务总价格,包单总金
		BigDecimal totalPrice = tsorder.getTotal();
		if(!(totalPrice.compareTo(BigDecimal.ZERO)==0)){
			itemsAndTotalEntity.setTotalPrice(totalPrice);
		}
		//小计 公司成本   ?
		BigDecimal costTotal = tsItemDao.queryCostTotalSUM(itemsAndTotalEntity.getNumber());
		if(!(costTotal.compareTo(BigDecimal.ZERO)==0)){
			itemsAndTotalEntity.setCostTotal(costTotal);
		}
		//小计 现结
		BigDecimal cashTotal = tsItemDao.queryCashTotalSUM(itemsAndTotalEntity.getNumber());
		if(!(cashTotal.compareTo(BigDecimal.ZERO)==0)){
			itemsAndTotalEntity.setCashTotal(cashTotal);
		}

		//佣金
		if(!(tsorder.getRebate().compareTo(BigDecimal.ZERO)==0)){
			itemsAndTotalEntity.setComm(tsorder.getRebate());
		} 
		//佣金率
		if(!(tsorder.getRebate().compareTo(BigDecimal.ZERO)==0) && 
				!(totalPrice.compareTo(BigDecimal.ZERO)==0) ){
			BigDecimal comml = new BigDecimal(0.00);
			comml = tsorder.getRebate().divide(totalPrice,2,RoundingMode.HALF_UP);
			itemsAndTotalEntity.setComml(comml);
		}

		//对账实收 
		BigDecimal check = new BigDecimal(0.00);
		if(!(cashTotal.compareTo(BigDecimal.ZERO)==0)&&!(tsorder.getTotal().compareTo(BigDecimal.ZERO)==0)){
			check = tsorder.getTotal().subtract(cashTotal);
		} else {
			check = tsorder.getTotal();
		};
		itemsAndTotalEntity.setCheck(check);

		//成本 
		BigDecimal costA = new BigDecimal(0.00);
		costA = tsorder.getRebate().add(costTotal).add(cashTotal);
		itemsAndTotalEntity.setCostAll(costA);

		//毛利润   服务总价格 - 成本
		BigDecimal mly = new BigDecimal(0.00);
		if(!(costA.compareTo(BigDecimal.ZERO)==0)&& !(totalPrice.compareTo(BigDecimal.ZERO)==0)){
			mly = totalPrice.subtract(costA);
			itemsAndTotalEntity.setMly(mly);
		}

		//毛利润率  毛利润/服务总价格 
		BigDecimal mll = new BigDecimal(0.00);
		if(!(mly.compareTo(BigDecimal.ZERO)==0)&&!(totalPrice.compareTo(BigDecimal.ZERO)==0)){
			mll = mly.divide(totalPrice,2,RoundingMode.HALF_UP);
			itemsAndTotalEntity.setMlly(mll);
		}

		//信息源:
		//提成 率
		BigDecimal costX = new BigDecimal(0.00);
		if(commission.compareTo("AAA") == 0) {
			BigDecimal tcx = new BigDecimal(0.3);
			itemsAndTotalEntity.setTcx(tcx);
			//信息源提成  毛利润 *提成 率  
			costX = mly.multiply(tcx);
			itemsAndTotalEntity.setCostX(costX);
			//信息源为内部人员   信息源：业务员账号
			List<TsItemEntity> tsSch = tsItemDao.queryRemakeList
					(itemsAndTotalEntity.getTsUserId(),itemsAndTotalEntity.getNumber()); 
			if(!StringUtil.isEmpty(tsSch)) {
				for(TsItemEntity ts :tsSch) {
					String s = ts.getRemark().trim().substring(4); 
					//更新工资处：
					map.put("account", s);
					map.put("tsUserId", itemsAndTotalEntity.getTsUserId());
					map.put("number", itemsAndTotalEntity.getNumber());
					TsSchedulingCommissionEntity tsSchedulingC = 
							tsSchedulingCommissionDao.queryObjectByKey(map);
					List<TsSchedulingEntity> schedulingE = 
							tsSchedulingDao.queryObjectByMap(map);
					TsSchedulingCommissionEntity commissionEntity
					= new TsSchedulingCommissionEntity();
					BigDecimal c = new BigDecimal(0.00);
					if(tsSchedulingC == null){ 
						commissionEntity.setAccount(s);
						commissionEntity.setCommission(costX);
						commissionEntity.setCompany(schedulingE.get(0).getCompany());
						commissionEntity.setCreatetime(sd.format(date));
						commissionEntity.setName(schedulingE.get(0).getName());
						commissionEntity.setPhone(schedulingE.get(0).getPhone());
						commissionEntity.setTsUserId(schedulingE.get(0).getTsUserId());
						commissionEntity.setNumber(itemsAndTotalEntity.getNumber());

						tsSchedulingCommissionDao.save(commissionEntity);
						c = tsSchedulingCommissionDao.queryMathSUMByUN(commissionEntity.getTsUserId(),sd.format(date));
						commissionEntity.setCommissionTotal(c);
						tsSchedulingCommissionDao.update(commissionEntity);
					} else {
						BigDecimal bbb = tsSchedulingC.getCommission().add(costX);
						tsSchedulingC.setCommission(bbb);
						tsSchedulingC.setCreatetime(sd.format(date));
						tsSchedulingCommissionDao.update(tsSchedulingC);
						c = tsSchedulingCommissionDao.queryMathSUMByUN(tsSchedulingC.getTsUserId(),sd.format(date));
						tsSchedulingC.setCommissionTotal(c);
						tsSchedulingCommissionDao.update(tsSchedulingC);
					} 

					//更新操作员端：
					//							map.put("tsUserId", tsUserId);
					//							map.put("number", number);
					TyOrderServiceEntity tyOrderService =
							tyOrderServiceDao.queryObjectByUN(itemsAndTotalEntity.getNumber(),itemsAndTotalEntity.getTsUserId());
					//		System.out.println("无此账号002！"+schedulingE.getTsUserId());
					int t = tyOrderServiceDao.queryTotalByCN(itemsAndTotalEntity.getNumber(),schedulingE.get(0).getTsUserId());
					if(t == 0){
						TyOrderServiceEntity tyOrder = new TyOrderServiceEntity(); 
						tyOrder.setTsUserid(schedulingE.get(0).getTsUserId());
						//tyOrder.setUserid(tyOrderService.getUserid());
						tyOrder.setName(tyOrderService.getName());
						tyOrder.setNumber(tyOrderService.getNumber());
						tyOrder.setPrice(tyOrderService.getPrice());
						tyOrder.setScore(tyOrderService.getScore());
						tyOrder.setEvaluate(tyOrderService.getEvaluate());
						tyOrder.setCommission(commissionEntity.getCommissionTotal());
						tyOrder.setCreatetime(date);
						//查询需要
						tyOrder.setStatus(tyOrderService.getServiceStatus());
						tyOrder.setUserStatus(tyOrderService.getUserStatus());
						tyOrder.setAuditState(tyOrderService.getAuditState()); 
						tyOrderServiceDao.save(tyOrder);
					} else { 
						TyOrderServiceEntity tyOrderS =
								tyOrderServiceDao.queryObjectByUN(itemsAndTotalEntity.getNumber(),schedulingE.get(0).getTsUserId());

						//查询需要
						tyOrderS.setStatus(tyOrderService.getServiceStatus());
						tyOrderS.setUserStatus(tyOrderService.getUserStatus());
						tyOrderS.setAuditState(tyOrderService.getAuditState()); 
						//System.out.println(c);
						tyOrderS.setCommission(c);
						tyOrderS.setCreatetime(date); 
						tyOrderServiceDao.update(tyOrderS); 
					}
				}

			}

		} else {
			itemsAndTotalEntity.setCostX(costX);
		}

		//净利润   毛利润-信息源提成
		BigDecimal jly = new BigDecimal(0.00);
		jly = mly.subtract(costX);
		itemsAndTotalEntity.setJly(jly);

		//净利润率   净利润 /服务总价格 
		BigDecimal jll = new BigDecimal(0.00);
		if(!(jly.compareTo(BigDecimal.ZERO)==0) && !(totalPrice.compareTo(BigDecimal.ZERO)==0)){
			jll = jly.divide(totalPrice,2,RoundingMode.HALF_UP);
			itemsAndTotalEntity.setJll(jll);
		}
		//经办人：
		//提成率
		//		BigDecimal tcj = new BigDecimal(0.24);
		//		itemsAndTotalEntity.setTcj(tcj);
		//		//经办人提成     净利润  *提成 率
		//		BigDecimal costJ = new BigDecimal(0.00);
		//		if(!(jly.compareTo(BigDecimal.ZERO)==0) && !(tcj.compareTo(BigDecimal.ZERO)==0)){
		//			costJ = jly.multiply(tcj);
		//			itemsAndTotalEntity.setCostJ(costJ);
		//		}

		//
		//公司收入  净利润   - 经办人提成
		BigDecimal companyIncom = new BigDecimal(0.00);
		if(!(jly.compareTo(BigDecimal.ZERO)==0)&& !(itemsAndTotalEntity.getCostJ().compareTo(BigDecimal.ZERO)==0)){
			companyIncom = jly.subtract(itemsAndTotalEntity.getCostJ());
			itemsAndTotalEntity.setCompanyIncome(companyIncom);
		}
		//现结 合计  经办人提成 +信息源提成 
		if(!(costX.compareTo(BigDecimal.ZERO)==0)&&!(itemsAndTotalEntity.getCostJ().compareTo(BigDecimal.ZERO)==0)){
			BigDecimal cashxj = costX.add(itemsAndTotalEntity.getCostJ());
			itemsAndTotalEntity.setCashXJ(cashxj);
		}
		//后勤人员提成  
		BigDecimal hjrytc = tsItemDao.queryHjrytcByUN(tsorder.getTsUserId(),tsorder.getNumber());
		if(!(hjrytc.compareTo(BigDecimal.ZERO)==0)){
			itemsAndTotalEntity.setHjrytc(hjrytc);
		} 

		//行政利润  SUM（count*（cost - unit））
		BigDecimal b = tsItemDao.queryAProfitSUM(itemsAndTotalEntity.getNumber());
		if(!(b.compareTo(BigDecimal.ZERO)==0)){
			itemsAndTotalEntity.setAdProfit(b);
		}  

		tsItemsAndTotalDao.deleteByUN(itemsAndTotalEntity.getTsUserId(),itemsAndTotalEntity.getNumber());
		tsItemsAndTotalDao.save(itemsAndTotalEntity);
		TsReportOrderEntity reportOrderEntity = 
				tsReportOrderDao.queryObjectByUN(itemsAndTotalEntity.getTsUserId(), itemsAndTotalEntity.getNumber());
		//reportOrderEntity.setStatus((long) 2);
		reportOrderEntity.setAuditor(itemsAndTotalEntity.getCostAll());
		reportOrderEntity.setCompanyProfit(itemsAndTotalEntity.getCompanyIncome());
		tsReportOrderDao.update(reportOrderEntity);

		return null;
	}

}
