var deleteUrl="";
var globalUrl = "";
$.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
	_title: function(title) {
		var $title = this.options.title || '&nbsp;';
		if( ("title_html" in this.options) && this.options.title_html == true )
			title.html($title);
		else title.text($title);
	}
}));
$(function() {
//	$.extend($.fn, {
//		mask : function() {
//			$(this).append('<div class="message-loading-overlay" style="z-index:10000"><i class="fa-spin ace-icon fa fa-spinner orange2 bigger-160"></i></div>');
//		},
//		unmask : function() {
//			$(this).find('.message-loading-overlay').remove();
//		}});
	$("body").bind("mousedown", function(event) {
		if(event.target.id!="zTreeDemoBackground2" && event.target.id.indexOf("treeDemo2")==-1 && $("#zTreeDemoBackground2").is(":visible")){
			$("#zTreeDemoBackground2").hide();
		}
	});
	
	
	// 强行关闭浏览器 结束当前用户会话
	/*$(window).unload(function(evt) {
		if (typeof evt == 'undefined') {
			evt = window.event;
		}
		var ua = navigator.userAgent.toLowerCase();
		if (evt) {
			var n = window.event.screenX - window.screenLeft;
			if (ua.match(/msie ([\d.]+)/)[1] == 6.0) {// IE6
				n = window.event.screenX
			}
			var b = n > document.documentElement.scrollWidth - 60;
			if (b && window.event.clientY < 0 || window.event.altKey) {
				// 这个可以排除刷新 关闭的时候触发
				leaveSystem(user);
			}
		}
	});*/	
	
	jQuery.validator.addMethod("phone", function (value, element) {
		return this.optional(element) || (/^1[3|5|7|8][0-9]\d{4,8}$/.test(value) || /^1[4][2|7]\d{4,8}$/.test(value)) && value.length==11;
	}, "手机格式不正确");
	
	jQuery.validator.addMethod("qq", function (value, element) {
		return this.optional(element) || (/^[1-9]\d{4,19}$/.test(value));
	}, "QQ号码格式不正确");
	
	jQuery.validator.addMethod("cardNo", function (value, element) {
		return this.optional(element) || (/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(value));
	}, "身份证格式不正确");
	
	jQuery.validator.addMethod("CNEN", function (value, element) {
		return this.optional(element) || (/^[\u4e00-\u9fa5a-zA-Z]{0,}$/.test(value));
	}, "只能输入中英文");
	jQuery.validator.addMethod("CNENNO", function (value, element) {
		return this.optional(element) || (/^[\u4e00-\u9fa5a-zA-Z0-9]{0,}$/.test(value));
	}, "只能输入中英文与数字");
	jQuery.validator.addMethod("ENNUM", function (value, element) {
		return this.optional(element) || (/^[0-9a-zA-Z]{0,}$/.test(value));
	}, "只能输入数字和字母");
	$.validator.addMethod("checkPassword", function(value, element, params) {
		var oldpassword = $("#oldpassword").val();
		if(isnull(oldpassword)){
			if(isnull(value)){
				return false;
			}else{
				return true;
			}
		}else{
			return true;
		}		
	}, "不能为空");
	
	Date.prototype.format = function(format){
		 var o = {
			 "M+" : this.getMonth()+1, //month
			 "d+" : this.getDate(),    //day
			 "h+" : this.getHours(),   //hour
			 "m+" : this.getMinutes(), //minute
			 "s+" : this.getSeconds(), //second
			 "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
			 "S" : this.getMilliseconds() //millisecond
		 };
		 if(/(y+)/.test(format)) 
			 format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
		 for(var k in o)
			 if(new RegExp("("+ k +")").test(format))
				 format = format.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] :("00"+ o[k]).substr((""+ o[k]).length));
		 return format;
	}
	
	Function.prototype.method = function(name, func) {
		this.prototype[name] = func; 
		return this;
	};
	if (!String.prototype.trim) { //判断下浏览器是否自带有trim()方法  
		String.method('trim', function() {
			return this.replace(/^\s+|\s+$/g, '');
		});
		String.method('ltrim', function() {
			return this.replace(/^\s+/g, '');
		});
		String.method('rtrim', function() {
			return this.replace(/\s+$/g, '');
		});
	}
});

/**
 * 判断td宽度是否超过 超过添加title
 */
$("#pageContentArea").on("mouseover"," .table td",function(){
	if($(this).get(0).scrollWidth>$(this).get(0).clientWidth){
		var text = $(this).text();
		$(this).attr("title",text);
	}
});

