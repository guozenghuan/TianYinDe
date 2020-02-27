var id = T.p("id");
var vm = new Vue({
	el:'#rrapp',
	data:{
		title:"新增",
		music:null,
		modulMusic:{}
	},
	created: function() {
		if(id != null){
			this.title = "修改";
			this.getInfo(id)
		}
    },
	methods: {
		getInfo: function(id){
			$.get("../modulmusic/info/"+id, function(r){
                vm.modulMusic = r.modulMusic;
                
                $('#payMusicId').attr('src',vm.modulMusic.path);
                var fry_audio=$('#payMusicId').get('0');
                fry_audio.load();
            });
		},
		uploadMp3: function(e){
			var that=this;
            var file = e.target.files[0];
            
            //获取文件类型
            var type = file.type;
            if(type == "audio/mp3"){
            	vm.music = file;
            	
            	var reader = new FileReader();
                reader.readAsDataURL(file); // 读出 base64
                reader.onloadend = function () {
                    var dataURL = reader.result;
                    $('#payMusicId').attr('src',dataURL);
                    var fry_audio=$('#payMusicId').get('0');
                    fry_audio.load();
                };
            }else{
            	alert("只能选择MP3类型的音乐文件！");
            }
		},
		saveOrUpdate: function (event) {
			var url = vm.modulMusic.id == null ? "../modulmusic/save" : "../modulmusic/update";
			
			var formData = new FormData();
			formData.append("music", vm.music);
			formData.append("modulMusic", JSON.stringify(vm.modulMusic));
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