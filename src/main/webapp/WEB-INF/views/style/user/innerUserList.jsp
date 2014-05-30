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
        $(function () {
            // 处理全选
            $('#chk_all').click(function () {
                $('input[name = "chkName"]').prop('checked', $('#chk_all').prop('checked'));
            });
            //批量启用/禁用用户
            $('#batchUse').click(function () {
                var ids = checkSelect();
                alert(ids);
            });
            <%--//行点击事件--%>
            <%--$('#queryTable tbody tr').css({'cursor': 'pointer'}).on('click', function () {--%>
            <%--window.location.href = '${ctx}/user/index/info?id=' + $(this).attr('data-row-id');--%>
            <%--})--%>
            <%--$('input[name="chkName"]').parent('td').on('click', function (event) {--%>
            <%--event.stopPropagation();--%>
            <%--});--%>
        });
        function checkSelect() {
            var names = document.getElementsByName("chkName");
            var ids = "";
            if (names.length >= 1) {
                for (var i = 0; i < names.length; i++) {
                    if (names[i].checked == true) {
                        ids = ids + names[i].value + ",";
                    }
                }
                ids = ids.substring(0, ids.length - 1);
            }
            if (ids.length == 0) {
                alert("请选择需要操作的项！");
                return false;
            } else {
                return ids;
            }
        }
        //批量删除
        function deleteAll() {
            var msg = "您确定要删除吗？";
            var names = document.getElementsByName("chkName");
            var ids = "";
            if (names.length >= 1) {
                for (var i = 0; i < names.length; i++) {
                    if (names[i].checked == true) {
                        ids = ids + names[i].value + ",";
                    }
                }
                ids = ids.substring(0, ids.length - 1);
            }
            if (ids.length == 0) {
                alert("请选择删除的项！");
                return false;
            } else {
                if (confirm(msg) == true) {
                    arrayIds(ids);
                    return true;
                } else {
                    return false;
                }
            }
        }
        function arrayIds(ids) {
            $("#ids").val(ids);
            $('#queryForm')[0].method = 'get';
            $('#queryForm')[0].action = '${ctx}/user/index/deleteBatch?ids=' + ids;
            $('#queryForm').submit();
        }
    </script>
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span2"></div>
        <form id="queryForm" action="${ctx}/user/index/list/${type}" method="get">
            <div class="span8">
                <div>
                    <h3>内部用户管理列表</h3>
                </div>
                <div class="toolbar">
                    <ul>
                        <li><h4>用户名：</h4></li>
                        <li><input type="text" name="userName" value="${userName}" placeholder="例：张三"/></li>
                        <li><h4>角色名：</h4></li>
                        <li><input type="text" name="roleName" value="${roleName}" placeholder="例：系统管理员"/></li>
                        <li>
                            <button id="subFind" type="submit" class="btn btn-success">查询</button>
                        </li>
                    </ul>
                    </br>
                    <ul>
                        <li>
                            <a href="${ctx}/user/index/createInner" class="btn btn-success">新增</a>
                        </li>
                        <li>
                            <a href="#" class="btn btn-danger" id="deleteUser" onclick="deleteAll()">批删除</a>
                            <input type="hidden" name="ids" id="ids"/>
                        </li>
                        <li>
                            <a href="#" class="btn btn-warning" id="batchUse">启用/禁用</a>
                        </li>
                    </ul>
                </div>
                <table class="table" id="queryTable">
                    <thead>
                    <tr>
                        <th><input type="checkbox" id="chk_all" name="chk_all"></th>
                        <th>用户名</th>
                        <th>角色名</th>
                        <th>性别</th>
                        <th>邮箱</th>
                        <th>状态</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pageInfo.content}" var="user">
                        <tr>
                            <td><input type="checkbox" name="chkName" value="${user.id}"/></td>
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