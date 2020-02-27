var id = T.p("id");
var tsUserId = T.p("tsUserId");
var number = T.p("number");
var admin = localStorage.getItem('username');
console.log('admin: ', admin);
$(function () {
	// 查询中间表格
    $("#jqGrid").jqGrid({
        url: `../reportTsItem/list?tsUserId=${tsUserId}&number=${number}`,
        datatype: "json",
        colModel: [
            { label: '项目', name: 'item', numberOfColumns : 2,},
			{ label: '数量', name: 'count',},
			{ label: '成本', name: 'unit',},
			{ label: '业务员价格', name: 'cost',},
			{ label: '售价', name: 'price',},
			{ label: '现结', name: 'cash',},
			{ label: '备注', name: 'remark',},
        ],
		viewrecords: true,
        height: 'auto',
        autowidth:true,
        // multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order",
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        	var gridName = "jqGrid";
            Merger(gridName, 'item');
        }
    });
    // 请求第一个表格的数据
    $.ajax({
		type: "GET",
	    url: `../reportTsOrder/info?tsUserId=${tsUserId}&number=${number}`,
	    dataType: "json",
	    success: function(result){
	    	try{
				const data = result;
				console.log('data: ', data)
    			for(const key in data) {
    				if (key !== 'id' && key !== 'tsUserId') {
    					$(`#${key}`).text(data[key])
    				}
    			}
    		}
    		catch(err){
    			
    		}
		},
		error: function(result){
			//
		}
	});
    t3();
    // 请求第三个表格
    
});

// 请求第三个表格数据
function t3(b, status) {
	$.ajax({
		type: "GET",
	    url: `../reportTotal/info?tsUserId=${tsUserId}&number=${number}`,
	    dataType: "json",
	    success: function(result){
	    	const data = result;
			for(const key in data) {
				$(`#${key}`).text(data[key])
			}
		},
		error: function(result){
			//
		}
	});
}

//提交审核
function check(status) {
	const b = $('#b option:selected').val();
	console.log('b: ', b);
	const commission = $('#commission option:selected').val();
	$.ajax({
		type: "GET",
	    url: `../reportTotal/check?tsUserId=${tsUserId}&number=${number}&check=${status}&b=${b}&commission=${commission}&admin=${admin}`,
	    dataType: "json",
	    success: function(result){
	    	t3(b, status);
		},
		error: function(result){
			//
		}
	});
}
// 返回
function goback() {
	window.location.href="tsReportOrder.html";
}

function Merger(gridName, CellName) {  
    //得到显示到界面的id集合  
    var mya = $("#" + gridName + "").getDataIDs();  
    //数据总行数  
    var length = mya.length;  
    //定义合并行数  
    var rowSpanTaxCount = 1;  
    for (var i = 0; i < length; i += rowSpanTaxCount) {  
        //从当前行开始比对下面的信息  
        var before = $("#" + gridName + "").jqGrid('getRowData', mya[i]);  
        rowSpanTaxCount = 1;  
        for (j = i + 1; j <= length; j++) {  
            //和上边的信息对比 如果值一样就合并行数+1 然后设置rowspan 让当前单元格隐藏  
            var end = $("#" + gridName + "").jqGrid('getRowData', mya[j]);  
            if (before[CellName] == end[CellName]) {  
                rowSpanTaxCount++;  
                $("#" + gridName + "").setCell(mya[j], CellName, '', { display: 'none' });  
            } else {  
                break;
            }  
        }  
        $("#" + gridName + "").setCell(mya[i], CellName, '', '', { rowspan: rowSpanTaxCount });  
    }  
}