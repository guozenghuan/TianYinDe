$(function () {
    $("#jqGrid").jqGrid({
        url: '../tyorderservice/list?status=quotation',
        datatype: "json",
        colModel: [			
			{ label: '用户ID', name: 'userid', width: 1},
			{ label: '单号', name: 'number', width: 1},
			{ label: '服务费', name: 'servicePrice', width: 1},
			{ label: '下单时间', name: 'createtime', width: 1 ,formatter: function(value, options, row){
				return dateForStr(value);
			}},
			{ label: '派单状态', name: 'tsUserStatus', width: 1,formatter: function(value, options, row){
				//0-未派单，1-已派单
				if(value==0){
					return '<span class="label label-danger" style="padding: 6px 6px;background-color: #f0ad4e;border-color: #eea236;"><i class="fa fa-spinner"></i> 未派单</span>';
				}else {
					return '<span class="label label-success" style="padding: 6px 6px;"><i class="fa fa-check"></i> 已派单</span>';
				}
			}},
			{ label: '业务员ID', name: 'tsUserid', width: 1},
			{ label: '订单总价', name: 'price', width: 1},
			{ label: '是否付款', name: 'priceStatus', width: 1,formatter: function(value, options, row){
				//0-待付款，1-已付款
				if(value==0){
					return '<span class="label label-danger" style="padding: 6px 6px;background-color: #f0ad4e;border-color: #eea236;"><i class="fa fa-spinner"></i> 待付款</span>';
				}else if(value==1){
					return '<span class="label label-success" style="padding: 6px 6px;"><i class="fa fa-check"></i> 已线上付款</span>';
				}else if(value==2){
					return '<span class="label label-success" style="background-color: #00a9c4;padding: 6px 6px;"><i class="fa fa-check"></i> 线下打款</span>';
				}
			}},
			{ label: '订单状态', name: 'status', width: 1,formatter: function(value, options, row){
				//0-服务中，1-服务结束
				if(value==0 && (row.priceStatus==1 || row.priceStatus==2)){
					return '<span class="label label-danger" style="padding: 6px 6px;background-color: #ef2be8;border-color: #eea236;"><i class="fa fa-spinner"></i> 服务中</span>';
				}else if(value==1 && (row.priceStatus==1 || row.priceStatus==2)){
					if(row.userStatus==0){
						return '<span class="label label-danger" style="padding: 6px 6px;background-color: #337ab7;border-color: #2e6da4;"><i class="fa fa-check"></i> 业务员已确认</span>';
					}else{
						return '<span class="label label-success" style="padding: 6px 6px;"><i class="fa fa-check"></i> 服务结束</span>';
					}
					
				}else if(value==0 && row.priceStatus==0){
					return '<span class="label label-danger" style="padding: 6px 6px;background-color: #f0ad4e;border-color: #eea236;"><i class="fa fa-spinner"></i> 待付款</span>';
				}else if(value==1 && row.priceStatus==0){
					return '<span class="label label-danger" style="padding: 6px 6px;background-color: #e73333;border-color: #eea236;"><i class="fa fa-exclamation-triangle"></i> 交易失败</span>';
				}
			}},
			{ label: '评分', name: 'score', width: 1,formatter: function(value, options, row){
				if(value != null){
					var scoreHtml = '';
					for(var i=0;i<value;i++){
						scoreHtml += '<i class="fa fa-star" style="color: #f4940d;font-size: 18px;"></i>'; 
					}
					return scoreHtml;
				}else{
					return '';
				}
			}},
			{ label: '操作',  name: 'id',width: 2, formatter: function(value, options, row){
				var tsUserStatusBtn = '派单';
				if(row.tsUserStatus == 1){//已经派送业务员
					tsUserStatusBtn = '修改业务员';
				}
				
				var okServiceBtn = "";
				if(row.status == 0 && (row.priceStatus == 1 || row.priceStatus == 2)){//订单服务中并且用户已经付款
					okServiceBtn = '<a title="确认订单服务已完成" class="btn btn-primary" style="background-color: #62af3f;padding: 6px 6px;margin-left: 6px;margin-top: 3px;" onclick="okService('+row.id+');"><i class="fa fa-check-circle-o" style="font-size: 18px;width: 30px;"></i></a>';
				}else if(row.status == 0 && row.priceStatus == 0){//订单服务中并且用户没有付款
					okServiceBtn = '<a title="订单交易失败" class="btn btn-primary" style="background-color: #e73333;padding: 6px 6px;margin-left: 6px;margin-top: 3px;" onclick="falseService('+row.id+');"><i class="fa fa-times" style="font-size: 18px;width: 30px;"></i></a>';
				}
				
				var xianxiaMoneyBtn = "";
				if(row.status == 0 && row.priceStatus == 0){
					xianxiaMoneyBtn = '<a title="用户线下打款" class="btn btn-primary" style="background-color: #f0ad4e;padding: 6px 6px;margin-left: 6px;margin-top: 3px;" onclick="xianxiaMoneyService('+row.id+');"><i class="fa fa-money" style="font-size: 18px;width: 30px;"></i></a>';
				}
				
				if(row.status == 1){//服务结束
					return '<a title="查看订单信息" class="btn btn-primary" style="padding: 6px 6px;margin-left: 6px;margin-top: 3px;" href="tyorderservice_add.html?id='+row.id+'"><i class="fa fa-pencil-square-o" style="font-size: 18px;width: 30px;"></i></a>';
				}else{
					return '<a title="编辑订单信息" class="btn btn-primary" style="padding: 6px 6px;margin-left: 6px;margin-top: 3px;" href="tyorderservice_add.html?id='+row.id+'"><i class="fa fa-pencil-square-o" style="font-size: 18px;width: 30px;"></i></a>'+
					   '<a title="派单业务员" class="btn btn-primary" style="padding: 6px 6px;margin-left: 6px;margin-top: 3px;background-color: #00a9c4;" onclick="paiDan('+row.id+',\''+row.number+'\');"><i class="fa fa-male" style="font-size: 18px;width: 30px;"></i>'+'</a>'+xianxiaMoneyBtn+okServiceBtn;
				}	
			}}
        ],
		viewrecords: true,
        height: getWindowsInnerHeight(),
        rowNum: 15,
		rowList : [15,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        //multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		keyword:null,
		tsUserStatus:-1,
		priceStatus:-1,
		status:-1
	},
	methods: {
		query: function () {
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'keyword': vm.keyword,"tsUserStatus":vm.tsUserStatus,'priceStatus': vm.priceStatus,'status': vm.status},
                page:1
            }).trigger("reloadGrid");
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			
			location.href = "tyorderservice_add.html?id="+id;
		}
	}
});

