<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>权限管理</title>
	<link rel="stylesheet" href="${ctx}/static/css/platform_index.css" type="text/css"/>
	<link rel="stylesheet" href="${ctx}/static/css/TreeGrid.css" type="text/css"/>
	<link rel="stylesheet" href="${ctx}/static/css/alert/jquery.alerts.css" type="text/css"/>
	<script type="text/javascript" src="${ctx}/static/js/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/TreeGrid.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/alert/jquery.alerts.js"></script>
    <script type="text/javascript">
        /*单击数据行后触发该事件
        id：行的id
        index：行的索引。
        data：json格式的行数据对象。
        */
        function itemClickEvent(id, index, data) {
            jQuery("#currentRow").val(id + ", " + index + ", " + TreeGrid.json2str(data));
            alert(TreeGrid.json2str(data));
        }

        function customStatus(row, col){
        	var status = row[col.dataField]?"使用中":"已禁用";
        	return status;
        }
        function customOrgName(row, col) {
            var name = row[col.dataField] || "";
            return name;
        }

        function customLook(row, col) {
            return "<a href='' style='color:blue;'>修改</a>";
        }
        /**
       		 新增同级权限
        */
        function addSameLevelM(){
        	
    		return false;
        }
        /**
       		 展开、关闭所有节点。
        	isOpen=Y表示展开，isOpen=N表示关闭
        */
        function expandAll(isOpen) {
            treeGrid.expandAll(isOpen);
        }
$(function(){
	var data = $.parseJSON('${jsonObj}');
	var config = {
            id: "tg1",
            width: "100%",
            renderTo: "div1",
            headerAlign: "left",
            headerHeight: "30",
            dataAlign: "left",
            indentation: "20",
            folderOpenIcon: "${ctx}/static/img/collapse.png",
            folderCloseIcon: "${ctx}/static/img/expand.png",
            defaultLeafIcon: "${ctx}/static/img/sort_asc.png",
            hoverRowBackground: "false",
            folderColumnIndex: "0",
            itemClick: "itemClickEvent",
            columns: [
                {headerText: "", headerAlign: "center", dataAlign: "center", width: "5%" },
                {headerText: "名称", dataField: "name", headerAlign: "center", handler: "customOrgName", width: "25%"},
                {headerText: "权限关键字", dataField: "permission", headerAlign: "center", dataAlign: "center", width: "25%" },
                {headerText: "url", dataField: "url", headerAlign: "center", dataAlign: "center", width: "25%" },
                {headerText: "排序", dataField: "sort", headerAlign: "center", dataAlign: "center", width: "5%" },
                {headerText: "层级", dataField: "level", headerAlign: "center", dataAlign: "center", width: "5%" },
                {headerText: "状态", dataField: "enable", headerAlign: "center",handler: "customStatus", dataAlign: "center", width: "5%" },
                {headerText: "操作", headerAlign: "center", dataAlign: "center", width: "5%", handler: "customLook"}
            ],
            data:data
        };
	//创建一个组件对象
    var treeGrid = new TreeGrid(config);
    treeGrid.show();
    treeGrid.expandAll('N');
});
    
    </script>
</head>
<body>
<div>
	<div class="pageheader">
	    <h1 class="pagetitle">权限管理</h1>
	</div>
	<div id="contentwrapper" class="contentwrapper">
	<div class="overviewhead">
		<div class="overviewselect">
			 <ul class="buttonlist">
				<li><a class="stdbtn btn_lime" onclick="addSameLevelM()">新增同级权限</a></li>
				<li><a class="stdbtn btn_red" href="javascript:;" onclick="return batchDelete()">新增下级权限</a></li>
				<li><a class="stdbtn btn_yellow" href="javascript:;" onclick="return batchEdit()">删除</a></li>
				<li><a class="stdbtn btn_red" href="javascript:;" onclick="return batchResetPwd()">禁用/启用</a></li>	
		     </ul>
         </div>
         </div>
		<div id="div1">
    	</div>  
	</div> 
	<br clear="all">        
</div>        
</body>
</html>
