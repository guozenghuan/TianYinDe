$(function () {
    $("#jqGrid").jqGrid({
        url: '../tcuserwithdrawal/list',
        datatype: "json",
        colModel: [
			{ label: '用户ID', name: 'userId', width: 1},
			{ label: '单号', name: 'orderNumber', width: 1},
			{ label: '提现金额', name: 'money', width: 1},
			{ label: '申请时间', name: 'createtime', width: 1 ,formatter: function(value, options, row){
				return dateForStr(value);
			}},
			{ label: '状态', name: 'status', width: 1,formatter: function(value, options, row){
				//0待支付，1已支付
				if(value=="shenqing"){
					return '<span class="label label-danger" style="padding: 6px 6px;background-color: #f0ad4e;border-color: #eea236;"><i class="fa fa-spinner"></i> 审核中</span>';
				}else if(value=="tongguo"){
					return '<span class="label label-success" style="padding: 6px 6px;"><i class="fa fa-check"></i> 审核通过</span>';
				}else{
					return '<span class="label label-danger" style="padding: 6px 6px;"><i class="fa fa-exclamation-triangle"></i> 审核失败</span>';
				}
			}},
			{ label: '备注', name: 'note', width: 2},
			{ label: '操作',  name: 'id',width: 2, formatter: function(value, options, row){
				if(row.status == "shenqing"){
					return '<a class="btn btn-primary" style="padding: 6px 6px;margin-left: 6px;margin-top: 3px;" onclick="seetingShenHe('+row.id+','+row.userId+',1);">审核通过</a>'+
					   '<a class="btn btn-default" style="padding: 6px 6px;margin-left: 6px;margin-top: 3px;" onclick="seetingShenHe('+row.id+','+row.userId+',0);">提现失败</a>';
				}else{
					return '<a class="btn btn-primary" style="background-color: #9470f2;padding: 6px 6px;margin-left: 6px;margin-top: 3px;" onclick="seetingShenHeXiangQing('+row.id+','+row.userId+',1);">查看信息</a>';
				}
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
		keyword:"",
		status:"-1",
		userWechat:{},
		wechatShoukuan:"../../../statics/images/wechat.png"
	},
	methods: {
		query: function () {
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'keyword': vm.keyword,"status":vm.status},
                page:1
            }).trigger("reloadGrid");
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			
			location.href = "tcuserwithdrawal_add.html?id="+id;
		}
	}
});

/**
 * 操作
 * @param id
 * @param type
 */
function seetingShenHe(id,userId,type){
	vm.wechatShoukuan = "../../../statics/images/wechat.png";
	$("#wechatMsgId").hide();
	var commitBtnText = "审核失败";
	var height = "30%";
	if(type == 1){
		commitBtnText = "审核通过";
		$("#tixianNoteId").val("提现审核通过,已通过微信转账,请注意查收!");
		height = "70%";
		
		//查询用户微信收款码信息
		$.get("../tcuserwithdrawal/info_user_wechat/"+userId, function(r){
           vm.userWechat = r.data.user;
           if(r.data.wechatStatus == 0){//没有上传
        	   vm.wechatShoukuan = "../../../statics/images/wechat.png";
           }else{
        	   vm.wechatShoukuan = validateImage(r.data.wechat);
           }
        });
		
		$("#wechatMsgId").show();
	}else{
		$("#tixianNoteId").val("提现审核失败,微信收款码有误,金额已返回到钱包中!!");
		$("#wechatMsgId").hide();
	}
	layer.open({
		type: 1,
		skin: 'layui-layer-molv',
		title: "审核提现订单",
		area: ['50%', height],
		shade: 0,
		shadeClose: false,
		offset:"10px",
		content: jQuery("#topUpTree_tixian"),
		btn: [commitBtnText, '取消'],
		btn1: function (index) {//保存信息
			var data = "type="+type+"&id="+id+"&note="+$("#tixianNoteId").val();
			$.ajax({
				type: "POST",
			    url: "../tcuserwithdrawal/setting?"+data,
			    dataType: "json",
			    success: function(result){
					if(result.code == 0){//登录成功
						$("#jqGrid").trigger("reloadGrid");
						alert("操作成功!");
						layer.close(index);
					}else{
						alert(result.msg);
					}
				}
			});
        }
	});
}

/**
 * 查看信息
 * @param id
 * @param type
 */
function seetingShenHeXiangQing(id,userId,type){
	vm.wechatShoukuan = "../../../statics/images/wechat.png";
	
	//查询用户微信收款码信息
	$.get("../tcuserwithdrawal/info_user_wechat/"+userId, function(r){
       vm.userWechat = r.data.user;
       if(r.data.wechatStatus == 0){//没有上传
    	   vm.wechatShoukuan = "../../../statics/images/wechat.png";
       }else{
    	   vm.wechatShoukuan = validateImage(r.data.wechat);
       }
    });
	
	layer.open({
		type: 1,
		skin: 'layui-layer-molv',
		title: "收款信息",
		area: ['50%', '70%'],
		shade: 0,
		shadeClose: false,
		offset:"10px",
		content: jQuery("#topUpTree_tixian_xiangqing")
	});
}

//验证图片链接是否有效
function validateImage(url){
	 try {
		 var xmlHttp ;
	     if (window.ActiveXObject)
	      {
	       xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	      }
	      else if (window.XMLHttpRequest)
	      {
	       xmlHttp = new XMLHttpRequest();
	      } 
	     xmlHttp.open("Get",url,false);
	     xmlHttp.send();
	     if(xmlHttp.status==404){
	    	 return "../../../statics/images/wechat.png";	 
	     }else{
	    	 return url;	 
	     }
	} catch (e) {
		return "../../../statics/images/wechat.png";	
	}
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
		    url: "../tcuserwithdrawal/delete",
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