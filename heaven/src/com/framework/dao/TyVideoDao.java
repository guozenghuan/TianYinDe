package com.framework.dao;

import com.framework.entity.TyVideoEntity;

/**
 * 视频展示
 * 
 * @author R & D
 * @email 
 * @date 2019-04-09 18:41:09
 */
public interface TyVideoDao extends BaseDao<TyVideoEntity> {
	TyVideoEntity shangyiMain(Long id);
	TyVideoEntity xiayiMain(Long id);
}
