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
    <title>站点管理</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${ctx}/static/css/bootstrap.css" media="screen">
    <link rel="stylesheet" href="${ctx}/static/css/font-awesome.css">
    <link rel="stylesheet" href="${ctx}/static/css/qlp.css">
    <script src="${ctx}/static/js/jquery.js"></script>
    <script src="${ctx}/static/js/bootstrap.js"></script>
    <script type="text/javascript">
        $(function () {
        });
    </script>
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span2"></div>
        <form id="queryForm" action="${ctx}/cms/site/list" method="get">
            <div class="span8">
                <div>
                    <h3>站点管理列表</h3>
                </div>
                <div class="toolbar">
                    <ul>
                        <li>
                            <a href="${ctx}/cms/site/create" class="btn btn-success">新增</a>
                        </li>
                    </ul>
                </div>
                <table class="table" id="queryTable">
                    <thead>
                    <tr>
                        <th>站点名称</th>
                        <th>英文名</th>
                        <th>域名</th>
                        <th>状态</th>
                        <th>简介</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pageInfo.content}" var="site">
                        <tr>
                            <td>${site.cName}</td>
                            <td>${site.eName}</td>
                            <td>${site.domianName}</td>
                            <td>
                                <c:if test="${site.visiable eq '1'}">
                                    <i class="icon-ok-sign">使用中</i>
                                </c:if>
                                <c:if test="${site.visiable eq '0'}">
                                    <i class="icon-remove-sign">已停用</i>
                                </c:if>
                            </td>
                            <td>
                            <qlp:htmlSplit htmlStr="${site.description}" length="15"/>
                            </td>
                            <td>
                                <a href="${ctx}/cms/site/edit?id=${site.id}" class="btn btn-warning">修改</a>
                                <a href="${ctx}/cms/site/delete?id=${site.id}" class="btn btn-primary">
                                    <c:if test="${site.visiable eq '0'}">
                                        启用
                                    </c:if>
                                    <c:if test="${site.visiable eq '1'}">
                                        停用
                                    </c:if>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="pagination pagination-centered">
                    <qlp:page page="${pageInfo}" formId="queryForm"></qlp:page>
                </div>
            </div>
        </form>
        <div class="span2"></div>
    </div>
</div>
</body>
</html>