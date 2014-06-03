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
        <div class="span2"></div>
        <form id="queryForm" action="${ctx}/user/index/list/${type}" method="get">
            <div class="span8">
                <div>
                    <h3>注册用户管理列表</h3>
                </div>
                <div class="toolbar">
                    <ul>
                        <li><h4>用户名：</h4></li>
                        <li><input type="text" name="userName" value="${userName}" placeholder="例：张三"/></li>
                        <li><h4>角色名：</h4></li>
                        <li><input type="text" name="roleName" value="${roleName}" placeholder="例：付费注册用户"/></li>
                        <li>
                            <button id="subFind" type="submit" class="btn btn-success">查询</button>
                        </li>
                    </ul>
                </div>
                <table class="table" id="queryTable">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>用户名</th>
                        <th>角色名</th>
                        <th>性别</th>
                        <th>邮箱</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pageInfo.content}" var="user" varStatus="i">
                        <tr>
                            <td>${i.index+1}</td>
                            <td>${user.name}</td>
                            <td>
                                <c:forEach items="${user.roles}" var="role">
                                    ${role.name}&nbsp;
                                </c:forEach>
                            </td>
                            <td>${user.sex}</td>
                            <td>${user.email}</td>
                            <td>
                                <c:if test="${user.state eq 'ENABLE'}">
                                    <i class="icon-ok-sign">使用中</i>
                                </c:if>
                                <c:if test="${user.state eq 'DISENABLE'}">
                                    <i class="icon-remove-sign">已禁用</i>
                                </c:if>
                            </td>
                            <td>
                                <a href="${ctx}/user/index/update?id=${user.id}&&type=${type}"
                                   class="btn btn-small btn-primary">修改</a>
                                <c:if test="${user.state eq 'ENABLE'}">
                                    <a href="${ctx}/user/index/batchUse?ids=${user.id}&type=${type}"
                                       class="btn btn-small btn-warning">禁用</a>
                                </c:if>
                                <c:if test="${user.state eq 'DISENABLE'}">
                                    <a href="${ctx}/user/index/batchUse?ids=${user.id}&type=${type}"
                                       class="btn btn-small btn-warning">启用</a>
                                </c:if>
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