$(function () {
	 //编辑器
	 $('.summernote').summernote({
		 	width: 640,
		 	height: 960,
	        focus:true,
	        lang: 'zh-CN',
	        placeholder : '请输入文章内容', // 提示
	        // summernote自定义配置
	        toolbar: [
	          ['operate', ['undo','redo']],
	          ['magic',['style','fontname']],
	          ['style', ['bold', 'italic', 'underline', 'clear']],
	          ['para', ['height','fontsize','ul', 'ol', 'paragraph']],
	          ['font', ['strikethrough', 'superscript', 'subscript']],
	          ['color', ['color']],
	          ['insert',['picture','video','link','table','hr']],
	          ['layout',['fullscreen','codeview']],
	        ]
	 		,
	        callbacks: {
                // 文件上传
                onImageUpload: function(files) {
                    var url = null;
                    if (window.createObjectURL != undefined) {
                        url = window.createObjectURL(files[0])
                    } else if (window.URL != undefined) {
                        url = window.URL.createObjectURL(files[0])
                    } else if (window.webkitURL != undefined) {
                        url = window.webkitURL.createObjectURL(files[0])
                    }
                    
                    vm.textImages.append(url,files[0]);
                    
                    $('.summernote').summernote('insertImage', url,'img');
                }
            }
	 });
	 
});

var id = T.p("id");
var vm = new Vue({
	el:'#rrapp',
	data:{
		title:"新增",
		tyCommodity:{},
		mainImage:imageUpDefout,
		imageFile:null,
		secondImages:new Array(),
		textImages:new FormData(),
		secondRvePathList:"",
		categoryList:[],
	},
	created: function() {
		if(id != null){
			this.title = "修改";
			this.getInfo(id)
		}else{
			this.tyCommodity.status = 0;
		};
		//获取分类信息
		this.getCategotyInfo();
		
		
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
		uploadsecondImages: function(e){
			var that=this;
            var files = e.target.files;
            var file = files[files.length-1];
            
            //获取文件类型
            var type = file.type;
            if(type == "image/jpeg" || type == "image/jpg" || type == "image/png"){
            	/*vm.imageFile = file;*/
            	
            	var reader = new FileReader();
                reader.readAsDataURL(file); // 读出 base64
                reader.onloadend = function () {
                    // 图片的 base64 格式, 可以直接当成 img 的 src 属性值        
                    var dataURL = reader.result;
                    $("#defoultSecond").hide();
                    //展示图片
                    $("#secondImages").append('<img class="deleteSecondImg" onclick="deleteSecondImage(this);" name="'+file.name+'" src="'+dataURL+'" style="width: 194px;height: 120px;margin-right: 10px;cursor: pointer;">');
                    //存储图片
                    vm.secondImages.push(file);
                };
            }else{
            	alert("只能选择jpeg、jpg、png类型的图片！");
            }
		},
		getInfo: function(id){
			$.get("../tycommodity/info/"+id, function(r){
				console.log(r);
				vm.tyCommodity = r.tyCommodity;
				console.log(vm.tyCommodity);
				//设置主图
                vm.mainImage = vm.tyCommodity.mainImage;
                //设置轮播图
                var secondImages = vm.tyCommodity.secondImages;
                if(secondImages!=undefined && secondImages!=null && secondImages!="" && secondImages.indexOf(",") != -1){
                	var secondImagesSpli = secondImages.split(",");
                	for(var i=0;i<secondImagesSpli.length;i++){
                		if(secondImagesSpli[i] != undefined && secondImagesSpli[i] != null && secondImagesSpli[i] != ""){
                			$("#defoultSecond").hide();
                            //展示图片
                            $("#secondImages").append('<img class="deleteSecondImg" onclick="deleteSecondImage(this);" name="uploadOk" src="'+secondImagesSpli[i]+'" style="width: 194px;height: 120px;margin-right: 10px;cursor: pointer;">');
                		}
                	}
                }
				
                //设置内容
    			$('.summernote').summernote('code',vm.tyCommodity.text);
            });
		},
		//获取分类信息
		getCategotyInfo: function(){
			$.get("../syscategory/list?page=1&limit=999", function(r){
				if(r.page.list){
					vm.categoryList = r.page.list;
					 /*for(var j=0;j<listArr.lenth;j++){
						 vm.categoryList.push({
							id: listArr[j].id,
						 	name:listArr[j].name
						 });
						}*/
				};
//				console.log(vm.categoryList);
			});
		},
		saveOrUpdate: function (event) {
			var url = vm.tyCommodity.id == null ? "../tycommodity/save" : "../tycommodity/update";
			
			var formData = new FormData();
			//主图
			formData.append("mainImage", vm.imageFile);
			//存储轮播图
			secondImagesAddFormData(formData);
			
			//信息
			//存储内容
			var textHtml = $('.summernote').summernote('code');
			vm.tyCommodity.text = textHtml;
			vm.tyCommodity.createtime = null;
			formData.append("tyCommodity", JSON.stringify(vm.tyCommodity));
			
			//存储内容图片
			//过滤内容中的图片
			var imgSrcs = getimgsrc(textHtml);
			for(var i=0;i<imgSrcs.length;i++){
				var forDataFile = vm.textImages.get(imgSrcs[i]);
				//存储内容图片
				formData.append(imgSrcs[i], forDataFile);
			}
			
			//存储删除的轮播图路径
			if(url == "../tycommodity/update"){
				formData.append("sedRvePathList", vm.secondRvePathList);
			}
			
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


//删除自身标签
function deleteSecondImage(o){
	$(o).remove();
	
	//判断删除的是否是已经上传的图片
	var removeName = $(o).attr("name");
	if(removeName.trim() == "uploadOk"){
		vm.secondRvePathList = vm.secondRvePathList + $(o).attr("src") + ",";
	}
	
	//判断是否是最后一张则显示原始上传图片
	if($("#secondImages").children().length == 1){
		$("#defoultSecond").show();
	}
}
//存储轮播图
function secondImagesAddFormData(formData){
	//获取存储的file
	var files = vm.secondImages;
	
	//获取展示中的图片信息
	var imgs = $(".deleteSecondImg");
	for(var i=0;i<imgs.length;i++){
		var imgName = $(imgs[i]).attr("name");
		for(var ii=0;ii<files.length;ii++){
			var fileName = files[ii].name;
			if(imgName == fileName){
				//存储轮播图
				formData.append("secondImage_"+i, files[ii]);
			}
		}
	}
}





