package com.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.framework.dao.ModulMusicDao;
import com.framework.entity.ModulMusicEntity;
import com.framework.service.ModulMusicService;
import com.framework.utils.ConfigUtil;
import com.framework.utils.R;
import com.framework.utils.StringUtil;
import com.framework.utils.TransactionRollBack;


@Service("modulMusicService")
public class ModulMusicServiceImpl implements ModulMusicService {
	@Autowired
	private ModulMusicDao modulMusicDao;
	
	@Override
	public ModulMusicEntity queryObject(Long id){
		ModulMusicEntity modulMusicEntity = modulMusicDao.queryObject(id);
		modulMusicEntity.setCreatetime(null);
		return modulMusicEntity;
	}
	
	@Override
	public List<ModulMusicEntity> queryObjectByKey(ModulMusicEntity modulMusic){
		return modulMusicDao.queryObjectByKey(modulMusic);
	}
	
	@Override
	public List<ModulMusicEntity> queryList(Map<String, Object> map){
		return modulMusicDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return modulMusicDao.queryTotal(map);
	}
	
	@Transactional
	@Override
	public R save(HttpServletRequest request){
		//转换成实体类
		ModulMusicEntity modulMusic = JSONObject.toJavaObject(JSON.parseObject(request.getParameter("modulMusic")), ModulMusicEntity.class);
		
		if(StringUtil.isEmpty(modulMusic) || StringUtil.isEmpty(modulMusic.getName())){
			return R.error("请填写音乐名称");
		}
		
		//取出form-data中的二进制字段
		MultipartHttpServletRequest multipartRequest=(MultipartHttpServletRequest) request;
		
		/**保存音乐**/
		//判断音乐是否上传
		MultipartFile mainImage = multipartRequest.getFile("music");
		if(mainImage==null){
			return R.error("请上传音乐文件!");
		}
		//判断音乐类型
		String mainImageType = mainImage.getContentType();
		if(!mainImageType.equals("audio/mp3")){
			return R.error("只能选择MP3类型的音乐文件！");
		}
		
		//音乐路径和地址
		String mainImagePath = ConfigUtil.getMusciSfilePath() + StringUtil.randomUUID() + ".mp3";
		
		// 上传
		try {
			mainImage.transferTo(new File(ConfigUtil.getTomcatWebapps()+mainImagePath));
			
			//设置主图路径
			modulMusic.setPath(mainImagePath);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			//操作失败执行数据回滚
			TransactionRollBack.rollBackUpdate(mainImagePath);
			return R.error("保存音乐失败!");
		} catch (IOException e) {
			e.printStackTrace();
			//操作失败执行数据回滚
			TransactionRollBack.rollBackUpdate(mainImagePath);
			return R.error("保存音乐失败!");
		}
		
		modulMusic.setType("system");
		modulMusic.setUserId(0L);
		modulMusic.setCreatetime(new Date());
		modulMusicDao.save(modulMusic);
		
		return R.ok();
	}
	
	@Transactional
	@Override
	public R update(HttpServletRequest request){
		//转换成实体类
		ModulMusicEntity modulMusic = JSONObject.toJavaObject(JSON.parseObject(request.getParameter("modulMusic")), ModulMusicEntity.class);
		ModulMusicEntity histotyEntity = modulMusicDao.queryObject(modulMusic.getId());
		if(StringUtil.isEmpty(histotyEntity)){
			return R.error("查找不到改信息,请刷新当前页面后重新操作!");
		}
		
		if(StringUtil.isEmpty(modulMusic) || StringUtil.isEmpty(modulMusic.getName())){
			return R.error("请填写音乐名称");
		}
		
		//取出form-data中的二进制字段
		MultipartHttpServletRequest multipartRequest=(MultipartHttpServletRequest) request;
		
		/**保存音乐**/
		//判断音乐是否上传
		MultipartFile mainImage = multipartRequest.getFile("music");
		if(mainImage!=null){
			//判断音乐类型
			String mainImageType = mainImage.getContentType();
			if(!mainImageType.equals("audio/mp3")){
				return R.error("只能选择MP3类型的音乐文件！");
			}
			
			//音乐路径和地址
			String mainImagePath = ConfigUtil.getMusciSfilePath() + StringUtil.randomUUID() + ".mp3";
			
			// 上传
			try {
				mainImage.transferTo(new File(ConfigUtil.getTomcatWebapps()+mainImagePath));
				
				//设置主图路径
				modulMusic.setPath(mainImagePath);
			} catch (IllegalStateException e) {
				e.printStackTrace();
				//操作失败执行数据回滚
				TransactionRollBack.rollBackUpdate(mainImagePath);
				return R.error("保存音乐失败!");
			} catch (IOException e) {
				e.printStackTrace();
				//操作失败执行数据回滚
				TransactionRollBack.rollBackUpdate(mainImagePath);
				return R.error("保存音乐失败!");
			}
		}
		
		modulMusic.setType("system");
		modulMusic.setUserId(0L);
		modulMusic.setCreatetime(new Date());
		modulMusicDao.save(modulMusic);
		
		//删除原来的音乐文件
		try {
			ConfigUtil.deleteFile(histotyEntity.getPath());
		} catch (Exception e) {}
		
		return R.ok();
	}
	
	@Override
	public void delete(Long id){
		modulMusicDao.delete(id);
	}
	
	@Transactional
	@Override
	public void deleteBatch(Long[] ids){
		String delPath = modulMusicDao.queryObject(ids[0]).getPath();
		modulMusicDao.deleteBatch(ids);
		//删除文件
		try {
			ConfigUtil.deleteFile(delPath);
		} catch (Exception e) {}
	}
	
}
