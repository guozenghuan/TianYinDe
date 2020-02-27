$(function () {
    $("#jqGrid").jqGrid({
        url: '../syscategory/list',
        datatype: "json",
        colModel: [			
			{ label: '类目名称', name: 'name', width: 50},
//			{ label: '类目关键字，以JSON数组格式', name: 'keywords', width: 50},
			{ label: '类目广告语介绍', name: 'desc', width: 80},
//			{ label: '父类目ID', name: 'pid', width: 50},
//			{ label: '类目图标', name: 'iconUrl', width: 50},
			{ label: '类目图标', name: 'iconUrl', width: 80 ,formatter: function(value,options,row){
				return '<img class="img-circle" style="width: 70px;height: 70px;cursor: pointer;" src='+value+' />';
			}},
//			{ label: '类目图片', name: 'picUrl', width: 50},
//			{ label: '', name: 'level', width: 50},
//			{ label: '排序', name: 'sortOrder', width: 50},
//			{ label: '创建时间', name: 'addTime', width: 50},
//			{ label: '更新时间', name: 'updateTime', width: 50},
//			{ label: '逻辑删除', name: 'deleted', width: 50},
			{ label: '操作',  name: 'id',width: 80, formatter: function(value, options, row){
				return '<a class="btn btn-primary" style="padding: 6px 6px;margin-left: 6px;margin-top: 3px;" href="syscategory_add.html?id='+row.id+'">编辑信息</a>'+
					   '<a class="btn btn-default" style="padding: 6px 6px;margin-left: 6px;margin-top: 3px;" onclick="delThisById(\''+row.id+'\');">删除</a>';
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
		
	},
	methods: {
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			
			location.href = "syscategory_add.html?id="+id;
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
		    url: "../syscategory/delete",
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