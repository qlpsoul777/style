<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="locale" value="${pageContext.request.locale}"/>
<!DOCTYPE html>
<html>
<head>
    <title>登录</title>
    <link href="${ctx}/static/css/main.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctx}/static/js/jquery.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/fun.base.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/script.js"></script>
    <script src="${ctx}/static/js/DD_belatedPNG.js" type="text/javascript"></script>
    <script >
        $(document).ready(function(){
            DD_belatedPNG.fix('.png')
        });
    </script>
</head>
<body>


<div class="login">
    <div class="box png">
        <div class="logo png"></div>
        <div class="input">
            <form action="${ctx}/user/index/login" method="post">
                <div class="log">
                    <div class="name">
                        <label>用户名</label><input type="text" class="text" id="value_1" placeholder="用户名" name="loginName" tabindex="1">
                    </div>
                    <div class="pwd">
                        <label>密　码</label><input type="password" class="text" id="value_2" placeholder="密码" name="password" tabindex="2">
                        <input type="submit" class="submit" tabindex="3" value="登录">
                        <div class="check"></div>
                    </div>
                    <div class="tip">${msg}</div>
                </div>
            </form>
        </div>
    </div>
    <div class="air-balloon ab-1 png"></div>
    <div class="air-balloon ab-2 png"></div>
    <div class="footer"></div>
</div>
</body>
</html>