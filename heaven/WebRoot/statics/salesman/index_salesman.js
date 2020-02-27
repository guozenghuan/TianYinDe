var zdWin;
var vm = new Vue({
	el:'#rrapp',
	data:{
		historyPrice:0,
		serviceData:[],
		keyword:"",
		priceStatus:1,
		status:"fuwuzhong",
		tyOrderService:{},
		tyOrderServiceSet:{},
		fuwu:{},
		fuwuIds:"",
		service:{},
		serviceIds: new Array(),
		page:1,
		pageText:"",
		tsUserid: '',
		id: '',
		userId: '',
		zdzh: [],	// 转单账号
		number: '',
	},
	created: function(){
 		this.getServiceOrderData(1);
 		
 		//判断是否授权过
 		$.ajax({
 			type: "GET",
 		    url: "/heaven/salesman/user/user_wechat",
 		    dataType: "json",
 		    success: function(result){
 		    	if(result.code == 0){
 		    		layer.open({
 			    	    content: '您没有授权任何微信号到该业务员账号下,是否授权该微信号(授权后则能使用微信直接登入,如需要取消授权则进入电脑端业务员后台清空微信授权账号信息)？'
 			    	    ,btn: ['确定授权', '取消']
 			    	    ,yes: function(index){
 		    	    	window.location.href ='/wechat/wechat_salesman_sq?id='+result.id;
 			    	    }
 			    	});
 		    	}
 		},
 			error: function(result){
 				layer.open({
 	    		    content: '授权失败,请重新使用账号密码登入,或者进入电脑版的业务员后台清空微信授权!'
 	    		    ,skin: 'msg'
 	    		    ,time: 2 //2秒后自动关闭
 	    		  });
 			}
 		});
 	},
	methods: {
		// 转单
		openZd(number, tsUserId) {
			console.log('number: ', number);
			this.number = number;
			const self = this;
			$.get(`/heaven/salesman/user/query/scheduling?&tsUserId=${tsUserId}`, function(res) {
				console.log('res: ', res)
				if (res.code === 0) {
					self.zdzh = res.account;
					var options = '';
					for(const row of res.account) {
						options += `<option value=${row}>${row}</option>`
					}
					var clientHeight = document.body.clientHeight;
			        clientHeight = parseInt(clientHeight)-140;
					var html = '<div class="housing_alert" style="overflow-x:hidden;overflow-y: scroll;height:'+clientHeight+'px;">'+
					'	<div class="housing_alert_details_div" style="margin-top:200px;">'+
					'		<div style="width: 30%;float: left;text-align: right;color: #8d8d8d;">转单账号: </div>'+
					'        <div style="width: 70%;float: left;">'+
					'			<select placeholder="请选择转单账号" style="width: 100%;" id="zd">'+
					'				"'+ options +'"'+
					'			</select>'+
					'		</div>'+
					'    </div>'+
					'</div>'+
					'<div class="center_btn_alert">'+
					'    <div class="center_btn_alert_one" onclick="confirmZd('+tsUserId+');">'+
					'       	<div style="font-size: 38px;font-size:bold;"><img src="statics/images/save.png"> 确认转单</div>'+
					'	</div>'+
					'	<div class="center_btn_alert_two" onclick="layer.closeAll();">'+
					'      	<div style="font-size: 38px;font-size:bold;"><img src="statics/images/cancel.png"> 取消</div>'+
					'	</div>'+
					'</div>';
					zdWin = layer.open({
						  type: 1
						  ,content: html
						  ,anim: 'up'
						  ,style: 'position:fixed; left:0; top:0; width:100%; height:100%; border: none; -webkit-animation-duration: .5s; animation-duration: .5s;'
					});
					addHtmlByServiceOrder('sbdd');
				}
				else {
					alert(res.msg);
				}
			})
		},
		// 上报订单
		sbdd: function(id, userid) {
			const self = this;
			fwType = 'sbdd';
			vm.tyOrderService.id = id;
			vm.$data.userId = userid;
			/*获取信息*/
			$.get("/heaven/salesman/user/tyorderservice_info/"+id, function(r){
				if(r.code == 0){
					// vm.tyOrderService = r.tyOrderService;
					var tyOrderService = r.tyOrderService;
					self.tsUserid = tyOrderService.tsUserid;
					var clientHeight = document.body.clientHeight;
			        clientHeight = parseInt(clientHeight)-140;
					vm.fuwu = r.fuwu;
			        var fuwuIds = "";
			        for(var i=0;i<vm.fuwu.length;i++){
			        	fuwuIds = fuwuIds + vm.fuwu[i].sort + ",";
			        	//计算总价格
			        	vm.historyPrice = accAdd(vm.historyPrice,vm.fuwu[i].name);
			        	
			        }
			        vm.fuwuIds = fuwuIds;
			        var price = tyOrderService.price || 0;
					var clientHeight = document.body.clientHeight;
			        clientHeight = parseInt(clientHeight)-140;
			        
			        var scoreHtml = "未评分";
			        if(tyOrderService.score!=null && tyOrderService.score!=""){
			        	scoreHtml = "";
			        	for(var isc=0;isc<tyOrderService.score;isc++){
			        		scoreHtml += '<i class="fa fa-star" style="color: #f4940d;font-size: 50px;"></i>';
			        	}
			        	
			        }
			        
			        var evaluate = "未评论";
			        if(tyOrderService.evaluate!=null && tyOrderService.tyOrderService!=""){
			        	evaluate = tyOrderService.evaluate;
			        }
			        
			        var okServiceBtn = "不可操作";
					if(tyOrderService.status == 0 && (tyOrderService.priceStatus == 1 || tyOrderService.priceStatus == 2)){//订单服务中并且用户已经付款
						okServiceBtn = '<a class="btn btn-primary" style="width: 40%;font-size: 36px;background-color: #62af3f;padding: 6px 6px;margin-left: 6px;margin-top: 3px;" onclick="okService('+tyOrderService.id+');">服务已完成</a>';
					}else if(tyOrderService.status == 0 && tyOrderService.priceStatus == 0){//订单服务中并且用户没有付款
						okServiceBtn = '<a class="btn btn-primary" style="width: 30%;font-size: 33px;background-color: #e73333;padding: 6px 6px;margin-left: 6px;margin-top: 3px;" onclick="falseService('+tyOrderService.id+');">交易失败</a>'+
						'<a class="btn btn-primary" style="width: 30%;font-size: 33px;background-color: #f0ad4e;padding: 6px 6px;margin-left: 5%;margin-top: 3px;" onclick="xianxiaMoneyService('+tyOrderService.id+');">线下打款</a>';
					}
					
					var price = tyOrderService.price;
					if(tyOrderService.price == null  || tyOrderService.price == undefined){
						price = 0;
						tyOrderService.price = 0;
					}
					
					var note = tyOrderService.note;
					if(note == undefined || note == null || note.trim() == ""){
						note = "";
					}
					
					var name = tyOrderService.name || '';
					var age = tyOrderService.age || 0;
					var sex = tyOrderService.sex || '男';
					var tel = tyOrderService.tel || '';
					var ting = tyOrderService.ting || '';
					var yuan = tyOrderService.yuan || '';
					var loanTime = tyOrderService.loadTime || '';
					var chit = tyOrderService.chit || '';
					var orderNumb = tyOrderService.orderNumb || '';
					var rebate = tyOrderService.rebate || 0;
					var tsNumber = tyOrderService.tsNumber || 0;
					var sort = tyOrderService.sort || '';
					var jtdc = tyOrderService.jtdc || '';
					
					// 设置定金
					var deposit = tyOrderService.deposit || 0;
			        
					var html = '<!--底部房源信息-->'+
					'<div class="housing_alert" style="overflow-x:hidden;overflow-y: scroll;height:'+clientHeight+'px;">'+
					'    <div class="housing_alert_details_div">'+
					'        <div style="width: 30%;float: left;text-align: right;color: #8d8d8d;">单号 &nbsp;&nbsp;&nbsp;</div>'+
					'        <div style="width: 70%;float: left;" id="sbddNumber"> '+tyOrderService.number+'</div>'+
					'    </div>'+
					'    <div class="housing_alert_details_div">'+
					'        <div style="width: 30%;float: left;text-align: right;color: #8d8d8d;">姓名 &nbsp;&nbsp;&nbsp;</div>'+
					'        <div style="width: 70%;float: left;">'+
					'            <input type="text" placeholder="请输入姓名" value='+name+' type="number" id="sbddName" style="width: 80%;height: 50px;font-size: 28px;font-weight: bold;">'+
					'        </div>'+
					'    </div>'+
					'    <div class="housing_alert_details_div">'+
					'        <div style="width: 30%;float: left;text-align: right;color: #8d8d8d;">性别 &nbsp;&nbsp;&nbsp;</div>'+
					'        <div style="width: 70%;float: left;">'+
					'            <label><input name="Sex" style="width: 30px;height: 30px;display: inline-block;vertical-align: middle;" type="radio" value="男" checked="checked" />男 </label>'+
					'			 <label><input name="Sex" style="width: 30px;height: 30px;display: inline-block;vertical-align: middle;" type="radio" value="女" />女 </label>'+
					'        </div>'+
					'    </div>'+
					'    <div class="housing_alert_details_div">'+
					'        <div style="width: 30%;float: left;text-align: right;color: #8d8d8d;">年龄 &nbsp;&nbsp;&nbsp;</div>'+
					'        <div style="width: 70%;float: left;">'+
					'            <input type="text" placeholder="请输入年龄" type="number" value='+age+' id="sbddAge" style="width: 80%;height: 50px;font-size: 28px;font-weight: bold;">'+
					'        </div>'+
					'    </div>'+
					'    <div class="housing_alert_details_div">'+
					'        <div style="width: 30%;float: left;text-align: right;color: #8d8d8d;">电话 &nbsp;&nbsp;&nbsp;</div>'+
					'        <div style="width: 70%;float: left;">'+
					'            <input type="text" placeholder="请输入电话" value="'+tel+'" type="number" id="sbddTel" style="width: 80%;height: 50px;font-size: 28px;font-weight: bold;">'+
					'        </div>'+
					'    </div>'+
					'    <div class="housing_alert_details_div">'+
					'        <div style="width: 30%;float: left;text-align: right;color: #8d8d8d;">厅 &nbsp;&nbsp;&nbsp;</div>'+
					'        <div style="width: 70%;float: left;">'+
					'            <input type="text" placeholder="请输入厅" type="number" id="sbddTing" value="'+ting+'" style="width: 80%;height: 50px;font-size: 28px;font-weight: bold;">'+
					'        </div>'+
					'    </div>'+
					'    <div class="housing_alert_details_div">'+
					'        <div style="width: 30%;float: left;text-align: right;color: #8d8d8d;">办厂 &nbsp;&nbsp;&nbsp;</div>'+
					'        <div style="width: 70%;float: left;">'+
					'            <input type="text" placeholder="请输入办厂" type="number" id="sbddYuan" value="'+yuan+'" style="width: 80%;height: 50px;font-size: 28px;font-weight: bold;">'+
					'        </div>'+
					'    </div>'+
					'    <div class="housing_alert_details_div">'+
					'        <div style="width: 30%;float: left;text-align: right;color: #8d8d8d;">结款时间 &nbsp;&nbsp;&nbsp;</div>'+
					'        <div style="width: 70%;float: left;">'+
					'            <input type="text" placeholder="请输入结款时间" type="number" id="sbddLoanTime" value="'+loanTime+'" style="width: 80%;height: 50px;font-size: 28px;font-weight: bold;">'+
					'        </div>'+
					'    </div>'+
					'    <div class="housing_alert_details_div">'+
					'        <div style="width: 30%;float: left;text-align: right;color: #8d8d8d;">类别 &nbsp;&nbsp;&nbsp;</div>'+
					'        <div style="width: 70%;float: left;">'+
					'            <input type="text" placeholder="请输入类别" type="number" id="sbddSort" value="'+sort+'" style="width: 80%;height: 50px;font-size: 28px;font-weight: bold;">'+
					'        </div>'+
					'    </div>'+
					'    <div class="housing_alert_details_div">'+
					'        <div style="width: 30%;float: left;text-align: right;color: #8d8d8d;">接体地址 &nbsp;&nbsp;&nbsp;</div>'+
					'        <div style="width: 70%;float: left;">'+
					'            <input type="text" placeholder="请输入接体地址" type="number" id="sbddJtdc" value="'+jtdc+'" style="width: 80%;height: 50px;font-size: 28px;font-weight: bold;">'+
					'        </div>'+
					'    </div>'+
					'    <div class="housing_alert_details_div">'+
					'        <div style="width: 30%;float: left;text-align: right;color: #8d8d8d;">包单 &nbsp;&nbsp;&nbsp;</div>'+
					'        <div style="width: 70%;float: left;">'+
					'            <input type="text" placeholder="请输入包单" type="number" id="sbddChit"  value="'+chit+'" style="width: 80%;height: 50px;font-size: 28px;font-weight: bold;">'+
					'        </div>'+
					'    </div>'+
					'    <div class="housing_alert_details_div">'+
					'        <div style="width: 30%;float: left;text-align: right;color: #8d8d8d;">业务编号 &nbsp;&nbsp;&nbsp;</div>'+
					'        <div style="width: 70%;float: left;">'+
					'            <input type="text" placeholder="请输入业务编号" type="number" id="sbddOrderNumb" value="'+orderNumb+'" style="width: 80%;height: 50px;font-size: 28px;font-weight: bold;">'+
					'        </div>'+
					'    </div>'+
					'    <div class="housing_alert_details_div">'+
					'        <div style="width: 30%;float: left;text-align: right;color: #8d8d8d;">佣金 &nbsp;&nbsp;&nbsp;</div>'+
					'        <div style="width: 70%;float: left;">'+
					'            <input type="text" placeholder="佣金" type="number" id="sbddRebate" value="'+rebate+'" style="width: 80%;height: 50px;font-size: 28px;font-weight: bold;">'+
					'        </div>'+
					'    </div>'+
					'    <div class="housing_alert_details_div">'+
					'        <div style="width: 30%;float: left;text-align: right;color: #8d8d8d;">业务员人数 &nbsp;&nbsp;&nbsp;</div>'+
					'        <div style="width: 70%;float: left;">'+
					'            <input type="text" placeholder="业务员人数" type="number" id="sbddTsNumber" value="'+tsNumber+'" style="width: 80%;height: 50px;font-size: 28px;font-weight: bold;">'+
					'        </div>'+
					'    </div>'+
					'    <div class="housing_alert_details_div" style="margin-top: 61px;">'+
					'        <div style="width: 30%;float: left;text-align: right;color: #8d8d8d;">订单服务总价 &nbsp;&nbsp;&nbsp;</div>'+
					'        <div style="width: 70%;float: left;">'+
					'            <input type="text" placeholder="订单服务总价" id="sbddTotal" value="'+price+'" style="width: 80%;height: 50px;font-size: 28px;font-weight: bold;">'+
					'        </div>'+
					'    </div>'+
					'    <div class="housing_alert_details_div" style="margin-top: 38px;" onclick=addserviceMsg("sbdd");>'+
					'		<a class="btn btn-success" style="width: 80%;background-color: #449d44;border-color: #398439;color: #fff;font-size: 38px;margin-left: 10%;">添加服务信息 <i class="fa fa-plus-square"></i></a>'+
					'    </div>'+
					'    <div id="order_service_msg" class="housing_alert_details_div" style="overflow-y: hidden;overflow-x:hidden;margin-top: 38px;">'+
					'<!-- 服务信息 -->'+
					'    </div>'+

					'<!-- 服务信息 -->'+
					
					'    </div>'+
					'    <div class="housing_alert_details_div" style="margin-top: 38px;height: 300px;"></div>'+
					'</div>'+
					'<!--底部菜单-->'+
					'<div class="center_btn_alert">'+
					'    <div class="center_btn_alert_one" onclick="reportTsOrder();">'+
					'        <div style="font-size: 38px;font-size:bold;"><img src="statics/images/save.png"> 上报订单</div>'+
					'    </div>'+
					'    <div class="center_btn_alert_two" onclick="layer.closeAll();">'+
					'        <div style="font-size: 38px;font-size:bold;"><img src="statics/images/cancel.png"> 取消</div>'+
					'    </div>'+
					'</div>';
					var pageii = layer.open({
						  type: 1
						  ,content: html
						  ,anim: 'up'
						  ,style: 'position:fixed; left:0; top:0; width:100%; height:100%; border: none; -webkit-animation-duration: .5s; animation-duration: .5s;'
					});
					addHtmlByServiceOrder('sbdd');
					
					var rObj = document.getElementsByName("sex");
		            for(var i = 0;i < rObj.length;i++){
		                if(rObj[i].value == tyOrderService.sex){
		                    rObj[i].checked =  'checked';
		                }
		            }
				}else{
					layer.open({
		    		    content: '获取订单信息失败!'
		    		    ,skin: 'msg'
		    		    ,time: 2 //2秒后自动关闭
		    		  });
				}
				
		    });
		},
		openWindos: function(arg){
			openWindos(arg);
		},
		getServiceOrderData: function(page){
			$.ajax({
				type: "GET",
			    url: "/heaven/salesman/user/tyorderservice_list?page="+page+"&limit=10",
			    dataType: "json",
			    data:{"keyword":this.keyword,"status":this.status},
			    success: function(result){
					if(result.code == 0){
						vm.serviceData = result.page.list;
						vm.pageText = "数据共: "+result.page.totalCount+" 条,点击继续加载";
					}else{
						layer.open({
			    		    content: '获取订单信息失败,请刷新页面后重新尝试!'
			    		    ,skin: 'msg'
			    		    ,time: 2 //2秒后自动关闭
			    		  });
					}
				},
				error: function(result){
					alert(JSON.stringify(result))
					layer.open({
		    		    content: '获取订单信息失败,请重新登入!'
		    		    ,skin: 'msg'
		    		    ,time: 2 //2秒后自动关闭
		    		  });
					// parent.location.href ='/login_salesman.html';
				}
			});
		},
		pageDataOrderMsg: function(){
			vm.page += 1;
			$.ajax({
				type: "GET",
			    url: "/heaven/salesman/user/tyorderservice_list?page="+vm.page+"&limit=10",
			    dataType: "json",
			    data:{"keyword":this.keyword,"status":this.status},
			    success: function(result){
					if(result.code == 0){
						if(result.page.list!=null && result.page.list.length > 0){
							var pageData = new Array();
							for(var ii=0;ii<vm.serviceData.length;ii++){
								pageData.push(vm.serviceData[ii]);
							}
							for(var i=0;i<result.page.list.length;i++){
								pageData.push(result.page.list[i]);
							}
							
							vm.serviceData = pageData;
							
							vm.pageText = "数据共: "+result.page.totalCount+" 条,点击继续加载";

							$("html,body").animate({scrollTop:$("#housingID_"+result.page.list[0].id).offset().top},1000)
						}else{
							vm.pageText = "数据共: "+result.page.totalCount+" 条,数据已全部加载";
						}
					}else{
						layer.open({
			    		    content: '获取订单信息失败,请刷新页面后重新尝试!'
			    		    ,skin: 'msg'
			    		    ,time: 2 //2秒后自动关闭
			    		  });
					}
				},
				error: function(result){
					layer.open({
		    		    content: '获取订单信息失败,请重新登入!'
		    		    ,skin: 'msg'
		    		    ,time: 2 //2秒后自动关闭
		    		  });
					// parent.location.href ='/login_salesman.html';
				}
			});
		},
		logout: function(){
			$.ajax({
				type: "POST",
			    url: "/heaven/salesman/logout",
			    dataType: "json",
			    success: function(result){
					if(result.code == 0){
						parent.location.href ='/login_salesman.html';
					}else{
						layer.open({
			    		    content: result.msg
			    		    ,skin: 'msg'
			    		    ,time: 2 //2秒后自动关闭
			    		  });
					}
				}
			});
		},
		updatePassword: function(){
			layer.open({
				type: 1,
				skin: 'layui-layer-molv',
				title: "修改密码",
				area: ['60%', '270px'],
				shadeClose: false,
				content: jQuery("#passwordLayer"),
				btn: ['修改','取消'],
				btn1: function (index) {
					var data = "password="+vm.password+"&newPassword="+vm.newPassword;
					$.ajax({
						type: "POST",
					    url: "/heaven/salesman/user/update_password",
					    data: data,
					    dataType: "json",
					    success: function(result){
							if(result.code == 0){
								layer.close(index);
								layer.alert('修改成功', function(index){
									location.reload();
								});
							}else{
								layer.open({
					    		    content: result.msg
					    		    ,skin: 'msg'
					    		    ,time: 2 //2秒后自动关闭
					    		  });
							}
						}
					});
	            }
			});
		}
	}
});

