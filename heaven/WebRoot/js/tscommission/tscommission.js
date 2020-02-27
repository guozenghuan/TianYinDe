

$(function () {
    $("#jqGrid").jqGrid({
        url: '../tscommission/check/commission',
        datatype: "json",
        colModel: [
            { label: 'id', name: 'id', width: 1},
			{ label: '业务员ID', name: 'tsUserId', width: 2},
			{ label: '公司名称', name: 'company', width: 1},
			{ label: '姓名', name: 'name', width: 3},
			{ label: '手机号', name: 'phone', width: 3},
			{ label: '业务员账号', name: 'account', width: 3},
//			{ label: '添加时间', name: 'createtime', width: 3},
			{ label: '单号', name: 'number', width: 2},
			{ label: '提成', name: 'commission', width: 2},
			{ label: '业务员本月累计提成', name: 'commissionTotal', width: 3},
			{ label: '创建日期', name: 'createtime', width: 3},
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
        	time: vm.time,
        	account: vm.account,
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
	data() {
		return {
			account: '',
			time: '',
			ywy:'',
			ywyArr: [],
		}
	},
	created: function() {
		const self = this;
		$.ajax({
			dataType: 'json',
			contentType: 'application/json;charset=utf-8',
		    url: '../tscommission/info/name',
		    success: function(r){
				self.ywyArr = r;
			}
		});
		
	},
	methods: {
		query: function() {
			const self = this;
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{
                	sta: self.auditStatus,
                	tsUserId: 0,
                	time: this.time,
                	account: this.account,
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
		    url: '../tscommission/delete',
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