var validateForm;
var spinner;
globalUrl = "system/loadRole";
deleteUrl = "system/deleteRole";
$(function(){
	validateForm = $('#roleForm').validate();
	var colNames = ['','角色名','级别'];
	var colModels = [
	     	{name:'id',sortable:false, width:60,editable: true,hidden:true},
		    {name:'name',index:'name', sortable:false,editable: true},
		    {name:'level',index:'level', sortable:false,editable: true}
	    ];
	var grid_selector = "#itemGrid";
	var pager_selector = "#itemGridPager";
	initJqgrid(grid_selector,pager_selector,globalUrl,colNames,colModels);
	
	spinner = createSpinner($("#level"),1,100);
	
	$("#addBtn").click(function(){
		validateForm.resetForm();
		$("#id").val("");
		//$("#level").val(0);
		spinner.ace_spinner("value",0);
		openDialog("roleDialog","roleDialogDiv","角色管理",480,260,true,"提交",function(){
			submitForm();
		 });
	});
	$("#updateBtn").click(function(){
		validateForm.resetForm();
		var selectedId = $("#itemGrid").jqGrid("getGridParam", "selrow");
		if (selectedId) {
			var rowData = $("#itemGrid").jqGrid("getRowData", selectedId);
			$("#id").val(rowData.id);
			$("#name").val(rowData.name);
			//$("#level").val(rowData.level);
			spinner.ace_spinner("value",rowData.level);
			openDialog("roleDialog","roleDialogDiv","角色管理",480,260,true,"提交",function(){
				submitForm();
			});
		}else{
			showMessage("请选择修改对象！");
		}
	});
	
	$("#searchBtn").click(function(){
		openDialog("searchDialog","searchDialogDiv","角色查询",620,260,true,"查询",function(){
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
	if($('#roleForm').valid()){
		 mask();
		 $("#roleForm").ajaxSubmit(function(data){
			 unmask();
			 if(data.success){
				 $("#roleDialog").dialog("close");
				 reloadGrid($("#itemGrid"));
			 }else{
				 showMessage(data.message);
			 }
		 });
	}
}