function deleteData(callback){
	var selectedIds = $("#itemGrid").jqGrid("getGridParam", "selarrrow"); // 获取被选中的多行数据
	var str = "";
	if (selectedIds.length > 0) {
		if (confirm('确定要批量删除数据吗？')) {
			var list = new Array();
			for (var i = 0; i < selectedIds.length; i++) {
				var rowData = $("#itemGrid").jqGrid("getRowData",selectedIds[i]);
				list.push(rowData.id);
			}
			str=list.join(",");
			mask();
			ajaxRequest(deleteUrl,{"id":str},function(data){
				unmask();
				showMessage(data.message);
				if(data.success){
					reloadGrid($("#itemGrid"));
				}
				if(!isnull(callback)){
					callback();
				}
			});
		}

	} else {
		showMessage("请选择删除对象");
	}
}

//操作后的弹出框效果
function showMessage(text) {
	jAlert(text, '提示');
	unmask();
}

/**
 * 异步加载
 * @param url
 * @param data
 * @param callback
 */
function ajaxRequest(url,data,callback){
	$.ajax({
		url:url,
		data:data,
		type:'POST',
		success:function(datas){
			callback(datas);
		},
		error:function(response){
			unmask();
			try{
				var res = eval("(" + response.responseText + ")");
				alert(res.errorMessage);
				
			}catch(e){
				alert(response.responseText);
			}
		}
	});
}

/**
 * 同步加载
 * @param url
 * @param data
 * @param callback
 */
function syncRequest(url,data,callback){
	$.ajax({
		url:url,
		data:data,
		type:'POST',
		async:false,
		success:function(datas){
			callback(datas);
		}
	});
}

/**
 * 
 * @param select
 * @param url
 * @param data
 * @param isShowHead  是否显示"请选择"
 * @param value  是否选中
 * @param callback
 */
function createSelect(select, url, data, isShowHead, value, callback) {
	ajaxRequest(url,data,function(result){
		select.empty();
		if (isShowHead) {
			$("<option value=''>请选择</option>").appendTo(select);
		}
		if (result.length > 0) {
			for (var i = 0; i < result.length; i++) {
				$("<option value='" + result[i].id + "'>"
						+ result[i].name + "</option>").appendTo(select);
			}
			if(!isnull(value)){
				select.attr("value", value);// 赋值让其呈选中状态
			}
		}
		if (callback != null && callback != undefined) {
			callback();
		}
	});
}
function createSelect2(select, url, data, isShowHead, value, callback) {
	ajaxRequest(url,data,function(datas){
		var result = datas.root;
		select.empty();
		if (isShowHead) {
			$("<option value=''>请选择</option>").appendTo(select);
		}
		if (result.length > 0) {
			for (var i = 0; i < result.length; i++) {
				$("<option value='" + result[i].id + "'>"
						+ result[i].name + "</option>").appendTo(select);
			}
			if(!isnull(value)){
				select.attr("value", value);// 赋值让其呈选中状态
			}
		}
		if (callback != null && callback != undefined) {
			callback();
		}
	});
}
function createSelectName(select, url, data, isShowHead, value, callback) {
	ajaxRequest(url,data,function(result){
		select.empty();
		if (isShowHead) {
			$("<option value=''>请选择</option>").appendTo(select);
		}
		if (result.length > 0) {
			for (var i = 0; i < result.length; i++) {
				$("<option value='" + result[i].name + "'>"
						+ result[i].name + "</option>").appendTo(select);
			}
			if(!isnull(value)){
				select.attr("value", value);// 赋值让其呈选中状态
			}
		}
		if (callback != null && callback != undefined) {
			callback();
		}
	});
}

function isnull(data) {
	if (data == null || data == undefined || data == "") {
		return true;
	} else {
		if(typeof data == "string" && data.trim()==""){
			return true;
		}
		return false;
	}
}

function replaceBlank(content) {
	var content2 = content.replace(/<br>+/g,"")
						  .replace(/<div>+|<\/div>+/g,"")
						  .replace(/<p>+|<P>+/g,"")
						  .replace(/<\/p>+|<\/P>+/g,"")
						  .replace(/&nbsp;+/g,"");
	if(isnull(content2)){
		content = "";
	}
	if(content==""){
		return "";
	} else {
		return content2;
	}
}

/**
 * 
 * @param divId
 * @param appendDiv  弹框显示的div位置
 * @param title      弹框标题
 * @param width      弹框宽度
 * @param height     弹框高度
 * @param isShowBtn  是否显示弹框上的按钮
 * @param btnName    自定义按钮名字
 * @param method     自定义按钮事件
 * @returns
 */
