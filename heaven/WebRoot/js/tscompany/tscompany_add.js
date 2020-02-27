var id = T.p("id");
var vm = new Vue({
	el:'#rrapp',
	data:{
		title:"新增",
		tsCompany:{}
	},
	created: function() {
		if(id != null){
			this.title = "修改";
			this.getInfo(id)
		}
    },
	methods: {
		getInfo: function(id){
			$.get("../tscompany/info/"+id, function(r){
                vm.tsCompany = r.tsCompany;
            });
		},
		saveOrUpdate: function (event) {
			var url = vm.tsCompany.id == null ? "../tscompany/save" : "../tscompany/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.tsCompany),
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