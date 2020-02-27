package com.framework.utils;

import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * 
 * @author Administrator
 *
 */
public class TransactionRollBack {
	
	/**
	 * 更新数据时，以防数据更新多条，以及没有更新然后执行回滚
	 * 使用前记得在方法上加注解：@Transactional
	 */
	public static void rollBackUpdate(){
		//执行回滚
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	}
	
	/**
	 * 更新数据时，以防数据更新多条，以及没有更新然后执行回滚,并且删除文件夹
	 * 使用前记得在方法上加注解：@Transactional
	 */
	public static void rollBackUpdate(String folderPath){
		//执行回滚
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		if(!StringUtil.isEmpty(folderPath)){
			//删除文件夹
			ConfigUtil.delFolder(folderPath);
		}
	}
	
}
