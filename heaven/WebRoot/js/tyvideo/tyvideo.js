$(function () {
    $("#jqGrid").jqGrid({
        url: '../tyvideo/list',
        datatype: "json",
        colModel: [			
			{ label: '视频标题', name: 'title', width: 2},
			{ label: '视频详情', name: 'note', width: 2},
			/*{ label: '封面图路径', name: 'image', width: 50},*/
			{ label: '视频', name: 'path', width: 2, formatter: function(value, options, row){
				return '<video src="'+value+'" controls="controls" style="width: 194px;height: 120px;"></video>';
			}},
			/*{ label: '排序-默认顺序和id一样', name: 'sort', width: 50},*/
			{ label: '状态', name: 'status', width: 1, formatter: function(value, options, row){
				return value === 0 ? '<span class="label label-success" style="padding: 6px 6px;">显示中</span>' :'<span class="label label-danger" style="padding: 6px 6px;">已隐藏</span>';
			}},
			{ label: '创建时间', name: 'createtime', width: 1 ,formatter: function(value, options, row){
				return dateForStr(value);
			}},
			{ label: '操作',  name: 'id',width: 2, formatter: function(value, options, row){
				return '<a class="btn btn-warning" style="padding: 6px 6px;margin-left: 6px;margin-top: 3px;" onclick="updateSors('+row.id+',0);"><i class="fa fa-arrow-up" aria-hidden="true"></i> 上移</a>'+
					   '<a class="btn btn-warning" style="padding: 6px 6px;margin-left: 6px;margin-top: 3px;" onclick="updateSors('+row.id+',1);"><i class="fa fa-arrow-down" aria-hidden="true"></i> 下移</a>'+
					   '<a class="btn btn-primary" style="padding: 6px 6px;margin-left: 6px;margin-top: 3px;" href="tyvideo_add.html?id='+row.id+'">编辑信息</a>'+
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
		tyVideo:{}
	},
	methods: {
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			
			location.href = "tyvideo_add.html?id="+id;
		}
	}
});

//排序信息
function updateSors(id,type){
	vm.tyVideo = {};
	vm.tyVideo.id = id;
	vm.tyVideo.status = type;
	$.ajax({
		type: "POST",
	    url: "../tyvideo/updateMainSors",
	    data: JSON.stringify(vm.tyVideo),
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
		    url: "../tyvideo/delete",
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


