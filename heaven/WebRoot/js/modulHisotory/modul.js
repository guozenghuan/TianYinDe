$(function () {
	
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		modulData:{}
	},
	created: function(){
		this.info();
	},
	methods: {
		info: function (){
			$.get("../modul/list?limit=9&page=1", function(r){
				var pages = Math.round(r.page.totalCount/8);
				if(pages == 0){
					pages = 1;
				}
				$("#jqPaginator").jqPaginator({
			        totalPages: pages,
			        visiblePages: 8,
			        currentPage: 1,
			        first: '<li class="first"><a href="javascript:void(0);">首页<\/a><\/li>',
			        prev: '<li class="prev"><a href="javascript:void(0);"><i class="arrow arrow2"><\/i>上一页<\/a><\/li>',
			        next: '<li class="next"><a href="javascript:void(0);">下一页<i class="arrow arrow3"><\/i><\/a><\/li>',
			        last: '<li class="last"><a href="javascript:void(0);">末页<\/a><\/li>',
			        page: '<li class="page"><a href="javascript:void(0);">{{page}}<\/a><\/li>',
			        onPageChange: function (n) {
			        	$.get("../modul/list?limit=9&page="+n, function(rs){
			        		vm.modulData = r.page.list;
			            });
			            
			        	$("#demo2-text").html("总共 " + r.page.totalCount + " 条数据");
			        }
			    });
		    });
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			
			location.href = "modul_add.html?id="+id;
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
		    url: "../modul/delete",
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