var id = T.p("id");
var vm = new Vue({
	el:'#rrapp',
	data:{
		title:"新增",
		tyOrderCommodity:{}
	},
	created: function() {
		if(id != null){
			this.title = "修改";
			this.getInfo(id)
		}
    },
	methods: {
		getInfo: function(id){
			$.get("../tyordercommodity/info/"+id, function(r){
                vm.tyOrderCommodity = r.tyOrderCommodity;
            });
		},
		saveOrUpdate: function (event) {
			var url = vm.tyOrderCommodity.id == null ? "../tyordercommodity/save" : "../tyordercommodity/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.tyOrderCommodity),
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