package com.framework.service;

import com.framework.entity.TyHelpEntity;
import com.framework.utils.R;

import java.util.List;
import java.util.Map;

/**
 * 平台帮助
 * 
 * @author R & D
 * @email 
 * @date 2019-04-10 14:46:55
 */
public interface TyHelpService {
	
	TyHelpEntity queryObject(Long id);
	
	TyHelpEntity queryWQ();
	
	List<TyHelpEntity> queryObjectByKey(TyHelpEntity tyHelp);
	
	List<TyHelpEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TyHelpEntity tyHelp);
	
	void update(TyHelpEntity tyHelp);
	
	R updateSors(TyHelpEntity tyHelp);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
