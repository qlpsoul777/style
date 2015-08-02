<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>修改角色</title>
	<link rel="stylesheet" href="${ctx}/static/css/platform_index.css" type="text/css"/>
	<link rel="stylesheet" href="${ctx}/static/css/zTreeStyle/zTreeStyle.css">
	<style type="text/css">
	#editForm input[type='text'],select,textarea{
		width:75%;
	}
	.error{ color: #ff0000; }
	</style>
	<script type="text/javascript" src="${ctx}/static/js/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/platform_index.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/validation/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/jquery.ztree.all-3.5.js"></script>
    <script type="text/javascript">
	    $(function(){
	    	$('#subBtn').on('click',function(){
	        	var array = [];
	        	var nodes = zTreeObj.getCheckedNodes(true);
	        	for (var i = 0; i < nodes.length; i++) {
	        		array.push(nodes[i].id); 
	            }
	        	$('#moduleIds').val(array);
	        });
	    });
    	
	    var zTreeObj;
	    var setting = {
	        showLine: true, //是否显示节点间的连线
	        checkable: true, //每个节点上是否显示 CheckBox
	        check: {
	            enable: true
	        },
	        data: {
	            simpleData: {
	                enable: true,
	                idKey: "id",
	                pIdKey: "pid"
	            }
	        }
	    };
	    // zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
	    var zNodes = $.parseJSON('${modules}');
	    $(document).ready(function () {
	        zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
	        var selectedNodes = $.parseJSON('${selectedIds}');
	        if(selectedNodes.length > 0){
	        	for(var i = 0; i < selectedNodes.length; i++){
	        		var node = zTreeObj.getNodeByParam("id",selectedNodes[i]);
	        		if(node){
	        			node.checked = true;
	                    zTreeObj.updateNode(node);
	        		}
	        	}
	        }
	    });
    </script>
</head>
<body>
<div>
	<div class="editbread">
	    <ul class="breadcrumbs">
         	 <li><a>系统管理</a></li>
             <li><a href="${ctx }/role/list">角色管理</a></li>
             <li>修改角色</li>
         </ul>
	</div>
	<div id="contentwrapper" class="contentwrapper">
		<div id="validation" class="subcontent">
			<form id="editForm" class="stdform" method="post" action="${ctx }/role/saveAdd">
				<input type="hidden" name="id" value="${role.id }"/>
				<input type="hidden" id="moduleIds" name="moduleIds"/>
			    <p>
			    	<label><span class="mustLable">*</span>角色名:</label>
			        <span class="field"><input type="text" name="name" id="name" value="${role.name }" class="longinput"/></span>
			    </p>
			    <p>
                	<label>描述</label>
                    <span class="field"><textarea cols="80" name="description" rows="5" class="longinput">${role.description }</textarea></span> 
                </p>
                <p>
                   <label>权限:</label>
                   <div>
                   <span class="field">
                    	<ul id="treeDemo" class="ztree"></ul>
                    </span>
                   </div>  
                </p>
			    <br />
			    <p class="stdformbutton">
			    	<button class="submit radius2" id="subBtn">保存</button>&nbsp; &nbsp;&nbsp; &nbsp;
			    	<input class="reset radius2" value="取消" type="reset">
			    </p>
			</form>
		</div>
	</div>
</div>
</body>
</html>