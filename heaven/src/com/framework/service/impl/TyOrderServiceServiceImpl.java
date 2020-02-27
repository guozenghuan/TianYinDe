package com.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.framework.dao.TcUserDao;
import com.framework.dao.TsUserDao;
import com.framework.dao.TyOrderServiceDao;
import com.framework.dao.TyServiceDao;
import com.framework.dao.TyUserServiceDao;
import com.framework.entity.TcUserEntity;
import com.framework.entity.TsUserEntity;
import com.framework.entity.TyOrderServiceEntity;
import com.framework.entity.TyServiceEntity;
import com.framework.entity.TyUserServiceEntity;
import com.framework.service.TyOrderServiceService;
import com.framework.service.TyServiceService;
import com.framework.service.TyUserServiceService;
import com.framework.utils.R;
import com.framework.utils.StringUtil;
import com.framework.utils.TransactionRollBack;
import com.framework.utils.wechat.ModulMsgByOpenId;



@Service("tyOrderServiceService")
public class TyOrderServiceServiceImpl implements TyOrderServiceService {
	@Autowired
	private TyOrderServiceDao tyOrderServiceDao;
	
	@Autowired
	private TsUserDao tsUserDao;
	
	@Autowired
	private TcUserDao tcUserDao;
	
	@Autowired
	private TyServiceDao tyServiceDao;
	
	@Autowired
	private TyUserServiceDao tyUserServiceDao;
	
	@Override
	public TyOrderServiceEntity queryObject(Long id){
		return tyOrderServiceDao.queryObject(id);
	}
	
	@Override
	public List<TyOrderServiceEntity> queryObjectByKey(TyOrderServiceEntity tyOrderService){
		return tyOrderServiceDao.queryObjectByKey(tyOrderService);
	}
	
	@Override
	public List<TyOrderServiceEntity> queryList(Map<String, Object> map){
		return tyOrderServiceDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tyOrderServiceDao.queryTotal(map);
	}
	
	@Override
	public void save(TyOrderServiceEntity tyOrderService){
		tyOrderServiceDao.save(tyOrderService);
	}
	
	@Override
	public void update(TyOrderServiceEntity tyOrderService){
		tyOrderServiceDao.update(tyOrderService);
	}
	
	@Override
	public void delete(Long id){
		tyOrderServiceDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		tyOrderServiceDao.deleteBatch(ids);
	}

	@Transactional
	@Override
	public synchronized R paiDan(Long orderId, Long tsUserId) {
		if(StringUtil.isEmpty(orderId) || StringUtil.isEmpty(tsUserId)){
			return R.error("获取不到订单或业务员信息,请刷新后重新操作!");
		}
		
		//判断业务员信息
		TsUserEntity tsUserEntity = tsUserDao.queryObject(tsUserId);
		if(StringUtil.isEmpty(tsUserEntity)){
			return R.error("获取不到业务员信息,请刷新后重新操作!");
		}
		
		//判断该订单信息
		TyOrderServiceEntity tServiceEntity = tyOrderServiceDao.queryObject(orderId);
		if(StringUtil.isEmpty(tServiceEntity)){
			return R.error("获取不到订单信息,请刷新后重新操作!");
		}
		
		if(tServiceEntity.getStatus() == 1){
			return R.error("该订单服务已经结束,不可继续操作!");
		}
		if(tServiceEntity.getTsUserStatus() == 1){
			return R.error("该订单已经派送业务员,不可重复派送,刷新当前页面后可点击修改业务员!");
		}
		
		//派单业务员
		tServiceEntity.setTsUserStatus(1);
		tServiceEntity.setTsUserid(tsUserEntity.getId());
		tyOrderServiceDao.update(tServiceEntity);
		
		//获取用户Openid
		TcUserEntity tcUserEntity = tcUserDao.queryObject(tServiceEntity.getUserid());
		
		if(tcUserEntity != null && !StringUtil.isEmpty(tcUserEntity.getOpenid())){
			//服务预约派单通知(通知用户)
			ModulMsgByOpenId.serviceOrderNotice(tcUserEntity.getOpenid(),tsUserEntity.getName(),tsUserEntity.getPhone());
		}
		
		if(!StringUtil.isEmpty(tsUserEntity.getWechatOpenid())){
			//新派单提醒(通知业务员)
			ModulMsgByOpenId.paiDanServiceNew(tsUserEntity.getWechatOpenid(),tServiceEntity.getNumber(),tServiceEntity.getName(),tServiceEntity.getPhone());
		}
		
		return R.ok("操作成功!");
	}

