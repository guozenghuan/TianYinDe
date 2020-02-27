var vm = new Vue({
	el : '#rrapp',
	data : {
		tyService:{},
		children:{"name":"","text":""},
		tyServiceFWMain:{},
		updateFWMain:{},
		fwChildren:{},
		updateFwSecondData:{},
		updateSors:{}
	},
	created : function() {
		
	},
	methods : {
		addLiuCheng : function() {
			btnLiuChengStatus();
		}
	}
});

var vm_two = new Vue({
	el : '#rrapp_two',
	data : {
		fuwu:{}
	},
	created : function() {
		
	},
	methods : {
		
	}
});

var rrapp_jiage = new Vue({
	el : '#rrapp_jiage',
	data : {
		tyServiceJG:{}
	},
	created : function() {
		
	},
	methods : {
		queryJiaGe: function(){
			$.ajax({
				type: "POST",
			    url: "../tyservice/updateJiaGe",
			    contentType:"application/json",
			    data: JSON.stringify(rrapp_jiage.tyServiceJG),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功');
					}else{
						alert(r.msg);
					}
				}
			});
		}
	}
});

function btnLiuChengStatus(){
	if($("#xiaJiLCId").is(':hidden')){
	    //如果隐藏时
		$("#xiaJiLCId").show();
		$("#addLCBtn").css({"background-color":"#dd4c3a","border-color":"#dd4c3a"});
		$("#addLCBtn").html('取消下级流程信息 <i class="fa fa-minus-square"></i>');
	}else{
	    //如果显示时
		$("#xiaJiLCId").hide();
		$("#addLCBtn").css({"background-color":"#5cb85c","border-color":"#5cb85c"});
		$("#addLCBtn").html('新增下级流程信息 <i class="fa fa-plus-square"></i>');
	}
}

$(function() {
	//初始化数据
	initOrgchartData();
});

function initOrgchartData(){
	$.get("../tyservice/list", function(r) {
		//加载价格信息
		rrapp_jiage.tyServiceJG = r.jiage;
		
		//加载服务信息
		vm_two.fuwu = r.fuwu;
		
		//加载流程信息
		$("#chart-container").html("");
		$('#chart-container').orgchart({
			'data' : r.liucheng,
			'nodeTitle' : 'name',
			'nodeContent' : 'text',
			'nodeId' : 'id'
		});
		
		$(".node").click(function () {
			var clickId = this.id;
			//查询信息
			$.get("../tyservice/info/"+clickId, function(r){
                vm.tyService = r.tyService;
                addOrgchartData(clickId);
            });
		});
		
		
		/*$(".orgchart ").each(function(i){
			if(i==0){
				$(this).before('<div class="grid-btn" style="text-align: center;"><h3>服务流程信息</h3><div class="baguetteBoxOne gallery"></div>');
			}
			if(i==1){
				$(this).before('<div class="grid-btn" style="text-align: center;margin-top: 30px;"><h3>服务详情信息</h3><div class="baguetteBoxOne gallery"></div>');
			}
		});*/
	});
}

