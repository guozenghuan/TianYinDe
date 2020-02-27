package com.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.framework.dao.TbFlowingRecordDao;
import com.framework.dao.TyOrderCommodityDao;
import com.framework.entity.TbFlowingRecordEntity;
import com.framework.entity.TcUserEntity;
import com.framework.entity.TyOrderCommodityEntity;
import com.framework.service.TcUserService;
import com.framework.service.TyOrderCommodityService;
import com.framework.utils.R;
import com.framework.utils.StringUtil;
import com.framework.utils.wechat.ModulMsgByOpenId;



@Service("tyOrderCommodityService")
public class TyOrderCommodityServiceImpl implements TyOrderCommodityService {
	@Autowired
	private TyOrderCommodityDao tyOrderCommodityDao;
	@Autowired
	private TbFlowingRecordDao tbFlowingRecordDao;
	@Autowired
	private TcUserService tcUserService;
	
	@Override
	public TyOrderCommodityEntity queryObject(Long id){
		return tyOrderCommodityDao.queryObject(id);
	}
	
	@Override
	public List<TyOrderCommodityEntity> queryList(Map<String, Object> map){
		return tyOrderCommodityDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tyOrderCommodityDao.queryTotal(map);
	}
	
	@Override
	public void save(TyOrderCommodityEntity tyOrderCommodity){
		tyOrderCommodityDao.save(tyOrderCommodity);
	}
	
	@Transactional
	@Override
	public void update(TyOrderCommodityEntity tyOrderCommodity){
		tyOrderCommodityDao.update(tyOrderCommodity);
	}
	
	@Override
	public void delete(Long id){
		tyOrderCommodityDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		tyOrderCommodityDao.deleteBatch(ids);
	}

	@Transactional
	@Override
	public R sendGoods(TyOrderCommodityEntity tyOrderCommodity) {
		if(tyOrderCommodity == null || tyOrderCommodity.getId() == null){
			return R.error("操作的订单有误,请刷新当前页面后重新操作!");
		}
		TyOrderCommodityEntity tEntity = tyOrderCommodityDao.queryObject(tyOrderCommodity.getId());
		if(tEntity == null){
			return R.error("操作的订单不存在,请刷新当前页面后重新操作!");
		}
		if(tEntity.getStatus() != 0){
			return R.error("操作的订单已完成或已退款,不可继续操作!");
		}
		if(tEntity.getExpressStatus() == 1){
			return R.error("操作的订单已发货,无需重复操作!");
		}
		
		if(StringUtil.isEmpty(tyOrderCommodity.getExpressName()) || StringUtil.isEmpty(tyOrderCommodity.getExpressNumber())){
			return R.error("快递名称和快递单号必须填写!");
		}
		
		tEntity.setExpressStatus(1);
		tEntity.setExpressName(tyOrderCommodity.getExpressName());
		tEntity.setExpressNumber(tyOrderCommodity.getExpressNumber());
		tEntity.setExpressTime(new Date());
		tyOrderCommodityDao.update(tEntity);
		
		//获取用户Openid
		TcUserEntity tcUserEntity = tcUserService.queryObject(tEntity.getUserId());
		if(tcUserEntity != null && !StringUtil.isEmpty(tcUserEntity.getOpenid())){
			//商品发货通知(通知用户)
			ModulMsgByOpenId.commoditySendGoods(tcUserEntity.getOpenid(),tyOrderCommodity.getExpressName(),tyOrderCommodity.getExpressNumber());
		}
		
		return R.ok("操作成功!");
	}

	@Transactional
	@Override
	public synchronized R refund(Long id) {
		TyOrderCommodityEntity tEntity = tyOrderCommodityDao.queryObject(id);
		if(tEntity == null){
			return R.error("操作的订单不存在,请刷新当前页面后重新操作!");
		}
		if(tEntity.getPayStatus() == 0){
			return R.error("操作的订单未付款,不能操作退款!");
		}
		if(tEntity.getStatus() != 0){
			return R.error("操作的订单已完成或已退款,不可继续操作!");
		}
		
		tEntity.setStatus(2);//修改为已退款
		tyOrderCommodityDao.update(tEntity);
		
		//保存流水信息
		TbFlowingRecordEntity tbFlowingRecordEntity = new TbFlowingRecordEntity();
		tbFlowingRecordEntity.setUserId(tEntity.getUserId());
		tbFlowingRecordEntity.setType("refund");
		tbFlowingRecordEntity.setTypeName("退款");
		tbFlowingRecordEntity.setSymbol("+");
		tbFlowingRecordEntity.setMoney(tEntity.getCommodityPrice());
		tbFlowingRecordEntity.setNote("商品订单号:"+tEntity.getNumber()+",退款金额已返还到钱包!");
		tbFlowingRecordEntity.setCreatetime(new Date());
		tbFlowingRecordDao.save(tbFlowingRecordEntity);
		
		//增加用户钱包金额
		Map<String, Object> addWalletMap = new HashMap<>();
		addWalletMap.put("id", tEntity.getUserId());
		addWalletMap.put("wallet", tEntity.getCommodityPrice());
		tcUserService.addWallet(addWalletMap);
		 
		return R.ok("操作成功!");
	}

	@Override
	public List<TyOrderCommodityEntity> queryObjectKey(TyOrderCommodityEntity tyOrderCommodity) {
		return tyOrderCommodityDao.queryObjectByKey(tyOrderCommodity);
	}
	
}
