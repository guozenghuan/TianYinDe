$(function () {
    $("#jqGrid").jqGrid({
        url: '../tystatistics/list',
        datatype: "json",
        colModel: [
            { label: '时间', name: 'createtime', width: 50 ,formatter: function(value, options, row){
				return dateForStr(value);
			}},
			{ label: '今日新增用户', name: 'todayUser', width: 50},
			{ label: '平台总用户', name: 'allUser', width: 50},
			{ label: '今日商品下单', name: 'todayCommodity', width: 50},
			{ label: '累计商品单量', name: 'allCommodity', width: 50},
			{ label: '今日服务下单', name: 'todayService', width: 50},
			{ label: '累计服务下单', name: 'allService', width: 50},
			{ label: '今日预约服务费', name: 'todayServiceMoney', width: 50},
			{ label: '累计预约服务费', name: 'allServiceMoney', width: 50},
			{ label: '今日服务套餐费', name: 'todayServicePrice', width: 50},
			{ label: '累计服务套餐费', name: 'allServicePrice', width: 50},
			{ label: '今日赠送平台礼物', name: 'todayGift', width: 50},
			{ label: '累计赠送平台礼物', name: 'allGift', width: 50},
			{ label: '今日收到奠金', name: 'todayCash', width: 50},
			{ label: '累计收到奠金', name: 'allCash', width: 50}
			/*,
			{ label: '操作',  name: 'id',width: 80, formatter: function(value, options, row){
				return '<a class="btn btn-primary" style="padding: 6px 6px;margin-left: 6px;margin-top: 3px;" href="tystatistics_add.html?id='+row.id+'">编辑信息</a>'+
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
		TyStatistics:{}
	},
	created: function() {
		this.getInfoNow();
    },
	methods: {
		getInfoNow: function(){
			$.get("../tystatistics/info_now", function(r){
                vm.TyStatistics = r.tyStatistics;
            });
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			
			location.href = "tystatistics_add.html?id="+id;
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
		    url: "../tystatistics/delete",
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