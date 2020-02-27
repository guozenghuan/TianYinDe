$(function () {
	//判断哪行该显示，该显示啥
	var createtimeFlag = false;//默认显示
	var serviceId = 0;
	
    $("#jqGrid").jqGrid({
        url: '../tsuser/list',
        datatype: "json",
        colModel: [	
	        { label: 'ID', name: 'id', width: 1},
			{ label: '类型', name: 'tsItemSort', width: 1},
			/*{ label: '头像', name: 'portraitUrl', width: 1 ,formatter: function(value,options,row){
				return '<img class="img-circle" style="width: 70px;height: 70px;cursor: pointer;" src='+value+' alt="头像"/>';
			}},*/
			{ label: '提成利率', name: 'rate', width: 1},
			{ label: '项目', name: 'item', width: 1},
			{ label: '单位', name: 'unit', width: 1},
			{ label: '单件成本', name: 'price', width: 1},
			{ label: '备注', name: 'remark', width: 1},
			{ label: '创建时间', name: 'createtime', width: 1 ,formatter: function(value, options, row){
				return dateForStr(value);
			}},
			{ label: '操作',  name: 'id',width: 1, formatter: function(value, options, row){
				return '<a class="btn btn-primary" style="padding: 6px 6px;margin-left: 6px;margin-top: 3px;" href="tsItemAndService_add.html?id='+row.id+'">编辑信息</a>'+
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
		company_id:-1,
		companyAll:{}
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
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{'keyword': vm.keyword,'company_id': vm.company_id},
                page:1
            }).trigger("reloadGrid");
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			
			location.href = "tsItemAndService_add.html?id="+id;
		},
	}
});

//派单给业务员
function setPaiDan(orderId,tsUserId){
	confirm('确定要派单给该业务员？', function(){
		var data = "orderId="+orderId+"&tsUserId="+tsUserId;
		$.ajax({
			type: "POST",
		    url: "../tyorderservice/pandan?"+data,
		    success: function(result){
		    	if(result.code == 0){
					//在iframe中调用父页面中定义的方法，关闭弹窗
			        window.parent.closeLayer();
				}else{
					alert(result.msg);
				}
			}
		});
	});
}

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
		    url: "../tsuser/delete",
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