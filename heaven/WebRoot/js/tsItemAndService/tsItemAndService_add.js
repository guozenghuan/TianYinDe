var id = T.p("id");

var vm = new Vue({
	el:'#rrapp',
	data:{
		title: id ? '新增' : '编辑' ,
		tsUser:{},
		imagesSrc:imageUpDefout,
		imageFile:null,
		tsItemSort:[
            {
            	label: '类型1',
            	value: 1
            },
            {
            	label: '类型2',
            	value: 2
            },
        ],
        form: {
        	tsItemSort: '',
        	rate: null,
        	item: '',
        	unit: null,
        	price: null,
        	remark: null,
        },
	},
	created: function() {
    },
	methods: {
		getInfo: function(id){
			$.get("../tsuser/info/"+id, function(r){
				r.tsUser.password = null;
                vm.tsUser = r.tsUser;
                vm.imagesSrc = vm.tsUser.portraitUrl;
            });
		},
		saveOrUpdate: function (event) {
			var formData = new FormData();
			formData.append("image", vm.imageFile);
			formData.append("tsUser", JSON.stringify(vm.tsUser));
			
			vm.tsUser.createtime = null;
			
			var url = vm.tsUser.id == null ? "../tsuser/save" : "../tsuser/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: formData,
			    contentType:false,
			    processData:false,//这个很有必要，不然不行
			    dataType:"json",
			    mimeType:"multipart/form-data",
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