<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="java.util.*" %>
<%
	String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
	request.setAttribute("base", basePath);
%>

<!DOCTYPE>
<html>
  <head>
    <title>智能化能源管理系统</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!-- bootstrap & fontawesome -->
		<link rel="stylesheet" href="${base}/bootstrap/assets/css/bootstrap.css" />
		<link rel="stylesheet" href="${base}/bootstrap/assets/css/font-awesome.css" />
		<link rel="stylesheet" href="${base}/css/jquery-ui-timepicker-addon.css" />
		<link rel="stylesheet" href="${base}/bootstrap/assets/css/ui.jqgrid.css" />
		<link href="${base}/js/alert/jquery.alerts.css" rel="stylesheet" type="text/css" />
		<link href="${base}/css/index.css" rel="stylesheet" type="text/css" />

		<!-- page specific plugin styles -->
		<link rel="stylesheet" href="${base}/bootstrap/assets/css/jquery-ui.custom.css" />
		<link rel="stylesheet" href="${base}/bootstrap/assets/css/jquery.gritter.css" />
		<link rel="stylesheet" href="${base}/bootstrap/assets/css/datepicker.css" />
		<link rel="stylesheet" href="${base}/bootstrap/assets/css/bootstrap-editable.css" />
		<link rel="stylesheet" href="${base}/bootstrap/assets/css/select2.css" />
		<link rel="stylesheet" href="${base}/bootstrap/assets/css/jquery-ui.css" />

		<!-- text fonts -->
		<link rel="stylesheet" href="${base}/bootstrap/assets/css/ace-fonts.css" />

		<!-- ace styles -->
		<link rel="stylesheet" href="${base}/bootstrap/assets/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />

		<!--[if lte IE 9]>
			<link rel="stylesheet" href="${base}/bootstrap/assets/css/ace-part2.css" class="ace-main-stylesheet" />
		<![endif]-->

		<!--[if lte IE 9]>
		  <link rel="stylesheet" href="${base}/bootstrap/assets/css/ace-ie.css" />
		<![endif]-->
		<link rel="stylesheet" href="${base}/css/zTreeStyle/zTreeStyle.css" type="text/css" />
		<link rel="stylesheet" href="${base}/css/index.css" type="text/css" />

		<!-- inline styles related to this page -->
		<!-- ace settings handler -->
		<script src="${base}/bootstrap/assets/js/ace-extra.js"></script>

		<!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->
		<!--[if lte IE 8]>
		<script src="${base}/bootstrap/assets/js/html5shiv.js"></script>
		<script src="${base}/bootstrap/assets/js/respond.js"></script>
		<![endif]-->
		
		
	<style>
			/* .table {
				table-layout:fixed;word-wrap:break-word;
			}
			.table td{
				white-space:nowrap;overflow:hidden;text-overflow: ellipsis;
			} */
			.ui-dialog .ui-button{
			border:none;outline:none
			}
		</style>
  </head>
  
  <body class="no-skin">
  <jsp:include page="header.jsp" flush="true" />
  
    <div class="main-container" id="main-container">
        <script type="text/javascript">
			try{ace.settings.check('main-container' , 'fixed')}catch(e){}
		</script>
		
		<jsp:include page="menu.jsp" flush="true" />
		
		<div class="main-content" id="rightDivContent">
		<div class="main-content-inner">
		    
			
			<div class="page-content">
			<jsp:include page="setBox.jsp" flush="true" />
			
			<div data-ajax-content="true" class="page-content-area" id="pageContentArea" style="opacity: 1;"></div>
			</div>
		
		
		</div>
		</div>
		
		<div class="zTreeDemoBackground2" id="zTreeDemoBackground2"
		style="display: none; position: absolute; height: 200px; min-width: 150px; background-color: white; border: 1px solid; 
		z-index: 99999; overflow-y: auto; overflow-x: auto;">
		<ul id="treeDemo2" class="ztree"
			style="font-size: 13px; font-weight: bold;"></ul>
		</div>

		<div id="dialog-confirm" class="hide">
			<div class="alert alert-info bigger-110">请谨慎操作！</div>

			<div class="space-6"></div>

			<p class="bigger-110 bolder center grey">
				<i class="ace-icon fa fa-hand-o-right blue bigger-120"></i>
				<span id="confirm_content">确定要删除该条数据吗?</span>
			</p>
		</div>
		<!-- #dialog-confirm -->

		<jsp:include page="footer.jsp" flush="true" />

			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
			</a>
    </div>
    
    
