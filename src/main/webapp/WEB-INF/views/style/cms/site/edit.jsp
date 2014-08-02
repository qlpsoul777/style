<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="locale" value="${pageContext.request.locale}"/>
<!DOCTYPE html>
<html>
<head>
    <title>站点管理</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${ctx}/static/css/bootstrap.css" media="screen">
    <link rel="stylesheet" href="${ctx}/static/css/qlp.css">
    <style type="text/css" rel="stylesheet">
        .error{
            color: red;
        }
    </style>
    <script src="${ctx}/static/js/jquery.js"></script>
    <script src="${ctx}/static/js/bootstrap.js"></script>
    <script src="${ctx}/static/js/validation/jquery.validate.js"></script>
    <script type="text/javascript">
        $(function(){
            $("#editForm").validate({
                errorElement:"em",
                rules: {
                    cName: "required",
                    path: {
                        required: true,
                        url: true
                    },
                    domianName: "required"
                },
                messages: {
                    cName: "不能为空",
                    path: {
                        required: "不能为空",
                        url: "请输入合法的网址"
                    },
                    domianName: "不能为空"
                }
            });
        })
    </script>
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span1"></div>
        <div class="span10">
            <div>
                <h3>站点编辑</h3>
            </div>
            <form:form id="editForm" action="${ctx}/cms/site/save" method="post" modelAttribute="site" cssClass="form-horizontal">
                <form:hidden path="id" />
                <div class="toolbar">
                    <ul>
                        <li>
                            <button class="btn btn-success" type="submit">保存</button>
                        </li>
                        <li>
                            <a href="${ctx}/cms/site/list" class="btn btn-warning">取消</a>
                        </li>
                    </ul>
                </div>
            <table class="table">
                <tr>
                    <td>
                        <div class="control-group">
                            <%--@declare id="cname"--%><label class="control-label" for="cName">站点中文名：</label>
                            <div class="controls">
                                <form:input path="cName"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="control-group">
                            <%--@declare id="ename"--%><label class="control-label" for="eName">站点英文名：</label>
                            <div class="controls">
                                <form:input path="eName"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="control-group">
                            <%--@declare id="simplename"--%><label class="control-label" for="simpleName">站点简称：</label>
                            <div class="controls">
                                <form:input path="simpleName"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="control-group">
                            <%--@declare id="domianname"--%><label class="control-label" for="domianName">域名：</label>
                            <div class="controls">
                                <form:input path="domianName"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="control-group">
                            <%--@declare id="path"--%><label class="control-label" for="path">访问路径：</label>
                            <div class="controls">
                                <form:input path="path"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <div class="control-group">
                            <%--@declare id="description"--%><label class="control-label" for="description">站点简介：</label>

                            <div class="controls">
                                <form:textarea path="description" style="width: 750px;height: 150px;resize: none"/>
                            </div>
                        </div>
                    </td>
                </tr>
            </form:form>
        </div>
        <div class="span1"></div>
    </div>
</div>
</body>
</html>