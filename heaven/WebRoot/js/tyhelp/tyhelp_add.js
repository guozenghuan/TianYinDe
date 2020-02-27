var id = T.p("id");
var vm = new Vue({
	el:'#rrapp',
	data:{
		title:"新增",
		tyHelp:{}
	},
	created: function() {
		if(id != null){
			this.title = "修改";
			this.getInfo(id)
		}
    },
	methods: {
		getInfo: function(id){
			$.get("../tyhelp/info/"+id, function(r){
                vm.tyHelp = r.tyHelp;
            });
		},
		saveOrUpdate: function (event) {
			var url = vm.tyHelp.id == null ? "../tyhelp/save" : "../tyhelp/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.tyHelp),
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