<!--[if !IE]> -->
	<script type="text/javascript" src="${base}/bootstrap/assets/js/jquery.js"></script>
<!-- <![endif]-->

		<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='${base}/bootstrap/assets/js/jquery1x.js'>"+"<"+"/script>");
</script>
<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='${base}/bootstrap/assets/js/jquery.mobile.custom.js'>"+"<"+"/script>");
			
		</script>
		<script src="${base}/bootstrap/assets/js/bootstrap.js"></script>
		<!--[if lte IE 8]>
		  <script src="${base}/bootstrap/assets/js/excanvas.js"></script>
		<![endif]-->
<!-- 兼容高版本jquery下过时的api -->
<script type="text/javascript">
var basePath = "${base}";
	jQuery.browser = {};
	(function() {
		jQuery.browser.msie = false;
		jQuery.browser.version = 0;
		if (navigator.userAgent.match(/MSIE ([0-9]+)./)) {
			jQuery.browser.msie = true;
			jQuery.browser.version = RegExp.$1;
		}
	})();
	var wimadress="localhost:8887";
	var user = "${sessionUser.userName}";
</script>
		
		<script src="${base}/bootstrap/assets/js/jquery-ui.js"></script>
		<script src="${base}/bootstrap/assets/js/jquery-ui.custom.js"></script>
		<script src="${base}/bootstrap/assets/js/jquery.ui.touch-punch.js"></script>
		<script src="${base}/bootstrap/assets/js/jquery.gritter.js"></script>
		<script src="${base}/bootstrap/assets/js/bootbox.js"></script>
		<script src="${base}/bootstrap/assets/js/jquery.easypiechart.js"></script>
		
		<script src="${base}/bootstrap/assets/js/jquery.hotkeys.js"></script>
		<script src="${base}/bootstrap/assets/js/bootstrap-wysiwyg.js"></script>
		<script src="${base}/bootstrap/assets/js/select2.js"></script>
		<script src="${base}/bootstrap/assets/js/fuelux/fuelux.spinner.js"></script>
		<script src="${base}/bootstrap/assets/js/x-editable/bootstrap-editable.js"></script>
		<script src="${base}/bootstrap/assets/js/x-editable/ace-editable.js"></script>
		<script src="${base}/bootstrap/assets/js/jquery.maskedinput.js"></script>
		<script src="${base}/bootstrap/assets/js/fuelux/fuelux.wizard.js"></script>
		<script src="${base}/bootstrap/assets/js/jquery.validate.js"></script>
		
    <!-- ace scripts -->
		<script src="${base}/bootstrap/assets/js/ace/elements.scroller.js"></script>
		<script src="${base}/bootstrap/assets/js/ace/elements.colorpicker.js"></script>
		<script src="${base}/bootstrap/assets/js/ace/elements.fileinput.js"></script>
		<script src="${base}/bootstrap/assets/js/ace/elements.typeahead.js"></script>
		<script src="${base}/bootstrap/assets/js/ace/elements.wysiwyg.js"></script>
		<script src="${base}/bootstrap/assets/js/ace/elements.spinner.js"></script>
		<script src="${base}/bootstrap/assets/js/ace/elements.treeview.js"></script>
		<script src="${base}/bootstrap/assets/js/ace/elements.wizard.js"></script>
		<script src="${base}/bootstrap/assets/js/ace/elements.aside.js"></script>
		<script src="${base}/bootstrap/assets/js/ace/ace.js"></script>
		<script src="${base}/bootstrap/assets/js/ace/ace.ajax-content.js"></script>
		<script src="${base}/bootstrap/assets/js/ace/ace.touch-drag.js"></script>
		<script src="${base}/bootstrap/assets/js/ace/ace.sidebar.js"></script>
		<script src="${base}/bootstrap/assets/js/ace/ace.sidebar-scroll-1.js"></script>
		<script src="${base}/bootstrap/assets/js/ace/ace.submenu-hover.js"></script>
		<script src="${base}/bootstrap/assets/js/ace/ace.widget-box.js"></script>
		<script src="${base}/bootstrap/assets/js/ace/ace.settings.js"></script>
		<script src="${base}/bootstrap/assets/js/ace/ace.settings-rtl.js"></script>
		<script src="${base}/bootstrap/assets/js/ace/ace.settings-skin.js"></script>
		<script src="${base}/bootstrap/assets/js/ace/ace.widget-on-reload.js"></script>
		<script src="${base}/bootstrap/assets/js/ace/ace.searchbox-autocomplete.js"></script>		
		<script src="${base}/bootstrap/assets/js/jqGrid/jquery.jqGrid.src.js"></script>
		<script src="${base}/bootstrap/assets/js/jqGrid/i18n/grid.locale-cn.js"></script>
		<script src="${base}/bootstrap/assets/js/jquery.dataTables.js"></script>
		<script src="${base}/bootstrap/assets/js/jquery.dataTables.bootstrap.js"></script>        
        <script src="${base}/bootstrap/assets/js/jquery.form.js"></script>
        
        <script src="${base}/js/alert/jquery.alerts.js"></script>      
		<script src="${base}/js/common/jquery-ui-timepicker-addon.js"></script>
		<script src="${base}/js/common/jquery-ui-timepicker-zh-CN.js"></script>
        <script src="${base}/js/common/jquery.validate.min.js"></script>
        <script src="${base}/js/common/jquery.metadata.js"></script>
        <script src="${base}/js/common/jquery.ztree.core-3.5.js"></script>
		<script src="${base}/js/common/jquery.ztree.excheck-3.5.js"></script>
        <script src="${base}/js/common/ajaxfileupload.js"></script>
        <script src="${base}/js/common/messages_cn.js"></script>
        <script src="${base}/js/common/mask.js" type="text/javascript" ></script>
        <script src="${base}/js/common/global.js"></script>
        <script src="${base}/media/js/jquery.pulsate.min.js" type="text/javascript"></script>
        <!-- 即时通讯 -->
        <script type="text/javascript" src="${base}/js/system/websocket.js"></script>
		<%-- 
		
		<script src="${base}/js/ztree.js"></script>
		<script type="text/javascript" src="${base}/js/common/plug.js"></script>
		
		<script type="text/javascript" src="${base}/js/common/jquery.qrcode.js"></script>
		<script type="text/javascript" src="${base}/js/common/qrcode.js"></script>
		<script type='text/javascript' src="${base}/dwr/engine.js"></script>
        <script type='text/javascript' src="${base}/dwr/util.js"></script>
        <script type="text/javascript" src="${base}/dwr/interface/xlsoutputDwr.js"></script> --%>
		
		<script type="text/javascript">
		var currentUrl = "${url}";
		
		$(function(){
			setTimeout(function(){
				//$('#skin-colorpicker').find("option[value='#222A2D']").click();
				//$('#skin-colorpicker').find("option[value='#222A2D']").change();
				if(isnull(websocket)){
					initWebSocket(user);
				}
			},10);
			$('#sidebar').find('ul li a').click(function(){
				if($(this).attr("class")!="dropdown-toggle"){
					//syncRequest("logined",{},function(data){
						//if(data=="0"){
							//window.location.reload();
						//}
					//});
					location.href = "${base}/home#"+$(this).attr("role");
				}
			});
		});
 
		function turnPage(url,obj){
			location.href = url;
		}
		function logout(){
			leaveSystem(user);
			location.href = "${base}/login/logout";
		}
		</script>
  </body>
</html>