function openDialog(divId,appendDiv,title,width,height,isShowBtn,btnName,method){
	var dialog;
	if(isShowBtn){//显示按钮
		dialog = $("#"+divId).removeClass('hide').dialog({
			appendTo: "#"+appendDiv,
			closeOnEscape:false,
			modal: true,
			width: width,
			height: height,
			closeText:"关闭",
			title: "<div class='widget-header widget-header-small'><h4 class='smaller'><i class='ace-icon fa fa-check'></i>"+title+"</h4></div>",
			title_html: true,
			buttons: [
				{
					text: "取消",
					"class" : "btn btn-xs",
					click: function() {
						$( this ).dialog( "close" ); 
					} 
				},
				{
					text: btnName,
					"class" : "btn btn-primary btn-xs",
					click: function() {
						if(!isnull(method)){
							method();
						}
					} 
				}
			]
		});
	}else{
		dialog = $("#"+divId).removeClass('hide').dialog({
			appendTo: "#"+appendDiv,
			closeOnEscape:false,
			modal: true,
			width: width,
			height: height,
			closeText:"关闭",
			title: "<div class='widget-header widget-header-small'><h4 class='smaller'><i class='ace-icon fa fa-check'></i>"+title+"</h4></div>",
			title_html: true
		});
	}
	return dialog;
}

/**
 * 
 * @param divId
 * @param appendDiv  弹框显示的div位置
 * @param title      弹框标题
 * @param width      弹框宽度
 * @param height     弹框高度
 * @param isShowBtn  是否显示弹框上的按钮
 * @param btnName    自定义按钮名字
 * @returns
 */
function openDialogNoBtnName(divId,appendDiv,title,width,height,isShowBtn,btnName){
	var dialog;
	if(isShowBtn){//显示按钮
		dialog = $("#"+divId).removeClass('hide').dialog({
			appendTo: "#"+appendDiv,
			closeOnEscape:false,
			modal: true,
			width: width,
			height: height,
			title: "<div class='widget-header widget-header-small'><h4 class='smaller'><i class='ace-icon fa fa-check'></i>"+title+"</h4></div>",
			title_html: true,
			buttons: [
				{
					text: btnName,
					"class" : "btn btn-primary btn-xs",
					click: function() {
						$( this ).dialog( "close" );
					} 
				}
			]
		});
	}else{
		dialog = $("#"+divId).removeClass('hide').dialog({
			appendTo: "#"+appendDiv,
			closeOnEscape:false,
			modal: true,
			width: width,
			height: height,
			title: "<div class='widget-header widget-header-small'><h4 class='smaller'><i class='ace-icon fa fa-check'></i>"+title+"</h4></div>",
			title_html: true
		});
	}
	return dialog;
}

/**
 * 删除之前的确认框
 * @param callback
 * @param title  标题名字  有默认值
 * @param content  内容  有默认值
 * @param btnName  按钮名称  有默认值
 */
function showDelDialog(callback,title,content,btnName){
	if(isnull(title)){
		title = "删除数据?";
	}
	if(!isnull(content)){
		$("#confirm_content").html(content);
	}else{
		$("#confirm_content").html("确定要删除该条数据吗?");
	}
	if(isnull(btnName)){
		btnName = "删除";
	}
	$( "#dialog-confirm" ).removeClass('hide').dialog({
		resizable: false,
		modal: true,
		title: "<div class='widget-header'><h4 class='smaller'><i class='ace-icon fa fa-exclamation-triangle red'></i> "+title+"</h4></div>",
		title_html: true,
		buttons: [
			{
				html: "<i class='ace-icon fa fa-trash-o bigger-110'></i>&nbsp; "+btnName,
				"class" : "btn btn-danger btn-xs",
				click: function() {
					$( this ).dialog( "close" );
					if(!isnull(callback)){
						callback();
					}
				}
			},
			{
				html: "<i class='ace-icon fa fa-times bigger-110'></i>&nbsp; 取消",
				"class" : "btn btn-xs",
				click: function() {
					$( this ).dialog( "close" );
				}
			}
		]
	});
}

/**
 * 异步文件上传
 * @param fileId  input的ID
 * @param url     请求的后台地址
 * @param callback  回调方法
 */
function ajaxFileUpload(fileId,url,callback){
	$.ajaxFileUpload({
        url:url, // 需要链接到服务器地址
        secureuri: false,
        fileElementId:fileId, // 文件选择框的id属性
        dataType: 'text', // 服务器返回的格式
        success: function(data,status){
        	if(data.indexOf("failure")>-1){
				alert(data);
			}else if(!isnull(callback)){
        		callback(data);
			}
        }
	});
}

/**
 * 查询table
 * @param dataTable
 * @param otherCase  其他的附加参数
 */
//function searchButton(dataTable,otherCase) {
//	var searchs = getSearchGroup();	
//	var str = "";
//	if(!isnull(otherCase)){
//		for(var i=0;i<otherCase.length;i++){
//			searchs.push(otherCase[i]);
//		}
//	}
//	str = "[" + searchs.join(",") + "]";	
//	dataTable.fnFilter(str);
//}

function searchButton() {
	var str = getSearchGroup2();
	var s = globalUrl.indexOf("?");
	if (s != -1){
		str = ReplaceAll(str, "?", "&");
	}
	globalUrl = encodeURI(globalUrl);
	str = encodeURI(str);

	// 将变量赋值给全局变量方便清空时使用
	$("#itemGrid").jqGrid("setGridParam", {
				url : globalUrl + str
			});
	reloadGrid($("#itemGrid"));
	$('#searchDialog').dialog('close');
}