// 确认转单
function confirmZd(tsUserId) {
	const account = $('#zd').val();
	var number = vm.number;
	console.log('number', number, 'tsUserId: ', tsUserId, 'account: ', account);
	$.get(`/heaven/salesman/user/change/scheduling?number=${number}&account=${account}`, function(res) {
		if(res.code === 0) {
			layer.close(zdWin);
		}
		alert(res.msg)
	})
}

// 提交上报订单
function reportTsOrder() {
	// 单号
	const number = $('#sbddNumber').text();
	const data = {
		number,
		userId: vm.$data.userId,
		tsUserId: vm.$data.tsUserid,
		name: $('#sbddName').val(),
		sex: $('input[name="Sex"][type="radio"]:radio:checked').val(),
		age: Number($('#sbddAge').val()),
		phone: $('#sbddTel').val(),
		ting: $('#sbddTing').val(),
		yuan: $('#sbddYuan').val(),
		loanTime: $('#sbddLoanTime').val(),
		sort: $('#sbddSort').val(),
		jtdc: $('#sbddJtdc').val(),
		chit: $('#sbddChit').val(),
		orderNumb: $('#sbddOrderNumb').val(),
		createTime: $('#sbddCreateTime').val(),
		total: Number($('#sbddTotal').val()),
		rebate: Number($('#sbddRebate').val()),
		tsNumber: Number($('#sbddTsNumber').val()),
	};
	// 去除空格
	for(const key in data) {
		if (typeof data[key] === 'string') {
			data[key] = data[key].trim();
		}
	}
	// 服务数量
	const counts = $('.fuwuSums');
	// 服务
	const items = $('.sbddItem');
	// 服务/商品id
	const itemIds = $.map($('.sbddItem'), item => {
		console.log('sbddItem: ', item.id);
		return item.id;
	});
	// 单间售价
	const names = $('.name');
	// 现结
	const cashs = $('.sbddXj');
	// 备注
	const remarks = $('.sbddBzxx');
	// 服务数组
	const arr = $.map($('.fuwuSums'), function(item, index) { return {
		tsUserId: vm.$data.tsUserid,
		number: number.trim(),
		itemId: Number(itemIds[index]),
		item: items[index].innerText.replace(/[\/]/g,''),
		count: Number(counts[index].innerText),
		unit: 0,
		price: Number(names[index].innerText.replace(/[^\d.]/g, "")),
		cost: 0,
		cash: Number(cashs[index].value),
		remark: remarks[index].value,
	}})
	console.log('arr: ',arr);
	$.ajax({
		url: '/heaven/salesman/user/reportTsOrder/save', 
		type: 'POST',
		dataType: 'json',
		contentType: 'application/json;charset=utf-8',
		data: JSON.stringify(data),
		success: function(res) {
			if (res.code === 0) {
				$.ajax({
					url: '/heaven/salesman/user/reportTsItem/save',
					type: 'POST',
					dataType: 'json',
					contentType: 'application/json;charset=utf-8',
					data: JSON.stringify(arr),
					success: function(res) {
						if (res.code === 0) {
							alert(res.message);
							vm.getServiceOrderData(1);
							layer.closeAll();
						}
					}
				})
			}
			else {
				alert(res.msg);
			}
		}
	})
}

