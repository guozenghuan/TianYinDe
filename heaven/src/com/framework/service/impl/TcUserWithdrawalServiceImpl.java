package com.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.framework.dao.TbFlowingRecordDao;
import com.framework.dao.TcUserDao;
import com.framework.dao.TcUserWithdrawalDao;
import com.framework.entity.TbFlowingRecordEntity;
import com.framework.entity.TcUserEntity;
import com.framework.entity.TcUserWithdrawalEntity;
import com.framework.service.TcUserWithdrawalService;
import com.framework.utils.OrderCodeFactory;
import com.framework.utils.R;
import com.framework.utils.StringUtil;
import com.framework.utils.TransactionRollBack;



@Service("tcUserWithdrawalService")
public class TcUserWithdrawalServiceImpl implements TcUserWithdrawalService {
	@Autowired
	private TcUserWithdrawalDao tcUserWithdrawalDao;
	
	@Autowired
	private TbFlowingRecordDao tbFlowingRecordDao;
	
	@Autowired
	private TcUserDao tcUserDao;
	
	@Override
	public TcUserWithdrawalEntity queryObject(Long id){
		return tcUserWithdrawalDao.queryObject(id);
	}
	
	@Override
	public List<TcUserWithdrawalEntity> queryObjectByKey(TcUserWithdrawalEntity tcUserWithdrawal){
		return tcUserWithdrawalDao.queryObjectByKey(tcUserWithdrawal);
	}
	
	@Override
	public List<TcUserWithdrawalEntity> queryList(Map<String, Object> map){
		return tcUserWithdrawalDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tcUserWithdrawalDao.queryTotal(map);
	}
	
	@Override
	public void save(TcUserWithdrawalEntity tcUserWithdrawal){
		tcUserWithdrawalDao.save(tcUserWithdrawal);
	}
	
	@Override
	public void update(TcUserWithdrawalEntity tcUserWithdrawal){
		tcUserWithdrawalDao.update(tcUserWithdrawal);
	}
	
	@Override
	public void delete(Long id){
		tcUserWithdrawalDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		tcUserWithdrawalDao.deleteBatch(ids);
	}

	@Transactional
	@Override
	public synchronized R withdrawalByUser(Long userId, BigDecimal money) {
		//判断金额是否大于小于零
		if(money.compareTo(new BigDecimal(0)) == -1 || money.compareTo(new BigDecimal(0)) == 0){
			return R.error("提现金额必须要大于0!");
		}
		//判断用户钱包金额是否满足提现金额
		TcUserEntity userEntity = tcUserDao.queryObject(userId);
		if(userEntity.getWallet().compareTo(money) == -1){
			return R.error("账户余额不足!");
		}
		
		String moneyFlag = String.valueOf(money);
		if(StringUtil.isIndexOfStr(moneyFlag, ".")){
			if(moneyFlag.split("\\.")[1].length() > 2){
				return R.error("提现金额小数点后只能有两位数!");
			}
		}
		
		//扣除账户
		Map<String, Object> map = new HashMap<>();
		map.put("wallet", money);
		map.put("id", userId);
		int size = tcUserDao.reduceWallet(map);
		if(size != 1){
			//没有扣钱成功
			return R.error("提现失败,数据库异常请稍后再试!");
		}
		
		TcUserWithdrawalEntity tcUserWithdrawal = new TcUserWithdrawalEntity();
		tcUserWithdrawal.setUserId(userId);
		tcUserWithdrawal.setMoney(money);
		tcUserWithdrawal.setNote("提现申请成功,工作人员将会在一个工作日内为您审核!");
		tcUserWithdrawal.setOrderNumber(OrderCodeFactory.toCode(userId));
		tcUserWithdrawal.setStatus("shenqing");
		tcUserWithdrawal.setCreatetime(new Date());
		tcUserWithdrawalDao.save(tcUserWithdrawal);
		
		return R.ok("操作成功!");
	}

	@Transactional
	@Override
	public synchronized R updateSetting(Long id, Integer type,String note) {
		if(StringUtil.isEmpty(id) || StringUtil.isEmpty(type)){
			return R.error("查找不到信息,请刷新后重新尝试!");
		}
		
		//获取提现信息
		TcUserWithdrawalEntity tcUserWithdrawal = tcUserWithdrawalDao.queryObject(id);
		if(StringUtil.isEmpty(tcUserWithdrawal)){
			return R.error("查找不到信息,请刷新后重新尝试!");
		}
		
		if(type == 1){//通过
			tcUserWithdrawal.setStatus("tongguo");
			
			//添加流水
			TbFlowingRecordEntity tbFlowingRecordEntity = new TbFlowingRecordEntity();
			tbFlowingRecordEntity.setUserId(tcUserWithdrawal.getUserId());
			tbFlowingRecordEntity.setType("cash_withdrawal");
			tbFlowingRecordEntity.setTypeName("提现");
			tbFlowingRecordEntity.setSymbol("-");
			tbFlowingRecordEntity.setMoney(tcUserWithdrawal.getMoney());
			tbFlowingRecordEntity.setNote("提现成功!");
			tbFlowingRecordEntity.setCreatetime(new Date());
			tbFlowingRecordDao.save(tbFlowingRecordEntity);
		}else{//不通过
			tcUserWithdrawal.setStatus("shibai");
			
			//返回钱包金额
			Map<String, Object> map = new HashMap<>();
			map.put("id", tcUserWithdrawal.getUserId());
			map.put("wallet", tcUserWithdrawal.getMoney());
			int userFlag = tcUserDao.addWallet(map);
			if(userFlag != 1){
				TransactionRollBack.rollBackUpdate();//回滚数据
				return R.error("操作失败,提现金额返还用户钱包失败!");
			}
		}
		tcUserWithdrawal.setNote(note);
		tcUserWithdrawalDao.update(tcUserWithdrawal);
		
		return R.ok("操作成功!");
	}
	
}
