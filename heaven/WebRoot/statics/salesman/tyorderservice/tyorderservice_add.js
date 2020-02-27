var id = T.p("id");
var vm = new Vue({
	el:'#rrapp',
	data:{
		title:"新增",
		title2:"服务详情",
		tyOrderService:{},
		tyOrderServiceSet:{},
		fuwu:{},
		fuwuIds:"",
		service:{},
		serviceIds: new Array()
	},
	created: function() {
		if(id != null){
			this.title = "订单信息";
			this.getInfo(id);
		}
    },
	methods: {
		getInfo: function(id){
			$.get("../../../salesman/user/tyorderservice_info/"+id, function(r){
                vm.tyOrderService = r.tyOrderService;
                vm.fuwu = r.fuwu;
                var fuwuIds = "";
                for(var i=0;i<vm.fuwu.length;i++){
                	fuwuIds = fuwuIds + vm.fuwu[i].sort + ",";
                }
                vm.fuwuIds = fuwuIds;
            });
		},
		getServiceInfo: function(id){
			$.get("../../../salesman/user/tyorderservice_service_info", function(r){
                vm.service = r.service;
                layer.open({
            		type: 1,
            		skin: 'layui-layer-molv',
            		title: " 添加服务信息 ",
            		area: ['90%', '80%'],
            		shade: 0,
            		shadeClose: false,
            		offset:"10px",
            		content: jQuery("#topUpTree_service"),
            		btn: ['确认操作', '取消'],
            		btn1: function (index) {
            			//保存信息
            			var fuwuIds = vm.fuwuIds;
            			var prices = "0";
            			//显示已经添加的服务信息
            			var serviceData = vm.serviceIds;
            			var dataArray = new Array();
            			for(var i=0;i<serviceData.length;i++){
            				var service = vm.service;
            				var id = serviceData[i];
            				for(var ii=0;ii<service.length;ii++){
            					var childrenFw = service[ii].childrenFw;
            					for(var iii=0;iii<childrenFw.length;iii++){
            						if(id == childrenFw[iii].id){
            							var jsonData = {id: childrenFw[iii].id,name:childrenFw[iii].name,parentId:childrenFw[iii].parentId,sort:childrenFw[iii].id,text:childrenFw[iii].text};
            							vm.fuwu.push(jsonData);
            							
            							prices = accAdd(prices,childrenFw[iii].name);
//            							prices += parseFloat(childrenFw[iii].name);
            							fuwuIds = fuwuIds + childrenFw[iii].id + ",";
            						}
            					}
            				}
            			}
            			vm.fuwuIds = fuwuIds;
            			
            			layer.close(index);
            			
            			confirm('是否要在订单服务总价上添加新增的服务价格？', function(){
            				var fuwuPr = "0";
            				if(vm.tyOrderService.price == undefined || vm.tyOrderService.price == null || vm.tyOrderService.price == ""){
            					fuwuPr = prices;
            				}else{
            					fuwuPr = accAdd(vm.tyOrderService.price,prices);
//            					fuwuPr = parseFloat(vm.tyOrderService.price)+prices;
            				}
            				
            				vm.tyOrderServiceSet = vm.tyOrderService;
            				vm.tyOrderServiceSet.price = fuwuPr;
            				vm.tyOrderService = {};
            				vm.tyOrderService = vm.tyOrderServiceSet;
            				alert("订单服务总价上已添加新增的服务价格,保存服务信息后生效!");
            			});
                    }
            	});
            });
		},
		saveOrUpdate: function (event) {
			confirm('是否确定要保存已修改的信息？', function(){
				var fuwuIds = vm.fuwuIds;
				var orderId = vm.tyOrderService.id;
				var price = vm.tyOrderService.price;
				var note = vm.tyOrderService.note;
				if(note == undefined || note == ""){
					note = "";
				}
				if(price == undefined || price == ""){
					price = "";
				}
				
				var data = "orderId="+orderId+"&price="+price+"&note="+note+"&fuwuIds="+fuwuIds;
				$.ajax({ 
					type: "POST",
				    url: "../../../salesman/user/tyorderservice_updatefw?"+data,
				    success: function(r){
				    	if(r.code === 0){
							alert('操作成功', function(index){
								vm.back();
							});
						}else{
							alert(r.msg);
						}
					}
				});
				
			});
		},
		back: function (event) {
			history.go(-1);
		}
	}
});

//删除
function deleteFw(id){
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
			//只删除一次
			if(delFlag){
				delFlag = false;
				privace = data[i].name;
			}else{
				dataSave.push(data[i]);
				fuwuIds = fuwuIds + data[i].sort + ",";
			}
		}else{
			fuwuIds = fuwuIds + data[i].sort + ",";
			dataSave.push(data[i]);
		}
	}
	
	vm.fuwu = {};
	vm.fuwu = dataSave;
	vm.fuwuIds = fuwuIds;
	
	confirm('是否要在订单服务总价上减去删除服务的价格？', function(){
		var fuwuPr = "0";
		if(vm.tyOrderService.price == undefined || vm.tyOrderService.price == null || vm.tyOrderService.price == ""){
			fuwuPr = undefined;
		}else{
			fuwuPr = accSub(vm.tyOrderService.price,privace);
//			fuwuPr = parseFloat(vm.tyOrderService.price)-privace;
		}
		
		vm.tyOrderServiceSet = vm.tyOrderService;
		vm.tyOrderServiceSet.price = fuwuPr;
		vm.tyOrderService = {};
		vm.tyOrderService = vm.tyOrderServiceSet;
		alert("订单服务总价上已减去删除服务的价格,保存服务信息后生效!");
	});
}

//全选
function selectFwAll(id){
	var idSelect = id;
	id = id.split("_")[1];
	
	var selectValue = $("#"+idSelect).attr("name");
	if(selectValue.trim() == "quanxuan"){//执行全选
		var data = vm.service;
		for(var i=0;i<data.length;i++){
			if(data[i].id == parseInt(id)){
				var childrenFw = data[i].childrenFw;
				for(var ii=0;ii<childrenFw.length;ii++){
					$("#service_"+childrenFw[ii].id).css("background-color","rgb(71, 182, 50)");
					$("#service_"+childrenFw[ii].id).attr("name","quxiao");
					addServiceIds(childrenFw[ii].id);//添加
				}
			}
		}
		$("#"+idSelect).html("取消");
		$("#"+idSelect).attr("name","quxiao");
		$("#"+idSelect).css("background-color","rgb(251, 88, 83)");
	}else{//执行全部取消
		var data = vm.service;
		for(var i=0;i<data.length;i++){
			if(data[i].id == parseInt(id)){
				var childrenFw = data[i].childrenFw;
				for(var ii=0;ii<childrenFw.length;ii++){
					$("#service_"+childrenFw[ii].id).css("background-color","rgb(251, 88, 83)");
					$("#service_"+childrenFw[ii].id).attr("name","xuanzhong");
					delServiceIds(childrenFw[ii].id);//删除
				}
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






