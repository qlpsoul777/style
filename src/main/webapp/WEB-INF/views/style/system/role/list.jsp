<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>角色管理</title>
	<link rel="stylesheet" href="${ctx}/static/css/platform_index.css" type="text/css"/>
	<link rel="stylesheet" href="${ctx}/static/css/alert/jquery.alerts.css" type="text/css"/>
	
	<script type="text/javascript" src="${ctx}/static/js/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/platform_index.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/page_sync.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/alert/jquery.alerts.js"></script>
    <script type="text/javascript">
    $(function () {
    	var currentPage = $('#currentPage').val(),
    	totalSize = $('#totalSize').val(),
    	pageSize = $('#pageSize').val();
    	PageSync.init(currentPage,pageSize,totalSize);//分页
    	//处理全选
    	$('#checkall').on('click',function () {
	         $('input[name = "chkName"]').prop('checked', $('#checkall').prop('checked'));
	     });
    });
    //查询
    function search(){
    	$('#currentPage').val(0);
    	$('#queryForm').submit();
    }
    //批量删除
    function batchDelete(){
    	jConfirm('您确定删除吗？', '删除数据', function(r) {
    		if(r){
    			window.location.href = "${ctx}/role/batchOperate/D?ids="+validateSelected().join(',');
    			return true;
    		}
    	});
    	return false;
    }
    //批量禁用/启用
    function batchEdit(){
    	window.location.href = "${ctx}/role/batchOperate/E?ids="+validateSelected().join(',');
    	return true;
    }
    
    //批量操作前验证是否选取了数据
    function validateSelected(){
    	var array = [];
    	$('input[name="chkName"]:checked').each(function(){
    		array.push($(this).val()); 
    	});
    	if(array.length <= 0){
    		jAlert('请先选择待操作的角色', '选取数据');
    		return false;
    	}
    	return array;
    }
    </script>
</head>
<body>
<div>
	<div class="pageheader">
	    <h1 class="pagetitle">角色管理</h1>
	</div>
	<div id="contentwrapper" class="contentwrapper">
		  <form id="queryForm" action="${ctx }/role/list" method="get">
		  	<input id="totalSize" type="hidden" name="totalSize" value="${pageInfo.totalPages }"/>
		  	<input id="pageSize" type="hidden" name="pageSize" value="${pageInfo.size }"/>
		  	<input id="currentPage" type="hidden" name="currentPage" value="${pageInfo.number }"/>
			<div class="overviewhead">
			         角色名:<input  id="name_li" name="name_li" type="text" value="${roleName}">&nbsp;&nbsp;
			   <a href="javascript:search();" class="btn btn2 btn_blue btn_search radius50"><span>查询</span></a>
			   <div class="overviewselect">
					 <ul class="buttonlist">
						<li><a class="stdbtn btn_lime" href="${ctx }/role/add">新增</a></li>
						<li><a class="stdbtn btn_red" href="javascript:;" onclick="return batchDelete()">删除</a></li>
						<li><a class="stdbtn btn_yellow" href="javascript:;" onclick="return batchEdit()">启用/禁用</a></li>
				     </ul>
               </div>
             </div>
           </form>
			 <table class="stdtable stdtablecb" border="0" cellpadding="0" cellspacing="0">
                    <thead>
                        <tr>
                        	<th class="head0" align="center" width="3%"><input id="checkall" class="checkall" type="checkbox"/></th>
                            <th class="head1">角色名</th>
                            <th class="head0">状态</th>
                            <th class="head1">描述</th>
                            <th class="head0">操作</th>
                        </tr>
                    </thead>
                    <tfoot>
                        <tr>
                            <th class="head0" colspan="5" align="center">
	                            <ul class="pagination" id="pageDiv">
	                            </ul>
							</th>
                        </tr>
                    </tfoot>
                    <tbody>
                    <c:forEach items="${pageInfo.content}" var="role">
                    	<tr>
                        	<td align="center"><input type="checkbox" name="chkName" value="${role.id}"/></td>
                            <td>${role.name}</td>
                            <td>
	                           <c:if test="${role.enable}">使用中</c:if>
                               <c:if test="${!role.enable}">已禁用</c:if>
                            </td>
                            <td class="center">${role.description}</td>
                            <td class="center">
                            	<a href="${ctx }/role/edit?id=${role.id}">修改</a>
                            </td>
                        </tr>
                    </c:forEach> 
                    </tbody>
                </table>
	</div> 
	<br clear="all">        
</div>        
</body>
</html>
