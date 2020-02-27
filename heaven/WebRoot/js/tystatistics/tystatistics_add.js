var id = T.p("id");
var vm = new Vue({
	el:'#rrapp',
	data:{
		title:"新增",
		tyStatistics:{}
	},
	created: function() {
		if(id != null){
			this.title = "修改";
			this.getInfo(id)
		}
    },
	methods: {
		getInfo: function(id){
			$.get("../tystatistics/info/"+id, function(r){
                vm.tyStatistics = r.tyStatistics;
            });
		},
		saveOrUpdate: function (event) {
			var url = vm.tyStatistics.id == null ? "../tystatistics/save" : "../tystatistics/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.tyStatistics),
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