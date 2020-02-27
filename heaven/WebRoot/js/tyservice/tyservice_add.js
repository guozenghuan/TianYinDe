var id = T.p("id");
var vm = new Vue({
	el:'#rrapp',
	data:{
		title:"新增",
		tyService:{}
	},
	created: function() {
		if(id != null){
			this.title = "修改";
			this.getInfo(id)
		}
    },
	methods: {
		getInfo: function(id){
			$.get("../tyservice/info/"+id, function(r){
                vm.tyService = r.tyService;
            });
		},
		saveOrUpdate: function (event) {
			var url = vm.tyService.id == null ? "../tyservice/save" : "../tyservice/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.tyService),
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