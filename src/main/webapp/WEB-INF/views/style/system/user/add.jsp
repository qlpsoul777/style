<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>新增用户</title>
	<link rel="stylesheet" href="${ctx}/static/css/platform_index.css" type="text/css"/>
	<style type="text/css">
	#editForm input[type='text'],select,textarea{
		width:75%;
	}
	.error{ color: #ff0000; }
	</style>
	<script type="text/javascript" src="${ctx}/static/js/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/platform_index.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/validation/jquery.validate.min.js"></script>
    <script type="text/javascript">
    $(function(){
        $("#editForm").validate({
            errorElement:"em",
            rules: {
            	loginName: {required:true,remote:'${ctx}/user/repeatLoginName'},
            	name: "required",
            	email:{required:true,email:true}
            },
            messages: {
            	loginName: {required:"不能为空",remote:"登录名已存在"},
            	name: "不能为空",
            	email:{required:"不能为空",email:"邮箱格式有误"}
            }
        });
        
        $('#subBtn').on('click',function(){
        	var array = [];
        	$('input[name="roleId"]:checked').each(function(){
        		array.push($(this).val()); 
        	});
        	$('#roleIds').val(array);
        });
    });
    </script>
</head>
<body>
<div>
	<div class="editbread">
	    <ul class="breadcrumbs">
         	<li><a>系统管理</a></li>
             <li><a href="${ctx }/user/list/INNER">用户管理</a></li>
             <li>新增用户</li>
         </ul>
	</div>
	<div id="contentwrapper" class="contentwrapper">
		<div id="validation" class="subcontent">
			<form id="editForm" class="stdform" method="post" action="${ctx }/user/saveUser">
				<p>
			    	<label><span class="mustLable">*</span>登录名:</label>
			        <span class="field"><input type="text" name="loginName" id="loginName" class="longinput" /></span>
			    </p>
			    <p>
			    	<label><span class="mustLable">*</span>真实姓名:</label>
			        <span class="field"><input type="text" name="name" id="name" class="longinput" /></span>
			    </p>
			    <p>
			    	<label><span class="mustLable">*</span>邮箱：</label>
			        <span class="field"><input type="text" name="email" id="email" class="longinput" /></span>
			    </p>
			    <p>
			    	<label>联系方式：</label>
			        <span class="field"><input type="text" name="phoneNum" id="phoneNum" class="longinput" /></span> 
			    </p>
			    <p>
			    	<label>联系地址：</label>
			        <span class="field"><input type="text" name="address" id="address" class="longinput" /></span> 
			    </p>
			    <p>
                    <label>性别:</label>
                    <span class="field">
						<div class="editRadio">
							<span><input name="sex" value="0"  type="radio"/></span>
							<span>女 &nbsp; &nbsp;</span>
							<span><input name="sex" value="1" type="radio"/></span>
							<span>男 &nbsp; &nbsp;</span>
							<span><input name="sex" value="2" checked="checked" type="radio"/></span>
							<span>保密&nbsp; &nbsp;</span>
						</div>
                    </span>
                </p>
                <p>
                    <label>角色:</label>
                    <span class="field">
						<div class="editRadio">
							<input name="roleIds" value="" id="roleIds" type="hidden"/>
							<c:forEach items="${allRoles}" var="role">
								<span><input name="roleId" value="${role.id }" type="checkbox"/></span>
								<span>${role.name } &nbsp; &nbsp;</span>
							</c:forEach>
						</div>
                    </span>
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