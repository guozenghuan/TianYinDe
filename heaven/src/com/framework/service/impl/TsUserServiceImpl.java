package com.framework.service.impl;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.framework.dao.TsCompanyDao;
import com.framework.dao.TsSchedulingDao;
import com.framework.dao.TsUserDao;
import com.framework.entity.TsCompanyEntity;
import com.framework.entity.TsSchedulingEntity;
import com.framework.entity.TsUserEntity;
import com.framework.service.TsUserService;
import com.framework.session.UserSession;
import com.framework.utils.ConfigUtil;
import com.framework.utils.R;
import com.framework.utils.StringUtil;
import com.framework.utils.TransactionRollBack;
import com.framework.utils.images.ImgCompress;



@Service
public class TsUserServiceImpl implements TsUserService {
	@Autowired
	private TsUserDao tsUserDao;
	@Autowired
	private TsCompanyDao tsCompanyDao;
	@Autowired
	private TsSchedulingDao tsSchedulingDao;

	@Override
	public TsUserEntity queryObject(Long id){
		TsUserEntity tsUserEntity = tsUserDao.queryObject(id);
		tsUserEntity.setPassword(null);
		tsUserEntity.setCreatetime(null);
		return tsUserEntity;
	}

	@Override
	public List<TsUserEntity> queryList(Map<String, Object> map){	
		return tsUserDao.queryList(map);
	}

	@Override
	public List<TsUserEntity> queryObjectByKey(TsUserEntity tsUserEntity) {
		return tsUserDao.queryObjectByKey(tsUserEntity);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return tsUserDao.queryTotal(map);
	}

	@Override
	public void updateNow(TsUserEntity tsUserEntity){
		tsUserDao.update(tsUserEntity);
	}


