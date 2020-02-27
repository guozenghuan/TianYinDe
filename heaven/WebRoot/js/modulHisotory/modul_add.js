$(function(){
	vm.getModulMusic();
});
var id = T.p("id");
var vm = new Vue({
	el:'#rrapp',
	data:{
		title:"新增",
		modul:{},
		modulMusicList:{},
		modulId:null,
		iframeId:0
	},
	created: function() {
		if(id != null){
			this.title = "修改";
			$("#saveUpdateBtn").val("修改信息");
		}else{
			this.modul.status = "show";
			$("#addModulWindows").hide();
		}
		$("#slelectmodulS").hide();
    },
	methods: {
		getInfo: function(id){
			$.get("../modul/info/"+id, function(r){
                vm.modul = r.modul;
                vm.modulId = r.modul.id;
                var moduleList = vm.modul.moduleList;
                for(var i=0;i<moduleList.length;i++){
                	//展示模板信息
                	var iframeModulId = 'iframeModul_'+i;
                    var modulHtml = '<iframe id="'+iframeModulId+'" scrolling="yes" frameborder="0" style="width: 400px;height: 610px;margin-left: 20px;" src="'+moduleList[i]+'&iframeModulId='+iframeModulId+'"></iframe>';
    				$("#addModulWindows").before(modulHtml);
    				vm.iframeId = i;
                }
            });
		},
		getModulMusic: function(){
			$.get("../modul/list_music", function(r){
                vm.modulMusicList = r.modulMusicList;
                if(id != null){
        			vm.getInfo(id)
        		}
            });
		},
		saveOrUpdate: function (event) {
			var url = vm.modul.id == null ? "../modul/save" : "../modul/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.modul),
			    success: function(r){
			    	if(r.code === 0){
			    		vm.modulId = r.modulId;
						alert('操作成功');
						$("#addModulWindows").show();
						
						if(url == "../modul/save"){
							$("#saveBtnStatus").hide();
						}
					}else{
						alert(r.msg);
					}
				}
			});
		},
		loseModuls: function(){
			$("#slelectmodulS").hide();
		},
		back: function (event) {
			history.go(-1);
		}
	}
});

function removeIframeById(iframeModulId){
	$("#"+iframeModulId).remove();
}

function addModulBtn(){
	$("#slelectmodulS").show();
	$("html,body").animate({scrollTop:$("#slelectmodulS").offset().top-100},1);
}

//选择模板
function selectModuls(modulCode){
	var data = "id="+vm.modulId+"&modulCode="+modulCode;
	$.ajax({
		type: "POST",
	    url: "../modul/save_modul?"+data,
	    success: function(r){
	    	if(r.code === 0){
				alert('操作成功');
				$("#addModulWindows").show();
				
				if(url == "../modul/save"){
					$("#saveBtnStatus").hide();
				}
				
				vm.iframeId = vm.iframeId+1;
				var modulPrame = "mainModulId="+vm.modulId+"&id="+r.modulId+"&type=system&seetingBtn=true&modul="+modulCode+"&iframeModulId=iframeModul_"+vm.iframeId;
				var modulHtml = '<iframe id="iframeModul_'+vm.iframeId+'" scrolling="yes" frameborder="0" style="width: 400px;height: 610px;margin-left: 20px;" src="../../../statics/modul/'+modulCode+'.html?'+modulPrame+'"></iframe>';
				$("#addModulWindows").before(modulHtml);
				
				$("#slelectmodulS").hide();
				
				$("html,body").animate({scrollTop:$("#addModulWindows").offset().top-100},1);
			}else{
				alert(r.msg);
			}
		}
	});
}


