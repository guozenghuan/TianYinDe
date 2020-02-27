var id = T.p("id");

var vm = new Vue({
	el:'#rrapp',
	data:{
		title:"新增",
		tsUser:{},   //用户信息
		stScheduling: null,
		imagesSrc:imageUpDefout,  //图片
		imageFile:null,
		companyAll:{}         //公司下拉选项
	},
	created: function() {
		//获取公司数据
		getCompanyAllData(function(result){
	    	vm.companyAll = result.tsCompany;
	    	vm.getInfo(id);
	    });
	    // 获取用户信息
		this.getInfo();
    },
	methods: {
		getQueryVariable: function(variable){
	       var query = window.location.search.substring(1);
	       var vars = query.split("&");
	       for (var i=0;i<vars.length;i++) {
	               var pair = vars[i].split("=");
	               if(pair[0] == variable){return pair[1];}
	       }
	       return(false);
		},
		//获取用户信息
		getInfo: function(){
			let userId = this.getQueryVariable('id')
			$.get("../tsscheduling/info/"+userId, function(r){
				vm.tsUser = r.tsScheduling;
//				r.tsUser.password = null;
//                vm.tsUser = r.tsUser;   //用户全部信息
//                vm.imagesSrc = vm.tsUser.portraitUrl;  //图片
            });
		},
		//编辑确定保存
		saveOrUpdate: function (event) {
			var url ='../tsscheduling/add',
				tsuserid = this.getQueryVariable('tsuserid'),
				scheduling = vm.stScheduling;
			console.log(scheduling);
			$.ajax({
				type: "GET",
			    url: url + '?tsUserId=' + tsuserid + '&tsScheduling=' + scheduling,
			    contentType:false,
			    processData:false,//这个很有必要，不然不行
			    dataType:"json",
			    mimeType:"multipart/form-data",
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.back();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		//返回
		back: function (event) {
			history.go(-1);
		}
	}
});