	@Transactional
	@Override
	public R save(HttpServletRequest request){
		//转换成实体类
		TsUserEntity tsUserEntity = JSONObject.toJavaObject(JSON.parseObject(request.getParameter("tsUser")), TsUserEntity.class);

		if(tsUserEntity == null){
			return R.error("请填写信息!");
		}
		String accountFlag = StringUtil.isAccount(tsUserEntity.getAccount());
		if(!accountFlag.equals("true")){
			return R.error(accountFlag);
		}
		String passwordFlag = StringUtil.isPassword(tsUserEntity.getPassword());
		if(!passwordFlag.equals("true")){
			return R.error(passwordFlag);
		}
		String nameFlag = StringUtil.isNickName(tsUserEntity.getName());
		if(!nameFlag.equals("true")){
			return R.error(nameFlag);
		}
		String phoneFlag = StringUtil.isMobile(tsUserEntity.getPhone());
		if(!phoneFlag.equals("true")){
			return R.error(phoneFlag);
		}

		if(tsUserEntity.getCompanyId() == null){
			return R.error("请选择公司!");
		}
		TsCompanyEntity tsCompanyEntity = tsCompanyDao.queryObject(tsUserEntity.getCompanyId());
		if(tsCompanyEntity == null){
			return R.error("选择的公司信息不存在!请刷新当前页面后重新操作!");
		}

		//判断账号是否存在
		TsUserEntity tsUserAccount = new TsUserEntity();
		tsUserAccount.setAccount(tsUserEntity.getAccount());
		List<TsUserEntity> accountIs = tsUserDao.queryObjectByKey(tsUserAccount);
		if(!accountIs.isEmpty() || accountIs.size() >= 1){
			return R.error("账号已经存在,请重新填写!");
		}

		tsUserEntity.setAchievement(new BigDecimal(0));
		tsUserEntity.setScore(new BigDecimal(0));

		//加密密码  sha256加密
		tsUserEntity.setPassword(new Sha256Hash(tsUserEntity.getPassword()).toHex());
		tsUserEntity.setCreatetime(new Date());
		tsUserDao.save(tsUserEntity);

		//取出form-data中的二进制字段
		MultipartHttpServletRequest multipartRequest=(MultipartHttpServletRequest) request;
		//file是form-data中二进制字段对应的name
		MultipartFile imageFile = multipartRequest.getFile("image");
		if(imageFile==null){
			//操作失败执行数据回滚
			TransactionRollBack.rollBackUpdate(null);
			return R.error("请上传头像!");
		}

		//判断图片类型
		String fileType = imageFile.getContentType();
		if(!fileType.equals("image/jpeg") && !fileType.equals("image/jpg") && !fileType.equals("image/png")){
			//操作失败执行数据回滚
			TransactionRollBack.rollBackUpdate(null);
			return R.error("只能上传jpeg、jpg、png类型的图片!");
		}

		//图片路径和地址
		String[] names = imageFile.getOriginalFilename().split("\\.");
		String path = ConfigUtil.getSalesmanPortrait(tsUserEntity.getId().toString()) + StringUtil.randomUUID() + "." + names[names.length-1];

		// 上传
		try {
			imageFile.transferTo(new File(ConfigUtil.getTomcatWebapps()+path));

			//压缩图片
			ImgCompress.compressImag(400, 400, ConfigUtil.getTomcatWebapps()+path);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			//操作失败执行数据回滚
			TransactionRollBack.rollBackUpdate(null);
			return R.error("保存图片失败01!");
		} catch (IOException e) {
			e.printStackTrace();
			//操作失败执行数据回滚
			TransactionRollBack.rollBackUpdate(null);
			return R.error("保存图片失败02!");
		}

		//设置图片的路径
		tsUserEntity.setPortraitUrl(path);
		int updateFlag = tsUserDao.update(tsUserEntity);
		if(updateFlag!=1){
			//操作失败执行数据回滚
			TransactionRollBack.rollBackUpdate(null);
			return R.error("保存图片路径失败!!");
		}

		//新增值班人员 2019-12-17 14:14:55
		TsSchedulingEntity tsScheduling = new TsSchedulingEntity();
		tsScheduling.setTsUserId(tsUserEntity.getId());
		tsScheduling.setCompany(tsCompanyEntity.getName());
		tsScheduling.setPortraitUrl(path);
		tsScheduling.setName(tsUserEntity.getName());
		tsScheduling.setPhone(tsUserEntity.getPhone());
		tsScheduling.setAccount(tsUserEntity.getAccount());
        tsScheduling.setScheduling("");
		try {
			tsSchedulingDao.saveYWY(tsScheduling);
		} catch (Exception e) {
			//操作失败执行数据回滚
			TransactionRollBack.rollBackUpdate(null);
			return R.error("新增值班人员失败!");
		}

		return R.ok("操作成功!");
	}

