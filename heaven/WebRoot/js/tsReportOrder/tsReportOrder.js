

$(function () {
    $("#jqGrid").jqGrid({
        url: '../tsReportOrder/orderList',
        datatype: "json",
        colModel: [
            { label: 'id', name: 'id', width: 1},
			{ label: '业务员ID', name: 'tsUserId', width: 2},
			{ label: '用户ID', name: 'userId', width: 1},
			{ label: '单号', name: 'number', width: 3},
		//	{ label: '下单时间', name: 'createtime', width: 3},
			{ label: '订单提交时间', name: 'ordertime', width: 3},
//			{ label: '添加时间', name: 'createtime', width: 3},
			{ label: '服务总价', name: 'total', width: 2},
			{ label: '公司成本', name: 'auditor', width: 2},
			{ label: '公司利润', name: 'companyProfit', width: 2},
			{ label: '业务员提成', name: 'profit', width: 2},
			{ label: '审核状态', name: 'status', width: 1, formatter: function(value, options, row) {
				if (value == 2) {
					return '审核中'
				}
				else if (value == 0) {
					return '审核通过'
				}
				else if (value == 1) {
					return '审核未通过'
				}
			}},
			{ label: '审核时间', name: 'checktime', width: 3},
			{ label: '操作',  name: 'id',width: 2, formatter: function(value, options, row){
				return '<a class="btn btn-primary" style="padding: 6px 6px;margin-left: 6px;margin-top: 3px;" href="tsReportOrder_audit.html?id='+row.id+'&tsUserId='+row.tsUserId+'&number='+row.number+'">审核</a>'+
					   '<a class="btn btn-default" style="padding: 6px 6px;margin-left: 6px;margin-top: 3px;" onclick="delThisById(\''+row.tsUserId+'\',\''+row.number+'\');">删除</a>';
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
        postData: {
        	sta: 2,
        	tsUserId: 0,
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order",
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
		auditStatus: 2,
	},
	created: function() {
		// this.query();
	},
	methods: {
		query: function() {
			const self = this;
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{
                	sta: self.auditStatus,
                	tsUserId: 0,
            	},
                page:1
            }).trigger("reloadGrid");
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			
			location.href = "modulmusic_add.html?id="+id;
		}
	}
});

//删除
function delThisById(tsUserId, number){
	if(!tsUserId){
		alert("要删除的信息出现异常，请刷新当前页面后重新操作!");
		return ;
	}
	
	const data = {
		tsUserId,
		number,
	}
	confirm('确定要删除选中的记录？', function(){
		$.ajax({
			dataType: 'json',
			contentType: 'application/json;charset=utf-8',
		    url: `../tsReportOrder/orderDelete`,
    		data,
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