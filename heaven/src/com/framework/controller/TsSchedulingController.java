package com.framework.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.ParagraphView;

import org.apache.commons.lang.ObjectUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.framework.entity.TsCompanyEntity;
import com.framework.entity.TsSchedulingEntity;
import com.framework.entity.TsSchedulingPaiDanEntity;
import com.framework.service.TsCompanyService;
import com.framework.service.TsSchedulingPaiDanService;
import com.framework.service.TsSchedulingService;
import com.framework.service.TyOrderServiceService;
import com.framework.utils.DateUtils;
import com.framework.utils.PageUtils;
import com.framework.utils.R;
import com.framework.utils.StringUtil;

/**
 * 业务员值班安排
 * @author GZH
 * @ date 2019-12-13 16:39:57
 */
@Controller
@RequestMapping("tsscheduling")
public class TsSchedulingController {
	@Autowired
	private TsSchedulingService tsSchedulingServicer;
	@Autowired
	private TsSchedulingPaiDanService tsSchedulingPaiDanService;
	@Autowired
	private TyOrderServiceService tyOrderServiceService;
	@Autowired
	private TsCompanyService tsCompanyService;

	@RequestMapping("/tsscheduling.html")
	public String list(){
		return "tsscheduling/tsscheduling.html";
	}

	@RequestMapping("/tsscheduling_add.html")
	public String add(){
		return "tsscheduling/tsscheduling_add.html";
	}

	/**
	 * 列表
	 * @throws UnsupportedEncodingException 
	 * 
	 */
	@ResponseBody
	@RequestMapping("/list")
	//@RequiresPermissions("tsScheduling:list")
	public R list(String keyword,String company,Integer page, Integer limit) throws UnsupportedEncodingException{
		Map<String, Object> map = new HashMap<>();
		//String company01 = new String(company.getBytes("ISO-8859-1"),"UTF-8");
		map.put("company",company);
		map.put("keyword",keyword);
		map.put("page", (page - 1) * limit);
		map.put("limit", limit);

		List<TsSchedulingEntity> stScheduling = null;
		int total = 0 ;
		//起始行
		if(map.get("company") == null && map.get("keyword") == null || map.get("company").equals("全部")
				|| map.get("company").equals("-1")){
			int page01=(page-1)*limit;
			//查询列表数据
			stScheduling = tsSchedulingServicer.queryListObject(page01,limit);
			//查询总页数
			total = tsSchedulingServicer.queryTotalAll();
		} else {	
			stScheduling = tsSchedulingServicer.queryList(map);
			//查询总页数
			total = tsSchedulingServicer.queryTotal(map);
		}
		PageUtils pageUtil = new PageUtils(stScheduling,total,limit,page);
		return R.ok().put("page",pageUtil);
	} 

	/**
	 * 查询业务员信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	//@RequiresPermissions("tsScheduling:info")
	public R inf0(@PathVariable("id")Long id){
		TsSchedulingEntity tsScheduling = tsSchedulingServicer.queryObjectByUserId(id);
		return R.ok().put("tsScheduling", tsScheduling);

	}

	/**
	 *  值班安排
	 * @throws ParseException 
	 */
	@ResponseBody
	@RequestMapping("/add")
	//@RequiresPermissions("tsScheduling:add")
	public R add(HttpServletRequest request) throws ParseException{
		//参数定义
		String paraName = null;
		Map<String, Object> map = new HashMap<>();
		//获取请求参数并转换
		Enumeration<String> enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			paraName = enu.nextElement();
			map.put(paraName, request.getParameter(paraName));
		}
		if(!StringUtil.isEmpty(StringUtil.toUTF8One(request, "tsUserId")) 
				&& !StringUtil.toUTF8One(request, "tsUserId").equals("-1")){
			map.put("tsUserId", Long.valueOf(StringUtil.toUTF8One(request,
					"tsUserId")));
		}
		if(!StringUtil.isEmpty(StringUtil.toUTF8One(request, "tsScheduling")) 
				&& !StringUtil.toUTF8One(request, "tsScheduling").equals("-1")){
			map.put("tsScheduling", String.valueOf(StringUtil.toUTF8One(request,
					"tsScheduling")));
		}
		//校验值班日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//当前日期
		Calendar calendat = Calendar.getInstance();
		String dateStr = sdf.format(calendat.getTime());
		//值班日期
		Date date = sdf.parse((String) map.get("tsScheduling"));
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		String da = sdf.format(cal.getTime());
		if(cal.before(calendat)&&!(dateStr.equals(da))){
			return R.error("值班日期错误！");
		}
		if(dateStr.equals(da)){
			tsSchedulingServicer.checkScheduling((Long) map.get("tsUserId"),(String) map.get("tsScheduling"));
		} else if(!dateStr.equals(da)){
			tsSchedulingServicer.checkSchedulingChange((Long) map.get("tsUserId"),(String) map.get("tsScheduling"));
		} 

		//新增值班日期
		TsSchedulingEntity schedulingEntity = new TsSchedulingEntity();
		schedulingEntity.setTsscheduling(date);
		schedulingEntity.setTsUserId((Long) map.get("tsUserId"));
		schedulingEntity.setScheduling((String) map.get("tsScheduling"));
		schedulingEntity.setCreatetime(sd.format(calendat.getTime()));
		R r = tsSchedulingServicer.addTsScheduling(schedulingEntity);
		
		return r ;

	}
	/**
	 * 删除值班日期
	 */
	@ResponseBody
	@RequestMapping("/delete/{id}")
	//@RequiresPermissions("tsScheduling:delete")
	public R delete(@PathVariable("id")Long id){	

		tsSchedulingServicer.update(id);

		return R.ok();//redirect:login.html

	}
	

}













