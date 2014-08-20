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
                    <h3>站点列表</h3>
                </div>
                <table class="table" id="queryTable">
                    <thead>
                    <tr>
                        <th>站点名称</th>
                        <th>域名</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pageInfo.content}" var="site">
                        <tr>
                            <td>${site.cName}</td>
                            <td>${site.domianName}</td>
                            <td>
                                <a href="${ctx}/cms/content/look?siteId=${site.id}" class="btn btn-warning">进入</a>
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