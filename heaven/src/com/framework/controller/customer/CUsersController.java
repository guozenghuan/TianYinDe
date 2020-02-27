package com.framework.controller.customer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.framework.entity.Modul01Entity;
import com.framework.entity.Modul02Entity;
import com.framework.entity.Modul03Entity;
import com.framework.entity.Modul04Entity;
import com.framework.entity.Modul05Entity;
import com.framework.entity.Modul06Entity;
import com.framework.entity.Modul07Entity;
import com.framework.entity.Modul08Entity;
import com.framework.entity.Modul09Entity;
import com.framework.entity.Modul10Entity;
import com.framework.entity.Modul11Entity;
import com.framework.entity.ModulEntity;
import com.framework.entity.ModulMusicEntity;
import com.framework.entity.TbAddressEntity;
import com.framework.entity.TbFlowingRecordEntity;
import com.framework.entity.TcUserEntity;
import com.framework.entity.TcUserPayPasswordEntity;
import com.framework.entity.TcUserWechatEntity;
import com.framework.entity.TcUserWithdrawalEntity;
import com.framework.entity.TsUserEntity;
import com.framework.entity.TyInvitationCashmoneyEntity;
import com.framework.entity.TyInvitationCommentEntity;
import com.framework.entity.TyInvitationGiftEntity;
import com.framework.entity.TyInvitationToinviteEntity;
import com.framework.entity.TyOrderCommodityEntity;
import com.framework.entity.TyOrderServiceEntity;
import com.framework.entity.TyServiceEntity;
import com.framework.entity.TyUserServiceEntity;
import com.framework.service.ModulMusicService;
import com.framework.service.ModulService;
import com.framework.service.TbAddressService;
import com.framework.service.TbFlowingRecordService;
import com.framework.service.TcUserPayPasswordService;
import com.framework.service.TcUserService;
import com.framework.service.TcUserWechatService;
import com.framework.service.TcUserWithdrawalService;
import com.framework.service.TsUserService;
import com.framework.service.TyHelpService;
import com.framework.service.TyInvitationCashmoneyService;
import com.framework.service.TyInvitationCommentService;
import com.framework.service.TyInvitationGiftService;
import com.framework.service.TyInvitationToinviteService;
import com.framework.service.TyOrderCommodityService;
import com.framework.service.TyOrderServiceService;
import com.framework.service.TyServiceService;
import com.framework.service.TyUserServiceService;
import com.framework.session.UserSession;
import com.framework.utils.ConfigUtil;
import com.framework.utils.PageUtils;
import com.framework.utils.R;
import com.framework.utils.StringUtil;


@Controller
@RequestMapping("/customer/user")
@CrossOrigin
public class CUsersController {
	
	@Autowired
	private TbAddressService tbAddressService;
	
	@Autowired
	private TyOrderCommodityService tyOrderCommodityService;
	
	@Autowired
	private TyOrderServiceService tyOrderServiceService;
	
	@Autowired
	private TcUserService tcUserService;
	
	@Autowired
	private TsUserService tsUserService;
	
	@Autowired
	private TyServiceService tyServiceService;
	
	@Autowired
	private ModulService modulService;
	
	@Autowired
	private TbFlowingRecordService tbFlowingRecordService;
	
	@Autowired
	private ModulMusicService modulMusicService;
	
	@Autowired
	private TyInvitationCommentService tyInvitationCommentService;
	
	@Autowired
	private TyInvitationToinviteService tyInvitationToinviteService;
	
	@Autowired
	private TyInvitationCashmoneyService tyInvitationCashmoneyService;
	
	@Autowired
	private TyInvitationGiftService tyInvitationGiftService;
	
	@Autowired
	private TyUserServiceService tyUserServiceService;
	
	@Autowired
	private TcUserWithdrawalService tcUserWithdrawalService;
	
	@Autowired
	private TcUserPayPasswordService tcUserPayPasswordService;
	
	@Autowired
	private TcUserWechatService tcUserWechatService;
	
	@Autowired
	private TyHelpService tyHelpService;
	