function reloadGrid(grid) {
	grid.jqGrid("setGridParam", {
				search : true
			}).trigger("reloadGrid", [{page : 1}]);
//	grid.jqGrid("setGridParam", {
//		search : true
//	}).trigger("reloadGrid");
}
function freshGrid(){
	nosearchButton();
	reloadGrid($("#itemGrid"));
}
//单击查询清空按钮
function nosearchButton() {
	$("#searchForm").resetForm();
	if (globalUrl != "") {
		$("#itemGrid").jqGrid("setGridParam", {
					url : globalUrl
				});
		$(".propertyValue1").val('');
		reloadGrid($("#itemGrid"));
	}
}


function ReplaceAll(str, sptr, sptr1) {
	while (str.indexOf(sptr) >= 0) {
		str = str.replace(sptr, sptr1);
	}
	return str;
}

//得到查询框的值Div
function getSearchGroup2() {
	var str = "?moren=true";
	var tempI = 0;
	$("#searchDiv .searchTr").each(function() {
		if (!isnull($(this).find('.propertyValue1').val())) {
			str += "&searchGroups[" + tempI + "].tempMatchType="
					+ $(this).find('.tempMatchType').val() + "&searchGroups["
					+ tempI + "].propertyName="
					+ $(this).find('.propertyName').val()
					+ "&searchGroups[" + tempI + "].propertyValue1="
					+ $(this).find('.propertyValue1').val()
					+ "&searchGroups[" + tempI + "].tempType="
					+ $(this).find('.tempType').val()
					+ "&searchGroups[" + tempI + "].propertyValue2="
					+ $(this).find('.propertyValue2').val();
			tempI++;
		}
	});
	return str;
}
//得到查询框的值Div
function getSearchGroup() {
	var json = new Array();
	$("#searchDiv .searchTr").each(function() {
		if (!isnull($(this).find('.propertyValue1').val())) {
			var propertyValue2 = $(this).find('.propertyValue2').val();
			if(!isnull(propertyValue2)){
				json.push('{"tempMatchType":'+$(this).find('.tempMatchType').val()+',"propertyName":'+$(this).find('.propertyName').val()
						+',"propertyValue1":"'+$(this).find('.propertyValue1').val()+'","tempType":'+$(this).find('.tempType').val()
						+',"propertyValue2":"'+propertyValue2+'"}');
			}else{
				json.push('{"tempMatchType":'+$(this).find('.tempMatchType').val()+',"propertyName":'+$(this).find('.propertyName').val()
						+',"propertyValue1":"'+$(this).find('.propertyValue1').val()+'","tempType":'+$(this).find('.tempType').val()+'}');
			}
		}
	});
	
	return json;
}

/**
 * 
 * @param tabId  dataTable的ID
 * @param url    请求的url路径
 * @param objData  数据对象
 * @param bFilter  是否支持搜索方法
 * @param bPaginate  是否要分页显示
 * @param showNum  显示序号  默认为null  1：表示第1列作为序号列 2：表示第2列作为序号列
 * @param callback  回调方法
 * @param searchCase  额外传递的参数  数组  格式：[{"tempMatchType":"5","propertyName":loginName,"propertyValue1":"jth","tempType":"String"}]
 * @returns  返回dataTable对象
 */