function telPhoneStr(phone){
	return "tel:"+phone;
}

// 点击添加服务信息时记录类型
var fwType = '';

var pageii2;
var clickFlag = true;//防止多次点击触发事件
function addserviceMsg(type){
	fwType = type;
	if(!clickFlag){
		return;
	}
	clickFlag = false;
	console.log('vm.serviceIds: ', vm.serviceIds)
	vm.serviceIds = new Array();
	$.get("/heaven/salesman/user/tyorderservice_service_info", function(r){
        vm.service = r.service;
        
        var serviceMsgHtml = "";
        for(var i=0;i<vm.service.length;i++){
        	var dataOne = vm.service[i];
        	
        	var htmlTwo = "";
        	var quanxuanIds = "";
        	if(dataOne.childrenFw != null && dataOne.childrenFw[0].id != -1){
        		for(var ii=0;ii<dataOne.childrenFw.length;ii++){
        			var dataTwo = dataOne.childrenFw[ii];
        			var typeTexzt = "无详情信息";
        			if(dataTwo.type != null && dataTwo.type != ""){
        				typeTexzt = dataTwo.type;
        			}
        			
        			quanxuanIds = quanxuanIds+'serviceSize_'+dataTwo.id+'-';
        			
        			//加深已添加的服务选项
        			var selectServiceColor = "#c24448";//原本的颜色
        			if(vm.fuwuIds.indexOf(dataTwo.id+",") != -1){
        				selectServiceColor = "#5168e2";
        			}
        			
        			var htmlTwos = 
        				'			    <li style="background: '+selectServiceColor+';" id="li-service_'+dataTwo.id+'" class="touch_alert">'+
                        '		    		<mark data-text="'+typeTexzt+'" onclick="serviceMsgByInfoId(this);" style="z-index: 8;width: 500px;line-height: 36px;overflow: hidden;height: 50px;"><font style="color: #dfce1c;font-weight: bolder;">￥'+dataTwo.name+'</font>'+'/'+dataTwo.text+'</mark>'+
                        '			        <mark style="padding: 6px 10px 18px 0px;">'+
                        '						<i class="fa fa-plus-circle" style="margin-left: 10px;margin-right: 10px;float: right;font-size: 88px;" onclick="selectFwFlag(1,\'serviceSize_'+dataTwo.id+'\');"></i>'+
                        '						<font style="float: right;font-size: 60px;width: 100px;text-align: center;color: #ffffff;font-weight: bolder;" id="serviceSize_'+dataTwo.id+'">0</font>'+
                        '						<i class="fa fa-minus-circle" style="margin-right: 10px;float: right;font-size: 88px;" onclick="selectFwFlag(0,\'serviceSize_'+dataTwo.id+'\');"></i>'+
                        '			  	    </mark>'+
                        '			    </li>';
        			htmlTwo = htmlTwo+htmlTwos;
        		}
        	}
        	
        	var htmlOne = 
                '<div class="jq22-container">'+
                '			<div class="leaderboard">'+
                '			  <h1 style="display: inline-block;font-weight: bolder;width: 76%;line-height: 42px;overflow: hidden;height: 70px;">'+
                				dataOne.text+
                '			  </h1>'+
                '			  <a class="btn btn-danger" style="margin-right: 10px;margin-top: 32px;float: right;font-size: 38px;width: 15%;background-color: rgb(71, 182, 50);border-color: rgb(55, 61, 74);" name="quanxuan" id="serviceAll_'+dataOne.id+'" onclick="selectFwAll(this.id,\''+quanxuanIds+'\');"> 全选 </a>'+
                '			  <ol>'+
                '=htmlTwos='+
                '			  </ol>'+
                '			  '+
                '			</div>'+
                '		</div>';
        	
        	serviceMsgHtml = serviceMsgHtml+htmlOne.replace("=htmlTwos=",htmlTwo);
        	
        }
        
        var clientHeight = document.body.clientHeight;
        clientHeight = parseInt(clientHeight)-140;
        
        pageii2 = layer.open({
  		  type: 1
  		  ,content: '<div class="housing" style="overflow-x:hidden;overflow-y: scroll;height:'+clientHeight+'px;">'+serviceMsgHtml+'</div>'+'<!--底部菜单-->'+
  			'<div class="center_btn_alert" style="z-index: 2;">'+
  			'    <div class="center_btn_alert_one" onclick=addService();>'+
  			'        <div><img src="statics/images/save.png"> 添加服务信息</div>'+
  			'    </div>'+
  			'    <div class="center_btn_alert_two" onclick="cancelAddService();">'+
  			'        <div><img src="statics/images/cancel.png"> 取消</div>'+
  			'    </div>'+
  			'</div>'
  		  ,anim: 'up'
  		  ,style: 'position:fixed; left:0; top:0; width:100%; height:100%; border: none; -webkit-animation-duration: .5s; animation-duration: .5s;'
        });
        
        layer.open({
		    content: '蓝色背景的服务为已选项,如要查看服务详情请点击服务标题!'
		    ,skin: 'msg'
		    ,time: 2 //2秒后自动关闭
		  });
	});
}
function cancelAddService(){
	layer.close(pageii2);
	clickFlag = true;
}
function serviceMsgByInfoId(e){
	var text = $(e).data("text");
	//信息框
	  layer.open({
	    content: '<div style="font-size:38px;line-height: 48px;">'+text+'</div>'
	    ,btn: '我知道了'
	  });
}
function addService(){
	if(vm.serviceIds == null || vm.serviceIds == undefined || vm.serviceIds.length <= 0){
		layer.open({
		    content: '没有选择任何服务信息'
		    ,skin: 'msg'
		    ,time: 2 //2秒后自动关闭
		  });
		return;
	}
	
	//保存信息
	var fuwuIds = vm.fuwuIds;
	var prices = "0";
	//显示已经添加的服务信息
	var serviceData = vm.serviceIds;
	var dataArray = new Array();
	for(var i=0;i<vm.fuwu.length;i++){
		dataArray.push(vm.fuwu[i]);
	}
	for(var i=0;i<serviceData.length;i++){
		var service = vm.service;
		var id = serviceData[i];
		for(var ii=0;ii<service.length;ii++){
			var childrenFw = service[ii].childrenFw;
			for(var iii=0;iii<childrenFw.length;iii++){
				if(id == childrenFw[iii].id){
					var jsonData = {id: childrenFw[iii].id,name:childrenFw[iii].name,parentId:childrenFw[iii].parentId,sort:childrenFw[iii].id,text:childrenFw[iii].text};
					dataArray.push(jsonData);
					
					prices = accAdd(prices,childrenFw[iii].name);
					fuwuIds = fuwuIds + childrenFw[iii].id + ",";
				}
			}
		}
	}
	// 如果是上报订单，添加属性
	if (fwType === 'sbdd') {
		dataArray = dataArray.map(item => {
			const obj = {
				isSbdd: true,
				cost: 0,
				cash: 0,
				remark: '',
			}
			return Object.assign(item, obj);
		})
	}
	vm.fuwu = dataArray;
	/*vm.fuwuIds = vm.fuwuIds+fuwuIds;*/
	vm.fuwuIds = fuwuIds;
	
	var fuwuPr = "0";
	if(vm.tyOrderService.price == undefined || vm.tyOrderService.price == null || vm.tyOrderService.price == ""){
		fuwuPr = prices;
	}else{
		fuwuPr = accAdd(vm.tyOrderService.price,prices);
	}
	$("#orderPriceId").val(fuwuPr);
	
	//原价上添加价格
	vm.historyPrice = accAdd(vm.historyPrice,prices);
	$("#hisotoryPriceId").html(vm.historyPrice);
	
	vm.tyOrderServiceSet = vm.tyOrderService;
	vm.tyOrderServiceSet.price = fuwuPr;
	vm.tyOrderService = {};
	vm.tyOrderService = vm.tyOrderServiceSet;
	
	  layer.open({
	    content: '订单服务总价上已添加新增的服务价格,保存服务信息后生效!'
	    ,skin: 'msg'
	    ,time: 2 //2秒后自动关闭
	  });
	
	  /*layer.open({
	    content: '是否要在订单服务总价上添加新增的服务价格？'
	    ,btn: ['确定', '取消']
	    ,yes: function(index){
	    	layer.close(index);
	    }
	  });*/
	  
	addHtmlByServiceOrder();
	layer.close(pageii2);
	clickFlag = true;
	
	calcuFWTotal();
	
}

