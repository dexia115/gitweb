<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="roleDialogDiv"></div>
<div id="roleDialog" class="hide">
	<form class="form-horizontal" id="roleForm" method="post"
		action="system/saveRole">
		<input type="hidden" name="id" id="id" />
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right"
				for="form-field-1"> 角色名: </label>

			<div class="col-sm-9">
				<input class="{required:true}" type="text" id="name" name="name" ></input>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right"
				for="form-field-1"> 级别: </label>

			<div class="col-sm-9">
				<input class="{required:true}" type="text" id="level" name="level"></input>
			</div>
		</div>
	</form>
</div>

<div id="searchDialogDiv"></div>
<div id="searchDialog" class="hide">
<form action="" id="searchForm">
	<table width="500" border="0" id="searchDiv">
		<tr class="searchTr">
			<td width="15%">角色名：</td>
			<td width="15%"><select class="tempMatchType">
					<option value="5">包含</option>
					<option value="4">等于</option>
			</select></td>
			<td><input type="hidden" value="name" class="propertyName" />
			<input type="text" class="propertyValue1" />
			<input type="hidden" value="String" class="tempType" /></td>
		</tr>
	</table>
	</form>
</div>