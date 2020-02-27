$(function () {
    $("#jqGrid").jqGrid({
        url: '../tcuser/list',
        datatype: "json",
        colModel: [			
//			{ label: '用户openid', name: 'openid', width: 50},
			{ label: '用户ID', name: 'id', width: 1},
			{ label: '用户昵称', name: 'nickname', width: 1},
			{ label: '头像', name: 'headimgurl', width: 1 ,formatter: function(value,options,row){
				return '<img class="img-circle" style="width: 70px;height: 70px;cursor: pointer;" src='+value+' alt="头像"/>';
			}},
			{ label: '钱包', name: 'wallet', width: 1},
			{ label: '注册时间', name: 'createtime', width: 1 ,formatter: function(value, options, row){
				return dateForStr(value);
			}},
			{ label: '状态', name: 'status',width: 1, formatter: function(value, options, row){
				return value === 0 ? '<span class="label label-success" style="padding: 6px 6px;">正常</span>' :'<span class="label label-danger" style="padding: 6px 6px;">已冻结</span>';
			}},
			{ label: '操作',  name: 'id',width: 2, formatter: function(value, options, row){
				var violationsBtn = row.status === 0 ? '<a class="btn btn-danger" style="padding: 6px 6px;margin-left: 6px;margin-top: 3px;" onclick="settingStatus(\''+row.id+'\',1);">冻结账户</a>' : '<a class="btn btn-success" style="padding: 6px 6px;margin-left: 6px;margin-top: 3px;" onclick="settingStatus(\''+row.id+'\',0);">解除冻结</a>';
				
				return '<a class="btn btn-primary" style="padding: 6px 6px;margin-left: 6px;margin-top: 3px;" onclick="rePayPawwrod('+row.id+');">初始化支付密码</a>'+
					   violationsBtn;
			}}
        ],
		viewrecords: true,
        height: getWindowsInnerHeight(),
        rowNum: 10,
		rowList : [10,30,50],
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
		tcuserStatus:{}
	},
	methods: {
		query: function () {
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{'keyword': vm.keyword},
                page:1
            }).trigger("reloadGrid");
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			
			location.href = "tcuser_add.html?id="+id;
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
		    url: "../tcuser/delete",
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

/**
 * 初始化支付密码
 * @param id
 */
function rePayPawwrod(id){
	confirm('确定要将该用户的支付密码初始化为: 123456 ？', function(){
		$.ajax({
			type: "POST",
		    url: "../tcuser/update_paypwd?id="+id,
		    success: function(r){
		    	if(r.code === 0){
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

//设置状态
function settingStatus(id,status){
	vm.tcuserStatus = {};
	vm.tcuserStatus.id = id;
	vm.tcuserStatus.status = status;
	
	var text = "确定要为该用户解除冻结操作吗?";
	if(status == 1){
		text = "确定要冻结该用户吗?";
	}
	
	confirm(text, function(){
		$.ajax({
			type: "POST",
		    url: "../tcuser/update",
		    data: JSON.stringify(vm.tcuserStatus),
		    success: function(r){
		    	if(r.code === 0){
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




