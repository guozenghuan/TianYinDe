var id = T.p("id");
var vm = new Vue({
	el:'#rrapp',
	data:{
		title:"新增",
		tyInvitationToinvite:{}
	},
	created: function() {
		if(id != null){
			this.title = "修改";
			this.getInfo(id)
		}
    },
	methods: {
		getInfo: function(id){
			$.get("../tyinvitationtoinvite/info/"+id, function(r){
                vm.tyInvitationToinvite = r.tyInvitationToinvite;
            });
		},
		saveOrUpdate: function (event) {
			var url = vm.tyInvitationToinvite.id == null ? "../tyinvitationtoinvite/save" : "../tyinvitationtoinvite/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.tyInvitationToinvite),
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