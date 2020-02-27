/**
 * 正常输入字体
 * @param o
 * @param size
 */
function inputByNormal(o,size){
	//判断是否拥有操作权限
	var status = getParam("status");
	if(status == undefined || status == null || parseInt(status) != 1){
		//不可编辑,只有status等于1才能编辑
		return;
	}
    parent.layer.open({
        anim: 'up'
        ,content: '<textarea id="inputById" style="width: 100%;height: 100px;font-size: 15px;font-weight: bold;border-radius: 10px;" maxlength="'+size+'">'+$(o).html()+'</textarea>'
        ,btn: ['保存信息', '取消']
        ,yes: function(){
            var setText = $("#inputById",parent.document).val();
            if(setText.length > size){
                setText = setText.substr(0,size);
            }
            $(o).html(setText);
            parent.layer.closeAll();
        }
    });
}

/**
 * 正常输入字体,textarea回车换行\r
 * @param o
 * @param size
 */
function inputByNormalTextAreaR(o,size){
    //判断是否拥有操作权限
    var status = getParam("status");
    if(status == undefined || status == null || parseInt(status) != 1){
        //不可编辑,只有status等于1才能编辑
        return;
    }

    var text = $(o).html();
    text = text.replace(/<br>/g,"\r");

    parent.layer.open({
        anim: 'up'
        ,content: '<textarea id="inputById" style="width: 100%;height: 300px;font-size: 15px;font-weight: bold;border-radius: 10px;" maxlength="'+size+'">'+text+'</textarea>'
        ,btn: ['保存信息', '取消']
        ,yes: function(){
            var setText = $("#inputById",parent.document).val();
            if(setText.length > size){
                setText = setText.substr(0,size);
            }
            $(o).html(setText);
            parent.layer.closeAll();
        }
    });
}

/**
 * 限定换行字符
 * @param o
 * @param size
 */
function inputByBrLength(o,size,length){
	//判断是否拥有操作权限
	var status = getParam("status");
	if(status == undefined || status == null || parseInt(status) != 1){
		//不可编辑,只有status等于1才能编辑
		return;
	}

    var text = $(o).html();
    text = text.replace(/<br>/g,"");
    parent.layer.open({
        anim: 'up'
        ,content: '<textarea id="inputById" style="width: 100%;height: 100px;font-size: 15px;font-weight: bold;border-radius: 10px;" maxlength="'+size+'">'+text+'</textarea>'
        ,btn: ['保存信息', '取消']
        ,yes: function(){
            var setText = $("#inputById",parent.document).val();
            if(setText.length > size){
                setText = setText.substr(0,size);
            }

            var getText = "";
            for(var i=0;i<setText.length;i++){
                if(i==length){
                    getText += "<br>"+setText.substr(i,1);
                }else{
                    getText += setText.substr(i,1);
                }
            }

            $(o).html(getText);
            parent.layer.closeAll();
        }
    });
}

/**
 * 每个字符换行
 * @param o
 * @param size
 */
function inputByBrOne(o,size){
	//判断是否拥有操作权限
	var status = getParam("status");
	if(status == undefined || status == null || parseInt(status) != 1){
		//不可编辑,只有status等于1才能编辑
		return;
	}

    var text = $(o).html();
    text = text.replace(/<br>/g,"");
    parent.layer.open({
        anim: 'up'
        ,content: '<textarea id="inputById" style="width: 100%;height: 100px;font-size: 15px;font-weight: bold;border-radius: 10px;" maxlength="'+size+'">'+text+'</textarea>'
        ,btn: ['保存信息', '取消']
        ,yes: function(){
            var getText = $("#inputById",parent.document).val();
            if(getText.length > size){
                getText = getText.substr(0,size);
            }

            var setText = "";
            for(var i=0;i<getText.length;i++){
                if(setText.trim() == ""){
                    setText += getText.substr(i,1);
                }else{
                    setText += "<br>"+getText.substr(i,1);
                }
                if(setText.length == size){
                    break;
                }
            }

            $(o).html(setText);
            parent.layer.closeAll();
        }
    });
}


/**
 * 选择图片,设置div背景图和div子元素内第一个img标签的src
 */
function selectImageByDivBGI(o,cropWidth,cropHeight){
    //判断是否拥有操作权限
    var status = getParam("status");
    if(status == undefined || status == null || parseInt(status) != 1){
        //不可编辑,只有status等于1才能编辑
        return;
    }

    //创建新的file按钮
    $("#upLoderImageDIVId").html("");
    $("#upLoderImageDIVId").html('<input id="upLoderImageId" type="file" accept="image/*" style="display: none;" />');

	$('#upLoderImageId').click();
	$('#upLoderImageId').change(function(e){
		var selectedFile = $('#upLoderImageId').get(0).files[0];
        //获取图片宽高
        getFileWidthAndHeight(selectedFile,function(jsonData){
            getOneFileBase64(selectedFile,function(bases){
                //页面层
                parent.layer.open({
                    type: 1
                    ,content: '<div class="component">'+
                        '	<div class="overlay">'+
                        '		<div class="overlay-inner"></div>'+
                        '	</div>'+
                        '	<img class="resize-image" src="'+bases+'" alt="image for resizing">'+
                        '	<button class="btn-crop" onclick="cancelCropImag();" style="width: 60px;top: 0px;height: 20px;left: 0;margin-left: 120px;margin-top: 76px;background-color: rgb(110, 104, 105);">取消<img class="icon-crop" src="../cropping/img/cancel.svg"></button><br>'+
                        '	<button class="btn-crop js-crop" style="width: 90px;top: 0px;height: 20px;margin-top: 76px;left: 0;">确认裁剪<img class="icon-crop" src="../cropping/img/crop.svg"></button>'+
                        '</div>'
                    ,anim: 'up'
                    ,style: 'position:fixed; bottom:0; left:0; width: 100%; height: 100%; padding:10px 0; border:none;'
                    ,success: function (layero) {
                        //初始化裁剪工具
                        resizeableImage($('.resize-image',parent.document),cropWidth,cropHeight,jsonData.width,jsonData.height,function(componentBase){
                            $(o).css("background-image","url("+componentBase+")");
                            //设置子元素img的src
                            var imgSrc = $(o).find("img").eq(0);
                            $(imgSrc).attr("src",componentBase);

                            //关闭窗口
                            cancelCropImag();
                        });
                    }
                });
            });
        });
	})
}