function addHtmlByServiceOrder(){
	var htmlText = "";
	var addDelAllIds = "";
	//给数组分组，相同的存入
	var fuwuMap = new Map();
	var arrFlag = new Array(); 
	for(var i=0;i<vm.fuwu.length;i++){
		var fuwuId = vm.fuwu[i].sort;
		if(arrFlag.indexOf(fuwuId) == -1){//不存在
			arrFlag.push(fuwuId);
			fuwuMap.set(fuwuId,"1-"+i);
		}else{//存在
			var mapV = fuwuMap.get(fuwuId);
			var mapVs = mapV.split("-");
			var addSize = parseInt(mapVs[0])+1;
			fuwuMap.set(fuwuId,addSize+"-"+i);
		}
	}
	for(var i=0;i<arrFlag.length;i++){
		var fuwuMapKey = arrFlag[i];
		var mapValue = fuwuMap.get(fuwuMapKey);
		var values = mapValue.split("-");
		
		var fuwuIndex = parseInt(values[1]);
		var fuwuSums = values[0];
		
		var data = vm.fuwu[fuwuIndex];
		var delSize = parseInt(fuwuSums)*parseFloat(data.name);
		var text;
		if (fwType === 'sbdd') {
			text = 
'						    <li style="height:210px;color:#333;">'+
'					    		<mark style="width: 500px;line-height: 36px;overflow: hidden;height: 50px;">数量:<font style="font-weight: bolder;" class="fuwuSums" id="fuwuSums'+data.id+'">'+fuwuSums+'</font>-<font style="color: #dfce1c;font-weight: bolder;cursor:pointer;" class="name" id="name'+data.sort+'">￥'+data.name+'</font><span class="sbddItem" id="' + data.sort + '">/'+data.text+'</span></mark>'+
'						        <mark style="padding: 6px 10px 18px 0px;">'+
'								    <a class="btn btn-danger" style="width: 98px;font-size: 60px;float: right;background-color: #fb5853;" id="delete_'+data.sort+'" name="'+delSize+'" onclick="deleteFw(this);"><i class="fa fa-trash"></i></a>'+
'									<div style="margin-top:100px;padding-left:20px;padding-right: 20px;" id="jslx' + data.sort +'">'+
'										<label><input type="radio" style="width:30px;height:30px;vertical-align: middle;" checked="checked" name="jslx'+data.id+'" value="gscb" onchange="changeJslx('+data.id+', this)" class="sbddGscb" />公司成本</label>'+
'										<label><input type="radio" style="width:30px;height:30px;vertical-align: middle;" name="jslx'+data.id+'" value="xj" onchange="changeJslx('+data.id+', this)" />现结</label>'+
'										<input type="text" style="color:#333;" disabled="disabled" id="actual' + data.id + '" type="number" class="sbddXj" />'+
'										<input type="textarea" style="display:block;color:#333;width:100%;margin-top: 10px;" placeholder="备注信息" class="sbddBzxx" />'		
'									</div>'+
'						  	    </mark>'+
'						    </li>';
		}
		else {
			text = 
'						    <li>'+
'					    		<mark style="width: 500px;line-height: 36px;overflow: hidden;height: 50px;">数量:<font style="font-weight: bolder;">'+fuwuSums+'</font>-<font style="color: #dfce1c;font-weight: bolder;">￥'+data.name+'</font>/'+data.text+'</mark>'+
'						        <mark style="padding: 6px 10px 18px 0px;">'+
'								    <a class="btn btn-danger" style="width: 98px;font-size: 60px;float: right;background-color: #fb5853;" id="delete_'+data.sort+'" name="'+delSize+'" onclick="deleteFw(this);"><i class="fa fa-trash"></i></a>'+
'						  	    </mark>'+
'						    </li>';
		}
		htmlText = htmlText + text;
		addDelAllIds = addDelAllIds+'delete_'+data.sort+'-';
	}
	
	var addDelAllBtn = "";
	if(addDelAllIds != null && addDelAllIds.trim() != ""){
		//添加全部删除按钮
		addDelAllBtn = '<a onclick="delAllService();" class="btn btn-success" style="width: 80%;background-color: #fb5853;border-color: #ff0a0a;color: #fff;font-size: 38px;margin-left: 10%;margin-top: 10px;margin-bottom: 10px;">删除所有服务 <i class="fa fa-trash"></i></a>';
	}
	
	var html = 
		'						<div class="leaderboard">'+
		addDelAllBtn+
		'						  <ol>'+
		htmlText+
		'						  </ol>'+
		'						  '+
		'						</div>';
	
	$("#order_service_msg").html(html);
}

