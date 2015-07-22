<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>style后台管理系统</title>
	<link rel="stylesheet" href="${ctx}/static/css/platform_index.css" type="text/css" />
	<script type="text/javascript" src="${ctx}/static/js/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/platform_index.js"></script>
    <script type="text/javascript">
        $(function () {
			$('#menuTop li').eq(0).addClass('current');
        });
        function editUserInfo(id){
        	if(id){
        		return true;
        	}
        	return false;
        }
        function editPwd(id){
        	if(id){
        		return true;
        	}
        	return false;
        }
    </script>
</head>
<body class="withvernav">
	<div class="bodywrapper">
	<!-- 页面顶部start -->
	<div class="topheader">
        <div class="left">
            <h1 class="logo">Style</h1>
            <span class="slogan">后台管理系统</span>
            <br clear="all" />  
        </div>
        <div class="right">
            <div id="userinfo" class="userinfo">
            	<img src="${ctx}/static/images/thumbs/avatar.png" alt="" />
                <span>${user.loginName }</span>
            </div>
            <div id="userinfodrop" class="userinfodrop">
            	<div class="avatar">
	            	<c:choose>
					   <c:when test="${empty user.imgPath}">
					   		<img src="${ctx}/static/images/thumbs/avatarbig.png" alt="头像" style="height: 110px; width: 95px;"/>
					   </c:when>
					   <c:otherwise>
					   		<img src="${ctx}/${user.imgPath}" alt="头像" style="height: 110px; width: 95px;"/>
					   </c:otherwise>
					</c:choose>
					<div class="changetheme">
						<button class="stdbtn btn_orange">上传头像</button>
                    </div>
                </div>
                <div id="userdata" class="userdata">
                	<h4>${user.name }</h4>
                    <span class="email">${user.email }</span>
                    <ul>
                    	<li><a target="mainFrame"  href="${ctx}/user/editUserInfo/${user.id}" onclick="return editUserInfo('${user.id}')">修改资料</a></li>
                        <li><a target="mainFrame"  href="${ctx}/user/editPwd/${user.id}" onclick="return editPwd('${user.id}')">密码设置</a></li>
                        <li><a target="mainFrame" href="">我的消息</a></li>
                        <li><a href="${ctx }/platform/login">退出</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
	<div class="header"></div>
	<!-- 页面顶部end -->
    <!-- 左侧菜单start -->
	    <div class="vernav2 iconmenu">
    	<ul id="menuTop">
    		<c:if test="${!empty menus}">
    			<c:forEach items="${menus}" var="menu">
	    			<li><a href="#${menu.id }" class="editor" data="outmenu">${menu.name}</a>
		    			<span class="arrow"></span>
		    			<ul id="${menu.id}">
		    				<c:forEach items="${menu.children}" var="child">
			               		<li><a href="${ctx}${child.url}" target="mainFrame" data="inmenu">${child.name}</a></li>
		    				</c:forEach>
			            </ul>
		            </li>
    			</c:forEach>
    		</c:if>
        </ul>
        <a class="togglemenu"></a>
        <br /><br />
    </div>
	<!-- 左侧菜单end -->
	<!-- 右侧内容start -->
	<div class="centercontent">
		<iframe id="mainFrame" name="mainFrame"  src="" frameborder="0" scrolling="auto" width="100%"
                    height="auto" style="position: absolute; margin: 0px; left: 0px; right: 0px; top: 0px;
                     bottom: 0px; height: 570px; z-index: 0; display:
                      block; visibility: visible; padding-left:0px; background-color:#FFFFFF"></iframe>
	</div>
	<!-- 右侧内容end -->
	</div>
</body>
</html>