	/**
	 * 判断支付密码是否设置过,等于true=已经设置过，false=未设置
	 */
	@Transactional
	@ResponseBody 
	@RequestMapping(value = "/flag_paypwd", method = RequestMethod.POST)
	public R flagWechatpPwd(HttpServletRequest request) {
		TcUserEntity tcUserEntity = UserSession.customer(request);
		/**判断用户是否被冻结**/
		if(tcUserEntity.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		
		//判断用户是否可以设置过支付密码
		if(tcUserPayPasswordService.setPayPwdFlag(tcUserEntity.getId())){
			return R.ok().put("data", false);
		}else{
			return R.ok().put("data", true);
		}
		
	}
	
	/**
	 * 设置支付密码
	 */
	@Transactional
	@ResponseBody 
	@RequestMapping(value = "/set_paypwd", method = RequestMethod.POST)
	public R wechatpPwd(HttpServletRequest request,String payPassword,String payPasswordTwo) {
		TcUserEntity tcUserEntity = UserSession.customer(request);
		/**判断用户是否被冻结**/
		if(tcUserEntity.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		
		//判断用户是否可以设置过支付密码
		if(tcUserPayPasswordService.setPayPwdFlag(tcUserEntity.getId())){
			//可以设置支付密码
			if(StringUtil.isEmpty(payPassword) || StringUtil.isEmpty(payPasswordTwo)){
				return R.error("请填写支付密码");
			}
			if(!StringUtil.isNumeric(payPassword) || !StringUtil.isNumeric(payPasswordTwo)){
				return R.error("支付密码必须是纯数字!");
			}
			if(payPassword.length() != 6 || payPasswordTwo.length() != 6){
				return R.error("支付密码必须6位数!");
			}
			if(!payPassword.trim().equals(payPasswordTwo.trim())){
				return R.error("支付密码输入不一致!");
			}
			
			TcUserPayPasswordEntity tcUserPayPasswordEntity = new TcUserPayPasswordEntity();
			tcUserPayPasswordEntity.setUserId(tcUserEntity.getId());
			// sha256加密
			payPassword = new Sha256Hash(payPassword).toHex();
			tcUserPayPasswordEntity.setPayPassword(payPassword);
			tcUserPayPasswordEntity.setUpdatetime(new Date());
			tcUserPayPasswordService.save(tcUserPayPasswordEntity);
			
			return R.ok("操作成功!");
		}else{
			return R.error("支付密码已经设置过,不可重复设置,如支付密码已经忘记则联系客服!");
		}
		
	}
	
	/**
	 * 修改支付密码
	 */
	@Transactional
	@ResponseBody
	@RequestMapping(value = "/update_paypwd", method = RequestMethod.POST)
	public R withdrawalWechat(HttpServletRequest request,String payPassword,String newPayPassword) {
		TcUserEntity tcUserEntity = UserSession.customer(request);
		/**判断用户是否被冻结**/
		if(tcUserEntity.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		
		//判断用户是否可以设置过支付密码
		if(!tcUserPayPasswordService.setPayPwdFlag(tcUserEntity.getId())){
			//可以修改支付密码
			if(StringUtil.isEmpty(payPassword) || StringUtil.isEmpty(newPayPassword)){
				return R.error("请填写支付密码");
			}
			if(!StringUtil.isNumeric(payPassword) || !StringUtil.isNumeric(newPayPassword)){
				return R.error("支付密码必须是纯数字!");
			}
			if(payPassword.length() != 6 || newPayPassword.length() != 6){
				return R.error("支付密码必须6位数!");
			}
			
			TcUserPayPasswordEntity tcUserPayPasswordEntity = new TcUserPayPasswordEntity();
			tcUserPayPasswordEntity.setUserId(tcUserEntity.getId());
			// sha256加密
			payPassword = new Sha256Hash(payPassword).toHex();
			tcUserPayPasswordEntity.setPayPassword(payPassword);
			List<TcUserPayPasswordEntity> list = tcUserPayPasswordService.queryObjectByKey(tcUserPayPasswordEntity);
			if(list.isEmpty() || list.size() <= 0){
				return R.error("支付密码错误!");
			}
			
			TcUserPayPasswordEntity updatePwd = list.get(0);
			// sha256加密
			newPayPassword = new Sha256Hash(newPayPassword).toHex();
			updatePwd.setPayPassword(newPayPassword);
			tcUserPayPasswordService.update(updatePwd);
			
			return R.ok("操作成功!");
		}else{
			return R.error("支付密码未设置过,不可修改,请先设置支付密码!");
		}
	}
	
	
	/**
	 * 上传微信收款码
	 */
	@Transactional
	@ResponseBody 
	@RequestMapping(value = "/wechat_withdrawal", method = RequestMethod.POST)
	public R withdrawalWechat(HttpServletRequest request) {
		TcUserEntity tcUserEntity = UserSession.customer(request);
		/**判断用户是否被冻结**/
		if(tcUserEntity.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		
		TcUserPayPasswordEntity tcUserPayPasswordEntity = new TcUserPayPasswordEntity();
		tcUserPayPasswordEntity.setUserId(tcUserEntity.getId());
		List<TcUserPayPasswordEntity> list = tcUserPayPasswordService.queryObjectByKey(tcUserPayPasswordEntity);
		if(list.isEmpty() || list.size() <= 0){
			return R.error("请先设置支付密码后再上传微信收款码!");
		}
		
		String payPassword = StringUtil.toUTF8One(request, "payPassword");
		
		// sha256加密
		payPassword = new Sha256Hash(payPassword).toHex();
		//判断支付密码是否正确
		if(!payPassword.trim().equals(list.get(0).getPayPassword().trim())){
			return R.error("支付密码不正确!");
		}
		
		//获取图片
		//取出form-data中的二进制字段
		MultipartHttpServletRequest multipartRequest=(MultipartHttpServletRequest) request;
		//判断主图是否上传
		MultipartFile wechatImage = multipartRequest.getFile("wechat_image");
		if(wechatImage==null){
			return R.error("请上传主图!");
		}
		//判断图片类型
		String mainImageType = wechatImage.getContentType();
		if(!mainImageType.equals("image/jpeg") && !mainImageType.equals("image/jpg") && !mainImageType.equals("image/png")){
			return R.error("微信收款码图片格式错误!只能上传jpeg、jpg、png类型的图片!");
		}
		
		//判断收款码是否上传过
		TcUserWechatEntity uEntity = new TcUserWechatEntity();
		uEntity.setUserId(tcUserEntity.getId());
		List<TcUserWechatEntity> list2 = tcUserWechatService.queryObjectByKey(uEntity);
		
		//保存图片
		String savePathFlag = ConfigUtil.saveWechatPayImage(wechatImage);
		if(StringUtil.isEmpty(savePathFlag)){
			return R.error("保存微信收款码失败,请稍后再试!");
		}
		
		if(list2.isEmpty() || list2.size() <= 0){
			//没有上传过,则新增
			TcUserWechatEntity tcUserWechatEntity = new TcUserWechatEntity();
			tcUserWechatEntity.setUserId(tcUserEntity.getId());
			tcUserWechatEntity.setCreatetime(new Date());
			tcUserWechatEntity.setWechat(savePathFlag);
			tcUserWechatService.save(tcUserWechatEntity);
		}else{
			//已经上传过,则修改
			TcUserWechatEntity tcUserWechatEntity = new TcUserWechatEntity();
			tcUserWechatEntity.setId(list2.get(0).getId());
			tcUserWechatEntity.setUserId(tcUserEntity.getId());
			tcUserWechatEntity.setCreatetime(new Date());
			tcUserWechatEntity.setWechat(savePathFlag);
			tcUserWechatService.update(tcUserWechatEntity);
			
			//删除原来的
			ConfigUtil.delFolder(list2.get(0).getWechat());
		}
		
		return R.ok("操作成功!");
	}
	
	
	/**
	 * 提现申请
	 */
	@Transactional
	@ResponseBody 
	@RequestMapping(value = "/withdrawal", method = RequestMethod.POST)
	public R withdrawal(HttpServletRequest request,BigDecimal money) {
		TcUserEntity tcUserEntity = UserSession.customer(request);
		/**判断用户是否被冻结**/
		if(tcUserEntity.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		return tcUserWithdrawalService.withdrawalByUser(tcUserEntity.getId(),money);
	}
	
	/**
	 * 查看提现申请信息
	 */
	@Transactional
	@ResponseBody 
	@RequestMapping(value = "/user_withdrawal", method = RequestMethod.POST)
	public R userWithdrawal(HttpServletRequest request,Integer page) {
		TcUserEntity tcUserEntity = UserSession.customer(request);
		/**判断用户是否被冻结**/
		if(tcUserEntity.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * 10);
		map.put("limit", 10);
		map.put("userId", tcUserEntity.getId());
		
		//查询列表数据
		List<TcUserWithdrawalEntity> tcUserWithdrawalList = tcUserWithdrawalService.queryList(map);
		int total = tcUserWithdrawalService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tcUserWithdrawalList, total, 10, page);
		
		return R.ok("操作成功!").put("data", pageUtil);
	}
	
	
	/**
	 * 评论
	 */
	@Transactional
	@ResponseBody 
	@RequestMapping(value = "/comment_modul", method = RequestMethod.POST)
	public R commentModul(HttpServletRequest request,Long modulId,String text) {
		TcUserEntity tcUserEntity = UserSession.customer(request);
		/**判断用户是否被冻结**/
		if(tcUserEntity.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		if(StringUtil.isEmpty(text) || StringUtil.isEmpty(modulId)){
			return R.ok();
		}
		if(text.length()>30){
			return R.ok("评论的字数最多30个!");
		}
		//判断该请帖是否存在
		ModulEntity modulEntity = modulService.queryObject(modulId);
		if(StringUtil.isEmpty(modulEntity)){
			return R.error("没有该请帖信息,可能请帖已被删除!");
		}
		
		TyInvitationCommentEntity tyInvitationComment = new TyInvitationCommentEntity();
		tyInvitationComment.setUserId(tcUserEntity.getId());
		tyInvitationComment.setModulId(modulEntity.getId());
		tyInvitationComment.setModulUserId(modulEntity.getUserId());
		tyInvitationComment.setName(tyInvitationToinviteService.nameModul(tcUserEntity));
		tyInvitationComment.setNote(text.trim());
		tyInvitationComment.setCreatetime(new Date());
		tyInvitationCommentService.save(tyInvitationComment);
		
		return R.ok("操作成功!");
	}
	
	/**
	 * 赴邀
	 * @param request
	 * @param modulId
	 * @param name
	 * @param phone
	 * @param number
	 * @return
	 */
	@Transactional
	@ResponseBody 
	@RequestMapping(value = "/toinvite_modul", method = RequestMethod.POST)
	public R toinviteModul(HttpServletRequest request,Long modulId,String name,String phone,Integer number) {
		TcUserEntity tcUserEntity = UserSession.customer(request);
		/**判断用户是否被冻结**/
		if(tcUserEntity.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		if(StringUtil.isEmpty(name) || StringUtil.isEmpty(modulId) || StringUtil.isEmpty(phone) || StringUtil.isEmpty(number)){
			return R.error("请完善所有信息!");
		}
		
		String nameFlag = StringUtil.isNickName(name);
		if(!nameFlag.equals("true")){
			return R.error(nameFlag);
		}
		String phoneFlag = StringUtil.isMobile(phone);
		if(!phoneFlag.equals("true")){
			return R.error(phoneFlag);
		}
		if(number <= 0){
			return R.error("赴邀人数不能小于0!");
		}
		
		//判断该请帖是否存在
		ModulEntity modulEntity = modulService.queryObject(modulId);
		if(StringUtil.isEmpty(modulEntity)){
			return R.error("没有该请帖信息,可能请帖已被删除!");
		}
		
		TyInvitationToinviteEntity toinviteEntity = new TyInvitationToinviteEntity();
		toinviteEntity.setUserId(tcUserEntity.getId());
		toinviteEntity.setModulId(modulEntity.getId());
		toinviteEntity.setModulUserId(modulEntity.getUserId());
		toinviteEntity.setName(name);
		toinviteEntity.setPhone(phone);
		toinviteEntity.setNumber(number);
		toinviteEntity.setCreatetime(new Date());
		tyInvitationToinviteService.save(toinviteEntity);
		
		return R.ok("操作成功!");
	}
	
	/**
	 * 获取评论
	 */
	@ResponseBody
	@RequestMapping(value = "/comment_list", method = RequestMethod.GET)
	public R commentList(HttpServletRequest request,Integer page,String type){
		TcUserEntity tcUserEntity = UserSession.customer(request);
		/**判断用户是否被冻结**/
		if(tcUserEntity.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * 10);
		map.put("limit", 10);
		if(type.trim().equals("shoudao")){//收到的
			map.put("modulUserId", tcUserEntity.getId());
		}else{//发送的
			map.put("userId", tcUserEntity.getId());
		}
		
		//查询列表数据
		List<TyInvitationCommentEntity> tyInvitationCommentList = tyInvitationCommentService.queryList(map);
		int total = tyInvitationCommentService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tyInvitationCommentList, total, 10, page);
		
		return R.ok().put("data", pageUtil);
	}
	
	
	/**
	 * 获取赴邀信息
	 */
	@ResponseBody
	@RequestMapping(value = "/viteservice_list", method = RequestMethod.GET)
	public R viteServiceList(HttpServletRequest request,Integer page,String type){
		TcUserEntity tcUserEntity = UserSession.customer(request);
		/**判断用户是否被冻结**/
		if(tcUserEntity.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * 10);
		map.put("limit", 10);
		if(type.trim().equals("shoudao")){//收到的
			map.put("modulUserId", tcUserEntity.getId());
		}else{//发送的
			map.put("userId", tcUserEntity.getId());
		}
		
		//查询列表数据
		List<TyInvitationToinviteEntity> tyInvitationToinviteList = tyInvitationToinviteService.queryList(map);
		int total = tyInvitationToinviteService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tyInvitationToinviteList, total, 10, page);
		
		return R.ok().put("data", pageUtil);
	}
	
	/**
	 * 获取奠金信息
	 */
	@ResponseBody
	@RequestMapping(value = "/cashmoney_list", method = RequestMethod.GET)
	public R cashmoneyList(HttpServletRequest request,Integer page,String type){
		TcUserEntity tcUserEntity = UserSession.customer(request);
		/**判断用户是否被冻结**/
		if(tcUserEntity.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * 10);
		map.put("limit", 10);
		map.put("status", 0);
		if(type.trim().equals("shoudao")){//收到的
			map.put("modulUserId", tcUserEntity.getId());
		}else{//发送的
			map.put("userId", tcUserEntity.getId());
		}
		
		//查询列表数据
		List<TyInvitationCashmoneyEntity> tyInvitationCashmoneyList = tyInvitationCashmoneyService.queryList(map);
		int total = tyInvitationCashmoneyService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tyInvitationCashmoneyList, total, 10, page);
		
		return R.ok().put("data", pageUtil);
	}
	
	/**
	 * 获取平台礼物信息
	 */
	@ResponseBody
	@RequestMapping(value = "/gift_list", method = RequestMethod.GET)
	public R giftList(HttpServletRequest request,Integer page,String type){
		TcUserEntity tcUserEntity = UserSession.customer(request);
		/**判断用户是否被冻结**/
		if(tcUserEntity.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * 10);
		map.put("limit", 10);
		map.put("status", 0);
		if(type.trim().equals("shoudao")){//收到的
			map.put("modulUserId", tcUserEntity.getId());
		}else{//发送的
			map.put("userId", tcUserEntity.getId());
		}
		
		//查询列表数据
		List<TyInvitationGiftEntity> tyInvitationGiftList = tyInvitationGiftService.queryList(map);
		int total = tyInvitationGiftService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tyInvitationGiftList, total, 10, page);
		
		return R.ok().put("data", pageUtil);
	}
	
	
	/**
	 * 获取用户信息
	 */
	@ResponseBody 
	@RequestMapping(value = "/user_msg", method = RequestMethod.GET)
	public R userMsg(HttpServletRequest request) {
		TcUserEntity tcUserEntity = UserSession.customer(request);
		/**判断用户是否被冻结**/
		if(tcUserEntity.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		
		TcUserEntity userEntity = tcUserService.queryObject(tcUserEntity.getId());
		userEntity.setOpenid(null);
		return R.ok().put("data", userEntity);
	}
	
	/**
	 * 用户流水信息
	 * @param request
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user_flowing", method = RequestMethod.GET)
	public R list(HttpServletRequest request,Integer page){
		TcUserEntity tcUserEntity = UserSession.customer(request);
		/**判断用户是否被冻结**/
		if(tcUserEntity.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * 10);
		map.put("limit", 10);
		map.put("userid", tcUserEntity.getId());
		
		//查询列表数据
		List<TbFlowingRecordEntity> tcFlowingList = tbFlowingRecordService.queryList(map);
		int total = tbFlowingRecordService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tcFlowingList, total, 10, page);
		
		return R.ok().put("data", pageUtil);
	}
	
	/**
	 * 获取用户收货地址信息
	 */
	@ResponseBody 
	@RequestMapping(value = "/commodity_address", method = RequestMethod.GET)
	public R commodityAddress(HttpServletRequest request) {
		TcUserEntity tcUserEntity = UserSession.customer(request);
		/**判断用户是否被冻结**/
		if(tcUserEntity.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		
		TbAddressEntity tbAddress = new TbAddressEntity();
		tbAddress.setUserId(tcUserEntity.getId());
		List<TbAddressEntity> list = tbAddressService.queryObjectByKey(tbAddress);
		
		return R.ok().put("data", list);
	}
	
	/**
	 * 新增用户收货地址信息
	 */
	@ResponseBody 
	@RequestMapping(value = "/add_commodity_address", method = RequestMethod.POST)
	public R addCommodityAddress(HttpServletRequest request,String name,String phone,String address) {
		TcUserEntity tcUserEntity = UserSession.customer(request);
		/**判断用户是否被冻结**/
		if(tcUserEntity.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		
		String nameFlag = StringUtil.isNickName(name);
		if(!nameFlag.equals("true")){
			return R.error(nameFlag);
		}
		String phoneFlag = StringUtil.isMobile(phone);
		if(!phoneFlag.equals("true")){
			return R.error(phoneFlag);
		}
		if(StringUtil.isEmpty(address)){
			return R.error("请填写地址!");
		}
		
		TbAddressEntity tbAddress = new TbAddressEntity();
		tbAddress.setUserId(tcUserEntity.getId());
		tbAddress.setName(name);
		tbAddress.setPhone(phone);
		tbAddress.setAddres(address);
		tbAddress.setCreatetime(new Date());
		
		tbAddressService.save(tbAddress);
		
		return R.ok("操作成功!");
	}
	
	/**
	 * 修改用户收货地址信息
	 */
	@ResponseBody 
	@RequestMapping(value = "/update_commodity_address", method = RequestMethod.POST)
	public R updateCommodityAddress(HttpServletRequest request,Long addressId,String name,String phone,String address) {
		TcUserEntity tcUserEntity = UserSession.customer(request);
		/**判断用户是否被冻结**/
		if(tcUserEntity.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		
		//查找该地址是否存在
		TbAddressEntity quEntity = new TbAddressEntity();
		quEntity.setId(addressId);
		quEntity.setUserId(tcUserEntity.getId());
		List<TbAddressEntity> quEntityList = tbAddressService.queryObjectByKey(quEntity);
		if(quEntityList.isEmpty() || quEntityList.size() <= 0){
			return R.error("查找不到该收货地址,请重新选择要修改的收货地址!");
		}
		
		String nameFlag = StringUtil.isNickName(name);
		if(!name.equals("true")){
			return R.error(nameFlag);
		}
		String phoneFlag = StringUtil.isMobile(phone);
		if(!phoneFlag.equals("true")){
			return R.error(phoneFlag);
		}
		if(StringUtil.isEmpty(address)){
			return R.error("请填写地址!");
		}
		
		TbAddressEntity tbAddress = new TbAddressEntity();
		tbAddress.setId(addressId);
		tbAddress.setUserId(tcUserEntity.getId());
		tbAddress.setName(name);
		tbAddress.setPhone(phone);
		tbAddress.setAddres(address);
		
		tbAddressService.update(tbAddress);
		
		return R.ok("操作成功!");
	}
	
	/**
	 * 获取商品订单信息列表
	 */
	@ResponseBody 
	@RequestMapping(value = "/commodity_orders", method = RequestMethod.GET)
	public R commodityOrders(HttpServletRequest request,Integer type) {
		TcUserEntity tcUserEntity = UserSession.customer(request);
		/**判断用户是否被冻结**/
		if(tcUserEntity.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		
		TyOrderCommodityEntity tyOrderCommodity = new TyOrderCommodityEntity();
		tyOrderCommodity.setUserId(tcUserEntity.getId());
		if(type == 0){//全部订单
			
		}else if(type == 1){//待付款
			tyOrderCommodity.setPayStatus(0);
		}else if(type == 2){//待发货
			tyOrderCommodity.setExpressStatus(0);
			tyOrderCommodity.setStatus(0);
		}else if(type == 3){//已发货
			tyOrderCommodity.setExpressStatus(1);
			tyOrderCommodity.setStatus(1);
		}else if(type == 4){//已退款
			tyOrderCommodity.setStatus(2);
		}
		
		List<TyOrderCommodityEntity> list = tyOrderCommodityService.queryObjectKey(tyOrderCommodity);
		return R.ok().put("data", list);
	}
	
	/**
	 * 获取商品订单详细信息
	 */
	@ResponseBody 
	@RequestMapping(value = "/commodity_orders_id", method = RequestMethod.GET)
	public R commodityOrdersId(HttpServletRequest request,Long id) {
		TcUserEntity tcUserEntity = UserSession.customer(request);
		/**判断用户是否被冻结**/
		if(tcUserEntity.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		
		TyOrderCommodityEntity tyOrderCommodity = new TyOrderCommodityEntity();
		tyOrderCommodity.setId(id);
		tyOrderCommodity.setUserId(tcUserEntity.getId());
		List<TyOrderCommodityEntity> list = tyOrderCommodityService.queryObjectKey(tyOrderCommodity);
		if(list.isEmpty() || list.size() <= 0){
			return R.error("查找不到该订单信息!");
		}
		
		return R.ok().put("data", list.get(0));
	}
	
	/**
	 * 获取服务订单信息列表
	 */ 
	@ResponseBody 
	@RequestMapping(value = "/service_orders", method = RequestMethod.GET)
	public R serviceOrders(HttpServletRequest request,Integer type) {
		TcUserEntity tcUserEntity = UserSession.customer(request);
		/**判断用户是否被冻结**/
		if(tcUserEntity.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		Map<String,Object> map = new HashMap<>();
		map.put("userid", tcUserEntity.getId());//tOrderServiceEntity.setUserid(tcUserEntity.getId());
		map.put("payStatus", "1");//tOrderServiceEntity.setPayStatus(1);
		if(type == 0){//服务中      业务员确认/业务员未确认            线上，未付款 / 线下,业务员未确认
			map.put("priceStatusOne", "0");// 线上，未付款    
			//map.put("priceStatusTwo", "0");// 线下,业务员未确认
		}else{//已完成          线下付款/业务员确认      线上付款/完成付款
			map.put("priceStatusThree", "0");// 线下付款/业务员确认
			//map.put("priceStatusFour", "0"); //线上付款/完成付款
		}
		
		List<TyOrderServiceEntity> list = tyOrderServiceService.queryListByMap(map);
		return R.ok().put("data", list);
	}
	
	/**
	 * 获取服务订单详细信息
	 */
	@ResponseBody 
	@RequestMapping(value = "/service_orders_id", method = RequestMethod.GET)
	public R serviceOrdersId(HttpServletRequest request,Long id) {
		TcUserEntity tcUserEntity = UserSession.customer(request);
		/**判断用户是否被冻结**/
		if(tcUserEntity.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		
		TyOrderServiceEntity tyOrderCommodity = new TyOrderServiceEntity();
		tyOrderCommodity.setId(id);
		tyOrderCommodity.setUserid(tcUserEntity.getId());
		List<TyOrderServiceEntity> list = tyOrderServiceService.queryObjectByKey(tyOrderCommodity);
		if(list.isEmpty() || list.size() <= 0){
			return R.error("查找不到该订单信息!");
		}
		tyOrderCommodity = list.get(0);
		
		Map<String, Object> tsuserMap = new HashMap<>();
		if(!StringUtil.isEmpty(tyOrderCommodity.getTsUserid())){
			TsUserEntity tsUserEntity = tsUserService.queryObject(tyOrderCommodity.getTsUserid());
			tsUserEntity.setAccount(null);
			tsUserEntity.setPassword(null);
			tsUserEntity.setCompany(null);
			tsUserEntity.setCompanyId(null);
			tsUserEntity.setCreatetime(null);
			tsUserEntity.setWechatOpenid(null);
			
			if(StringUtil.isEmpty(tsUserEntity.getScore()) || tsUserEntity.getScore().compareTo(new BigDecimal(0)) == 0){
				tsUserEntity.setScore(new BigDecimal(5));
			}else{
				tsUserEntity.setScore(tsUserEntity.getScore().setScale(0, BigDecimal.ROUND_HALF_UP));
			}
			
			tsuserMap.put("status", true);
			tsuserMap.put("data", tsUserEntity);
		}else{
			tsuserMap.put("status", false);
			tsuserMap.put("data", "业务员正在派送中");
		}
		
		Map<String, Object> data = new HashMap<>();
		
		if(tyOrderCommodity.getServiceStatus() == 0){
			TyServiceEntity tyService = new TyServiceEntity();
			tyService.setParentId(0L);//只有最大的父亲id
			
			List<TyServiceEntity> nullList = new ArrayList<>();
			TyServiceEntity neEntity = new TyServiceEntity();
			neEntity.setId(-1L);
			nullList.add(neEntity);
			//服务详情数据
			tyService.setType("fuwu");
			List<TyServiceEntity> fuwu = tyServiceService.queryObjectByKeySort(tyService);
			for(TyServiceEntity fEntity : fuwu){
				TyServiceEntity fSend = new TyServiceEntity();
				fSend.setParentId(fEntity.getId());
				List<TyServiceEntity> fuwuSecond = tyServiceService.queryObjectByKeySort(fSend);
				if(fuwuSecond.isEmpty() || fuwuSecond.size() <= 0){
					fEntity.setChildrenFw(nullList);
				}else{
					fEntity.setChildrenFw(fuwuSecond);
				}
			}
			/*List<TyServiceEntity> fuwuTwo = new ArrayList<>();
			for(TyServiceEntity tyServiceEntity : fuwu){
				if(!tyServiceEntity.getChildrenFw().isEmpty() && tyServiceEntity.getChildrenFw() != null){
					for(TyServiceEntity tyServiceEntityTwo : tyServiceEntity.getChildrenFw()){
						fuwuTwo.add(tyServiceEntityTwo);
					}
				}
			}*/
			
			data.put("service", fuwu);
		}else{
			TyUserServiceEntity tyUserService = new TyUserServiceEntity();
			tyUserService.setParentId(id);
			List<TyUserServiceEntity> userFW = tyUserServiceService.queryObjectByKey(tyUserService);
			data.put("service", userFW);
			/*TyServiceEntity tyService = new TyServiceEntity();
			tyService.setParentId(0L);//只有最大的父亲id
			
			List<TyServiceEntity> nullList = new ArrayList<>();
			TyServiceEntity neEntity = new TyServiceEntity();
			neEntity.setId(-1L);
			nullList.add(neEntity);
			//服务详情数据
			tyService.setType(String.valueOf(id));
			fuwu = tyServiceService.queryObjectByKeySortUser(tyService);
			for(TyServiceEntity fEntity : fuwu){
				TyServiceEntity fSend = new TyServiceEntity();
				fSend.setParentId(fEntity.getId());
				List<TyServiceEntity> fuwuSecond = tyServiceService.queryObjectByKeySortUser(fSend);
				if(fuwuSecond.isEmpty() || fuwuSecond.size() <= 0){
					fEntity.setChildrenFw(nullList);
				}else{
					fEntity.setChildrenFw(fuwuSecond);
				}
			}*/
		}
		
		String functionCode = "";
		String text = "";
		if(tyOrderCommodity.getStatus() == 1 && tyOrderCommodity.getUserStatus() == 1){
			functionCode = "show_text";
			text="服务已完成";
			if(StringUtil.isEmpty(tyOrderCommodity.getScore())){
				//需要对业务员评价，显示评价按钮
				functionCode = "show_PingJia";
				text="显示评价按钮";
			}
		}else if(tyOrderCommodity.getStatus() == 1 && tyOrderCommodity.getUserStatus() == 0){
			functionCode = "show_QueRen";
			text="服务已完成,待确认";
		}else if(tyOrderCommodity.getPriceStatus() == 1 && tyOrderCommodity.getStatus() == 0){
			functionCode = "show_text";
			text = "正在服务中";
		}else if(tyOrderCommodity.getPriceStatus() == 2 && tyOrderCommodity.getStatus() == 0){
			functionCode = "show_text";
			text = "正在服务中,服务结束后收款";
		}else if(tyOrderCommodity.getServiceStatus() == 1 && tyOrderCommodity.getStatus() == 0){
			functionCode = "show_FuKuan";
			text = "显示付款服务套餐按钮";
		}else if(tyOrderCommodity.getTsUserStatus() == 1 && tyOrderCommodity.getStatus() == 0){
			functionCode = "show_text";
			text = "已派单业务员,稍后业务员会与您联系";
		}else{
			functionCode = "show_text";
			text = "正在派单业务员";
		}
		if(tyOrderCommodity.getIsCollect()==1){
	
		    if(tyOrderCommodity.getDepStu()==0){
	            functionCode = "show_deposit";
	        }else if(tyOrderCommodity.getDepStu()==1){
	        	if(tyOrderCommodity.getPriceStatus() == 0) {
	        		functionCode = "show_tailMoney";
	        	}
	        } 
		}
		Map<String, String> bottom = new HashMap<>();
		bottom.put("functionCode", functionCode);
		bottom.put("text", text);
		
		data.put("order", list.get(0));
		data.put("salesman", tsuserMap);
		data.put("bottom", bottom);
		
//		Map<String,Object> depositABP= tyOrderServiceService.checkDeposit(tyOrderCommodity);
//		//订金
//		data.put("deposit", depositABP.get("deposit"));
//		//尾款 
//		data.put("balancePayment", depositABP.get("balancePayment"));
		 
		return R.ok().put("data", data);
	}
	
	/**
	 * 确定服务订单已经完成
	 */
	@ResponseBody 
	@RequestMapping(value = "/ok_service_order", method = RequestMethod.POST)
	public R okServiceOrder(HttpServletRequest request,Long serviceId) {
		TcUserEntity tcUserEntity = UserSession.customer(request);
		/**判断用户是否被冻结**/
		if(tcUserEntity.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		
		TyOrderServiceEntity tyOrderCommodity = new TyOrderServiceEntity();
		tyOrderCommodity.setId(serviceId);
		tyOrderCommodity.setUserid(tcUserEntity.getId());
		List<TyOrderServiceEntity> list = tyOrderServiceService.queryObjectByKey(tyOrderCommodity);
		if(list.isEmpty() || list.size() <= 0){
			return R.error("查找不到该订单信息!");
		}
		tyOrderCommodity = list.get(0);
		
		/*if(tyOrderCommodity.getPriceStatus() == 0){
			return R.error("服务未付款,您可以选择线上支付或线下支付,选择线下支付方式请联系业务员!");
		}*/
		if(tyOrderCommodity.getStatus() == 0){
			return R.error("服务还在进行中,请等待业务员确认完成后再操作!");
		}
		if(tyOrderCommodity.getUserStatus() == 1){
			return R.error("服务订单您已确认完成,无需重复操作!");
		}
		
		TyOrderServiceEntity update =  new TyOrderServiceEntity();
		update.setId(serviceId);
		update.setUserStatus(1);
		tyOrderServiceService.update(update);
		
		return R.ok("操作成功!");
	}
	
	/**
	 * 获取维权信息
	 */
	@ResponseBody
	@RequestMapping(value = "/service_order_wq", method = RequestMethod.GET)
	public R serviceOrderWq(HttpServletRequest request){
		TcUserEntity tcUserEntity = UserSession.customer(request);
		/**判断用户是否被冻结**/
		if(tcUserEntity.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		
		return R.ok().put("data", tyHelpService.queryWQ());
	}
	
	
	/**
	 * 评价服务订单
	 */
	@ResponseBody 
	@RequestMapping(value = "/evaluate_service_order", method = RequestMethod.POST)
	public R evaluateServiceOrder(HttpServletRequest request,Long serviceId,Integer score,String evaluate) {
		TcUserEntity tcUserEntity = UserSession.customer(request);
		/**判断用户是否被冻结**/
		if(tcUserEntity.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		
		TyOrderServiceEntity tyOrderCommodity = new TyOrderServiceEntity();
		tyOrderCommodity.setId(serviceId);
		tyOrderCommodity.setUserid(tcUserEntity.getId());
		List<TyOrderServiceEntity> list = tyOrderServiceService.queryObjectByKey(tyOrderCommodity);
		if(list.isEmpty() || list.size() <= 0){
			return R.error("查找不到该订单信息!");
		}
		tyOrderCommodity = list.get(0);
		
		if(tyOrderCommodity.getStatus() == 0){
			return R.error("服务还在进行中,暂时不能评价!");
		}
		if(StringUtil.isEmpty(score) || StringUtil.isEmpty(evaluate)){
			return R.error("评分和评价必须填写才能提交成功!");
		}
		if(score != 1 && score != 2 && score != 3 && score != 4 && score != 5){
			return R.error("评分格式错误,评分最少为一颗星!");
		}
		if(evaluate.length() > 100){
			return R.error("评论最多只能输入一百个字符!");
		}
		
		TyOrderServiceEntity update =  new TyOrderServiceEntity();
		update.setId(serviceId);
		update.setScore(score);
		update.setEvaluate(evaluate);
		tyOrderServiceService.update(update);
		
		return R.ok("操作成功!");
	}
	
	/**
	 * 选择请帖
	 */
	@ResponseBody
	@RequestMapping(value = "/select_modul", method = RequestMethod.POST)
	public R selectModul(HttpServletRequest request,Long id){
		TcUserEntity tcUserEntity = UserSession.customer(request);
		/**判断用户是否被冻结**/
		if(tcUserEntity.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		
		return modulService.userSelectModulNow(tcUserEntity.getId(),id);
	}
	
	/**
	 * 获取用户自己的请帖
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user_moduls", method = RequestMethod.GET)
	public R moduls(HttpServletRequest request,Integer page){
		TcUserEntity tcUserEntity = UserSession.customer(request);
		/**判断用户是否被冻结**/
		if(tcUserEntity.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * 10);
		map.put("limit", 10);
		map.put("modulType", "user");
		map.put("userId", tcUserEntity.getId());
		
		//查询列表数据
		List<ModulEntity> modulList = modulService.queryListByNow(map);
		int total = modulService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(modulList, total, 10, page);
		
		return R.ok().put("data", pageUtil);
	}
	
	/**
	 * 获取主请帖下所有请帖信息,可编辑
	 *//*
	@ResponseBody
	@RequestMapping(value = "/user_modul_all_info", method = RequestMethod.GET)
	public R modulsAllInfo(HttpServletRequest request,Long id){
		TcUserEntity tcUserEntity = UserSession.customer(request);
		*//**判断用户是否被冻结**//*
		if(tcUserEntity.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		
		if(StringUtil.isEmpty(id)){
			return R.error("查找不到该请帖信息,请重新选择请帖!");
		}
		//判断该请帖是否属于该用户
		ModulEntity userModul = new ModulEntity();
		userModul.setId(id);
		userModul.setUserId(tcUserEntity.getId());
		List<ModulEntity> userModulList = modulService.queryObjectByKey(userModul);
		if(userModulList.isEmpty() || userModulList.size() <= 0){
			return R.error("查找不到该请帖信息,请重新选择请帖!");
		}
		
		Boolean seetingBtn = true;//可编辑
		ModulEntity modul = modulService.queryObject(id,seetingBtn);
		return R.ok().put("data", modul);
	}*/
	
	/**
	 * 修改请帖信息
	 */
	@Transactional
	@ResponseBody
	@RequestMapping(value = "/save_information_modul", method = RequestMethod.POST)
	public R saveInformationModul(HttpServletRequest request){
		TcUserEntity tcUserEntity = UserSession.customer(request);
		/**判断用户是否被冻结**/
		if(tcUserEntity.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		
		String mainModulIdStr = request.getParameter("mainModulId");
		if(StringUtil.isEmpty(mainModulIdStr)){
			return R.error("查找不到改请帖,请重新选择请帖后编辑!");
		}
		Long mainModulId;
		try {
			mainModulId = Long.valueOf(mainModulIdStr);
		} catch (Exception e) {
			return R.error("查找不到改请帖,请重新选择请帖后编辑!");
		}
		
		String modulBodyHtml = request.getParameter("modulBodyHtml");
		
		//判断该请帖是否属于用户
		ModulEntity modulEntity = modulService.queryObject(mainModulId);
		if(StringUtil.isEmpty(modulEntity)){
			return R.error("查找不到该请帖信息,请重新选择请帖!");
		}
		if(!modulEntity.getUserId().equals(tcUserEntity.getId())){
			return R.error("查找不到该请帖信息,请重新选择请帖!");
		}
		ModulEntity modul = new ModulEntity();
		modul.setId(modulEntity.getId());
		modul.setMusicId(modulEntity.getMusicId());
		modul.setStatus(modulEntity.getStatus());
		modul.setOneModulPath(modulBodyHtml);
		return modulService.update(modul);
	}
	
	/**
	 * 保存请帖信息
	 *//*
	@Transactional
	@ResponseBody
	@RequestMapping(value = "/save_information_modul", method = RequestMethod.POST)
	public R saveInformationModul(HttpServletRequest request){
		TcUserEntity tcUserEntity = UserSession.customer(request);
		*//**判断用户是否被冻结**//*
		if(tcUserEntity.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		//获取请帖类型
		String modulCode = request.getParameter("modul");
		//获取子请帖的主请帖id
		Long mainModulId = Long.valueOf(request.getParameter("mainModulId"));
		if(StringUtil.isEmpty(modulCode)){
			return R.error("查找不到改请帖,请重新选择请帖后编辑!");
		}
		
		//判断该请帖是否属于用户
		ModulEntity modulEntity = modulService.queryObject(mainModulId);
		if(StringUtil.isEmpty(modulEntity)){
			return R.error("查找不到该请帖信息,请重新选择请帖!");
		}
		if(!modulEntity.getUserId().equals(tcUserEntity.getId())){
			return R.error("查找不到该请帖信息,请重新选择请帖!");
		}
		
		*//**
		 * 判断该请帖是否属于用户
		 *//*
		if (modulCode.trim().equals("modul_01")) {
			//转换成实体类
			Modul01Entity modul01Entity = JSONObject.toJavaObject(JSON.parseObject(request.getParameter("entity")), Modul01Entity.class);
			if(StringUtil.isEmpty(modul01Entity) || modul01Entity.getId() == null){
				return R.error("请填写请帖信息!");
			}
			modulCode = modulCode.trim()+"="+modul01Entity.getId()+";";
			if(!StringUtil.isIndexOfStr(modulEntity.getModulNameAndId(), modulCode)){
				return R.error("查找不到该请帖信息,请重新选择请帖!");
			}
		} else if (modulCode.trim().equals("modul_02")) {
			//转换成实体类
			Modul02Entity modul02Entity = JSONObject.toJavaObject(JSON.parseObject(request.getParameter("entity")), Modul02Entity.class);
			if(StringUtil.isEmpty(modul02Entity) || modul02Entity.getId() == null){
				return R.error("请填写请帖信息!");
			}
			modulCode = modulCode.trim()+"="+modul02Entity.getId()+";";
			if(!StringUtil.isIndexOfStr(modulEntity.getModulNameAndId(), modulCode)){
				return R.error("查找不到该请帖信息,请重新选择请帖!");
			}
		} else if (modulCode.trim().equals("modul_03")) {
			//转换成实体类
			Modul03Entity modul03Entity = JSONObject.toJavaObject(JSON.parseObject(request.getParameter("entity")), Modul03Entity.class);
			if(StringUtil.isEmpty(modul03Entity) || modul03Entity.getId() == null){
				return R.error("请填写请帖信息!");
			}
			modulCode = modulCode.trim()+"="+modul03Entity.getId()+";";
			if(!StringUtil.isIndexOfStr(modulEntity.getModulNameAndId(), modulCode)){
				return R.error("查找不到该请帖信息,请重新选择请帖!");
			}
		} else if (modulCode.trim().equals("modul_04")) {
			//转换成实体类
			Modul04Entity modul04Entity = JSONObject.toJavaObject(JSON.parseObject(request.getParameter("entity")), Modul04Entity.class);
			if(StringUtil.isEmpty(modul04Entity) || modul04Entity.getId() == null){
				return R.error("请填写请帖信息!");
			}
			modulCode = modulCode.trim()+"="+modul04Entity.getId()+";";
			if(!StringUtil.isIndexOfStr(modulEntity.getModulNameAndId(), modulCode)){
				return R.error("查找不到该请帖信息,请重新选择请帖!");
			}
		} else if (modulCode.trim().equals("modul_05")) {
			//转换成实体类
			Modul05Entity modul05Entity = JSONObject.toJavaObject(JSON.parseObject(request.getParameter("entity")), Modul05Entity.class);
			if(StringUtil.isEmpty(modul05Entity) || modul05Entity.getId() == null){
				return R.error("请填写请帖信息!");
			}
			modulCode = modulCode.trim()+"="+modul05Entity.getId()+";";
			if(!StringUtil.isIndexOfStr(modulEntity.getModulNameAndId(), modulCode)){
				return R.error("查找不到该请帖信息,请重新选择请帖!");
			}
		} else if (modulCode.trim().equals("modul_06")) {
			//转换成实体类
			Modul06Entity modul06Entity = JSONObject.toJavaObject(JSON.parseObject(request.getParameter("entity")), Modul06Entity.class);
			if(StringUtil.isEmpty(modul06Entity) || modul06Entity.getId() == null){
				return R.error("请填写请帖信息!");
			}
			modulCode = modulCode.trim()+"="+modul06Entity.getId()+";";
			if(!StringUtil.isIndexOfStr(modulEntity.getModulNameAndId(), modulCode)){
				return R.error("查找不到该请帖信息,请重新选择请帖!");
			}
		} else if (modulCode.trim().equals("modul_07")) {
			//转换成实体类
			Modul07Entity modul07Entity = JSONObject.toJavaObject(JSON.parseObject(request.getParameter("entity")), Modul07Entity.class);
			if(StringUtil.isEmpty(modul07Entity) || modul07Entity.getId() == null){
				return R.error("请填写请帖信息!");
			}
			modulCode = modulCode.trim()+"="+modul07Entity.getId()+";";
			if(!StringUtil.isIndexOfStr(modulEntity.getModulNameAndId(), modulCode)){
				return R.error("查找不到该请帖信息,请重新选择请帖!");
			}
		} else if (modulCode.trim().equals("modul_08")) {
			//转换成实体类
			Modul08Entity modul08Entity = JSONObject.toJavaObject(JSON.parseObject(request.getParameter("entity")), Modul08Entity.class);
			if(StringUtil.isEmpty(modul08Entity) || modul08Entity.getId() == null){
				return R.error("请填写请帖信息!");
			}
			modulCode = modulCode.trim()+"="+modul08Entity.getId()+";";
			if(!StringUtil.isIndexOfStr(modulEntity.getModulNameAndId(), modulCode)){
				return R.error("查找不到该请帖信息,请重新选择请帖!");
			}
		} else if (modulCode.trim().equals("modul_09")) {
			//转换成实体类
			Modul09Entity modul09Entity = JSONObject.toJavaObject(JSON.parseObject(request.getParameter("entity")), Modul09Entity.class);
			if(StringUtil.isEmpty(modul09Entity) || modul09Entity.getId() == null){
				return R.error("请填写请帖信息!");
			}
			modulCode = modulCode.trim()+"="+modul09Entity.getId()+";";
			if(!StringUtil.isIndexOfStr(modulEntity.getModulNameAndId(), modulCode)){
				return R.error("查找不到该请帖信息,请重新选择请帖!");
			}
		} else if (modulCode.trim().equals("modul_10")) {
			//转换成实体类
			Modul10Entity modul10Entity = JSONObject.toJavaObject(JSON.parseObject(request.getParameter("entity")), Modul10Entity.class);
			if(StringUtil.isEmpty(modul10Entity) || modul10Entity.getId() == null){
				return R.error("请填写请帖信息!");
			}
			modulCode = modulCode.trim()+"="+modul10Entity.getId()+";";
			if(!StringUtil.isIndexOfStr(modulEntity.getModulNameAndId(), modulCode)){
				return R.error("查找不到该请帖信息,请重新选择请帖!");
			}
		} else if (modulCode.trim().equals("modul_11")) {
			//转换成实体类
			Modul11Entity modul11Entity = JSONObject.toJavaObject(JSON.parseObject(request.getParameter("entity")), Modul11Entity.class);
			if(StringUtil.isEmpty(modul11Entity) || modul11Entity.getId() == null){
				return R.error("请填写请帖信息!");
			}
			modulCode = modulCode.trim()+"="+modul11Entity.getId()+";";
			if(!StringUtil.isIndexOfStr(modulEntity.getModulNameAndId(), modulCode)){
				return R.error("查找不到该请帖信息,请重新选择请帖!");
			}
		}
		
		return modulService.saveInformationModul(request);
	}*/
	
	/**
	 * 删除子请帖信息
	 * @param id
	 * @param modulCode
	 * @return
	 *//*
	@Transactional
	@ResponseBody
	@RequestMapping(value = "/delete_child_modul", method = RequestMethod.POST)
	public R deleteModul(HttpServletRequest request,Long mainModulId,Long id,String modulCode){
		TcUserEntity tcUserEntity = UserSession.customer(request);
		*//**判断用户是否被冻结**//*
		if(tcUserEntity.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		if(StringUtil.isEmpty(mainModulId) || StringUtil.isEmpty(id) || StringUtil.isEmpty(modulCode)){
			return R.error("获取不到请帖信息,请刷新页面后重新操作!");
		}
		
		//判断该请帖是否属于该用户
		ModulEntity userModul = new ModulEntity();
		userModul.setId(mainModulId);
		userModul.setUserId(tcUserEntity.getId());
		List<ModulEntity> userModulList = modulService.queryObjectByKey(userModul);
		if(userModulList.isEmpty() || userModulList.size() <= 0){
			return R.error("查找不到该请帖信息,请重新选择请帖!");
		}
		//判断该子请帖是否属于用户
		String modulflag = modulCode.trim()+"="+id+";";
		if(!StringUtil.isIndexOfStr(userModulList.get(0).getModulNameAndId(), modulflag)){
			return R.error("查找不到该请帖信息,请重新选择请帖!");
		}
		
		return modulService.deleteModul(mainModulId,id,modulCode);
	}*/
	
	/**
	 * 删除主请帖
	 */
	@Transactional
	@ResponseBody
	@RequestMapping(value = "/delete_modul", method = RequestMethod.POST)
	public R delete(HttpServletRequest request,Long id){
		TcUserEntity tcUserEntity = UserSession.customer(request);
		/**判断用户是否被冻结**/
		if(tcUserEntity.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		if(StringUtil.isEmpty(id)){
			return R.error("获取不到请帖信息,请刷新页面后重新操作!");
		}
		
		//判断该请帖是否属于该用户
		ModulEntity userModul = new ModulEntity();
		userModul.setId(id);
		userModul.setUserId(tcUserEntity.getId());
		List<ModulEntity> userModulList = modulService.queryObjectByKey(userModul);
		if(userModulList.isEmpty() || userModulList.size() <= 0){
			return R.error("查找不到该请帖信息,请重新选择请帖!");
		}
		
		ModulEntity modulEntity = modulService.queryObject(id);
		if(StringUtil.isEmpty(modulEntity)){
			return R.error("没有该请帖信息,请刷新后重新选择要删除的请帖!");
		}
		if(modulEntity.getModulType().trim().equals("system")){
			return R.error("平台的公共请帖不可删除!");
		}
		
		Long[] ids = {id};
		modulService.deleteBatch(ids);
		
		//删除请帖信息
		ConfigUtil.deleteFile(modulEntity.getModulNameAndId());
		//删除文件夹
		//判断修改的文件是属于系统还是用户
    	String saveFile = "statics";//默认系统
    	String[] modulPathStrs = modulEntity.getModulNameAndId().split("/");
    	String typeName = modulPathStrs[modulPathStrs.length-1];
    	if(StringUtil.isEmpty(typeName) || !StringUtil.isIndexOfStr(typeName, ".")){
    		return R.error("没有该请帖信息,请刷新后重新选择要删除的请帖!");
    	}
    	if(!typeName.trim().equals("modul.html")){
    		//就是用户
    		saveFile = typeName.split("\\.")[0].trim();
    	}else{
    		return R.error("平台的公共请帖不可删除!");
    	}
    	//保存图片的文件夹路径
    	String webappsFilePath = modulEntity.getModulNameAndId().replace(typeName.trim(), saveFile+"/deleteDefoutStr");//保存到html代码中的文件夹路径
    	ConfigUtil.delFolder(webappsFilePath);
		
		return R.ok("操作成功!");
	}
	
	/**
	 *  获取音乐
	 * @param request
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/modul_musics", method = RequestMethod.GET)
	public R updateModulMusic(HttpServletRequest request,Integer page){
		TcUserEntity tcUserEntity = UserSession.customer(request);
		/**判断用户是否被冻结**/
		if(tcUserEntity.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * 15);
		map.put("limit", 15);
		
		//查询列表数据
		List<ModulMusicEntity> modulMusicList = modulMusicService.queryList(map);
		int total = modulMusicService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(modulMusicList, total, 15, page);
		
		return R.ok("操作成功!").put("data", pageUtil);
	}
	
	/**
	 * 修改请帖音乐
	 * @param request
	 * @param modulId
	 * @param musicId
	 * @return
	 */
	@Transactional
	@ResponseBody
	@RequestMapping(value = "/update_modul_music", method = RequestMethod.POST)
	public R updateModulMusic(HttpServletRequest request,Long modulId,Long musicId){
		TcUserEntity tcUserEntity = UserSession.customer(request);
		/**判断用户是否被冻结**/
		if(tcUserEntity.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		if(StringUtil.isEmpty(modulId) || StringUtil.isEmpty(musicId)){
			return R.error("没有该请帖或音乐的信息,请重新编辑!");
		}
		
		//判断该请帖是否属于该用户
		ModulEntity userModul = new ModulEntity();
		userModul.setId(modulId);
		userModul.setUserId(tcUserEntity.getId());
		List<ModulEntity> userModulList = modulService.queryObjectByKey(userModul);
		if(userModulList.isEmpty() || userModulList.size() <= 0){
			return R.error("查找不到该请帖信息,请重新选择请帖!");
		}
		
		//判断该音乐是否存在
		ModulMusicEntity modulMusicEntity = modulMusicService.queryObject(musicId);
		if(StringUtil.isEmpty(modulMusicEntity)){
			return R.error("没有该音乐,请重新选择!");
		}
		
		ModulEntity updateModulEntity = new ModulEntity();
		updateModulEntity.setId(modulId);
		updateModulEntity.setMusicId(modulMusicEntity.getId());
		modulService.updateSql(updateModulEntity);
		
		return R.ok("操作成功!");
	}
	
}