//添加信息
function addOrgchartData(id){
	layer.open({
		type: 1,
		/*offset: '50px',*/
		skin: 'layui-layer-molv',
		title: "修改/添加子节点信息",
		area: ['60%', '50%'],
		shade: 0,
		shadeClose: false,
		content: jQuery("#topUpTree"),
		btn: ['确认操作', '取消','删除信息'],
		btn1: function (index) {//保存信息
			//添加子节点信息
			vm.tyService.children = vm.children;
			$.ajax({
				type: "POST",
			    url: "../tyservice/update",
			    contentType:"application/json",
			    data: JSON.stringify(vm.tyService),
			    success: function(r){
			    	if(r.code === 0){
			    		vm.children.name="";
			    		vm.children.text="";
						alert('操作成功');
						layer.close(index);
						initOrgchartData();
						
						//如果显示时
						$("#xiaJiLCId").hide();
						$("#addLCBtn").css({"background-color":"#5cb85c","border-color":"#5cb85c"});
						$("#addLCBtn").html('新增下级流程信息 <i class="fa fa-plus-square"></i>');
					}else{
						alert(r.msg);
					}
				}
			});
        },
        btn3: function (index) {//取消
        	layer.close(index);
        	
        	//如果显示时
    		$("#xiaJiLCId").hide();
    		$("#addLCBtn").css({"background-color":"#5cb85c","border-color":"#5cb85c"});
    		$("#addLCBtn").html('新增下级流程信息 <i class="fa fa-plus-square"></i>');
        },
        btn3: function (index) {//删除信息
        	delThisById(id);
        	layer.close(index);
        	
        	//如果显示时
    		$("#xiaJiLCId").hide();
    		$("#addLCBtn").css({"background-color":"#5cb85c","border-color":"#5cb85c"});
    		$("#addLCBtn").html('新增下级流程信息 <i class="fa fa-plus-square"></i>');
        }
	});
}

//删除
function delThisById(id) {
	if (id == null || id == undefined || id == "" || id == "null") {
		alert("要删除的信息出现异常，请刷新当前页面后重新操作!");
		return;
	}

	var ids = new Array();
	ids.push(id);
	confirm('确定要删除选中的记录？', function() {
		$.ajax({
			type : "POST",
			url : "../tyservice/delete",
			contentType:"application/json",
			data : JSON.stringify(ids),
			success : function(r) {
				if (r.code == 0) {
					alert('操作成功', function(index) {
						$("#jqGrid").trigger("reloadGrid");
					});
					initOrgchartData();
				} else {
					alert(r.msg);
				}
			}
		});
	});
}


/**服务详情**/
//添加服务主要信息
function addFWMain(){
	layer.open({
		type: 1,
		/*offset: 'auto',*/
		skin: 'layui-layer-molv',
		title: "添加服务主要信息名称",
		area: ['60%', 'auto'],
		shade: 0,
		shadeClose: false,
		content: jQuery("#topUpTreeMain"),
		btn: ['确认操作', '取消'],
		btn1: function (index) {//保存信息
			//添加子节点信息
			vm.tyServiceFWMain.parentId = 0;//主父id为0
			vm.tyServiceFWMain.type = 'fuwu';//类型为服务
			$.ajax({
				type: "POST",
			    url: "../tyservice/save",
			    contentType:"application/json",
			    data: JSON.stringify(vm.tyServiceFWMain),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功');
						layer.close(index);
						initOrgchartData();
					}else{
						alert(r.msg);
					}
				}
			});
        }
	});
}

//添加服务下级信息
function addFwSecond(id){
	layer.open({
		type: 1,
		/*offset: 'auto',*/
		skin: 'layui-layer-molv',
		title: "添加服务子信息",
		area: ['60%', 'auto'],
		shade: 0,
		shadeClose: false,
		content: jQuery("#topUpTreeMainSecond"),
		btn: ['确认操作', '取消'],
		btn1: function (index) {//保存信息
			//添加子节点信息
			vm.fwChildren.parentId = id;//主父id
			$.ajax({
				type: "POST",
			    url: "../tyservice/save",
			    contentType:"application/json",
			    data: JSON.stringify(vm.fwChildren),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功');
						layer.close(index);
						initOrgchartData();
					}else{
						alert(r.msg);
					}
				}
			});
        }
	});
}
//修改服务下级信息
function updateFwSecondData(id){
	$.get("../tyservice/info/"+id, function(r){
        vm.updateFwSecondData = r.tyService;
        
        layer.open({
    		type: 1,
    		/*offset: 'auto',*/
    		skin: 'layui-layer-molv',
    		title: "修改服务主信息",
    		area: ['60%', 'auto'],
    		shade: 0,
    		shadeClose: false,
    		content: jQuery("#updateFwSecondData"),
    		btn: ['确认操作', '取消'],
    		btn1: function (index) {//保存信息
    			$.ajax({
    				type: "POST",
    			    url: "../tyservice/update",
    			    contentType:"application/json",
    			    data: JSON.stringify(vm.updateFwSecondData),
    			    success: function(r){
    			    	if(r.code === 0){
    						alert('操作成功');
    						layer.close(index);
    						initOrgchartData();
    					}else{
    						alert(r.msg);
    					}
    				}
    			});
            }
    	});
    });
}

