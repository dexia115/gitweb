<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
String params = request.getParameter("params");
request.setAttribute("base", basePath);
request.setAttribute("params", params);
%>

<!DOCTYPE>
<html>
  <head>
    <title>智能化能源管理系统登录</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!-- bootstrap & fontawesome -->
		<link rel="stylesheet" href="${base}/bootstrap/assets/css/bootstrap.css" />
		<link rel="stylesheet" href="${base}/bootstrap/assets/css/font-awesome.css" />

		<!-- text fonts -->
		<link rel="stylesheet" href="${base}/bootstrap/assets/css/ace-fonts.css" />

		<!-- ace styles -->
		<link rel="stylesheet" href="${base}/bootstrap/assets/css/ace.css" />

		<!--[if lte IE 9]>
			<link rel="stylesheet" href="${base}/bootstrap/assets/css/ace-part2.css" />
		<![endif]-->
		<link rel="stylesheet" href="${base}/bootstrap/assets/css/ace-rtl.css" />

		<!--[if lte IE 9]>
		  <link rel="stylesheet" href="${base}/bootstrap/assets/css/ace-ie.css" />
		<![endif]-->

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

		<!--[if lt IE 9]>
		<script src="${base}/bootstrap/assets/js/html5shiv.js"></script>
		<script src="${base}/bootstrap/assets/js/respond.js"></script>
		<![endif]-->
  </head>
  
  <body class="login-layout"><%--
  <div class="container">
   &nbsp;<br/>&nbsp;<br/>&nbsp;<br/>&nbsp;<br/>&nbsp;<br/>
</div>
    --%>

