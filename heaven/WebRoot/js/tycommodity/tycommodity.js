$(function () {
    $("#jqGrid").jqGrid({
        url: '../tycommodity/list',
        datatype: "json",
        colModel: [
			{ label: '主图', name: 'mainImage', width: 2 ,formatter: function(value,options,row){
				return '<img style="width: 194px;height: 120px;cursor: pointer;" src='+value+' alt="主图"/>';
			}},
			{ label: '名称', name: 'title', width: 2},
			{ label: '分类', name: 'categotyId', width: 2, formatter: function(value, options, row){
				var list=vm.categoryList;
				  for(var j=0;j<list.length;j++){
					  if(value==list[j].id){
						  return  list[j].name;
					  } 
					  if(!value){
						  return  "-";
					  } 
				 }
			}},
			{ label: '成本', name: 'cost', width: 1},
			{ label: '行政成本', name: 'administratorCost', width: 1},
			{ label: '价格', name: 'price', width: 1},
			{ label: '状态', name: 'status',width: 1, formatter: function(value, options, row){
				return value === 0 ? '<span class="label label-success" style="padding: 6px 6px;">显示中</span>' :'<span class="label label-danger" style="padding: 6px 6px;">已下架</span>';
			}},
			{ label: '创建时间', name: 'createtime', width: 1 ,formatter: function(value, options, row){
				return dateForStr(value);
			}},
			{ label: '操作',  name: 'id',width: 2, formatter: function(value, options, row){
				var violationsBtn = row.status === 0 ? '<a class="btn btn-danger" style="padding: 6px 6px;margin-left: 6px;margin-top: 3px;" onclick="settingStatus(\''+row.id+'\',1);">下架</a>' : '<a class="btn btn-success" style="padding: 6px 6px;margin-left: 6px;margin-top: 3px;" onclick="settingStatus(\''+row.id+'\',0);">上架</a>';
				
				return '<a class="btn btn-primary" style="padding: 6px 6px;margin-left: 6px;margin-top: 3px;" href="tycommodity_add.html?id='+row.id+'">编辑信息</a>'+
						violationsBtn+
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
		TyCommodity:{},
		TyCommodityStatus:{},
		title:null,
		categoryList:[],
	},
	created: function() {
		this.getCategotyInfo();
    },
	methods: {
		query: function () {
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{'title': vm.title},
                page:1
            }).trigger("reloadGrid");
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			
			location.href = "tycommodity_add.html?id="+id;
		},
		//获取分类信息
		getCategotyInfo: function(){
			$.get("../syscategory/list?page=1&limit=999", function(r){
				if(r.page.list){
					vm.categoryList = r.page.list;
					 /*for(var j=0;j<listArr.lenth;j++){
						 vm.categoryList.push({
							id: listArr[j].id,
						 	name:listArr[j].name
						 });
						}*/
				};
				console.log(vm.categoryList);
			});
		},
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
		    url: "../tycommodity/delete",
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

//设置商品状态
function settingStatus(id,status){
	vm.TyCommodityStatus = {};
	vm.TyCommodityStatus.id = id;
	vm.TyCommodityStatus.status = status;
	
	$.ajax({
		type: "POST",
	    url: "../tycommodity/update_status",
	    data: JSON.stringify(vm.TyCommodityStatus),
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