	@Transactional
	@Override
	public synchronized R updatefw(Long orderId, BigDecimal price, String note, String fuwuIds, String deposit, int isCollect) {
		//判断订单是否完成
		TyOrderServiceEntity oServiceEntity = tyOrderServiceDao.queryObject(orderId);
		if(StringUtil.isEmpty(oServiceEntity)){
			return R.error("查找不到该订单信息!");
		}
		//oServiceEntity.getPid()==0 && 空指针异常
		if( oServiceEntity.getStatus() == 1  ){
			return R.error("订单已经服务结束,不可修改订单的信息!");
		}
		//oServiceEntity.getPid()==0 && 空指针异常
		if( oServiceEntity.getPriceStatus() == 1  ){
			return R.error("该订单用户已经付了服务款,不可修改订单的信息!");
		}
		
		//判断价格是否小于等于0  && oServiceEntity.getPid()==0
		if(price.compareTo(BigDecimal.ZERO) == -1 ){
			return R.error("服务总价必须要大于等于0!");
		}
		
		//删除所有服务恓
		tyUserServiceDao.deleteByOrderId(orderId);
		
		if(!StringUtil.isEmpty(fuwuIds)){
			if(StringUtil.isIndexOfStr(fuwuIds, ",")){
				String[] fuwuIdsStr = fuwuIds.split(",");
				for(String fuwu : fuwuIdsStr){
					if(!StringUtil.isEmpty(fuwu)){
						//查找该服务信息是否存在
						TyServiceEntity serviceEntity = tyServiceDao.queryObject(Long.valueOf(fuwu));
						if(StringUtil.isEmpty(serviceEntity)){
							//回滚数据
							TransactionRollBack.rollBackUpdate();
							return R.error("没有该服务信息详情,请重新选择服务信息详情!");
						}else{
							//添加服务信息
							TyUserServiceEntity tyUserServiceEntity = new TyUserServiceEntity();
							tyUserServiceEntity.setParentId(oServiceEntity.getId());//服务订单id
							tyUserServiceEntity.setType("fuwu");
							String s = null;
							if(!StringUtil.isEmpty(serviceEntity.getCostPrice())) {
							  s = serviceEntity.getCostPrice().stripTrailingZeros().toPlainString();
							} else {
							  s = "0.00";
							}
							tyUserServiceEntity.setName(s);//该服务价格
							tyUserServiceEntity.setText(serviceEntity.getText());//服务详情
							tyUserServiceEntity.setSort(Integer.valueOf(serviceEntity.getId().toString()));//关联的服务id
							tyUserServiceDao.save(tyUserServiceEntity);
						}
					}
				}
			}
		}
		
		//修改服务订单信息
		oServiceEntity.setPrice(price);
		if(oServiceEntity.getDepStu() == 0 && isCollect == 1) {
		 	oServiceEntity.setDeposit(deposit);
		}
		oServiceEntity.setIsCollect(isCollect);
		if(!StringUtil.isEmpty(note) && !note.trim().equals("undefined")){
			oServiceEntity.setNote(note);
		}
        //fwz
		oServiceEntity.setFwz(3);
		oServiceEntity.setServiceStatus(1);
		tyOrderServiceDao.update(oServiceEntity);
		
		return R.ok("操作成功!");
	}

	@Override
	public R updateFuOk(Long orderId) {
		//TyOrderServiceEntity tyOrderServiceEntity = new TyOrderServiceEntity();
		//tyOrderServiceEntity.setPriceStatus(1);//线下付款
		TyOrderServiceEntity orderServiceEntity =tyOrderServiceDao.queryObject(orderId);
		orderServiceEntity.setId(orderId);
		if(orderServiceEntity.getPriceStatus()==2) {
			orderServiceEntity.setStatus(1);
		}
		orderServiceEntity.setFwz(1);
		orderServiceEntity.setAuditState(0);
		tyOrderServiceDao.update(orderServiceEntity);
		return R.ok("操作成功!");
	}

	@Override
	public R updateAuditState(Long id, Integer auditState) {
		TyOrderServiceEntity tyOrderServiceEntity = new TyOrderServiceEntity();
		tyOrderServiceEntity.setId(id);
		tyOrderServiceEntity.setAuditState(auditState);
		tyOrderServiceDao.update(tyOrderServiceEntity);
		return R.ok("操作成功!");
	}

	@Override
	public List<TyOrderServiceEntity> queryListSure(Map<String, Object> map) {
		
		return tyOrderServiceDao.queryListSure(map);
	}

	@Override
	public int queryTotalSure(Map<String, Object> map) {
		
		return tyOrderServiceDao.queryTotalSure(map);
	}

	@Override
	public List<TyOrderServiceEntity> queryListByMap(Map<String, Object> map) {
		 
		return tyOrderServiceDao.queryListByMap(map);
	}

	@Override
	public Map<String, Object> checkDeposit(TyOrderServiceEntity tyOrderCommodity) {
		Map<String, Object> map = new HashMap<>();
		TyOrderServiceEntity tyOrderServiceEntity = tyOrderServiceDao.queryObjectByUN
		(tyOrderCommodity.getNumber(),tyOrderCommodity.getTsUserid());
		if(tyOrderServiceEntity.getDepStu() == 0) {
			
		}
		
		return null;
	}
	
	
}
