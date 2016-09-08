<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<div id="sidebar" class="sidebar responsive">
	<script type="text/javascript">
				try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
			</script>
	
	<!-- /.sidebar-shortcuts -->
	<div class="sidebar-shortcuts" id="sidebar-shortcuts">
					<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
						<button class="btn btn-success">
							<i class="ace-icon fa fa-signal"></i>
						</button>

						<button class="btn btn-info">
							<i class="ace-icon fa fa-pencil"></i>
						</button>

						<!-- #section:basics/sidebar.layout.shortcuts -->
						<button class="btn btn-warning">
							<i class="ace-icon fa fa-users"></i>
						</button>

						<button class="btn btn-danger">
							<i class="ace-icon fa fa-cogs"></i>
						</button>

						<!-- /section:basics/sidebar.layout.shortcuts -->
					</div>

					<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
						<span class="btn btn-success"></span>

						<span class="btn btn-info"></span>

						<span class="btn btn-warning"></span>

						<span class="btn btn-danger"></span>
					</div>
				</div>

	<ul class="nav nav-list">
			<li id="homeLi"><a href="javascript:void(-1)" role="welcome"> <i
				class="menu-icon fa fa-tachometer"></i> <span class="menu-text">首页 </span>
		</a> <b class="arrow"></b></li>
			<li class="">
			      <a href="#" class="dropdown-toggle">
					         <i class="menu-icon fa fa-list-alt"></i> 
					         <span class="menu-text">设备管理</span> 
					         <b class="arrow fa fa-angle-down"></b>
			       </a> <b class="arrow"></b>
	
					<ul class="submenu">
					    <li><a href="javascript:void(-1)" role="researchAction/list"> <i
								class="menu-icon fa fa-caret-right"></i> 操作动作管理
						</a> <b class="arrow"></b></li>
						<li><a href="javascript:void(-1)" role="equipment/metronic"> <i
								class="menu-icon fa fa-caret-right"></i> 调研币设置
						</a> <b class="arrow"></b></li>
					
				 	 </ul>

			</li>
			<li class="">
			      <a href="#" class="dropdown-toggle">
					         <i class="menu-icon fa fa-desktop"></i> 
					         <span class="menu-text">报表分析</span> 
					         <b class="arrow fa fa-angle-down"></b>
			       </a> <b class="arrow"></b>
	
					<ul class="submenu">
				       <li><a href="javascript:void(-1)" role="equipment/report"> <i
						class="menu-icon fa fa-caret-right"></i>  报表分析
				       </a> <b class="arrow"></b></li>
				 	 </ul>
			</li>
			
			
			
			<li class=""><a href="#" class="dropdown-toggle">
				<i class="menu-icon fa fa-user"></i> <span class="menu-text">
					系统管理 </span> <b class="arrow fa fa-angle-down"></b>
		          </a> <b class="arrow"></b>

			<ul class="submenu">
			    <li><a href="javascript:void(-1)" role="system/role"> <i
						class="menu-icon fa fa-caret-right"></i>  角色管理
				       </a> <b class="arrow"></b>
				</li>
			    <li><a href="javascript:void(-1)" role="system/user"> <i
						class="menu-icon fa fa-caret-right"></i>  用户管理
				       </a> <b class="arrow"></b>
				</li>
				
			</ul></li>
	</ul>



	<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
		<i class="ace-icon fa fa-angle-double-left"
			data-icon1="ace-icon fa fa-angle-double-left"
			data-icon2="ace-icon fa fa-angle-double-right"></i>
	</div>

	<!-- /section:basics/sidebar.layout.minimize -->
	<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
				</script>
</div>