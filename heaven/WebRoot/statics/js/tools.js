//展示数据获取屏幕合适的高度
function getWindowsInnerHeight() {
	return parseInt(document.documentElement.clientHeight) - 350;
}

/**
 * 时间转换成：年-月-日 时:分
 */
function dateForStr(date) {
	var time = new Date(date);
	return time.getFullYear()
			+ "-"
			+ (time.getMonth() < 9 ? ("0" + (time.getMonth() + 1)) : (time
					.getMonth() + 1))
			+ "-"
			+ (time.getDate() < 10 ? ("0" + time.getDate()) : (time.getDate()))
			+ " "
			+ (time.getHours() < 10 ? ("0" + time.getHours()) : (time
					.getHours()))
			+ ":"
			+ (time.getMinutes() < 10 ? ("0" + time.getMinutes()) : (time
					.getMinutes()));
			/*+ ":"
			+ (time.getSeconds() < 10 ? ("0" + time.getSeconds()) : (time
					.getSeconds()));*/
}

//input框只能输入数字和一位小数点和小数点后面两位小数
function clearNoNum(obj){ 
    obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
    obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的  
    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$","."); 
    obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');//只能输入两个小数  
    if(obj.value.indexOf(".")< 0 && obj.value !=""){//以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额 
        obj.value= parseFloat(obj.value); 
    } 
} 



var imageUpDefout = '../../../statics/images/upimage.jpg';

/** 
 * JS获取html代码中所有的图片地址 
 * @param htmlstr 
 * @returns imgsrcArr 数组 
 */  
function getimgsrc(htmlstr) {  
    var reg = /<img.+?src=('|")?([^'"]+)('|")?(?:\s+|>)/gim;  
    var imgsrcArr = [];  
    while (tem = reg.exec(htmlstr)) {  
        imgsrcArr.push(tem[2]);  
    }  
    return imgsrcArr;  
}

/** 
 * 获取指定的URL参数值 
 * URL:http://www.quwan.com/index?name=tyler 
 * 参数：paramName URL参数 
 * 调用方法:getParam("name") 
 * 返回值:tyler 
 */ 
function getParam(paramName) { 
    paramValue = "", isFound = !1; 
    if (this.location.search.indexOf("?") == 0 && this.location.search.indexOf("=") > 1) { 
        arrSource = unescape(this.location.search).substring(1, this.location.search.length).split("&"), i = 0; 
        while (i < arrSource.length && !isFound) arrSource[i].indexOf("=") > 0 && arrSource[i].split("=")[0].toLowerCase() == paramName.toLowerCase() && (paramValue = arrSource[i].split("=")[1], isFound = !0), i++ 
    } 
    return paramValue == "" && (paramValue = null), paramValue 
}

/*===================**以下为公用请求类，用到的目录请添加权限**========================*/

/**
 * 获取所有公司信息
 * 权限：tscompany:list:open
 */
function getCompanyAllData(callback){
	$.get("../tscompany/listopen", function(r){
        if (callback != null && typeof callback === 'function') {
            callback(r);
        } else {
            callback();
        }
    });
}



