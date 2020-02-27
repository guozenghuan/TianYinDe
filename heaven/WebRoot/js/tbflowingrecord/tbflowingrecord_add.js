var id = T.p("id");
var vm = new Vue({
	el:'#rrapp',
	data:{
		title:"新增",
		tbFlowingRecord:{}
	},
	created: function() {
		if(id != null){
			this.title = "修改";
			this.getInfo(id)
		}
    },
	methods: {
		getInfo: function(id){
			$.get("../tbflowingrecord/info/"+id, function(r){
                vm.tbFlowingRecord = r.tbFlowingRecord;
            });
		},
		saveOrUpdate: function (event) {
			var url = vm.tbFlowingRecord.id == null ? "../tbflowingrecord/save" : "../tbflowingrecord/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.tbFlowingRecord),
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