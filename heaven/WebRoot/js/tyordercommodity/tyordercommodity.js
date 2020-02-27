$(function () {
    $("#jqGrid").jqGrid({
        url: '../tyordercommodity/list',
        datatype: "json",
        colModel: [			
	        { label: '订单号', name: 'number', width: 50},
			{ label: '用户ID', name: 'userId', width: 50},
			{ label: '商品名称', name: 'commodityTitle', width: 50},
			{ label: '商品价格', name: 'commodityPrice', width: 50},
			{ label: '支付状态', name: 'payStatus', width: 50,formatter: function(value, options, row){
				//0待支付，1已支付
				if(value==0){
					return '<span class="label label-danger" style="padding: 6px 6px;background-color: #f0ad4e;border-color: #eea236;"><i class="fa fa-spinner"></i> 待支付</span>';
				}else {
					return '<span class="label label-success" style="padding: 6px 6px;"><i class="fa fa-check"></i> 已支付</span>';
				}
			}},
			{ label: '发货状态', name: 'expressStatus', width: 50,formatter: function(value, options, row){
				//：0=待发货，1=已发货
				if(value==0){
					return '<span class="label label-danger" style="padding: 6px 6px;background-color: #f0ad4e;border-color: #eea236;"><i class="fa fa-spinner"></i> 待发货</span>';
				}else {
					return '<span class="label label-success" style="padding: 6px 6px;"><i class="fa fa-check"></i> 已发货</span>';
				}
			}},
			{ label: '发货时间', name: 'expressTime', width: 50,formatter: function(value, options, row){
				if(value == null || value == ""){
					return "待发货";
				}else{
					return dateForStr(value);
				}
			}},
			{ label: '快递名称', name: 'expressName', width: 50},
			{ label: '快递单号', name: 'expressNumber', width: 50},
			{ label: '订单状态', name: 'status', width: 50,formatter: function(value, options, row){
				//：0，未完成，1=已完成
				if(value==0){
					return '<span class="label label-danger" style="padding: 6px 6px;background-color: #f0ad4e;border-color: #eea236;"><i class="fa fa-spinner"></i> 未完成</span>';
				}else if(value==1){
					return '<span class="label label-success" style="padding: 6px 6px;"><i class="fa fa-check"></i> 已完成</span>';
				}else{
					return '<span class="label label-success" style="padding: 6px 6px;background-color: #00a9c4;"><i class="fa fa-retweet"></i> 已退款</span>';
				}
			}},
			{ label: '下单时间', name: 'createtime', width: 50 ,formatter: function(value, options, row){
				return dateForStr(value);
			}},
			{ label: '操作',  name: 'id',width: 80, formatter: function(value, options, row){
				var refundBtn = "";
				if(row.payStatus == 0){//未付款，没有退款按钮
					refundBtn = '';
				}else{
					refundBtn = '<a class="btn btn-primary" style="padding: 6px 6px;margin-left: 6px;margin-top: 3px;background-color: #00a9c4;" onclick="refund('+row.id+',\''+row.number+'\');">退款</a>';
				}
				
				if(row.status == 0){//只有未完成的状态才可以操作
					if(row.expressStatus == 0){//只有等于0-代发货时才有发货
						return '<a class="btn btn-primary" style="padding: 6px 6px;margin-left: 6px;margin-top: 3px;" onclick="sendGoods('+row.id+',\''+row.number+'\');">发货</a>'+
						   '<a class="btn btn-primary" style="padding: 6px 6px;margin-left: 6px;margin-top: 3px;background-color: #eb8f06;" onclick="address('+row.id+',\''+row.number+'\',\''+row.name+'\',\''+row.phone+'\',\''+row.addres+'\');">收货地址</a>'+
						   refundBtn;
					}else{
						return '<a class="btn btn-primary" style="padding: 6px 6px;margin-left: 6px;margin-top: 3px;" onclick="updateSendGoods('+row.id+',\''+row.number+'\',\''+row.expressName+'\');">修改快递信息</a>'+
						   '<a class="btn btn-primary" style="padding: 6px 6px;margin-left: 6px;margin-top: 3px;background-color: #eb8f06;" onclick="address('+row.id+',\''+row.number+'\',\''+row.name+'\',\''+row.phone+'\',\''+row.addres+'\');">收货地址</a>'+
						   refundBtn;
					}
				}else{
					return '<a class="btn btn-primary" style="padding: 6px 6px;margin-left: 6px;margin-top: 3px;background-color: #eb8f06;" onclick="address('+row.id+',\''+row.number+'\',\''+row.name+'\',\''+row.phone+'\',\''+row.addres+'\');">收货地址</a>';
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
		tyOrderCommodity:{},
		addressMsg:{},
		keyword:null,
		pay_status:-1,
		express_status:-1,
		status:-1
	},
	methods: {
		query: function () {
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{'keyword': vm.keyword,'pay_status': vm.pay_status,'express_status': vm.express_status,'status': vm.status},
                page:1
            }).trigger("reloadGrid");
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			location.href = "tyordercommodity_add.html?id="+id;
		}
	}
});

//删除
function delThisById(id){
	if(id == null || id == undefined || id == "" || id == "null"){
		alert("要删除的信息出现异常，请刷新当前页面后重新操作!");
		return ;
	}
	
	var ids = new Array();
	ids.push(id);
	confirm('确定要删除选中的记录？', function(){
		$.ajax({
			type: "POST",
		    url: "../tyordercommodity/delete",
		    data: JSON.stringify(ids),
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


//发货
function sendGoods(id,number){
	vm.tyOrderCommodity.id = null;
	vm.tyOrderCommodity.id = id;
	layer.open({
		type: 1,
		skin: 'layui-layer-molv',
		title: "订单: "+number,
		area: ['60%', 'auto'],
		shade: 0,
		shadeClose: false,
		content: jQuery("#topUpTree_sendGoods"),
		btn: ['确认操作', '取消'],
		btn1: function (index) {//保存信息
			$.ajax({
				type: "POST",
			    url: "../tyordercommodity/sendGoods",
			    contentType:"application/json",
			    data: JSON.stringify(vm.tyOrderCommodity),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							$("#jqGrid").trigger("reloadGrid");
						});
						layer.close(index);
					}else{
						alert(r.msg);
					}
				}
			});
        }
	});
}

//修改快递信息
function updateSendGoods(id,number,name){
	vm.tyOrderCommodity = {};
	$.get("../tyordercommodity/info/"+id, function(r){
        vm.tyOrderCommodity = r.tyOrderCommodity;
        
        layer.open({
    		type: 1,
    		skin: 'layui-layer-molv',
    		title: "订单: "+number,
    		area: ['60%', 'auto'],
    		shade: 0,
    		shadeClose: false,
    		content: jQuery("#topUpTree_sendGoods"),
    		btn: ['确认操作', '取消'],
    		btn1: function (index) {//保存信息
    			$.ajax({
    				type: "POST",
    			    url: "../tyordercommodity/updateSendGoods",
    			    contentType:"application/json",
    			    data: JSON.stringify(vm.tyOrderCommodity),
    			    success: function(r){
    			    	if(r.code === 0){
    						alert('操作成功', function(index){
    							$("#jqGrid").trigger("reloadGrid");
    						});
    						layer.close(index);
    					}else{
    						alert(r.msg);
    					}
    				}
    			});
            }
    	});
    });
}

//退款
function refund(id,number){
	confirm('确定要退款单号为:'+number+" 的订单吗?", function(){
		$.ajax({
			type: "POST",
		    url: "../tyordercommodity/refund/"+id,
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

//查看收货地址
function address(id,number,name,phone,addres){
	vm.addressMsg = {};
	vm.addressMsg.id = id;
	vm.addressMsg.name = name;
	vm.addressMsg.phone = phone;
	vm.addressMsg.addres = addres;
	layer.open({
		type: 1,
		skin: 'layui-layer-molv',
		title: "操作订单: "+number+" 的收货地址",
		area: ['60%', 'auto'],
		shade: 0,
		shadeClose: false,
		content: jQuery("#topUpTree_addres"),
		btn: ['保存', '取消'],
		btn1: function (index) {//保存信息
			$.ajax({
				type: "POST",
			    url: "../tyordercommodity/updateAddres",
			    contentType:"application/json",
			    data: JSON.stringify(vm.addressMsg),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							$("#jqGrid").trigger("reloadGrid");
						});
						layer.close(index);
					}else{
						alert(r.msg);
					}
				}
			});
        }
	});
}







