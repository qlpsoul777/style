<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="qlp" uri="/qlpTagLib" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="locale" value="${pageContext.request.locale}"/>
<!DOCTYPE html>
<html>
<head>
    <title>注册页面</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${ctx}/static/css/bootstrap.css" media="screen">
    <script src="${ctx}/static/js/jquery.js"></script>
    <script src="${ctx}/static/js/bootstrap.js"></script>
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span2"></div>
        <div class="span8">
            <h1>恭喜您: ${user.loginName}，登录成功</h1>
            <table class="table">
                <thead>
                <th>模块id</th>
                <th>模块名</th>
                </thead>
                <tbody>
                <c:forEach items="${apps}" var="a">
                    <tr>
                        <td>${a.id}</td>
                        <td>${a.name}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <table class="table">
                <thead>
                <th>功能id</th>
                <th>功能名</th>
                <th>模块名</th>
                <th>操作</th>
                </thead>
                <tbody>
                <c:forEach items="${funcs}" var="f">
                    <tr>
                        <td>${f.id}</td>
                        <td>${f.name}</td>
                        <td>${f.application.name}</td>
                        <td><a href="${ctx}/${f.uri}">管理</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="span2"></div>
    </div>
</div>
</body>
</html>