	@Transactional
	@Override
	public R updateMy(HttpServletRequest request){
		TsUserEntity tsUser = UserSession.salesman(request);
		System.out.println("图片路径和地址: " +"updateMy");

		//转换成实体类
		TsUserEntity tsUserEntityNew = JSONObject.toJavaObject(JSON.parseObject(request.getParameter("tsUser")), TsUserEntity.class);
		if(tsUserEntityNew == null){
			return R.error("请填写信息!");
		}

		TsUserEntity tsUserEntity = new TsUserEntity();
		tsUserEntity.setId(tsUserEntityNew.getId());
		tsUserEntity.setName(tsUserEntityNew.getName());
		tsUserEntity.setPhone(tsUserEntityNew.getPhone());

		if(!tsUser.getId().equals(tsUserEntity.getId())){
			return R.error("修改信息失败,您没有权限,请重新登入后再修改!");
		}

		//取出form-data中的二进制字段
		MultipartHttpServletRequest multipartRequest=(MultipartHttpServletRequest) request;
		//file是form-data中二进制字段对应的name
		MultipartFile imageFile = multipartRequest.getFile("image");
		if(imageFile==null){
			//设置图片的路径为空，不修改图片
			tsUserEntity.setPortraitUrl(null);
		}else{
			//判断图片类型
			String fileType = imageFile.getContentType();
			if(!fileType.equals("image/jpeg") && !fileType.equals("image/jpg") && !fileType.equals("image/png")){
				return R.error("只能上传jpeg、jpg、png类型的图片!");
			}

			//图片路径和地址
			String[] names = imageFile.getOriginalFilename().split("\\.");
			String path = ConfigUtil.getSalesmanPortrait(tsUserEntity.getId().toString()) + StringUtil.randomUUID() + "." + names[names.length-1];

			// 上传   ConfigUtil.getTomcatWebapps()
			try {
				imageFile.transferTo(new File(ConfigUtil.getTomcatWebapps()+path));

				//压缩图片
				ImgCompress.compressImag(400, 400, ConfigUtil.getTomcatWebapps()+path);
			} catch (IllegalStateException e) {
				e.printStackTrace();
				return R.error("保存图片失败04!");
			} catch (IOException e) {
				e.printStackTrace();
				return R.error("保存图片失败05!");
			}

			//删除原来的图片
			ConfigUtil.deleteFile(tsUser.getPortraitUrl());

			//设置图片的路径
			tsUserEntity.setPortraitUrl(path);
		}

		int updateFlag = tsUserDao.update(tsUserEntity);
		if(updateFlag!=1){
			//操作失败执行数据回滚
			TransactionRollBack.rollBackUpdate(null);
			return R.error("保存图片路径失败!!");
		}

		return R.ok("操作成功!");
	}

