<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="userDialogDiv"></div>
<div id="userDialog" class="hide">
	<form class="form-horizontal" id="userForm" method="post" action="system/saveUser">
		<table style="width: 100%;">
			<tr>
				<td>
					<div class="form-group">
						<label class="control-label col-xs-12 col-sm-3 no-padding-right"
							for="comment">用户名：</label>
						<div class="col-xs-12 col-sm-9">
							<div class="clearfix">
								<input type="hidden" id="id" name="id" value="" /> 
								<input class="{required:true,rangelength:[6,12],remote:{url:'system/checkUser',data:{'userId':function() {return $('#id').val();}}},messages:{remote:'该用户已存在'} }"
									type="text" id="username" name="username"></input>
							</div>
						</div>
					</div>
				</td>
				<td>
					<div class="form-group">
						<label class="control-label col-xs-12 col-sm-3 no-padding-right"
							for="comment">密码：</label>
						<div class="col-xs-12 col-sm-9">
							<div class="clearfix">
								<input class="{checkPassword:[],rangelength:[6,12]}"
									maxlength="20" type="password" id="password" name="password"></input>
								<input type="hidden" id="oldpassword" />
							</div>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="form-group">
						<label class="control-label col-xs-12 col-sm-3 no-padding-right"
							for="comment">角色：</label>
						<div class="col-xs-12 col-sm-9">
							<div class="clearfix">
								<select class="{required:true}" style="width:120px" id="roleId" name="role.id"></select>
							</div>
						</div>
					</div>
				</td>
				<td>
					<div class="form-group">
						<label class="control-label col-xs-12 col-sm-3 no-padding-right"
							for="comment">性别：</label>
						<div class="col-xs-12 col-sm-9">
							<div class="clearfix">
								<select class="{required:true}" id="sex" name="sex">
				<option value="男" selected="selected">男</option>
				<option value="女">女</option>
				</select>
							</div>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="form-group">
						<label class="control-label col-xs-12 col-sm-3 no-padding-right"
							for="comment">真实姓名：</label>
						<div class="col-xs-12 col-sm-9">
							<div class="clearfix">
								<input class="{required:true}" type="text" id="realname" name="realname"></input>
							</div>
						</div>
					</div>
				</td>
				<td>
					<div class="form-group">
						<label class="control-label col-xs-12 col-sm-3 no-padding-right"
							for="comment">电话：</label>
						<div class="col-xs-12 col-sm-9">
							<div class="clearfix">
								<input class="{required:true,phone:[]}" type="text" id="telphone" name="telphone"></input>
							</div>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="form-group">
						<label class="control-label col-xs-12 col-sm-3 no-padding-right"
							for="comment">邮件：</label>
						<div class="col-xs-12 col-sm-9">
							<div class="clearfix">
								<input class="{email:true}" type="text" id="email" name="email"></input>
							</div>
						</div>
					</div>
				</td>
				<td></td>
				</tr>
		</table>
	</form>
</div>

<div id="searchDialogDiv"></div>
<div id="searchDialog" class="hide">
	<form action="" id="searchForm">
		<table width="500" border="0" id="searchDiv">
			<tr class="searchTr">
				<td width="15%">用户名：</td>
				<td width="15%"><select class="tempMatchType">
						<option value="5">包含</option>
						<option value="4">等于</option>
				</select></td>
				<td><input type="hidden" value="username" class="propertyName" />
					<input type="text" class="propertyValue1" />
					<input type="hidden" value="String" class="tempType" /></td>
			</tr>
			<tr class="searchTr">
				<td width="15%">姓名：</td>
				<td width="15%"><select class="tempMatchType">
						<option value="5">包含</option>
						<option value="4">等于</option>
				</select></td>
				<td><input type="hidden" value="realname" class="propertyName" />
					<input type="text" class="propertyValue1" />
					<input type="hidden" value="String" class="tempType" /></td>
			</tr>
			<tr class="searchTr">
				<td width="15%">电话：</td>
				<td width="15%"><select class="tempMatchType">
						<option value="5">包含</option>
						<option value="4">等于</option>
				</select></td>
				<td><input type="hidden" value="telphone" class="propertyName" />
					<input type="text" class="propertyValue1" />
					<input type="hidden" value="String" class="tempType" /></td>
			</tr>
		</table>
	</form>
</div>