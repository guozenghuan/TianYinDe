var id = T.p("id");
var vm = new Vue({
	el:'#rrapp',
	data:{
		title:"新增",
		tcUser:{}
	},
	created: function() {
		if(id != null){
			this.title = "修改";
			this.getInfo(id)
		}
    },
	methods: {
		getInfo: function(id){
			$.get("../tcuser/info/"+id, function(r){
                vm.tcUser = r.tcUser;
            });
		},
		saveOrUpdate: function (event) {
			var url = vm.tcUser.id == null ? "../tcuser/save" : "../tcuser/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.tcUser),
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
		back: function (event) {
			history.go(-1);
		}
	}
});