/**
 * 选择图片,设置img标签的src
 */
function selectImageSrc(o,cropWidth,cropHeight){
    //判断是否拥有操作权限
    var status = getParam("status");
    if(status == undefined || status == null || parseInt(status) != 1){
        //不可编辑,只有status等于1才能编辑
        return;
    }

    //创建新的file按钮
    $("#upLoderImageDIVId").html("");
    $("#upLoderImageDIVId").html('<input id="upLoderImageId" type="file" accept="image/*" style="display: none;" />');

    //重新加载
    $('#upLoderImageId').click();
    $('#upLoderImageId').change(function(e){
        var selectedFile = $('#upLoderImageId').get(0).files[0];
        //获取图片宽高
        getFileWidthAndHeight(selectedFile,function(jsonData){
        	getOneFileBase64(selectedFile,function(bases){
                //页面层
                layer.open({
                    type: 1
                    ,content: '<div class="component">'+
                        '	<div class="overlay">'+
                        '		<div class="overlay-inner"></div>'+
                        '	</div>'+
                        '	<img class="resize-image" src="'+bases+'" alt="image for resizing">'+
                        '	<button id="croppingCancleID" class="btn-crop" onclick="cancelCropImag();" style="width: 60px;top: 0px;height: 20px;left: 0;margin-left: 120px;margin-top: 76px;background-color: rgb(110, 104, 105);">取消<img class="icon-crop" src="../cropping/img/cancel.svg"></button><br>'+
                        '	<button class="btn-crop js-crop" style="width: 90px;top: 0px;height: 20px;margin-top: 76px;left: 0;">确认裁剪<img class="icon-crop" src="../cropping/img/crop.svg"></button>'+
                        '</div>'
                    ,anim: 'up'
                    ,style: 'position:fixed; bottom:0; left:0; width: 100%; height: 100%; padding:10px 0; border:none;'
                    ,success: function (layero) {
                        //初始化裁剪工具
                    	resizeableImage($('.resize-image'),cropWidth,cropHeight,jsonData.width,jsonData.height,function(componentBase){
                            //设置img的src
                            $(o).attr("src",componentBase);
                            //关闭窗口
                            cancelCropImag();
                        });
                    }
                });
            });
        });
    })
}

/**
 * 取消裁剪，关闭窗口
 */
function cancelCropImag(){
	//重新加载
	$.getScript('../cropping/js/component.js?_=' + Date.parse(new Date()));

	//创建新的file按钮
	$("#upLoderImageDIVId").html("");
	// $("#upLoderImageDIVId").html('<input id="upLoderImageId" type="file" accept="image/gif,image/jpeg,image/png" style="display: none;" />');
    $("#upLoderImageDIVId").html('<input id="upLoderImageId" type="file" accept="image/*" style="display: none;" />');
    layer.closeAll();
}

/**
* 从 file 域获取 本地图片 url
*/
function getObjectURL(file)
{
    var url = "";
    if(window.createObjectURL!=undefined)
    {
        url = window.createObjectURL(file);
    }
   else if(window.URL!=undefined)
    {
       url = window.URL.createObjectURL(file);
   }
   else if (window.webkitURL != undefined)
   {
       url = window.webkitURL.createObjectURL(file);
   }
    return url;
}


//获取单个file的bases64编码
function getOneFileBase64(file,callback){
	var reader = new FileReader();
	reader.readAsDataURL(file); // 读出 base64
	reader.onloadend = function () {
	    // 图片的 base64 格式, 可以直接当成 img 的 src 属性值
	    var dataURL = reader.result;
	    if (callback != null && typeof callback === 'function') {
            callback(dataURL);
        } else {
            callback();
        }
	};
}

/**
 * 根据file对象获取图片宽高
 * @param fileData
 * @param callback
 */
function getFileWidthAndHeight(fileData,callback){
    //读取图片数据
    var reader = new FileReader();
    reader.onload = function (e) {
        var data = e.target.result;
        //加载图片获取图片真实宽度和高度
        var image = new Image();
        image.onload=function(){
            var widthImage = image.width;
            var heightImage = image.height;
            if (callback != null && typeof callback === 'function') {
                callback({width:widthImage,height:heightImage});
            } else {
                callback();
            }
        };
        image.src= data;
    };
    reader.readAsDataURL(fileData);
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