function initTables(tabId,url,objData,bFilter,bPaginate,showNum,callback,searchCase){
	var searchs = "";
	if(!isnull(searchCase) && searchCase.length>0){
		searchs = "[" + searchCase.join(",") + "]";
	}
	if(isnull(showNum)){
		showNum = -1;
	}else{
		showNum = showNum - 1;
	}
	var currentPage=0,pageSize=0;
	var tables = $('#'+tabId).dataTable( {
		"columns": objData,
		"columnDefs": [{
			"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
				if(showNum != -1){
					$(nTd).text((iRow + 1)+(currentPage-1)*pageSize);
				}
			},
			"targets": showNum
		}],
		"bAutoWidth": true,
		"bDeferRender":true,
		"bLengthChange": true, //开关，是否显示每页大小的下拉框
		"bPaginate": false, //开关，是否显示分页器
		"bPaginate": bPaginate,  //是否分页
		"iDisplayLength":10, //每页显示10条记录  默认
		"bInfo": true, //开关，是否显示表格的一些信息
		"bFilter": bFilter, //开关，是否启用客户端过滤器
		"bJQueryUI": true, //开关，是否启用JQueryUI风格
		"bProcessing": true, //当datatable获取数据时候是否显示正在处理提示信息
		"aLengthMenu": [[10, 15, 30], [10, 15, 30]],
		"bServerSide": true,
		"sPaginationType": 'full_numbers',//分页样式 
		"sAjaxSource": url,
		"sAjaxDataProp":"aaData",
		"sServerMethod":"POST",
		"oSearch": { "sSearch": searchs},
		"oLanguage": {
			"sProcessing": "正在加载中......",
            "sSearch": "搜索:",
            "sLengthMenu": "每页显示 _MENU_ 条记录",
            "sZeroRecords": "没有记录",
            "sInfo": "共 _TOTAL_条记录",
            "sInfoEmpty": "显示0条记录",
            "oPaginate": {
                "sPrevious": " 上一页 ",
                "sNext":     " 下一页 ",
                "sFirst": "首页",
                "sLast": "尾页"
            }
		},
		"sDom":'<"row"<"col-xs-6"r><"col-xs-6"f>>t<"row"<"col-xs-3"l><"col-xs-2"i><"col-xs-4"p>>',
		"preDrawCallback": function( settings ) {
			var iDisplayStart = settings._iDisplayStart;
			pageSize = settings._iDisplayLength;
			currentPage = iDisplayStart / pageSize +1;
		},
		"drawCallback": function( settings ) {
			$("#"+settings.sInstance+" input[type='checkbox']").prop("checked", false);
			$("#"+settings.sInstance+" tbody input[type='checkbox']").change(function(){
				if(!$(this).prop("checked")){
					$("#"+settings.sInstance+" thead input[type='checkbox']").prop("checked", false);
				}else{
					var len = $("#"+settings.sInstance+" tbody input[type='checkbox']").not("input:checked").length;
					if(len==0){
						$("#"+settings.sInstance+" thead input[type='checkbox']").prop("checked", true);
					}
				}
			});
		},
		"initComplete": function(settings, json) {
			if(!isnull(callback)){
				callback();
			}
		}
    });
	return tables;
}

/**
 * 刷新table
 * @param dataTable
 * @param pageNo 页码
 */
function freshTable(dataTable,pageNo){
	var oSettings = dataTable.fnSettings();
	var iDisplayLength = oSettings._iDisplayLength;
//	var iRecordsTotal = oSettings._iRecordsTotal;
//	if(iRecordsTotal>iDisplayLength && iRecordsTotal%iDisplayLength==1){
//		oSettings._iDisplayStart = iRecordsTotal-1-iDisplayLength;
//	}
	if(!isnull(pageNo)){
		oSettings._iDisplayStart = iDisplayLength*(pageNo-1);
	}
	dataTable.fnDraw(oSettings);
	$(".dataTable input[type='checkbox']").prop("checked", false);
}

/**
 * 查看table行的某一列的值
 * @param row  当前行对象
 * @param colNum  要查看的列数 1：表示查看第1列
 * @returns
 */
function getColData(row,colNum){
	var num = colNum-1;
	if(!isnull(row) && num>=0){
		var datas = $(row).find("td");
		return datas[num].innerHTML;
	}else{
		return "";
	}
}

/**
 * 加载tabs并附上分页控件 
 * @param divId  显示位置的DIV
 * @param tabsObj  [{"name":"xxx","url":"xxx","objData":objData},{"name":"xxx","url":"xxx","objData":objData}]
 */
function showTabsAndLoadData(divId,tabsObj){
	$("#"+divId).html("");
	var htmls = '<div id="tabs"><ul>';
	var tabDiv = '';
	if(tabsObj.length>0){
		for(var i=0;i<tabsObj.length;i++){
			htmls += '<li><a href="#tabs-'+i+'">'+tabsObj[i].name+'</a></li>';
			tabDiv += '<div id="tabs-'+i+'"><table id="tabs-'+i+'_dataTable" class="table table-striped table-bordered table-hover"></table></div>';
		}
		htmls += '</ul>'+tabDiv+'</div>';
		$("#showTabs").html(htmls);
		
		$('#tabs').on('click', 'li a', function() {
			var href = $(this).attr("href");
			var n = href.substring(href.length-1);
			href = href.substring(1);
			var tableId = href+"_dataTable";
			if (!$.fn.dataTable.isDataTable('#'+tableId)) {
				initTables(tableId,tabsObj[n].url,tabsObj[n].objData,true,true,tabsObj[n].num);
			}
		});
		setTimeout(function(){
			$("#tabs").tabs();
			$('#tabs li a[href="#tabs-0"]').click();
		},100);
	}
}

/**
 * 加载tabs页 
 * @param divId  显示位置的DIV
 * @param tabsObj  [{"name":"xxx","html":"text","id":"tabs_id"}]
 */
function showTabs(divId,tabsObj,callback){
	$("#"+divId).html("");
	var htmls = '<div id="'+divId+'_tabs"><ul>';
	var tabDiv = '';
	if(tabsObj.length>0){
		for(var i=0;i<tabsObj.length;i++){
			var id = tabsObj[i].id;
			if(!isnull(id)){
				htmls += '<li><a href="#'+id+'">'+tabsObj[i].name+'</a></li>';
				tabDiv += '<div id="'+id+'">'+tabsObj[i].html+'</div>';
			}else{
				htmls += '<li><a href="#'+divId+'_tabs-'+i+'">'+tabsObj[i].name+'</a></li>';
				tabDiv += '<div id="'+divId+'_tabs-'+i+'">'+tabsObj[i].html+'</div>';
			}
		}
		htmls += '</ul>'+tabDiv+'</div>';
		$("#"+divId).html(htmls);
		
		setTimeout(function(){
			$('#'+divId+'_tabs').tabs();			
			if(!isnull(callback)){
				callback();
			}
		},100);
	}
}

