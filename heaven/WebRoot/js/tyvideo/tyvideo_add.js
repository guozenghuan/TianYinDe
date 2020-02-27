var id = T.p("id");
var vm = new Vue({
	el:'#rrapp',
	data:{
		title:"新增",
		tyVideo:{},
		mainImage:imageUpDefout,
		imageFile:null,
		mainVideo:null,
		videoFile:null
	},
	created: function() {
		if(id != null){
			this.title = "修改";
			this.getInfo(id)
		}
    },
	methods: {
		uploadMainImage: function(e){
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
                    vm.mainImage = dataURL;
                };
            }else{
            	alert("只能选择jpeg、jpg、png类型的图片！");
            }
		},
		uploadVideo: function(e){
			var that=this;
            var file = e.target.files[0];
            
            //获取文件类型
            var type = file.type;
            
            /*if(type == "video/avi" || type == "video/mpg" || type == "video/wmv" || type == "video/3gp"
            	 || type == "video/mov" || type == "video/mp4" || type == "video/asf" || type == "video/asx"
            		 || type == "video/flv" || type == "video/mkv"){*/
            if(type == "video/mp4"){
            	vm.videoFile = file;
            	
            	var reader = new FileReader();
                reader.readAsDataURL(file); // 读出 base64
                reader.onloadend = function () {
                    // 图片的 base64 格式, 可以直接当成 img 的 src 属性值        
                    var dataURL = reader.result;
                    //展示图片
                    vm.mainVideo = dataURL;
                };
            }else{
            	alert("只能选择mp4类型的视频！");
            }
		},
		getInfo: function(id){
			$.get("../tyvideo/info/"+id, function(r){
				//展示图片
                vm.mainVideo = r.tyVideo.path;
                vm.tyVideo = r.tyVideo;
            });
		},
		saveOrUpdate: function (event) {
			var url = vm.tyVideo.id == null ? "../tyvideo/save" : "../tyvideo/update";
			
			var formData = new FormData();
			//视频封面图
			formData.append("mainImage", vm.imageFile);
			//视频
			formData.append("videoFile", vm.videoFile);
			//信息
			vm.tyVideo.createtime = null;
			formData.append("tyVideo", JSON.stringify(vm.tyVideo));
			
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