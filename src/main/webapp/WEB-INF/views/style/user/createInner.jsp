<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="locale" value="${pageContext.request.locale}"/>
<!DOCTYPE html>
<html>
<head>
    <title>用户管理列表</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${ctx}/static/css/bootstrap.css" media="screen">
    <link rel="stylesheet" href="${ctx}/static/css/font-awesome.css">
    <link rel="stylesheet" href="${ctx}/static/css/qlp.css">
    <script src="${ctx}/static/js/jquery.js"></script>
    <script src="${ctx}/static/js/bootstrap.js"></script>
    <script type="text/javascript">
    </script>
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span1"></div>
        <form id="createForm" action="${ctx}/user/index/saveInner" method="post" class="form-horizontal">
            <div class="span10">
                <div>
                    <h3>新增用户</h3>
                </div>
                <div class="toolbar">
                    <ul>
                        <li>
                            <a href="${ctx}/user/index/createInner" class="btn btn-success">保存</a>
                        </li>
                        <li>
                            <a href="#" class="btn btn-danger" id="deleteUser" onclick="deleteAll()">取消</a>
                        </li>
                    </ul>
                </div>

            </div>
        </form>
        <div class="span1"></div>
    </div>
</div>
</body>
</html>