// 点击结算类型
function changeJslx(id, e) {
	const inputEle = document.querySelector(`#actual${id}`);
	if (e.value !== 'xj') {
		inputEle.disabled = true;
		inputEle.value = '';
	}
	else {
		inputEle.disabled = false;
	}
}

// 转单
function openZd(number, tsUserid) {
	
}

function openWindos(id){
	/*获取信息*/
	$.get("/heaven/salesman/user/tyorderservice_info/"+id, function(r){
		if(r.code == 0){
			vm.tyOrderService = r.tyOrderService;
			var tyOrderService = r.tyOrderService;
			
			vm.fuwu = r.fuwu;
	        var fuwuIds = "";
	        for(var i=0;i<vm.fuwu.length;i++){
	        	fuwuIds = fuwuIds + vm.fuwu[i].sort + ",";
	        	//计算总价格
	        	vm.historyPrice = accAdd(vm.historyPrice,vm.fuwu[i].name);
	        	
	        }
	        vm.fuwuIds = fuwuIds;
			
			var clientHeight = document.body.clientHeight;
	        clientHeight = parseInt(clientHeight)-140;
	        
	        var scoreHtml = "未评分";
	        if(tyOrderService.score!=null && tyOrderService.score!=""){
	        	scoreHtml = "";
	        	for(var isc=0;isc<tyOrderService.score;isc++){
	        		scoreHtml += '<i class="fa fa-star" style="color: #f4940d;font-size: 50px;"></i>';
	        	}
	        	
	        }
	        
	        var evaluate = "未评论";
	        if(tyOrderService.evaluate!=null && tyOrderService.tyOrderService!=""){
	        	evaluate = tyOrderService.evaluate;
	        }
	        
	        var okServiceBtn = "不可操作";
			if(tyOrderService.status == 0 && (tyOrderService.priceStatus == 1 || tyOrderService.priceStatus == 2)){//订单服务中并且用户已经付款
				okServiceBtn = '<a class="btn btn-primary" style="width: 40%;font-size: 36px;background-color: #62af3f;padding: 6px 6px;margin-left: 6px;margin-top: 3px;" onclick="okService('+tyOrderService.id+');">服务已完成</a>';
			}else if(tyOrderService.status == 0 && tyOrderService.priceStatus == 0){//订单服务中并且用户没有付款
				okServiceBtn = '<a class="btn btn-primary" style="width: 30%;font-size: 33px;background-color: #e73333;padding: 6px 6px;margin-left: 6px;margin-top: 3px;" onclick="falseService('+tyOrderService.id+');">交易失败</a>'+
				'<a class="btn btn-primary" style="width: 30%;font-size: 33px;background-color: #f0ad4e;padding: 6px 6px;margin-left: 5%;margin-top: 3px;" onclick="xianxiaMoneyService('+tyOrderService.id+');">线下打款</a>'+
				'<a class="btn btn-primary" style="width: 40%;font-size: 36px;background-color: #62af3f;padding: 6px 6px;margin-left: 6px;margin-top: 3px;" onclick="okService('+tyOrderService.id+');">服务已完成</a>';
			}
			
			var price = tyOrderService.price;
			if(tyOrderService.price == null  || tyOrderService.price == undefined){
				price = 0;
				tyOrderService.price = 0;
			}
			
			var note = tyOrderService.note;
			if(note == undefined || note == null || note.trim() == ""){
				note = "";
			}
			
			// 设置定金
			var deposit = tyOrderService.deposit || 0;
	        
			var html = '<!--底部房源信息-->'+
			'<div class="housing_alert" style="overflow-x:hidden;overflow-y: scroll;height:'+clientHeight+'px;">'+
			'    <div class="housing_alert_details_div">'+
			'        <div style="width: 30%;float: left;text-align: right;color: #8d8d8d;">订单操作. &nbsp;&nbsp;&nbsp;</div>'+
			'        <div style="width: 70%;float: left;"> '+okServiceBtn+'</div>'+
			'    </div>'+
			'    <div class="housing_alert_details_div">'+
			'        <div style="width: 30%;float: left;text-align: right;color: #8d8d8d;">单号 &nbsp;&nbsp;&nbsp;</div>'+
			'        <div style="width: 70%;float: left;"> '+tyOrderService.number+'</div>'+
			'    </div>'+
			'    <div class="housing_alert_details_div">'+
			'        <div style="width: 30%;float: left;text-align: right;color: #8d8d8d;line-height: initial;">服务费 &nbsp;&nbsp;&nbsp;</div>'+
			'        <div style="width: 70%;float: left;line-height: initial;"> ￥'+tyOrderService.servicePrice+'</div>'+
			'    </div>'+
			'    <div class="housing_alert_details_div">'+
			'        <div style="width: 30%;float: left;text-align: right;color: #8d8d8d;line-height: initial;">联系人 &nbsp;&nbsp;&nbsp;</div>'+
			'        <div style="width: 70%;float: left;line-height: initial;"> '+tyOrderService.name+' - ID:'+tyOrderService.userid+'</div>'+
			'    </div>'+
			'    <div class="housing_alert_details_div">'+
			'        <div style="width: 30%;float: left;text-align: right;color: #8d8d8d;line-height: initial;">手机号 &nbsp;&nbsp;&nbsp;</div>'+
			'        <div style="width: 70%;float: left;line-height: initial;"> '+tyOrderService.phone+'</div>'+
			'    </div>'+
			'    <div class="housing_alert_details_div">'+
			'        <div style="width: 30%;float: left;text-align: right;color: #8d8d8d;line-height: initial;">评分 &nbsp;&nbsp;&nbsp;</div>'+
			'        <div style="width: 70%;float: left;line-height: initial;"> '+scoreHtml+'</div>'+
			'    </div>'+
			'    <div class="housing_alert_details_div">'+
			'        <div style="width: 30%;float: left;text-align: right;color: #8d8d8d;line-height: initial;">评价 &nbsp;&nbsp;&nbsp;</div>'+
			'        <div style="width: 70%;float: left;line-height: initial;"> '+evaluate+'</div>'+
			'    </div>'+
			'    <div class="housing_alert_details_div">'+
			'        <div style="width: 30%;float: left;text-align: right;color: #8d8d8d;line-height: initial;">原价 &nbsp;&nbsp;&nbsp;</div>'+
			'        <div style="width: 70%;float: left;line-height: initial;" id="hisotoryPriceId"> '+vm.historyPrice+'</div>'+
			'    </div>'+
			'    <div class="housing_alert_details_div">'+
			'        <div style="width: 30%;float: left;text-align: right;color: #8d8d8d;line-height: initial;">是否收取定金 &nbsp;&nbsp;&nbsp;</div>'+
			'        <div style="width: 70%;float: left;">'+
			'            <label><input type="radio" name="needDJ" value="1" />收取</label>'+
			'            <label><input type="radio" name="needDJ" value="0" />不收取</label>'+
			'        </div>'+
			'    </div>'+
			'    <div class="housing_alert_details_div">'+
			'        <div style="width: 30%;float: left;text-align: right;color: #8d8d8d;line-height: initial;">定金 &nbsp;&nbsp;&nbsp;</div>'+
			'        <div style="width: 70%;float: left;">'+
			'            <input type="text" placeholder="可输入定金" type="number" id="orderDeposit" value="'+deposit+'" style="width: 80%;height: 50px;font-size: 28px;font-weight: bold;">'+
			'        </div>'+
			'    </div>'+
			'    <div class="housing_alert_details_div" style="margin-top: 61px;">'+
			'        <div style="width: 30%;float: left;text-align: right;color: #8d8d8d;">订单服务总价 &nbsp;&nbsp;&nbsp;</div>'+
			'        <div style="width: 70%;float: left;">'+
			'            <input type="text" placeholder="订单服务总价" id="orderPriceId" value="'+price+'" style="width: 80%;height: 50px;font-size: 28px;font-weight: bold;">'+
			'        </div>'+
			'    </div>'+
			'    <div class="housing_alert_details_div" style="margin-top: 38px;">'+
			'        <div style="width: 30%;float: left;text-align: right;color: #8d8d8d;">价格备注 &nbsp;&nbsp;&nbsp;</div>'+
			'        <div style="width: 70%;float: left;">'+
			'            <textarea id="orderNodeId" placeholder="价格备注" style="width: 80%;height: 250px;font-size: 28px;font-weight: bold;">'+note+'</textarea>'+
			'        </div>'+
			'    </div>'+
			'    <div class="housing_alert_details_div" style="margin-top: 38px;" onclick="addserviceMsg();">'+
			'		<a class="btn btn-success" style="width: 80%;background-color: #449d44;border-color: #398439;color: #fff;font-size: 38px;margin-left: 10%;">添加服务信息 <i class="fa fa-plus-square"></i></a>'+
			'    </div>'+
			
			'    <div id="order_service_msg" class="housing_alert_details_div" style="overflow-y: hidden;overflow-x:hidden;margin-top: 38px;">'+
			'<!-- 服务信息 -->'+
			
			'    </div>'+
			
			'    <div class="housing_alert_details_div" style="margin-top: 38px;height: 300px;"></div>'+
			'</div>'+
			'<!--底部菜单-->'+
			'<div class="center_btn_alert">'+
			'    <div class="center_btn_alert_one" onclick="saveServiceOrderMsg();">'+
			'        <div><img src="statics/images/save.png"> 保存信息</div>'+
			'    </div>'+
			'    <div class="center_btn_alert_two" onclick="layer.closeAll();">'+
			'        <div><img src="statics/images/cancel.png"> 取消</div>'+
			'    </div>'+
			'</div>';
			var pageii = layer.open({
				  type: 1
				  ,content: html
				  ,anim: 'up'
				  ,style: 'position:fixed; left:0; top:0; width:100%; height:100%; border: none; -webkit-animation-duration: .5s; animation-duration: .5s;'
			});
			
			var rObj = document.getElementsByName("needDJ");
            for(var i = 0;i < rObj.length;i++){
                if(rObj[i].value == tyOrderService.isCollect){
                    rObj[i].checked =  'checked';
                }
            }
            
			addHtmlByServiceOrder();
		}else{
			layer.open({
    		    content: '获取订单信息失败!'
    		    ,skin: 'msg'
    		    ,time: 2 //2秒后自动关闭
    		  });
		}
		
    });
}
function xianxiaMoneyService(orderId){
	layer.open({
	    content: '确定该服务订单用户选择线下付款？'
	    ,btn: ['确定', '取消']
	    ,yes: function(index){
	    	$.ajax({
				type: "POST",
			    url: "/heaven/salesman/user/xianxia_service_order?orderId="+orderId,
			    success: function(r){
					if(r.code == 0){
						layer.closeAll();
			    		layer.open({
			    		    content: '操作成功!'
			    		    ,skin: 'msg'
			    		    ,time: 2 //2秒后自动关闭
			    		  });
			    		$("#housingID_"+orderId).hide();
					}else{
						layer.open({
			    		    content: r.msg
			    		    ,skin: 'msg'
			    		    ,time: 2 //2秒后自动关闭
			    		  });
					}
				}
			});
	    }
	});
}
function saveServiceOrderMsg(){
	console.log('fwType: ', fwType);
	layer.open({
	    content: '是否确定要保存已修改的信息？'
	    ,btn: ['确定', '取消']
	    ,yes: function(index){
	    	var fuwuIds = vm.fuwuIds;
			var orderId = vm.tyOrderService.id;
//			var price = vm.tyOrderService.price;
//			var note = vm.tyOrderService.note;
			var price = $("#orderPriceId").val();
			var deposit = $('#orderDeposit').val() || 0;
			var note = $("#orderNodeId").val();
			var isCollect = $("input[name='needDJ']:checked").val();
			if(note == undefined || note == ""){
				note = "";
			}
			if(price == undefined || price == ""){
				price = "";
			}
			
			var data = "orderId="+orderId+"&price="+price+"&note="+note+"&fuwuIds="+fuwuIds+"&deposit="+deposit+"&isCollect="+isCollect;
			$.ajax({ 
				type: "POST",
			    url: "/heaven/salesman/user/tyorderservice_updatefw?"+data,
			    success: function(r){
			    	if(r.code === 0){
			    		layer.closeAll();
			    		layer.open({
			    		    content: '操作成功!'
			    		    ,skin: 'msg'
			    		    ,time: 2 //2秒后自动关闭
			    		  });
			    		
			    		//修改价格
			    		for(var i=0;i<vm.serviceData.length;i++){
			    			if(vm.serviceData[i].id == orderId){
			    				vm.serviceData[i].price = price;
			    				vm.serviceData[i].serviceStatus = 1;
			    			}
			    		}
					}else{
						layer.open({
			    		    content: r.msg
			    		    ,skin: 'msg'
			    		    ,time: 2 //2秒后自动关闭
			    		});
					}
				},
				failed: function(err) {
					console.err('err: ', err);
				}
			});
	    }
	});
}

