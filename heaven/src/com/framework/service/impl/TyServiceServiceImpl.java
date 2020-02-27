package com.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.framework.dao.TyServiceDao;
import com.framework.entity.TyServiceEntity;
import com.framework.service.TyServiceService;
import com.framework.utils.StringUtil;



@Service("tyServiceService")
public class TyServiceServiceImpl implements TyServiceService {
	@Autowired
	private TyServiceDao tyServiceDao;

	@Override
	public TyServiceEntity queryObject(Long id){
		return tyServiceDao.queryObject(id);
	}

	@Override
	public List<TyServiceEntity> queryObjectByKey(TyServiceEntity tyService) {
		return tyServiceDao.queryObjectByKey(tyService);
	}

	@Override
	public List<TyServiceEntity> queryObjectByKeySort(TyServiceEntity tyService) {
		//
		List<TyServiceEntity> tyServiceList = tyServiceDao.queryObjectByKeySort(tyService); 
		String ss = tyServiceList.get(0).getName();
		for(TyServiceEntity tyServicel : tyServiceList) {  
				String s = null;
				if(!StringUtil.isEmpty(tyServicel.getCostPrice())) {
					s = tyServicel.getCostPrice().stripTrailingZeros().toPlainString();
				} else {
					s = "0.00";
				} 
				tyServicel.setName(s);   
		}
		tyServiceList.get(0).setName(ss);

		return tyServiceList;
	}

	@Override
	public List<TyServiceEntity> queryObjectByKeySortUser(TyServiceEntity tyService) {
		return tyServiceDao.queryObjectByKeySortUser(tyService);
	}

	@Override
	public JSONArray recursive(Long parentId) {
		TyServiceEntity tyService = new TyServiceEntity();
		tyService.setParentId(parentId);
		List<TyServiceEntity> tyServiceList = tyServiceDao.queryObjectByKey(tyService);

		JSONArray jsonArray = new JSONArray();
		for(int i=0;i<tyServiceList.size();i++){
			TyServiceEntity tyEntity = tyServiceList.get(i);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", tyEntity.getId());
			jsonObject.put("name", tyEntity.getName());
			jsonObject.put("text", tyEntity.getText());

			JSONArray relationship = new JSONArray();
			JSONObject jObject = new JSONObject();
			jObject.put("children_num", 0);
			jObject.put("parent_num", 0);
			jObject.put("sibling_num", 0);
			relationship.add(jObject);
			jsonObject.put("relationship", relationship);

			JSONArray children = this.recursive(tyEntity.getId());
			if(!children.isEmpty() && children.size() >= 1){
				jsonObject.put("children",children);
			}

			jsonArray.add(jsonObject);
		}

		return jsonArray;
	}

	@Override
	public List<Long> getRecursiveIds(Long parentId) {
		TyServiceEntity tyService = new TyServiceEntity();
		tyService.setParentId(parentId);
		List<TyServiceEntity> tyServiceList = tyServiceDao.queryObjectByKey(tyService);

		List<Long> idsList = new ArrayList<>();
		for(int i=0;i<tyServiceList.size();i++){
			TyServiceEntity tyEntity = tyServiceList.get(i);

			List<Long> children = this.getRecursiveIds(tyEntity.getId());
			if(!children.isEmpty() && children.size() >= 1){
				for(Long ids : children){
					idsList.add(ids);
				}
			}

			idsList.add(tyEntity.getId());
		}

		return idsList;
	}

	@Override
	public List<TyServiceEntity> queryList(Map<String, Object> map){
		return tyServiceDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return tyServiceDao.queryTotal(map);
	}

	@Transactional
	@Override
	public void save(TyServiceEntity tyService){
		tyServiceDao.save(tyService);
		tyService.setSort(Integer.valueOf(tyService.getId().toString()));
		tyServiceDao.update(tyService);
	}

	@Transactional
	@Override
	public void update(TyServiceEntity tyService){
		TyServiceEntity updateEntity = new TyServiceEntity();
		updateEntity.setId(tyService.getId());
		updateEntity.setParentId(tyService.getParentId());
		updateEntity.setName(tyService.getName());
		updateEntity.setText(tyService.getText());
		updateEntity.setType(tyService.getType());
		updateEntity.setCostPrice(tyService.getCostPrice());
		updateEntity.setUnitPrice(tyService.getUnitPrice());

		tyServiceDao.update(updateEntity);

		if(tyService.getChildren()!=null && !StringUtil.isEmpty(tyService.getChildren().getName()) && !StringUtil.isEmpty(tyService.getChildren().getText())){
			TyServiceEntity secont = new TyServiceEntity();
			secont.setParentId(tyService.getId());
			secont.setName(tyService.getChildren().getName());
			secont.setText(tyService.getChildren().getText());
			tyServiceDao.save(secont);
		}
	}

	@Override
	public void delete(Long id){
		tyServiceDao.delete(id);
	}

	@Transactional
	@Override
	public void deleteBatch(Long[] ids){
		tyServiceDao.deleteBatch(ids);
	}

	@Override
	public void deleteFwAll(Long[] ids) {
		tyServiceDao.deleteFwAll(ids[0]);
	}

	@Override
	public void updateMainSors(TyServiceEntity tyService) {
		TyServiceEntity nowEntity = tyServiceDao.queryObject(tyService.getId());

		TyServiceEntity yidong = new TyServiceEntity();
		if(tyService.getName().trim().equals("0")){//上移
			yidong = tyServiceDao.shangyiMain(nowEntity.getSort());
		}else{//下移
			yidong = tyServiceDao.xiayiMain(nowEntity.getSort());
		}

		//如果信息已经是最上或者最下则无需重复操作
		if(StringUtil.isEmpty(yidong)){
			return;
		}

		TyServiceEntity update = new TyServiceEntity();
		update.setId(nowEntity.getId());
		update.setSort(yidong.getSort());
		tyServiceDao.update(update);

		update.setId(yidong.getId());
		update.setSort(nowEntity.getSort());
		tyServiceDao.update(update);
	}

	@Override
	public void updateMainSorsScd(TyServiceEntity tyService) {
		TyServiceEntity nowEntity = tyServiceDao.queryObject(tyService.getId());

		TyServiceEntity yidong = new TyServiceEntity();
		if(tyService.getName().trim().equals("0")){//上移
			yidong = tyServiceDao.shangyiMainScd(nowEntity);
		}else{//下移
			yidong = tyServiceDao.xiayiMainScd(nowEntity);
		}

		//如果信息已经是最上或者最下则无需重复操作
		if(StringUtil.isEmpty(yidong)){
			return;
		}

		TyServiceEntity update = new TyServiceEntity();
		update.setId(nowEntity.getId());
		update.setSort(yidong.getSort());
		tyServiceDao.update(update);

		update.setId(yidong.getId());
		update.setSort(nowEntity.getSort());
		tyServiceDao.update(update);
	}

	@Transactional
	@Override
	public void updateJiaGe(TyServiceEntity tyService) {
		TyServiceEntity uEntity = new TyServiceEntity();
		uEntity.setId(tyService.getId());
		uEntity.setType("jiage");
		uEntity.setName(tyService.getName());

		tyServiceDao.update(uEntity);
	}

}
