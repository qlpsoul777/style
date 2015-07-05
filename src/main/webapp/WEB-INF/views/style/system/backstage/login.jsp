<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <title>登录</title>
    <link href="${ctx}/static/css/main.css" rel="stylesheet" type="text/css" />
    <style>
        .jcaptcha-btn{
            position: absolute;
            padding-left: 10px;
            text-decoration: none;
        }
        .login-btn{
            float: left;
            width: 100%;
            text-align: center;
        }
    </style>
    <script type="text/javascript" src="${ctx}/static/js/jquery.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/fun.base.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/script.js"></script>
    <script src="${ctx}/static/js/DD_belatedPNG.js" type="text/javascript"></script>
    <script >
        $(document).ready(function(){
            DD_belatedPNG.fix('.png')
        });

        $(function() {
            $(".jcaptcha-btn").click(function() {
                $(".jcaptcha-img").attr("src", '${ctx}/jcaptcha.jpg?'+new Date().getTime());
            });
        });
    </script>
</head>
<body>


<div class="login">
    <div class="box png">
        <div class="logo png"></div>
        <div class="input">
            <form action="${ctx}/platform/login" method="post">
                <div class="log">
                    <div class="name">
                        <label>用户名：</label>
                        <input type="text" class="text" id="value_1" placeholder="用户名" name="username" tabindex="1">
                    </div>
                    <div class="pwd">
                        <label>密　码：</label>
                        <input type="password" class="text" id="value_2" placeholder="密码" name="password" tabindex="2">
                    </div>
                    <div class="captcha" style="bottom: 0px;position: relative">
                        <label>验证码：</label>
                        <input type="text" class="text" id="value_3" placeholder="验证码" name="jCaptchaCode" tabindex="3" style="width: 100px !important;">
                        <a class="jcaptcha-btn" href="javascript:;">
                            <img class="jcaptcha-img" src="${ctx}/jcaptcha.jpg" title="点击更换验证码" >
                            <strong style="text-decoration-line: none">换一张</strong>
                        </a>
                    </div>
                    <div class="login-btn">
                        <input type="submit" class="submit" tabindex="3" value="登录"/>
                        <input type="reset" class="reset" tabindex="4" value="重置"/>
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