function okService(orderId){
	layer.open({
	    content: '确定该服务订单已完成？'
	    ,btn: ['确定', '取消']
	    ,yes: function(index){
	    	$.ajax({
				type: "POST",
			    url: "/heaven/salesman/user/ok_service_order?orderId="+orderId,
			    success: function(r){
					if(r.code == 0){
						layer.closeAll();
			    		layer.open({
			    		    content: '操作成功!'
			    		    ,skin: 'msg'
			    		    ,time: 2 //2秒后自动关闭
			    		  });
//			    		$("#housingID_"+orderId).hide();
			    		//删除该项
			    		var serviceArray = new Array();
			    		for(var i=0;i<vm.serviceData.length;i++){
			    			if(vm.serviceData[i].id != orderId){
			    				serviceArray.push(vm.serviceData[i]);
			    			}
			    		}
			    		vm.serviceData = serviceArray;
					}else{
						alert(r.msg);
					}
				}
			});
	    }
	});
	
}
function falseService(orderId){
	layer.open({
	    content: '确定该服务订单交易失败？'
	    ,btn: ['确定', '取消']
	    ,yes: function(index){
	    	$.ajax({
				type: "POST",
			    url: "/heaven/salesman/user/false_service_order?orderId="+orderId,
			    success: function(r){
					if(r.code == 0){
						layer.closeAll();
			    		layer.open({
			    		    content: '操作成功!'
			    		    ,skin: 'msg'
			    		    ,time: 2 //2秒后自动关闭
			    		  });
//			    		$("#housingID_"+orderId).hide();
			    		//删除该项
			    		var serviceArray = new Array();
			    		for(var i=0;i<vm.serviceData.length;i++){
			    			if(vm.serviceData[i].id != orderId){
			    				serviceArray.push(vm.serviceData[i]);
			    			}
			    		}
			    		vm.serviceData = serviceArray;
					}else{
						alert(r.msg);
					}
				}
			});
	    }
	});
}