<div class="main-container">
	<div class="main-content">
		<div>
			<div style="top:30%;margin-top:-120px;left:50%;margin-left:-160px;position:absolute;">
				<div class="login-container">
					<div class="center">
						<h1>
						<i class="ace-icon fa fa-leaf green"></i>
						<!-- <span class="red">ACE</span> -->
						<span class="white" id="id-text2">智能化能源管理系统</span>
						</h1>
					</div>

					<div class="space-6"></div>

					<div class="position-relative">
						<div id="login-box" class="login-box visible widget-box no-border">
							<div class="widget-body">
								<div class="widget-main">
								<h4 class="header blue lighter bigger">
									<i class="ace-icon fa fa-coffee green"></i>
									<span style="color:red" id="errorHtml"></span>
								</h4>

								<div class="space-6"></div>

								<form id="loginForm" method="post" action="${base}/login/shiroLogin">
									<fieldset>
										<label class="block clearfix">
											<span class="block input-icon input-icon-right">
											<input type="text" class="form-control" maxlength="30" id="username" name="username" placeholder="Username"  />
											<i class="ace-icon fa fa-user"></i>
											</span>
										</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" maxlength="30" id="password" name="password" placeholder="Password"  />
															<i class="ace-icon fa fa-lock"></i>
														</span>
													</label>

													<div class="space"></div>

													<div class="clearfix">
														<button type="button" id="loginBtn" class="width-35 pull-right btn btn-sm btn-primary">
															<i class="ace-icon fa fa-key"></i>
															<span class="bigger-110">登录</span>
														</button>
													</div>

													<div class="space-4"></div>
												</fieldset>
											</form>
											<div class="space-6"></div>

											</div><!-- /.widget-main -->

										<div class="toolbar clearfix">
										</div>
									</div><!-- /.widget-body -->
								</div><!-- /.login-box -->

								<div id="forgot-box" class="forgot-box widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header red lighter bigger">
												<i class="ace-icon fa fa-key"></i>
												Retrieve Password
											</h4>

											<div class="space-6"></div>
											<p>
												Enter your email and to receive instructions
											</p>

											<form>
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="email" class="form-control" placeholder="Email" />
															<i class="ace-icon fa fa-envelope"></i>
														</span>
													</label>

													<div class="clearfix">
														<button type="button" class="width-35 pull-right btn btn-sm btn-danger">
															<i class="ace-icon fa fa-lightbulb-o"></i>
															<span class="bigger-110">Send Me!</span>
														</button>
													</div>
												</fieldset>
											</form>
										</div><!-- /.widget-main -->

										<div class="toolbar center">
											<a href="#" data-target="#login-box" class="back-to-login-link">
												Back to login
												<i class="ace-icon fa fa-arrow-right"></i>
											</a>
										</div>
									</div><!-- /.widget-body -->
								</div><!-- /.forgot-box -->

								<div id="signup-box" class="signup-box widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header green lighter bigger">
												<i class="ace-icon fa fa-users blue"></i>
												New User Registration
											</h4>

											<div class="space-6"></div>
											<p> Enter your details to begin: </p>

											<form>
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="email" class="form-control" placeholder="Email" />
															<i class="ace-icon fa fa-envelope"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="Username" />
															<i class="ace-icon fa fa-user"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="Password" />
															<i class="ace-icon fa fa-lock"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="Repeat password" />
															<i class="ace-icon fa fa-retweet"></i>
														</span>
													</label>

													<label class="block">
														<input type="checkbox" class="ace" />
														<span class="lbl">
															I accept the
															<a href="#">User Agreement</a>
														</span>
													</label>

													<div class="space-24"></div>

													<div class="clearfix">
														<button type="reset" class="width-30 pull-left btn btn-sm">
															<i class="ace-icon fa fa-refresh"></i>
															<span class="bigger-110">Reset</span>
														</button>

														<button type="button" class="width-65 pull-right btn btn-sm btn-success">
															<span class="bigger-110">Register</span>

															<i class="ace-icon fa fa-arrow-right icon-on-right"></i>
														</button>
													</div>
												</fieldset>
											</form>
										</div>

										<div class="toolbar center">
											<a href="#" data-target="#login-box" class="back-to-login-link">
												<i class="ace-icon fa fa-arrow-left"></i>
												Back to login
											</a>
										</div>
									</div><!-- /.widget-body -->
								</div><!-- /.signup-box -->
							</div><!-- /.position-relative -->

							<div class="navbar-fixed-top align-right">
								<br />
								&nbsp;
								<a id="btn-login-dark" href="#">Dark</a>
								&nbsp;
								<span class="blue">/</span>
								&nbsp;
								<a id="btn-login-blur" href="#">Blur</a>
								&nbsp;
								<span class="blue">/</span>
								&nbsp;
								<a id="btn-login-light" href="#">Light</a>
								&nbsp; &nbsp; &nbsp;
							</div>
						</div>
					</div><!-- /.col -->
				</div><!-- /.row -->
			</div><!-- /.main-content -->
		</div><!-- /.main-container -->
		
		<!-- basic scripts -->

		<!--[if !IE]> -->
		<script type="text/javascript">
			window.jQuery || document.write("<script src='${base}/bootstrap/assets/js/jquery.js'>"+"<"+"/script>");
		</script>

		<!-- <![endif]-->

		<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='${base}/bootstrap/assets/js/jquery1x.js'>"+"<"+"/script>");
</script>
<![endif]-->
<script type="text/javascript">
	var params = "${params}";
	var lastUrl = "${lastUrl}";
	var wimadress="127.0.0.1:8887";
	if('ontouchstart' in document.documentElement){
		document.write("<script src='${base}/bootstrap/assets/js/jquery.mobile.custom.js'>"+"<"+"/script>");
	}
