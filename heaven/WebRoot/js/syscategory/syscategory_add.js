var id = T.p("id");
var vm = new Vue({
	el:'#rrapp',
	data:{
		title:"新增",
		sysCategory:{},
		mainImage:imageUpDefout,
		imageFile:null,
	},
	created: function() {
		if(id != null){
			this.title = "修改";
			this.getInfo(id)
		}
    },
	methods: {
		getInfo: function(id){
			$.get("../syscategory/info/"+id, function(r){
                vm.sysCategory = r.sysCategory;
                vm.mainImage = r.sysCategory.iconUrl;
            });
		},
/*		saveOrUpdate: function (event) {
			var url = vm.sysCategory.id == null ? "../syscategory/save" : "../syscategory/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.sysCategory),
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
		},*/
		
		saveOrUpdate: function (event) {
			var url = vm.sysCategory.id == null ? "../syscategory/saveAddImg" : "../syscategory/updateImg";
			
			var formData = new FormData();
			//主图
			formData.append("mainImage", vm.imageFile);
			//信息
			formData.append("sysCategory", JSON.stringify(vm.sysCategory));
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
		},
		uploadMainImage: function(e){
			var that=this;
            var file = e.target.files[0];
            
            //获取文件类型
            var type = file.type;
            if(type == "image/jpeg" || type == "image/jpg" || type == "image/png"){
//            	alert(type);
            	vm.imageFile = file;
            	
            	var reader = new FileReader();
                reader.readAsDataURL(file); // 读出 base64
                reader.onloadend = function () {
                    // 图片的 base64 格式, 可以直接当成 img 的 src 属性值        
                    var dataURL = reader.result;
                    //展示图片
                    vm.mainImage = dataURL;
                };
            }else{
            	alert("只能选择jpeg、jpg、png类型的图片！");
            }
		},
	}
});