//派单
function paiDan(id,orderNumber){
	$("#paiDanIframeID").attr("src","../tsuser/tsuser.html?createtimeFlag=hide&serviceId="+id);
	layer.open({
		type: 1,
		skin: 'layui-layer-molv',
		title: "派送服务订单: "+orderNumber,
		area: ['90%', '80%'],
		shade: 0,
		shadeClose: false,
		offset:"10px",
		content: jQuery("#topUpTree_paiDan")
		/*,
		btn: ['确认操作', '取消'],
		btn1: function (index) {//保存信息
			
        }*/
	});
}

function closeLayer(){
	layer.closeAll();
	$("#jqGrid").trigger("reloadGrid");
}

function xianxiaMoneyService(orderId){
	confirm('确定该服务订单用户选择线下付款？', function(){
		$.ajax({
			type: "POST",
		    url: "../tyorderservice/xianxia_service_order?orderId="+orderId,
		    success: function(r){
				if(r.code == 0){
					alert('操作成功', function(index){
						$("#jqGrid").trigger("reloadGrid");
					});
				}else{
					alert(r.msg);
				}
			}
		});
	});
}


function okService(orderId){
	confirm('确定该服务订单已完成(如是线下打款的订单,请确定已收款后再确认服务订单已完成!)？', function(){
		$.ajax({
			type: "POST",
		    url: "../tyorderservice/ok_service_order?orderId="+orderId,
		    success: function(r){
				if(r.code == 0){
					alert('操作成功', function(index){
						$("#jqGrid").trigger("reloadGrid");
					});
				}else{
					alert(r.msg);
				}
			}
		});
	});
}
function falseService(orderId){
	confirm('确定该服务订单交易失败？', function(){
		$.ajax({
			type: "POST",
		    url: "../tyorderservice/false_service_order?orderId="+orderId,
		    success: function(r){
				if(r.code == 0){
					alert('操作成功', function(index){
						$("#jqGrid").trigger("reloadGrid");
					});
				}else{
					alert(r.msg);
				}
			}
		});
	});
}


