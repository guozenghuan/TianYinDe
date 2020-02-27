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
		iframeId:0,
		timestamp:Date.parse(new Date())
	},
	created: function() {
		if(id != null){
			this.title = "修改";
			$("#saveUpdateBtn").val("修改信息");
		}
    },
	methods: {
		getInfo: function(id){
			$.get("../modul/info/"+id, function(r){
                vm.modul = r.modul;
            });
		},
		getModulMusic: function(){
			$.get("../modul/list_music", function(r){
                vm.modulMusicList = r.modulMusicList;
                if(id != null){
        			vm.getInfo(id);
        		}
            });
		},
		saveOrUpdate: function (event) {
			//loading带文字
			var index = layer.open({
			    type: 2
			    ,content: '加载中'
			  });
			/*var index = layer.load(2, {
			  shade: [0.5,'#222d32'], //0.5透明度的白色背景
			  content:'保存中...',
			  success: function (layero) {
			        layero.find('.layui-layer-content').css({
			            'padding-top': '39px',
				        'width': '100px',
				        'font-size': '11px',
				        'font-weight': 'bold',
				        'color': 'white'
			        });
			    }
			});*/
			
			//设置模板回到第一页
			document.getElementById("modulIframeId").contentWindow.backToPateOneSwiper();
			//获取模板<body>里的代码
			var modulHtml = document.getElementById('modulIframeId').contentWindow.document.body.innerHTML;
			vm.modul.oneModulPath = modulHtml;
			$.ajax({
				type: "POST",
			    url: "../modul/update",
			    data: JSON.stringify(vm.modul),
			    success: function(r){
			    	if(r.code === 0){
			    		layer.close(index);
						alert('操作成功',function(){
							parent.location.reload();	
						});
					}else{
						layer.close(index);
						alert(r.msg);
					}
				}
			});
		},
		back: function (event) {
			parent.location.reload();
		}
	}
});



