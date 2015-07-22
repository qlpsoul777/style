<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>用户管理</title>
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
    			window.location.href = "${ctx}/user/batchDelete?ids="+validateSelected().join(',');
    			return true;
    		}
    	});
    	return false;
    }
    //批量禁用/启用
    function batchEdit(){
    	window.location.href = "${ctx}/user/batchEdit?ids="+validateSelected().join(',');
    	return true;
    }
    //批量重置密码
    function batchResetPwd(){
    	jConfirm('您确定重置所选用户密码吗？', '重置密码', function(r) {
    		if(r){
    			window.location.href = "${ctx}/user/batchResetPwd?ids="+validateSelected().join(',');
    			return true;
    		}
    	});
    	return false;
    }
    //批量操作前验证是否选取了数据
    function validateSelected(){
    	var array = [];
    	$('input[name="chkName"]:checked').each(function(){
    		array.push($(this).val()); 
    	});
    	if(array.length <= 0){
    		jAlert('请先选择待操作的用户', '选取数据');
    		return false;
    	}
    	return array;
    }
    </script>
</head>
<body>
<div>
	<div class="pageheader">
	    <h1 class="pagetitle">用户管理</h1>
	    <ul class="hornav">
	        <li class="current"><a href="#updates" id="inner">内部用户</a></li>
	        <li class=""><a href="#activities" id="outter">注册用户</a></li>
	    </ul>
	</div>
	<div id="contentwrapper" class="contentwrapper">
		<div style="display: block;" id="updates" class="subcontent">
		  <form id="queryForm" action="${ctx }/user/list/INNER" method="get">
		  	<input id="totalSize" type="hidden" name="totalSize" value="${pageInfo.totalPages }"/>
		  	<input id="pageSize" type="hidden" name="pageSize" value="${pageInfo.size }"/>
		  	<input id="currentPage" type="hidden" name="currentPage" value="${pageInfo.number }"/>
			<div class="overviewhead">
                                             用户名:<input  id="userName" name="userName" type="text" value="${userName }">&nbsp;&nbsp;
			         角色名:<input  id="roleName" name="roleName" type="text" value="${roleName }">&nbsp;&nbsp;
			   <a href="javascript:search();" class="btn btn2 btn_blue btn_search radius50"><span>查询</span></a>
			   <div class="overviewselect">
					 <ul class="buttonlist">
						<li><a class="stdbtn btn_lime" href="${ctx }/user/addUser">新增</a></li>
						<li><a class="stdbtn btn_red" href="javascript:;" onclick="return batchDelete()">删除</a></li>
						<li><a class="stdbtn btn_yellow" href="javascript:;" onclick="return batchEdit()">启用/禁用</a></li>
						<li><a class="stdbtn btn_red" href="javascript:;" onclick="return batchResetPwd()">重置密码</a></li>	
				     </ul>
               </div>
             </div>
           </form>
			 <table class="stdtable stdtablecb" border="0" cellpadding="0" cellspacing="0">
                    <thead>
                        <tr>
                        	<th class="head0" align="center" width="3%"><input id="checkall" class="checkall" type="checkbox"/></th>
                            <th class="head1">用户名</th>
                            <th class="head0">角色名</th>
                            <th class="head1">性别</th>
                            <th class="head0">邮箱</th>
                            <th class="head1">状态</th>
                            <th class="head1">操作</th>
                        </tr>
                    </thead>
                    <tfoot>
                        <tr>
                            <th class="head0" colspan="7" align="center">
	                            <ul class="pagination" id="pageDiv">
	                            </ul>
							</th>
                        </tr>
                    </tfoot>
                    <tbody>
                    <c:forEach items="${pageInfo.content}" var="user">
                    	<tr>
                        	<td align="center"><input type="checkbox" name="chkName" value="${user.id}"/></td>
                            <td>${user.name}</td>
                            <td>
	                            <c:forEach items="${user.roles}" var="role">
	                                    ${role.name}&nbsp;
	                            </c:forEach>
                            </td>
                            <td>
                            	<c:choose>
                            		<c:when test="${user.sex == 0}">女</c:when>
                            		<c:when test="${user.sex == 1}">男</c:when>
                            		<c:otherwise>
                            			保密
                            		</c:otherwise>
                            	</c:choose>
                            </td>
                            <td class="center">${user.email}</td>
                            <td class="center">
                            	<c:if test="${user.status eq 'ENABLE'}">使用中</c:if>
                                <c:if test="${user.status eq 'NOTENABLE'}">已禁用</c:if>
                                <c:if test="${user.status eq 'LOCKED'}">已锁定</c:if>
                            </td>
                            <td class="center">
                            	<a href="${ctx }/user/editUser?id=${user.id}">修改</a>
                            </td>
                        </tr>
                    </c:forEach> 
                    </tbody>
                </table>
		</div>
		<div id="activities" class="subcontent" style="display: none;">
			<form id="queryForm" action="${ctx }/user/list/INNER" method="get">
			  	<input id="totalSize" type="hidden" name="totalSize" value="${pageInfo.totalPages }"/>
			  	<input id="pageSize" type="hidden" name="pageSize" value="${pageInfo.size }"/>
			  	<input id="currentPage" type="hidden" name="currentPage" value="${pageInfo.number }"/>
				<div class="overviewhead">
	                                             用户名:<input  id="userName" name="userName" type="text">&nbsp;&nbsp;
				         角色名:<input  id="roleName" name="roleName" type="text">&nbsp;&nbsp;
				   <a href="" class="btn btn2 btn_blue btn_search radius50"><span>查询</span></a>
				   <div class="overviewselect">
						 <ul class="buttonlist">
							<li><button class="stdbtn btn_lime">新增</button></li>
							<li><button class="stdbtn btn_red">删除</button></li>
							<li><button class="stdbtn btn_yellow">启用/禁用</button></li>	
					     </ul>
	               </div>
	             </div>
	           </form>
		</div>
	</div> 
	<br clear="all">        
</div>        
</body>
</html>
