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
    <title>新闻管理</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${ctx}/static/css/bootstrap.css" media="screen">
    <link rel="stylesheet" href="${ctx}/static/css/font-awesome.css">
    <link rel="stylesheet" href="${ctx}/static/css/qlp.css">
    <script src="${ctx}/static/js/jquery.js"></script>
    <script src="${ctx}/static/js/bootstrap.js"></script>
    <script type="text/javascript">
        $(function () {
            var categoryId = '${currentNodeId}';
            var siteId = '${siteId}';
            if(categoryId == siteId){
                $('#createCont').hide();
            }
        });
    </script>
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <form id="queryForm" action="${ctx}/cms/category/childrenList" method="get">
            <input type="hidden" name="currentNodeId" value="${currentNodeId}"/>
            <input type="hidden" name="allFlag" value="${allFlag}"/>
            <div class="span10">
                <div>
                    <h3>新闻列表</h3>
                </div>
                <div class="toolbar">
                    <%--<ul>
                        <li><h4>角色名：</h4></li>
                        <li><input type="text" name="roleName" value="${roleName}" placeholder="例：系统管理员"/></li>
                        <li><h4>角色类型：</h4></li>
                        <li>
                            <select name="roleType" id="roleType">
                                <option value="" selected="selected">请选择角色类型</option>
                                <option value="INNER" <c:if test="${roleType eq 'INNER'}">selected="selected" </c:if>>
                                    内部角色
                                </option>
                                <option value="OUTER" <c:if test="${roleType eq 'OUTER'}">selected="selected" </c:if>>
                                    外部角色
                                </option>
                            </select>
                        <li>
                            <button id="subFind" type="submit" class="btn btn-success">查询</button>
                        </li>
                    </ul>
                    </br>--%>
                    <ul>
                        <li>
                            <a href="${ctx}/cms/content/create?categoryId=${currentNodeId}" id="createCont" class="btn btn-success">新增</a>
                        </li>
                        <%--<li>
                            <a href="#" class="btn btn-warning" id="batchUse">启用/禁用</a>
                        </li>--%>
                    </ul>
                </div>
                <table class="table" id="queryTable">
                    <thead>
                    <tr>
                        <th><input type="checkbox" id="chk_all" name="chk_all"></th>
                        <th>新闻标题</th>
                        <th>新闻类型</th>
                        <th>状态</th>
                        <th>所属分类</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pageInfo.content}" var="content">
                        <tr data-row-id="${content.id}">
                            <td><input type="checkbox" name="chkName" value="${content.id}"/></td>
                            <td>${category.name}</td>
                            <td>
                                <c:if test="${category.type eq 'SIMPLE'}">普通栏目</c:if>
                                <c:if test="${category.type eq 'LINK'}">链接栏目</c:if>
                            </td>
                            <td>
                                ${category.path}
                            </td>
                            <td>
                                ${category.sort}
                            </td>
                            <td>
                                <c:if test="${category.visiable eq '1'}">
                                    <i class="icon-ok-sign">已发布</i>
                                </c:if>
                                <c:if test="${category.visiable eq '0'}">
                                    <i class="icon-remove-sign">未发布</i>
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

    </div>
</div>
</body>
</html>