var id = T.p("id");
var vm = new Vue({
	el:'#rrapp',
	data:{
		title:"新增",
		tyInvitationGift:{}
	},
	created: function() {
		if(id != null){
			this.title = "修改";
			this.getInfo(id)
		}
    },
	methods: {
		getInfo: function(id){
			$.get("../tyinvitationgift/info/"+id, function(r){
                vm.tyInvitationGift = r.tyInvitationGift;
            });
		},
		saveOrUpdate: function (event) {
			var url = vm.tyInvitationGift.id == null ? "../tyinvitationgift/save" : "../tyinvitationgift/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.tyInvitationGift),
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