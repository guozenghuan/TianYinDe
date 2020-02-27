package com.framework.service;

import com.framework.entity.ModulEntity;
import com.framework.utils.R;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 模板信息表
 * 
 * @author R & D
 * @email 
 * @date 2019-04-28 12:58:09
 */
public interface ModulService {
	
	ModulEntity queryObject(Long id);
	ModulEntity queryObject(Long id,Boolean seetingBtn);
	
	R queryModulInfo(Long id,String modulCode);
	
	R userSelectModul(Long userid,Long modulId);
	
	R userSelectModulNow(Long userid,Long modulId);
	
	List<ModulEntity> queryObjectByKey(ModulEntity modul);
	
	List<ModulEntity> queryList(Map<String, Object> map);
	
	List<ModulEntity> queryListByNow(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	Long save(ModulEntity modul);
	
	Long saveModul(String modulCode);
	
	R saveInformationModul(HttpServletRequest request);
	
	R deleteModul(Long mainModulId,Long id,String modulCode);
	
	R update(ModulEntity modul);
	
	void updateSql(ModulEntity modul);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
