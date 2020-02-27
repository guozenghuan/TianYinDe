var id = T.p("id");
var vm = new Vue({
	el:'#rrapp',
	data:{
		title:"新增",
		tyGift:{}
	},
	created: function() {
		if(id != null){
			this.title = "修改";
			this.getInfo(id)
		}
    },
	methods: {
		getInfo: function(id){
			$.get("../tygift/info/"+id, function(r){
                vm.tyGift = r.tyGift;
            });
		},
		saveOrUpdate: function (event) {
			var url = vm.tyGift.id == null ? "../tygift/save" : "../tygift/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.tyGift),
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