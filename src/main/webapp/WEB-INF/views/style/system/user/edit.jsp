<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>修改信息</title>
	<link rel="stylesheet" href="${ctx}/static/css/platform_index.css" type="text/css"/>
	<link rel="stylesheet" href="${ctx}/static/css/date/pikaday.css" type="text/css"/>
	<style type="text/css">
	#editForm input[type='text'],select,textarea{
		width:75%;
	}
	.error{ color: #ff0000; }
	</style>
	<script type="text/javascript" src="${ctx}/static/js/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/platform_index.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/validation/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/date/moment.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/date/pikaday.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/date/pikaday.jquery.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/date/date-init.js"></script>
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
        
        DateTools.common($('#datepicker'), 'en_US');
        $('input:radio').eq('${user.sex}').attr('checked','checked');
    });
    </script>
</head>
<body>
<div>
	<div class="editbread">
	    <ul class="breadcrumbs">
         	<li><a>首页</a></li>
             <li>修改信息</li>
         </ul>
	</div>
	<div id="contentwrapper" class="contentwrapper">
		<div id="validation" class="subcontent">
			<form id="editForm" class="stdform" method="post" target="_top" action="${ctx }/user/saveUser/2">
				<input type="hidden" name="id" value="${user.id }"/>
			    <p>
			    	<label><span class="mustLable">*</span>真实姓名:</label>
			        <span class="field"><input type="text" name="name" id="name" class="longinput" value="${user.name }"/></span>
			    </p>
			    <p>
			    	<label><span class="mustLable">*</span>邮箱：</label>
			        <span class="field"><input type="text" name="email" id="email" class="longinput" value="${user.email }"/></span>
			    </p>
			    <p>
			    	<label>联系方式：</label>
			        <span class="field"><input type="text" name="phoneNum" id="phoneNum" class="longinput" value="${user.phoneNum }"/></span> 
			    </p>
			    <p>
			    	<label>联系地址：</label>
			        <span class="field"><input type="text" name="address" id="address" class="longinput" value="${user.address }"/></span> 
			    </p>
			    <p>
                    <label>性别:</label>
                    <span class="field">
						<div class="editRadio" id="sexRadio">
							<span><input name="sex" value="0"  type="radio"/></span>
							<span>女 &nbsp; &nbsp;</span>
							<span><input name="sex" value="1" type="radio"/></span>
							<span>男 &nbsp; &nbsp;</span>
							<span><input name="sex" value="2" type="radio"/></span>
							<span>保密&nbsp; &nbsp;</span>
						</div>
                    </span>
                </p>
                <p>
                  <label>出生日期:</label>
                  <span class="field">
                  	<input type="text" id="datepicker" name="birthday" value="${user.birthday }"/>
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