/**
 * 获取当前选中的tab页的对象
 * @param divId
 * @returns {Object}
 */
function getCurrentTabs(divId){
	var object=null;
	$("#"+divId+"_tabs").find("div").each(function(){
		if($(this).css("display")=="block"){
			object = $(this);
			return false;
		}
	});
	return object;
}

/**
 * 
 * @param obj
 * @param selectEvent  选中日期的事件
 */
function createDatePicker(obj,selectEvent){
	obj.datepicker({
		autoclose: true,
		changeMonth: true,
		changeYear: true,
		dateFormat: "yy-mm-dd",
		monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
		yearRange: '-100:+10',
		todayHighlight: true,
		onSelect:function(data){
			if(!isnull(selectEvent)){
				selectEvent(data);
			}
		}
	});
}

/**
 * 
 * @param obj
 * @param dateFormat  yy-mm-dd
 */
function createDatePicker2(obj,dateFormat){
	obj.datepicker({
		autoclose: true,
		changeMonth: true,
		changeYear: true,
		dateFormat: dateFormat,
		monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
		yearRange: '-100:+10',
		todayHighlight: true
	});
}

/**
 * 
 * @param obj
 * @param selectEvent  选中日期的事件
 */
function createDatetimePicker(obj,selectEvent){
	obj.datetimepicker({
		showSecond: true,
		changeMonth: true,
		changeYear: true,
		dateFormat: "yy-mm-dd",
		monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
		timeFormat: 'HH:mm:ss',
		onSelect:function(data){
			if(!isnull(selectEvent)){
				selectEvent(data);
			}
		}
	});
}

/**
 * 
 * @param obj
 * @param dateFormat  yy-mm-dd
 * @param timeFormat  HH:mm:ss
 */
function createDatetimePicker2(obj,dateFormat,timeFormat){
	obj.datetimepicker({
		showSecond: true,
		changeMonth: true,
		changeYear: true,
		dateFormat: dateFormat,
		monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
		timeFormat: timeFormat
	});
}

//创建编辑器
function createEdit(obj){
	obj.ace_wysiwyg({
		toolbar:
		[
			'font',
			null,
			'fontSize',
			null,
			{name:'bold', className:'btn-info'},
			{name:'italic', className:'btn-info'},
			{name:'strikethrough', className:'btn-info'},
			{name:'underline', className:'btn-info'},
			null,
			{name:'insertunorderedlist', className:'btn-success'},
			{name:'insertorderedlist', className:'btn-success'},
			{name:'outdent', className:'btn-purple'},
			{name:'indent', className:'btn-purple'},
			null,
			{name:'justifyleft', className:'btn-primary'},
			{name:'justifycenter', className:'btn-primary'},
			{name:'justifyright', className:'btn-primary'},
			{name:'justifyfull', className:'btn-inverse'},
			null,
			{name:'createLink', className:'btn-pink'},
			{name:'unlink', className:'btn-pink'},
			null,
			{name:'insertImage', className:'btn-success'},
			null,
			'foreColor',
			null,
			{name:'undo', className:'btn-grey'},
			{name:'redo', className:'btn-grey'}
		],
		'wysiwyg': {
			fileUploadError: showErrorAlert
		}
	}).prev().addClass('wysiwyg-style2');
	
	if ( typeof jQuery.ui !== 'undefined' && ace.vars['webkit'] ) {
		var lastResizableImg = null;
		function destroyResizable() {
			if(lastResizableImg == null) return;
			lastResizableImg.resizable( "destroy" );
			lastResizableImg.removeData('resizable');
			lastResizableImg = null;
		}

		var enableImageResize = function() {
			$('.wysiwyg-editor').on('mousedown', function(e) {
				var target = $(e.target);
				if( e.target instanceof HTMLImageElement ) {
					if( !target.data('resizable') ) {
						target.resizable({
							aspectRatio: e.target.width / e.target.height,
						});
						target.data('resizable', true);
						
						if( lastResizableImg != null ) {
							//disable previous resizable image
							lastResizableImg.resizable( "destroy" );
							lastResizableImg.removeData('resizable');
						}
						lastResizableImg = target;
					}
				}
			}).on('click', function(e) {
				if( lastResizableImg != null && !(e.target instanceof HTMLImageElement) ) {
					destroyResizable();
				}
			}).on('keydown', function() {
				destroyResizable();
			});
	    }

		enableImageResize();
	}
}