//修改服务主信息
function updateFwSecond(id){
	$.get("../tyservice/info/"+id, function(r){
        vm.updateFWMain = r.tyService;
        
        layer.open({
    		type: 1,
    		/*offset: 'auto',*/
    		skin: 'layui-layer-molv',
    		title: "修改服务主信息",
    		area: ['60%', 'auto'],
    		shade: 0,
    		shadeClose: false,
    		content: jQuery("#updateFwSecond"),
    		btn: ['确认操作', '取消'],
    		btn1: function (index) {//保存信息
    			//添加子节点信息
    			vm.fwChildren.parentId = id;//主父id
    			$.ajax({
    				type: "POST",
    			    url: "../tyservice/update",
    			    contentType:"application/json",
    			    data: JSON.stringify(vm.updateFWMain),
    			    success: function(r){
    			    	if(r.code === 0){
    						alert('操作成功');
    						layer.close(index);
    						initOrgchartData();
    					}else{
    						alert(r.msg);
    					}
    				}
    			});
            }
    	});
    });
}

//删除主服务详情信息以及所有子节点
function deleteFwAll(id){
	if (id == null || id == undefined || id == "" || id == "null") {
		alert("要删除的信息出现异常，请刷新当前页面后重新操作!");
		return;
	}

	var ids = new Array();
	ids.push(id);
	confirm('确定要删除选中的记录？', function() {
		$.ajax({
			type : "POST",
			url : "../tyservice/deleteFwAll",
			contentType:"application/json",
			data : JSON.stringify(ids),
			success : function(r) {
				if (r.code == 0) {
					alert('操作成功');
					initOrgchartData();
				} else {
					alert(r.msg);
				}
			}
		});
	});
}

//删除服务详情的子节点内容
function deleteFw(id){
	if (id == null || id == undefined || id == "" || id == "null") {
		alert("要删除的信息出现异常，请刷新当前页面后重新操作!");
		return;
	}

	var ids = new Array();
	ids.push(id);
	confirm('确定要删除选中的记录？', function() {
		$.ajax({
			type : "POST",
			url : "../tyservice/deleteFw",
			contentType:"application/json",
			data : JSON.stringify(ids),
			success : function(r) {
				if (r.code == 0) {
					alert('操作成功');
					initOrgchartData();
				} else {
					alert(r.msg);
				}
			}
		});
	});
}

//排序一级信息
function updateSors(id,type){
	vm.updateSors = {};
	vm.updateSors.id = id;
	vm.updateSors.name = type;
	$.ajax({
		type: "POST",
	    url: "../tyservice/updateMainSors",
	    contentType:"application/json",
	    data: JSON.stringify(vm.updateSors),
	    success: function(r){
	    	if(r.code === 0){
				alert('操作成功');
				initOrgchartData();
			}else{
				alert(r.msg);
			}
		}
	});
}

//排序二级信息
function updateSorsSecond(id,type){
	vm.updateSors = {};
	vm.updateSors.id = id;
	vm.updateSors.name = type;
	$.ajax({
		type: "POST",
	    url: "../tyservice/updateMainSorsScd",
	    contentType:"application/json",
	    data: JSON.stringify(vm.updateSors),
	    success: function(r){
	    	if(r.code === 0){
				alert('操作成功');
				initOrgchartData();
			}else{
				alert(r.msg);
			}
		}
	});
}








