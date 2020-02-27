var vm = new Vue({
	el:'#rrapp',
	data:{
		modulData:{},
		timestamp:Date.parse(new Date())
	},
	created: function(){
		this.info();
	},
	methods: {
		info: function (){
			$.get("../modul/list", function(r){
				vm.modulData = r.page;
		    });
		}
	}
});