</script>

		<script src="${base}/bootstrap/assets/js/jquery.form.js"></script>
		<script src="${base}/js/common/jquery.validate.min.js"></script>
		<script src="${base}/js/common/mask.js" type="text/javascript" ></script>
		<!-- 即时通讯 -->
        <script type="text/javascript" src="${base}/js/system/websocket.js"></script>
		<script type="text/javascript">
		
		//var hash = "";
		jQuery(function($) {
			//hash = window.location.hash;//锚点
			$(document).on('click', '.toolbar a[data-target]', function(e) {
				e.preventDefault();
				var target = $(this).data('target');
				$('.widget-box.visible').removeClass('visible');//hide others
				$(target).addClass('visible');//show target
			 });
			 setTimeout(function(){
				 $('body').attr('class', 'login-layout blur-login');
				 $('#id-text2').attr('class', 'white');
				 $('#id-company-text').attr('class', 'light-blue');
				 if(params=="failure"){
					 //window.location.reload();
					 location.href = "${base}/login.jsp?params=0";
				 }else if(params=="kickout"){
					 location.href = "${base}/login.jsp?params=1";
				 }else if(params=="0"){
					 $("#errorHtml").html("session已过期，请重新登录！");
				 }else if(params=="1"){
					 $("#errorHtml").html("当前账户在其他地方登录，你已被登出！");
				 }
			 },0);			 
		});
		
			//you don't need this, just used for changing background
			jQuery(function($) {
			 $('#btn-login-dark').on('click', function(e) {
				$('body').attr('class', 'login-layout');
				$('#id-text2').attr('class', 'white');
				$('#id-company-text').attr('class', 'blue');
				
				e.preventDefault();
			 });
			 $('#btn-login-light').on('click', function(e) {
				$('body').attr('class', 'login-layout light-login');
				$('#id-text2').attr('class', 'grey');
				$('#id-company-text').attr('class', 'blue');
				
				e.preventDefault();
			 });
			 $('#btn-login-blur').on('click', function(e) {
				$('body').attr('class', 'login-layout blur-login');
				$('#id-text2').attr('class', 'white');
				$('#id-company-text').attr('class', 'light-blue');
				
				e.preventDefault();
			 });
			 
			 $('#loginBtn').on('click', function(e) {
				 submitForm();
				 e.preventDefault();
			 });
			 
			 document.onkeydown=function(event){
				 var e = event || window.event || arguments.callee.caller.arguments[0];
				 if(e && e.keyCode==13){// enter 键
					 submitForm();
				 }
			 };
			 
			 function submitForm(){
				 if($('#loginForm').valid()){
					 $(".login-layout").mask("loading...");
					 $("#loginForm").ajaxSubmit(function(data){
						 if(data.success){
							 var user = $("#username").val();
							 if(lastUrl != "" && lastUrl != "home"){
								 location.href = "${base}/home#"+lastUrl;
							 }else{
								 location.href = "${base}/home#welcome";
							 }
						 }else{
							 $("#errorHtml").html(data.message);
							 $(".login-layout").unmask();
						 }
					 });
				 }
			 }
			 
			 $('#loginForm').validate({
					errorElement: 'div',
					errorClass: 'help-block',
					focusInvalid: false,
					ignore: "",
					rules: {
						username: {
							required: true
						},
						password: {
							required: true
						}
					},
					messages: {
						username: {
							required: "<font color='red'>用户名不能为空</font>"
						},
						password: {
							required: "<font color='red'>密码不能为空</font>"
						}
					},
					highlight: function (e) {
						$(e).closest('.form-group').removeClass('has-info').addClass('has-error');
					},
					success: function (e) {
						$(e).closest('.form-group').removeClass('has-error');//.addClass('has-info');
						$(e).remove();
					},
					errorPlacement: function (error, element) {
						if(element.is('input[type=checkbox]') || element.is('input[type=radio]')) {
							var controls = element.closest('div[class*="col-"]');
							if(controls.find(':checkbox,:radio').length > 1) controls.append(error);
							else error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
						}
						else if(element.is('.select2')) {
							error.insertAfter(element.siblings('[class*="select2-container"]:eq(0)'));
						}
						else if(element.is('.chosen-select')) {
							error.insertAfter(element.siblings('[class*="chosen-container"]:eq(0)'));
						}
						else error.insertAfter(element.parent());
					},
					submitHandler: function (form) {
					},
					invalidHandler: function (form) {
					}
				});
			
			});
		</script>
  </body>
</html>
