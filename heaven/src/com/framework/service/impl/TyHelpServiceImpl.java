package com.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.framework.dao.TyHelpDao;
import com.framework.entity.TyHelpEntity;
import com.framework.entity.TyVideoEntity;
import com.framework.service.TyHelpService;
import com.framework.utils.R;
import com.framework.utils.StringUtil;



@Service("tyHelpService")
public class TyHelpServiceImpl implements TyHelpService {
	@Autowired
	private TyHelpDao tyHelpDao;
	
	@Override
	public TyHelpEntity queryObject(Long id){
		return tyHelpDao.queryObject(id);
	}
	
	@Override
	public List<TyHelpEntity> queryObjectByKey(TyHelpEntity tyHelp){
		return tyHelpDao.queryObjectByKey(tyHelp);
	}
	
	@Override
	public List<TyHelpEntity> queryList(Map<String, Object> map){
		return tyHelpDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tyHelpDao.queryTotal(map);
	}
	
	@Transactional
	@Override
	public void save(TyHelpEntity tyHelp){
		tyHelp.setCreatetime(new Date());
		tyHelpDao.save(tyHelp);
		tyHelp.setSort(tyHelp.getId());
		tyHelpDao.update(tyHelp);
	}
	
	@Override
	public void update(TyHelpEntity tyHelp){
		tyHelpDao.update(tyHelp);
	}
	
	@Override
	public void delete(Long id){
		tyHelpDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		tyHelpDao.deleteBatch(ids);
	}

	@Override
	public R updateSors(TyHelpEntity tyHelp) {
		TyHelpEntity nowEntity = tyHelpDao.queryObject(tyHelp.getId());
		
		TyHelpEntity yidong = new TyHelpEntity();
		if(tyHelp.getSort().equals(0L)){//上移
			yidong = tyHelpDao.shangyiMain(nowEntity.getSort());
		}else{//下移
			yidong = tyHelpDao.xiayiMain(nowEntity.getSort());
		}
		
		//如果信息已经是最上或者最下则无需重复操作
		if(StringUtil.isEmpty(yidong)){
			return R.ok();
		}
		
		TyHelpEntity update = new TyHelpEntity();
		update.setId(nowEntity.getId());
		update.setSort(yidong.getSort());
		tyHelpDao.update(update);
		
		update.setId(yidong.getId());
		update.setSort(nowEntity.getSort());
		tyHelpDao.update(update);
		
		return R.ok();
	}

	@Override
	public TyHelpEntity queryWQ() {
		return tyHelpDao.queryWQ();
	}
	
}
