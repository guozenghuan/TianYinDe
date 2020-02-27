$(function () {
    $("#jqGrid").jqGrid({
        url: '../tyinvitationcomment/list',
        datatype: "json",
        colModel: [			
			/*{ label: '模板', name: 'modulId', width: 50},*/
			{ label: '发帖用户ID', name: 'modulUserId', width: 1},
			{ label: '宾客ID', name: 'userId', width: 1},
			{ label: '宾客姓名', name: 'name', width: 1},
			{ label: '评论信息', name: 'note', width: 3},
			{ label: '时间', name: 'createtime', width: 1 ,formatter: function(value, options, row){
				return dateForStr(value);
			}},
			{ label: '操作',  name: 'id',width: 2, formatter: function(value, options, row){
				return '<a class="btn btn-primary" style="padding: 6px 6px;margin-left: 6px;margin-top: 3px;" href="tyinvitationcomment_add.html?id='+row.id+'">编辑信息</a>'+
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
		userID:"",
		type:"fatie"
	},
	methods: {
		query: function () {
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{'type': vm.type,'userID': vm.userID},
                page:1
            }).trigger("reloadGrid");
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			
			location.href = "tyinvitationcomment_add.html?id="+id;
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
		    url: "../tyinvitationcomment/delete",
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