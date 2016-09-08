<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
	try {
		var scripts = [ null, null ];
		$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
		});
	} catch (e) {
	}
</script>
<div class="well well-sm">
<Button class="btn btn-white btn-default btn-round" id="addBtn">
<i class="ace-icon fa fa-plus-circle bigger-120 green"></i>新增</Button>
<Button class="btn btn-white btn-default btn-round" id="updateBtn">
<i class="ace-icon fa fa-pencil-square-o bigger-120 green"></i>编辑</Button>
<Button class="btn btn-white btn-default btn-round" id="searchBtn">
<i class="ace-icon fa fa-search bigger-120 green"></i>查询</Button>
<Button class="btn btn-white btn-default btn-round" id="freshBtn">
<i class="ace-icon fa fa-refresh bigger-120 green"></i>刷新</Button>
<Button class="btn btn-white btn-default btn-round" id="deleteBtn">
<i class="ace-icon fa fa-trash-o bigger-120 red"></i>删除</Button></div>

<table id='itemGrid'></table>
<div id='itemGridPager'></div>

<jsp:include page="form/role_form.jsp" flush="true" />

<script src="js/system/role.js"></script>