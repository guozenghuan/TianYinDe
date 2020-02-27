var vm = new Vue({
	el:'#rrapp',
	data:{
		imageFile:null,
		imagesSrc:imageUpDefout,
		tsUser:{}
	},
	created: function(){
		$.get("../../../salesman/user/user_msg", function(r){
			vm.tsUser = r.data;
			vm.imagesSrc = r.data.portraitUrl;
        });
	},
	methods: {
		uploadImage: function(e){
			var that=this;
            var file = e.target.files[0];
            
            //获取文件类型
            var type = file.type;
            if(type == "image/jpeg" || type == "image/jpg" || type == "image/png"){
            	vm.imageFile = file;
            	
            	var reader = new FileReader();
                reader.readAsDataURL(file); // 读出 base64
                reader.onloadend = function () {
                    // 图片的 base64 格式, 可以直接当成 img 的 src 属性值        
                    var dataURL = reader.result;
                    //展示图片
                    vm.imagesSrc = dataURL;
                };
            }else{
            	alert("只能选择jpeg、jpg、png类型的图片！");
            }
		},
		saveOrUpdate: function (event) {
			var formData = new FormData();
			formData.append("image", vm.imageFile);
			formData.append("tsUser", JSON.stringify(vm.tsUser));
			
			vm.tsUser.createtime = null;
			
			$.ajax({
				type: "POST",
			    url: "../../../salesman/user/tsuser_update",
			    data: formData,
			    contentType:false,
			    processData:false,//这个很有必要，不然不行
			    dataType:"json",
			    mimeType:"multipart/form-data",
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功');
					}else{
						alert(r.msg);
					}
				}
			});
		},
		clearWechat: function(){
			confirm('确定要清除授权的微信号信息？', function(){
				$.ajax({
					type: "POST",
				    url: "../../../salesman/user/clear_wechat",
				    dataType:"json",
				    success: function(r){
				    	if(r.code === 0){
				    		$.get("../../../salesman/user/user_msg", function(r){
								vm.tsUser = r.data;
								vm.imagesSrc = r.data.portraitUrl;
					        });
							alert('操作成功');
						}else{
							alert(r.msg);
						}
					}
				});
			});
		}
	}
});