// 修改价格
function changeName(id, name) {
	console.log('id: ', id);
	console.log('name: ', name);
	const pName = prompt('修改价格', name);
	vm.$data = vm.$data.fuwu.map(item => {
		if(item.id === id) {
			item.name = pName;
		}
	})
	const nDom = document.querySelector(`#name${id}`).innerHTML = `￥${pName}`;
	calcuFWTotal();
}

// 计算服务总价
function calcuFWTotal() {
	const fuwuSums = document.querySelectorAll('.fuwuSums');
	const name = document.querySelectorAll('.name');
	const total = document.querySelector('#sbddTotal');
	let _total = 0;
	for (let i = 0; i < fuwuSums.length; i++) {
		_total += fuwuSums[i].innerText*name[i].innerText.replace(/[^\d.]/g, "");
	}
	total.value = _total;
}

//删除单个
function deleteFw(o){
	//修改原价
	var hisotory = $(o).attr("name");
	vm.historyPrice = accSub(vm.historyPrice,hisotory);
	$("#hisotoryPriceId").html(vm.historyPrice);
	
	var id = $(o).attr("id");
	
	var idSelect = id;
	id = id.split("_")[1];
	
	var data = vm.fuwu;
	var dataSave = new Array();
	//删除
	var delFlag = true;
	var privace = "0";
	var fuwuIds = "";
	for(var i=0;i<data.length;i++){
		if(data[i].sort == parseInt(id)){
			privace = accAdd(data[i].name,privace);
			/*//只删除一次
			if(delFlag){
				delFlag = false;
				privace = data[i].name;
			}else{
				dataSave.push(data[i]);
				fuwuIds = fuwuIds + data[i].sort + ",";
			}*/
		}else{
			fuwuIds = fuwuIds + data[i].sort + ",";
			dataSave.push(data[i]);
		}
	}
	
	vm.fuwu = {};
	vm.fuwu = dataSave;
	vm.fuwuIds = fuwuIds;
	
	var fuwuPr = "0";
	if(vm.tyOrderService.price == undefined || vm.tyOrderService.price == null || vm.tyOrderService.price == ""){
		fuwuPr = undefined;
	}else{
		fuwuPr = accSub(vm.tyOrderService.price,privace);
	}
	
	$("#orderPriceId").val(fuwuPr);
	
	vm.tyOrderServiceSet = vm.tyOrderService;
	vm.tyOrderServiceSet.price = fuwuPr;
	vm.tyOrderService = {};
	vm.tyOrderService = vm.tyOrderServiceSet;
	
	  layer.open({
	    content: '订单服务总价上已减去删除服务的价格,保存服务信息后生效!'
	    ,skin: 'msg'
	    ,time: 2 //2秒后自动关闭
	  });
	
	addHtmlByServiceOrder();
}
//删除全部
function delAllService(){
	//设置价格为0
	$("#orderPriceId").val("0");
	vm.tyOrderService.price = 0;
	//清空服务ids
	vm.fuwuIds = "";
	//清空服务信息集合
	vm.fuwu = {};
	
	layer.open({
	    content: '订单服务总价上已减去删除服务的价格,保存服务信息后生效!'
	    ,skin: 'msg'
	    ,time: 2 //2秒后自动关闭
	  });
	addHtmlByServiceOrder();
}