function showErrorAlert (reason, detail) {
	var msg='';
	if (reason==='unsupported-file-type') { msg = "Unsupported format " +detail; }
	else {
		//console.log("error uploading file", reason, detail);
	}
	$('<div class="alert"> <button type="button" class="close" data-dismiss="alert">&times;</button>'+ 
	 '<strong>File upload error</strong> '+msg+' </div>').prependTo('#alerts');
}

//全选
function chooseAll(obj){
	var table = $(obj).parent().parent().parent().parent().parent();
	var tableId = table.attr("id");
	var isChecked = $(obj).is(":checked");
	$("#"+tableId+" input[type='checkbox']").prop("checked", isChecked);
}

//全选,置灰的不选中
function chooseAll2(obj){
	
	var table = $(obj).parent().parent().parent().parent().parent();
	var tableId = table.attr("id");
	var isChecked = $(obj).is(":checked");
	$("#"+tableId+" input[type='checkbox'][disabled!='disabled']").prop("checked", isChecked);
}

//生成全选checkkbox
function createAllCheckBox(){
	return '<label class="position-relative"><input type="checkbox" class="ace" onclick="chooseAll(this)" /><span class="lbl"></span></label>';
}
function createAllCheckBox2(){
	return '<label class="position-relative"><input type="checkbox" class="ace" onclick="chooseAll2(this)" /><span class="lbl"></span></label>';
}
//生成普通checkbox
function createCheckBox(data){
	return '<label class="position-relative"><input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span></label>';
}

/**
 * 
 * @param url
 * @param dataTable
 * @param title    删除时弹框的标题名字  有默认值
 * @param content  删除时弹框的内容  有默认值
 * @param btnName  删除时弹框的按钮名称  有默认值
 */
function deleteAll(url,dataTable,title,content,btnName){
	var ids = new Array();
	$(".dataTable tbody input[type='checkbox']:checked").each(function(){
		ids.push($(this).val());
		/*var oo = $(this).parent().parent();
		var aData = dataTable.fnGetData(oo); // get datarow
		alert(aData.name)*/
	});
	if(ids.length>0){
		showDelDialog(function(){
			ajaxRequest(url,{'ids':ids.join(",")},function(data){
				if(!data.success){
					alert(data.errorMessage);
				}else{
					if(!isnull(dataTable)){
						freshTable(dataTable);
					}
				}
			});
		},title,content,btnName);
	}else{
		alert("请选择删除对象！");
	}
}

function createImageUpload(obj){
	obj.ace_file_input({
		style:'well',
		btn_choose:'点击选择图片',
		btn_change:null,
		no_icon:'ace-icon fa fa-cloud-upload',
		droppable:true,
		thumbnail:'small',//large | fit	
		preview_error : function(filename, error_code) {
		}
	}).on('change', function(){
	});
	obj.ace_file_input('update_settings',
	{
		'allowExt': ["jpeg", "jpg", "png", "gif" , "bmp"],
		'allowMime': ["image/jpg", "image/jpeg", "image/png", "image/gif", "image/bmp"]
	});
}

/**
 * 图片回显
 * @param inputId  input[type=file]的id
 * @param imgSrc   图片路径
 */
function reviewImage(inputId,imgSrc){
	var fileName = imgSrc;
	if(imgSrc.indexOf("/")>-1){
		fileName = imgSrc.substring(imgSrc.lastIndexOf("/")+1);
	}
	$("#"+inputId).parent().find('.ace-file-name').remove();
	$("#"+inputId).parent().find(".ace-file-container").addClass('hide-placeholder').attr('data-title', null)
	.addClass('selected').html('<span class="ace-file-name" data-title="'+fileName+'">'
			 +('<img class="middle" style="width: 50px; height: 50px;" src="'+imgSrc+'"><i class="ace-icon fa fa-picture-o file-image"></i>')
					 +'</span>');
}

/**
 * 生成普通文件上传
 * @param obj
 */
function createFileUpload(obj){
	obj.ace_file_input({
		no_file:'暂无文件 ...',
		btn_choose:'选择',
		btn_change:'选择',
		droppable:false,
		onchange:null,
		thumbnail:false //| true | large
		//whitelist:'gif|png|jpg|jpeg'
		//blacklist:'exe|php'
		//onchange:''
		//
	});
}

/**
 * 创建spinner
 * @param obj
 * @param step  步长
 * @param max   最大值
 */
function createSpinner(obj,step,max){
	return obj.ace_spinner({value:0,min:0,max:max,step:step, btn_up_class:'btn-info' , btn_down_class:'btn-info'}).on('change', function(){
		//alert($(this).val())
	}); 
}


function mask(){
	$('#rightDivContent').mask("loading...");
}

function unmask(){
	$('#rightDivContent').unmask();
}

/**
 * 自定义弹框
 * @param msg
 */
