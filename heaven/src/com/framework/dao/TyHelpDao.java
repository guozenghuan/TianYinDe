package com.framework.dao;

import com.framework.entity.TyHelpEntity;

/**
 * 平台帮助
 * 
 * @author R & D
 * @email 
 * @date 2019-04-10 14:46:55
 */
public interface TyHelpDao extends BaseDao<TyHelpEntity> {
	TyHelpEntity shangyiMain(Long id);
	TyHelpEntity xiayiMain(Long id);
	TyHelpEntity queryWQ();
}
