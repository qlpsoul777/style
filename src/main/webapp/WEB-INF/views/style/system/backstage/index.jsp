<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="locale" value="${pageContext.request.locale}"/>
<!DOCTYPE html>
<html>
<head>
    <title>后台管理</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${ctx}/static/css/bootstrap.css" media="screen">
    <link rel="stylesheet" href="${ctx}/static/css/font-awesome.css">
    <link href="${ctx}/static/css/admin.base.css" rel="stylesheet" type="text/css">
    <script src="${ctx}/static/js/jquery.js"></script>
    <script src="${ctx}/static/js/bootstrap.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/gufeng.houtai.js"></script>
    <script type="text/javascript">
        $(function () {

        });

    </script>
</head>
<body>
<div class="container-fluid" style="padding: 0px;!important">
    <div class="row-fluid">
        <div id="topframe">
            <span class="logo"></span>
            <div style="width:300px; height:100px; float:right;">
                <div class="navigation">
                    <ul>
                        <li><a href="${ctx}/style/system/main" target="Main">系统首页</a></li>
                        <li><a href="javascript:void(0)" target="Main">修改密码</a></li>
                        <li><a href="javascript:void(0)" target="_top">退出登录</a></li>
                    </ul>
                </div>
                <div class="admin">${name}，您好，今天是<span id="currentTime"></span></div>
            </div>
        </div>
    </div>
    <div class="row-fluid">
        <div class="span2" style="height: auto">
            <div id="Menu">
                <ul class="OutMenu">
                    <c:forEach items="${apps}" var="app" varStatus="a">
                        <c:set value="${app.id}" var="aid"></c:set>
                        <li id="${a.index}">
                            <a href="javascript:void(0)">${app.name}</a>
                            <ul class="InMenu">
                                <c:forEach items="${funMap.get(aid)}" var="func">
                                    <li><a href="${ctx}/${func.uri}" target="contentFrame">${func.name}</a></li>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <div class="span10" style="height: auto">
            <iframe id="contentFrame" name="contentFrame"  src="" frameborder="0" scrolling="auto" width="100%"
                    height="auto" style="position: absolute; margin: 0px; left: 199px; right: 0px; top: 109px;
                     bottom: 0px; height: 533px; width: 1167px; z-index: 0; display:
                      block; visibility: visible; padding-left:0px; background-color:#FFFFFF"></iframe>
            <!--位置及导航-->
           <div id="NavBox">
                <div class="location">当前位置：古风后台管理系统 >>> 首页</div>
                <!--menu-->
                <ul class="menu">
                    <li><a href="#" class="selected">系统日志</a></li>
                    <li><a href="#">高级管理</a></li>
                </ul>
                <!--/menu-->
            </div>

        </div>
    </div>
</div>
</body>
</html>