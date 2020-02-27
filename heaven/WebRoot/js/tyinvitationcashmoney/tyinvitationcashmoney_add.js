var id = T.p("id");
var vm = new Vue({
	el:'#rrapp',
	data:{
		title:"新增",
		tyInvitationCashmoney:{}
	},
	created: function() {
		if(id != null){
			this.title = "修改";
			this.getInfo(id)
		}
    },
	methods: {
		getInfo: function(id){
			$.get("../tyinvitationcashmoney/info/"+id, function(r){
                vm.tyInvitationCashmoney = r.tyInvitationCashmoney;
            });
		},
		saveOrUpdate: function (event) {
			var url = vm.tyInvitationCashmoney.id == null ? "../tyinvitationcashmoney/save" : "../tyinvitationcashmoney/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.tyInvitationCashmoney),
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