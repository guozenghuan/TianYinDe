$(function () {
    $("#jqGrid").jqGrid({
        url: '../tyinvitationcashmoney/list',
        datatype: "json",
        colModel: [			
            { label: '订单号', name: 'orderNumber', width: 2},
			{ label: '发帖用户ID', name: 'modulUserId', width: 1},
			{ label: '宾客ID', name: 'userId', width: 1},
			{ label: '宾客姓名', name: 'name', width: 1},
			{ label: '奠金金额', name: 'money', width: 1},
			{ label: '支付状态', name: 'status',width: 1, formatter: function(value, options, row){
				return value === 0 ? '<span class="label label-success" style="padding: 6px 6px;">支付成功</span>' :'<span class="label label-danger" style="padding: 6px 6px;">未支付</span>';
			}},
			{ label: '时间', name: 'createtime', width: 1 ,formatter: function(value, options, row){
				return dateForStr(value);
			}}
			/*,
			{ label: '操作',  name: 'id',width: 2, formatter: function(value, options, row){
				return '<a class="btn btn-primary" style="padding: 6px 6px;margin-left: 6px;margin-top: 3px;" href="tyinvitationcashmoney_add.html?id='+row.id+'">编辑信息</a>'+
					   '<a class="btn btn-default" style="padding: 6px 6px;margin-left: 6px;margin-top: 3px;" onclick="delThisById(\''+row.id+'\');">删除</a>';
			}}*/
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
		orderNumber:"",
		userID:"",
		type:"fatie",
		status:"0"
	},
	methods: {
		query: function () {
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{'type': vm.type,'userID': vm.userID,'status':vm.status,"orderNumber":vm.orderNumber},
                page:1
            }).trigger("reloadGrid");
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			
			location.href = "tyinvitationcashmoney_add.html?id="+id;
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
		    url: "../tyinvitationcashmoney/delete",
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