//全选
function selectFwAll(id,qxids){
	if(qxids == null || qxids == undefined || qxids.trim() == ""){
		layer.open({
		    content: '全选或取消全选失败,可能该服务没有选项或者没有获取到服务信息,可取消后再次进入重新操作!'
		    ,skin: 'msg'
		    ,time: 2 //2秒后自动关闭
		  });
		return;
	}
	var quanxuanIds = qxids.split("-");
	
	var idSelect = id;
	id = id.split("_")[1];
	
	var selectValue = $("#"+idSelect).attr("name");
	if(selectValue.trim() == "quanxuan"){//执行全选
		for(var i=0;i<quanxuanIds.length;i++){
			var data = quanxuanIds[i];
			if(data != null && data != undefined && data.trim() != ""){
				//增加
				selectFwFlag(1,data);
			}
		}
		$("#"+idSelect).html("取消");
		$("#"+idSelect).attr("name","quxiao");
		$("#"+idSelect).css("background-color","rgb(251, 88, 83)");
	}else{//执行全部取消
		for(var i=0;i<quanxuanIds.length;i++){
			var data = quanxuanIds[i];
			if(data != null && data != undefined && data.trim() != ""){
				//减少
				selectFwFlag(0,data);
			}
		}
		$("#"+idSelect).html("全选");
		$("#"+idSelect).attr("name","quanxuan");
		$("#"+idSelect).css("background-color","rgb(71, 182, 50)");
	}
}

/**
* 单选
*/
function selectFw(id){
	var idSelect = id;
	id = id.split("_")[1];
	
	var selectValue = $("#"+idSelect).attr("name");
	if(selectValue.trim() == "xuanzhong"){//执行选择
		$("#"+idSelect).css("background-color","rgb(71, 182, 50)");
		$("#"+idSelect).attr("name","quxiao");
		addServiceIds(id);//添加
	}else{//执行取消
		$("#"+idSelect).css("background-color","rgb(251, 88, 83)");
		$("#"+idSelect).attr("name","xuanzhong");
		delServiceIds(id);//删除
	}
}
function selectFwFlag(flag,id){
	if(!id){
		layer.open({
		    content: '该选项获取信息时失败,请尝试取消后再重新进入,或者先选择其他信息保存成功后,再进入重新选择该选项!'
		    ,skin: 'msg'
		    ,time: 2 //2秒后自动关闭
		});
	}else{
		var size = $("#"+id).html();
		if(size == undefined || size == null){
			size = 0;
		}
		var idPush = id.split("_")[1];
		if(flag == 0){//减
			if(parseInt(size) > 0){
				var sum = parseInt(size)-1;
				$("#"+id).html(sum);
				if(sum == 0){
					//设置颜色变灰
					$("#"+id).css("color","#ffffff");
				}else{
					//设置颜色变绿
					$("#"+id).css("color","#14df2c");
				}
				
				/**删除id**/
				var delFlag = true;
				var data = vm.serviceIds;
				var dataSave = new Array();
				for(var i=0;i<data.length;i++){
					if(parseInt(data[i]) != parseInt(idPush) || delFlag == false){
						dataSave.push(data[i]);
					}else{
						delFlag = false;
					}
				}
				vm.serviceIds = dataSave;
			}else{
				$("#"+id).html(0);
				//设置颜色变灰
				$("#"+id).css("color","#ffffff");
			}
		}else{//加
			var sum = parseInt(size)+1;
			$("#"+id).html(sum);
			//设置颜色变绿
			$("#"+id).css("color","#14df2c");
			
			/**添加id**/
			vm.serviceIds.push(idPush);
		}
	}
}

/**
* 添加已经选择的信息
*/
function addServiceIds(id){
	var data = vm.serviceIds;

	var flag = true;
	for(var i=0;i<data.length;i++){
		if(parseInt(data[i]) == parseInt(id)){
			flag = false;
		}
	}
	if(flag){
		vm.serviceIds.push(id);
	}
	
}

/**
* 取消已经选择的信息
*/
function delServiceIds(id){
	var data = vm.serviceIds;
	var dataSave = new Array();

	for(var i=0;i<data.length;i++){
		if(parseInt(data[i]) != parseInt(id)){
			dataSave.push(data[i]);
		}
	}
	
	vm.serviceIds = dataSave;
}




/**
** 加法函数，用来得到精确的加法结果
** 说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。
** 调用：accAdd(arg1,arg2)
** 返回值：arg1加上arg2的精确结果
**/
function accAdd(arg1, arg2) {
  var r1, r2, m, c;
  try {
      r1 = arg1.toString().split(".")[1].length;
  }
  catch (e) {
      r1 = 0;
  }
  try {
      r2 = arg2.toString().split(".")[1].length;
  }
  catch (e) {
      r2 = 0;
  }
  c = Math.abs(r1 - r2);
  m = Math.pow(10, Math.max(r1, r2));
  if (c > 0) {
      var cm = Math.pow(10, c);
      if (r1 > r2) {
          arg1 = Number(arg1.toString().replace(".", ""));
          arg2 = Number(arg2.toString().replace(".", "")) * cm;
      } else {
          arg1 = Number(arg1.toString().replace(".", "")) * cm;
          arg2 = Number(arg2.toString().replace(".", ""));
      }
  } else {
      arg1 = Number(arg1.toString().replace(".", ""));
      arg2 = Number(arg2.toString().replace(".", ""));
  }
  return (arg1 + arg2) / m;
}

/**
** 减法函数，用来得到精确的减法结果
** 说明：javascript的减法结果会有误差，在两个浮点数相减的时候会比较明显。这个函数返回较为精确的减法结果。
** 调用：accSub(arg1,arg2)
** 返回值：arg1减去arg2的精确结果
**/
function accSub(arg1, arg2) {
  var r1, r2, m, n;
  try {
      r1 = arg1.toString().split(".")[1].length;
  }
  catch (e) {
      r1 = 0;
  }
  try {
      r2 = arg2.toString().split(".")[1].length;
  }
  catch (e) {
      r2 = 0;
  }
  m = Math.pow(10, Math.max(r1, r2)); //last modify by deeka //动态控制精度长度
  n = (r1 >= r2) ? r1 : r2;
  return ((arg1 * m - arg2 * m) / m).toFixed(n);
}