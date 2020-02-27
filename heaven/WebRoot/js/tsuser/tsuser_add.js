var id = T.p("id");

var vm = new Vue({
	el:'#rrapp',
	data:{
		title:"新增",
		tsUser:{},
		imagesSrc:imageUpDefout,
		imageFile:null,
		companyAll:{}
	},
	created: function() {
		//获取公司数据
	    getCompanyAllData(function(result){
	    	vm.companyAll = result.tsCompany;
	    	if(id == null){
	    		vm.tsUser.companyId = vm.companyAll[0].id;
			}else{
				vm.title = "修改";
				vm.getInfo(id);
			}
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