	@Transactional
	@Override
	public R update(HttpServletRequest request){
		//转换成实体类
		System.out.println("图片路径和地址: " +"update");
		TsUserEntity tsUserEntity = JSONObject.toJavaObject(JSON.parseObject(request.getParameter("tsUser")), TsUserEntity.class);

		if(tsUserEntity == null){
			return R.error("请填写信息!");
		}
		String accountFlag = StringUtil.isAccount(tsUserEntity.getAccount());
		if(!accountFlag.equals("true")){
			return R.error(accountFlag);
		}
		if(!StringUtil.isEmpty(tsUserEntity.getPassword())){
			String passwordFlag = StringUtil.isPassword(tsUserEntity.getPassword());
			if(!passwordFlag.equals("true")){
				return R.error(passwordFlag);
			}else{
				//加密密码  sha256加密
				tsUserEntity.setPassword(new Sha256Hash(tsUserEntity.getPassword()).toHex());
			}
		}
		String nameFlag = StringUtil.isNickName(tsUserEntity.getName());
		if(!nameFlag.equals("true")){
			return R.error(nameFlag);
		}
		String phoneFlag = StringUtil.isMobile(tsUserEntity.getPhone());
		if(!phoneFlag.equals("true")){
			return R.error(phoneFlag);
		}

		if(tsUserEntity.getCompanyId() == null){
			return R.error("请选择公司!");
		}
		TsCompanyEntity tsCompanyEntity = tsCompanyDao.queryObject(tsUserEntity.getCompanyId());
		if(tsCompanyEntity == null){
			return R.error("选择的公司信息不存在!请刷新当前页面后重新操作!");
		}

		//判断要修改的信息是否存在
		TsUserEntity accountIf = new TsUserEntity();
		accountIf.setId(tsUserEntity.getId());
		List<TsUserEntity> accountIfList = tsUserDao.queryObjectByKey(accountIf);
		if(accountIfList.isEmpty() || accountIfList.size() <= 0){
			return R.error("操作的账号信息不存在,请重新刷新后操作!");
		}

		//账号已经修改
		if(!accountIfList.get(0).getAccount().trim().equals(tsUserEntity.getAccount().trim())){
			//判断账号是否存在
			TsUserEntity tsUserAccount = new TsUserEntity();
			tsUserAccount.setAccount(tsUserEntity.getAccount());
			List<TsUserEntity> accountIs = tsUserDao.queryObjectByKey(tsUserAccount);
			if(!accountIs.isEmpty() || accountIs.size() >= 1){
				return R.error("账号已经存在,请重新填写!");
			}
		}

		//取出form-data中的二进制字段
		MultipartHttpServletRequest multipartRequest=(MultipartHttpServletRequest) request;
		//file是form-data中二进制字段对应的name
		MultipartFile imageFile = multipartRequest.getFile("image");
		String path = null;
		if(imageFile==null){
			//设置图片的路径为空，不修改图片
			tsUserEntity.setPortraitUrl(null);
		}else{
			//判断图片类型
			String fileType = imageFile.getContentType();
			if(!fileType.equals("image/jpeg") && !fileType.equals("image/jpg") && !fileType.equals("image/png")){
				return R.error("只能上传jpeg、jpg、png类型的图片!");
			}

			//图片路径和地址
			String[] names = imageFile.getOriginalFilename().split("\\.");
			path = ConfigUtil.getSalesmanPortrait(tsUserEntity.getId().toString()) + StringUtil.randomUUID() + "." + names[names.length-1];

			// 上传
			try {///home/tomcat8/webapps/
				imageFile.transferTo(new File(ConfigUtil.getTomcatWebapps()+path));
				
				//压缩图片
				ImgCompress.compressImag(400, 400, ConfigUtil.getTomcatWebapps()+path);
			} catch (IllegalStateException e) {
				e.printStackTrace();
				return R.error("保存图片失败04!");
			} catch (IOException e) {
				e.printStackTrace();
				return R.error("保存图片失败05!");
			}

			//删除原来的图片
			ConfigUtil.deleteFile(tsUserEntity.getPortraitUrl());

			//设置图片的路径
			tsUserEntity.setPortraitUrl(path);
		}
        
		int updateFlag = tsUserDao.update(tsUserEntity);
		if(updateFlag!=1){
			//操作失败执行数据回滚
			TransactionRollBack.rollBackUpdate(null);
			return R.error("保存图片路径失败!!");
		}

		//update 2019-12-17 
		TsSchedulingEntity[] tsScheduling = tsSchedulingDao.queryListObjectByTS(tsUserEntity.getId());
		if(tsScheduling == null){
			//操作失败执行数据回滚
			TransactionRollBack.rollBackUpdate(null);
			return R.error("获取值班列表失败!");
		}
		//tsScheduling.setTsUserId(tsUserEntity.getId());
		for(int i=0 ; i<tsScheduling.length ; i++ ) {
			tsScheduling[i].setCompany(tsCompanyEntity.getName());
			if(path != null) {
				tsScheduling[i].setPortraitUrl(path);
			}
			tsScheduling[i].setName(tsUserEntity.getName());
			tsScheduling[i].setPhone(tsUserEntity.getPhone());
			tsScheduling[i].setAccount(tsUserEntity.getAccount());
			int update = tsSchedulingDao.update(tsScheduling[i]);
			if(update != 1){
				//操作失败执行数据回滚
				TransactionRollBack.rollBackUpdate(null);
				return R.error("更新值班列表失败!");
			}
		} 
		return R.ok("操作成功!");
	}

	@Override
	public void delete(Long id){
		tsUserDao.delete(id);

	}

	@Override
	public void deleteBatch(Long[] ids){
		//判断要修改的信息是否存在
		Map<String,Object> map = new HashMap<>();
	 
		TsUserEntity accountIf = new TsUserEntity();
		accountIf.setId(ids[0]);
		List<TsUserEntity> accountIfList = tsUserDao.queryObjectByKey(accountIf);
		if(!accountIfList.isEmpty() && accountIfList.size() >= 1){
			//删除图片
			ConfigUtil.deleteFile(accountIfList.get(0).getPortraitUrl());
		}
		
		tsUserDao.deleteBatch(ids);
		map.put("account", accountIfList.get(0).getAccount());
		
		List<TsSchedulingEntity> tsSchedulingEntity = 
		tsSchedulingDao.queryObjectByKey(map);
		tsSchedulingDao.delete(tsSchedulingEntity.get(0).getTsUserId());
		
	}

}
