var validateForm;
globalUrl = "system/loadUser";
deleteUrl = "system/deleteUser";
$(function(){
	validateForm = $('#userForm').validate();
	var colNames = ['','','','用户名','角色',"姓名","电话","邮箱"];
	var colModels = [
	     	{name:'id',sortable:false, width:60,editable: true,hidden:true},
	     	{name:'role.id',sortable:false, width:60,editable: true,hidden:true},
	     	{name:'password',sortable:false, width:60,editable: true,hidden:true},
		    {name:'username',index:'username', sortable:false,editable: true},
		    {name:'role.name',index:'role.name', sortable:false,editable: true},
		    {name:'realname',index:'realname', sortable:false,editable: true},
		    {name:'telphone',index:'telphone', sortable:false,editable: true},
		    {name:'email',index:'email', sortable:false,editable: true}
	    ];
	var grid_selector = "#itemGrid";
	var pager_selector = "#itemGridPager";
	initJqgrid(grid_selector,pager_selector,globalUrl,colNames,colModels);
	
	createSelect($("#roleId"), "system/findRoles", {}, true, null, function(){
		
	});
	
	$("#addBtn").click(function(){
		validateForm.resetForm();
		$("#id").val("");
		$("#username").attr("readonly",false);
		openDialog("userDialog","userDialogDiv","用户管理",680,420,true,"提交",function(){
			submitForm();
		 });
	});
	$("#updateBtn").click(function(){
		validateForm.resetForm();
		var selectedId = $("#itemGrid").jqGrid("getGridParam", "selrow");
		if (selectedId) {
			var rowData = $("#itemGrid").jqGrid("getRowData", selectedId);
//			for(var item in rowData){
//		        console.info(item+"---"+rowData[item])
//		    }
			
			$("#id").val(rowData.id);
			var roleId = rowData["role.id"];
			$("#roleId").val(roleId);
			$("#username").val(rowData.username);
			$("#username").attr("readonly",true);
			$("#oldpassword").val(rowData.password);
			$("#realname").val(rowData.realname);
			$("#sex").val(rowData.sex);
			$("#telphone").val(rowData.telphone);
			$("#email").val(rowData.email);
			openDialog("userDialog","userDialogDiv","用户管理",680,420,true,"提交",function(){
				submitForm();
			});
		}else{
			showMessage("请选择修改对象！");
		}
	});
	
	$("#searchBtn").click(function(){
		openDialog("searchDialog","searchDialogDiv","用户查询",620,260,true,"查询",function(){
			searchButton();
		});
	});
	
	$('#freshBtn').click(function() {
		freshGrid();
	});
	
	$('#deleteBtn').click(function() {
		deleteData();
	});
});
function submitForm(){
	if($('#userForm').valid()){
		 mask();
		 $("#userForm").ajaxSubmit(function(data){
			 unmask();
			 if(data.success){
				 $("#userDialog").dialog("close");
				 reloadGrid($("#itemGrid"));
			 }else{
				 showMessage(data.message);
			 }
		 });
	}
}