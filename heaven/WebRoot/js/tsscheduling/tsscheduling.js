$(function () {

	
    $("#jqGridTsUser").jqGrid({
        url: '../tsscheduling/list',
        datatype: "json",
        colModel: [	
	        { label: 'ID', name: 'tsUserId', width: 1},
			{ label: '公司', name: 'company', width: 1},
			{ label: '头像', name: 'portraitUrl', width: 1 ,formatter: function(value,options,row){
				return '<img class="img-circle" style="width: 70px;height: 70px;cursor: pointer;" src='+value+' alt="头像"/>';
			}},
			{ label: '姓名', name: 'name', width: 1},
			{ label: '手机号', name: 'phone', width: 1},
			{ label: '业务员账号', name: 'account', width: 1},
			{ label: '值班日期', name: 'scheduling', width: 1},
			{ label: '创建时间', name: 'createtime', width: 1},
			{ label: '操作',  name: 'id',width: 1, formatter: function(value, options, row){
				return '<a class="btn btn-primary" style="padding: 6px 6px;margin-left: 6px;margin-top: 3px;" href="tsscheduling_add.html?id='+row.id+'&tsuserid='+row.tsUserId+'">编辑排班</a>'+
					   '<a class="btn btn-default" style="padding: 6px 6px;margin-left: 6px;margin-top: 3px;" onclick="delThisById(\''+row.id+'\');">删除</a>';
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
        pager: "#jqGridPagerTsUser",
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
       // postData:{'keyword':'1','company_id':vm.company,'Integer':'6'},
        gridComplete:function(){
        	
        	
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
    
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		keyword:null,   //搜索关键字
		company_id:-1,  //公司ID
		companyAll:{}  //搜索下拉选项值
	},
	created: function() {
		//获取公司数据
	    getCompanyAllData(function(result){
	    	vm.companyAll = result.tsCompany;
	    	this.company_id = -1;
	    });
    },
	methods: {
		query: function () {
			$("#jqGridTsUser").jqGrid('setGridParam',{
				mtype:"get",
                postData:{'keyword': vm.keyword,'company': vm.company_id},
                page:1,
            }).trigger("reloadGrid");
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			
			location.href = "tsscheduling_add.html?id="+id;
		}
	}
});



//删除排班
function delThisById(id){
	if(id == null || id == undefined || id == "" || id == "null"){
		alert("要删除的信息出现异常，请刷新当前页面后重新操作!");
		return ;
	}
	
//	var ids = new Array();
//	ids.push(id);
	confirm('确定要删除选中的记录？', function(){
		$.ajax({
			type: "GET",
		    url: "../tsscheduling/delete/" + id,
		    success: function(r){
				if(r.code == 0){
					alert('操作成功');
					// $("#jqGridTsUser").trigger("reloadGrid");
					vm.query();
				}else{
					alert(r.msg);
				}
			}
		});
	});
}