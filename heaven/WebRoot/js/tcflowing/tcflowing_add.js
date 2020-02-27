var id = T.p("id");
var vm = new Vue({
	el:'#rrapp',
	data:{
		title:"新增",
		tcFlowing:{}
	},
	created: function() {
		if(id != null){
			this.title = "修改";
			this.getInfo(id)
		}
    },
	methods: {
		getInfo: function(id){
			$.get("../tcflowing/info/"+id, function(r){
                vm.tcFlowing = r.tcFlowing;
            });
		},
		saveOrUpdate: function (event) {
			var url = vm.tcFlowing.id == null ? "../tcflowing/save" : "../tcflowing/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.tcFlowing),
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