function alertInfo(msg,callback){
	var option = {
			title: "消息提示",
			icon: "0 0",//蓝色i
			onOk: function(){
				if(!isnull(callback)){
					callback();
				}
			}
		}
	window.wxc.xcConfirm(msg,option);
}

function rightPageLocationHref(){
	var location = window.location.href;
	var url = location;
	var tmp = location.split("#")[0]+"#welcome";
	
	$("body").append("<a data-url=\"\" href=\""+ tmp +"\"><span  id='hidePageGotoAtag' ></span></a>");		
	var a = $("body").find("#hidePageGotoAtag");
//	$(a).attr("href",tmp);
	$(a).trigger("click");
	$(a).remove();

	$("body").append("<a data-url=\"\" href=\""+ url +"\"><span  id='hidePageGotoAtag' ></span></a>");		
	a = $("body").find("#hidePageGotoAtag");
//	$(a).attr("href",tmp);
	$(a).trigger("click");
	$(a).remove();
}
function showMenu(relativeControl, showDiv) {
	var cityObj = relativeControl;
	var cityOffset = relativeControl.offset();
	showDiv.css({
				left : cityOffset.left + "px",
				top : cityOffset.top + cityObj.outerHeight() + "px"
			}).slideDown("fast");
}
function hideMenu(hideDiv) {
	hideDiv.fadeOut("fast");
}

function initJqgrid(grid_selector,pager_selector,url,colNames,colModels,postData,caption){
	var isFirst = true;
	if(isnull(caption)){
		caption = "";
	}
	if(isnull(postData)){
		postData = {};
	}
	jQuery(grid_selector).jqGrid({
		url:url,
		mtype: 'POST',
		datatype: "json",
		height: "auto",
		autowidth: true,
		caption: caption,
		colNames:colNames,
		colModel:colModels,			
		viewrecords : true,
		rownumbers:true,
		rowNum:10,
		rownumWidth: 30,
		rowList:[10,20,30],
		pager : pager_selector,
		altRows: true,
		prmNames:{sort:'sortname',order:'order',rows:"pageSize",page: "pageNo"},
		postData:postData,
		jsonReader: {
			root: "items", //Get json array data's root property  
			page: "pageNo", //current page  
			total: "totalPageCount", //total page count  
			records: "totalCount" //total records count  
		},
		multiselect: true,
        multiboxonly: true,
        loadComplete : function(data) {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
				enableTooltips(table);
			}, 0);
		},
        gridComplete : function() {
				loadPager(grid_selector,pager_selector);
				if(isFirst){
					var gridId = grid_selector.substring(1);
					var width = $("#gview_"+gridId+" .ui-jqgrid-bdiv").width();
					$("#gview_"+gridId+" .ui-jqgrid-bdiv").css("width",width+10);
					$(window).on('resize.jqGrid', function () {
				    	width = $(grid_selector).parent().parent().parent().parent().parent().width();
				    	if(!isnull(width)){
				    		$(grid_selector).jqGrid( 'setGridWidth', width);
				    		$("#gview_"+gridId+" .ui-jqgrid-bdiv").css("width",width+10);
				    	}
				    });
					$(window).on('settings.ace.sidebar_scroll', function () {
						width = $(grid_selector).parent().parent().parent().parent().parent().width();
				    	if(!isnull(width)){
				    		$(grid_selector).jqGrid( 'setGridWidth', width);
				    		$("#gview_"+gridId+" .ui-jqgrid-bdiv").css("width",width+10);
				    	}
					});
					isFirst = false;
				}
		}
	});
}
function updatePagerIcons(table) {
	var replacement = 
	{
		'ui-icon-seek-first' : 'ace-icon fa fa-angle-double-left bigger-140',
		'ui-icon-seek-prev' : 'ace-icon fa fa-angle-left bigger-140',
		'ui-icon-seek-next' : 'ace-icon fa fa-angle-right bigger-140',
		'ui-icon-seek-end' : 'ace-icon fa fa-angle-double-right bigger-140'
	};
	$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
		var icon = $(this);
		var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
		if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
	});
}
function enableTooltips(table) {
	$('.navtable .ui-pg-button').tooltip({container:'body'});
	$(table).find('.ui-pg-div').tooltip({container:'body'});
}	
function loadPager(grid_selector,pager_selector){
	jQuery(grid_selector).jqGrid('navGrid',pager_selector,
			{ 	//navbar options
				edit: false,
				editicon : 'ace-icon fa fa-pencil blue',
				add: false,
				addicon : 'ace-icon fa fa-plus-circle purple',
				del: false,
				delicon : 'ace-icon fa fa-trash-o red',
				search: false,
				searchicon : 'ace-icon fa fa-search orange',
				refresh: true,
				refreshicon : 'ace-icon fa fa-refresh green',
				view: false,
				viewicon : 'ace-icon fa fa-search-plus grey',
			},
			{},
			{},
			{},
			{},
			{}
		);
}

