<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>修改密码</title>
	<link rel="stylesheet" href="${ctx}/static/css/platform_index.css" type="text/css"/>
	<style type="text/css">
	#editForm input[type='password']{
		width:75%;
		height:35px;
	}
	.error{ color: #ff0000; }
	</style>
	<script type="text/javascript" src="${ctx}/static/js/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/platform_index.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/validation/jquery.validate.min.js"></script>
    <script type="text/javascript">
    $(function(){
    	var code = parseInt('${msg.code}',10);
    	if(!isNaN(code)){
    		  if(code <= 0){
    			  $('.notibar').addClass('msgerror');
    			  $('.notibar').show();
    		  }else{
    			  $('.notibar').addClass('msgsuccess');
    			  $('.notibar').show();
    			  setTimeout("toIndex()",5000);
    		  }
    	}
    	$('.close').on('click',function(){
    		$(this).parent().hide();
    	});
        $("#editForm").validate({
            errorElement:"em",
            rules: {
            	oldPwd: {required:true},
            	newPwd: {required:true,rangelength:[6,20]},
            	repeatNewPwd:{required:true,equalTo:'#newPwd'}
            },
            messages: {
            	oldPwd: {required:"不能为空"},
            	newPwd: {required:"不能为空",rangelength:"密码长度为6-20个字符"},
            	repeatNewPwd:{required:"不能为空",equalTo:"与新密码不一致"}
            }
        });
        
    });
    function toIndex(){
    	top.document.location.href = '${ctx}/platform/login';
    }
    </script>
</head>
<body>
<div>
	<div>
		<div class="notibar" style="display:none;">
           <a class="close"></a>
           <p>${msg.msg }</p>
         </div>
	</div>
	<div class="editbread">
	    <ul class="breadcrumbs">
         	<li><a>首页</a></li>
             <li>修改密码</li>
         </ul>
	</div>
	<div id="contentwrapper" class="contentwrapper">
		<div id="validation" class="subcontent">
			<form id="editForm" class="stdform" method="post" target="_self" action="${ctx }/user/savePwd">
				<input type="hidden" name="id" value="${user.id }"/>
			    <p>
			    	<label><span class="mustLable">*</span>原始密码:</label>
			        <span class="field"><input type="password" name="oldPwd" id="oldPwd" class="longinput"/></span>
			    </p>
			    <p>
			    	<label><span class="mustLable">*</span>新密码：</label>
			        <span class="field"><input type="password" name="newPwd" id="newPwd" class="longinput"/></span>
			    </p>
			    <p>
			    	<label><span class="mustLable">*</span>重复新密码：</label>
			        <span class="field"><input type="password" name="repeatNewPwd" id="repeatNewPwd" class="longinput"/></span> 
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