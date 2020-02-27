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
import com.framework.dao.TyVideoDao;
import com.framework.entity.TyVideoEntity;
import com.framework.service.TyVideoService;
import com.framework.utils.ConfigUtil;
import com.framework.utils.R;
import com.framework.utils.StringUtil;
import com.framework.utils.TransactionRollBack;
import com.framework.utils.VideoUtils;



@Service("tyVideoService")
public class TyVideoServiceImpl implements TyVideoService {
	@Autowired
	private TyVideoDao tyVideoDao;
	
	@Override
	public TyVideoEntity queryObject(Long id){
		return tyVideoDao.queryObject(id);
	}
	
	@Override
	public List<TyVideoEntity> queryObjectByKey(TyVideoEntity tyVideo){
		return tyVideoDao.queryObjectByKey(tyVideo);
	}
	
	@Override
	public List<TyVideoEntity> queryList(Map<String, Object> map){
		return tyVideoDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tyVideoDao.queryTotal(map);
	}
	
	@Transactional
	@Override
	public R save(HttpServletRequest request){
		/**转换成实体类**/
		TyVideoEntity tyVideo = JSONObject.toJavaObject(JSON.parseObject(request.getParameter("tyVideo")), TyVideoEntity.class);
		if(StringUtil.isEmpty(tyVideo) || StringUtil.isEmpty(tyVideo.getTitle()) || StringUtil.isEmpty(tyVideo.getNote())){
			return R.error("请填写视频标题和视频详情!");
		}
		
		//取出form-data中的二进制字段
		MultipartHttpServletRequest multipartRequest=(MultipartHttpServletRequest) request;
		
		/**获取视频**/
		MultipartFile videoFile = multipartRequest.getFile("videoFile");
		if(StringUtil.isEmpty(videoFile)){
			return R.error("请上传视频!");
		}
		//判断视频类型
		String videoType = videoFile.getContentType();
		if(!videoType.equals("video/mp4")){
			return R.error("只能上传mp4类型的视频!");
		}
		//保存视频到本地
		String[] names = videoFile.getOriginalFilename().split("\\.");
		String mainImagePath = ConfigUtil.getVideosPath() + StringUtil.randomUUID() + "." + names[names.length-1];
		
		// 上传
		try {
			videoFile.transferTo(new File(ConfigUtil.getTomcatWebapps()+mainImagePath));
			//判断视频格式
			if(!VideoUtils.isVideoH264(ConfigUtil.getTomcatWebapps()+mainImagePath)){
				//删除已经保存的视频
				ConfigUtil.deleteFile(ConfigUtil.getTomcatWebapps()+mainImagePath);
				return R.error("该MP4格式错误,MP4格式必须为:h264,请下载视频转换工具： http://tianyinde.com/files_heaven/gsgc.zip ,安装后运行软件转换mp4编码格式!");
			}
			
			//设置视频路径
			tyVideo.setPath(mainImagePath);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			//操作失败执行数据回滚
			TransactionRollBack.rollBackUpdate(mainImagePath);
			return R.error("保存图片失败!");
		} catch (IOException e) {
			e.printStackTrace();
			//操作失败执行数据回滚
			TransactionRollBack.rollBackUpdate(mainImagePath);
			return R.error("保存图片失败!");
		}
		
		tyVideo.setCreatetime(new Date());
		tyVideo.setStatus(0);
		tyVideoDao.save(tyVideo);
		
		//排序序号
		tyVideo.setSort(tyVideo.getId());
		tyVideoDao.update(tyVideo);
		
		return R.ok("操作成功!");
	}
	
	@Transactional
	@Override
	public R update(HttpServletRequest request){
		/**转换成实体类**/
		TyVideoEntity tyVideo = JSONObject.toJavaObject(JSON.parseObject(request.getParameter("tyVideo")), TyVideoEntity.class);
		if(StringUtil.isEmpty(tyVideo) || StringUtil.isEmpty(tyVideo.getTitle()) || StringUtil.isEmpty(tyVideo.getNote())){
			return R.error("请填写视频标题和视频详情!");
		}
		
		TyVideoEntity queryUpdate = new TyVideoEntity();
		queryUpdate.setId(tyVideo.getId());
		List<TyVideoEntity> listEDelete = tyVideoDao.queryObjectByKey(queryUpdate);
		if(listEDelete.isEmpty() || listEDelete.size() <= 0){
			return R.error("查找不到改信息,可能已被删除,请刷新后重新操作!");
		}
		//保存该保存的信息
		queryUpdate.setTitle(tyVideo.getTitle());
		queryUpdate.setNote(tyVideo.getNote());
		
		//取出form-data中的二进制字段
		MultipartHttpServletRequest multipartRequest=(MultipartHttpServletRequest) request;
		
		/**获取视频**/
		MultipartFile videoFile = multipartRequest.getFile("videoFile");
		if(!StringUtil.isEmpty(videoFile)){//视频已更改
			//判断视频类型
			String videoType = videoFile.getContentType();
			if(!videoType.equals("video/mp4")){
				return R.error("只能上传mp4类型的视频!");
			}
			//保存视频到本地
			String[] names = videoFile.getOriginalFilename().split("\\.");
			String mainImagePath = ConfigUtil.getVideosPath() + StringUtil.randomUUID() + "." + names[names.length-1];
			
			// 上传
			try {
				videoFile.transferTo(new File(ConfigUtil.getTomcatWebapps()+mainImagePath));
				//判断视频格式
				if(!VideoUtils.isVideoH264(ConfigUtil.getTomcatWebapps()+mainImagePath)){
					//删除已经保存的视频
					ConfigUtil.deleteFile(ConfigUtil.getTomcatWebapps()+mainImagePath);
					return R.error("该MP4格式错误,MP4格式必须为:h264,请下载视频转换工具： http://tianyinde.com/files_heaven/gsgc.zip ,安装后运行软件转换mp4编码格式!");
				}
				
				//设置视频路径
				queryUpdate.setPath(mainImagePath);
				
				//删除视频
				ConfigUtil.deleteFile(listEDelete.get(0).getPath());
			} catch (IllegalStateException e) {
				e.printStackTrace();
				//操作失败执行数据回滚
				TransactionRollBack.rollBackUpdate(mainImagePath);
				return R.error("保存图片失败!");
			} catch (IOException e) {
				e.printStackTrace();
				//操作失败执行数据回滚
				TransactionRollBack.rollBackUpdate(mainImagePath);
				return R.error("保存图片失败!");
			}
		}
		
		
		tyVideoDao.update(queryUpdate);
		
		return R.ok("操作成功!");
	}
	
	@Override
	public void delete(Long id){
		tyVideoDao.delete(id);
	}
	
	@Transactional
	@Override
	public R deleteBatch(Long[] ids){
		//查询该信息
		TyVideoEntity queryUpdate = new TyVideoEntity();
		queryUpdate.setId(ids[0]);
		List<TyVideoEntity> listEDelete = tyVideoDao.queryObjectByKey(queryUpdate);
		if(listEDelete.isEmpty() || listEDelete.size() <= 0){
			return R.error("查找不到改信息,可能已被删除,请刷新后重新操作!");
		}
		
		//删除视频
		ConfigUtil.deleteFile(listEDelete.get(0).getPath());
		tyVideoDao.deleteBatch(ids);
		
		return R.ok();
	}

	@Transactional
	@Override
	public R updateSors(TyVideoEntity tyVideo) {
		TyVideoEntity nowEntity = tyVideoDao.queryObject(tyVideo.getId());
		
		TyVideoEntity yidong = new TyVideoEntity();
		if(tyVideo.getStatus() == 0){//上移
			yidong = tyVideoDao.shangyiMain(nowEntity.getSort());
		}else{//下移
			yidong = tyVideoDao.xiayiMain(nowEntity.getSort());
		}
		
		//如果信息已经是最上或者最下则无需重复操作
		if(StringUtil.isEmpty(yidong)){
			return R.ok();
		}
		
		TyVideoEntity update = new TyVideoEntity();
		update.setId(nowEntity.getId());
		update.setSort(yidong.getSort());
		tyVideoDao.update(update);
		
		update.setId(yidong.getId());
		update.setSort(nowEntity.getSort());
		tyVideoDao.update(update);
		
		return R.ok();
	}
	
}
