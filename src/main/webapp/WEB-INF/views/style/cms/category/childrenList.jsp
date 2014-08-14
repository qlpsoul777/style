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
    <title>角色管理</title>
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

            $('#queryTable tbody tr').css({'cursor': 'pointer'}).on('click', function () {
                window.location.href = '${ctx}/role/info?id=' + $(this).attr('data-row-id');
            })
            $('input[name="chkName"]').parent('td').on('click', function (event) {
                event.stopPropagation();
            });
            //批量启用/禁用角色
            $('#batchUse').click(function () {
                var ids = checkSelect();
                if (ids.length == 0) {
                    alert("请选择操作项！");
                    return false;
                } else {
                    if (confirm("您确定要启用/禁用这些角色吗？") == true) {
                        window.location.href = '${ctx}/role/batchUse?ids=' + ids;
                        return true;
                    } else {
                        return false;
                    }
                }
            });
        });
        //获取复选框的值
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
            return ids;
        }
    </script>
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <form id="queryForm" action="${ctx}/role/roleList" method="get">
            <div class="span10">
                <div>
                    <h3>角色管理列表</h3>
                </div>
                <div class="toolbar">
                    <ul>
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
                    </br>
                    <ul>
                        <li>
                            <a href="${ctx}/role/create" class="btn btn-success">新增</a>
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
                        <th>栏目名称</th>
                        <th>栏目类型</th>
                        <th>访问路径</th>
                        <th>排序字段</th>
                        <th>状态</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pageInfo.content}" var="category">
                        <tr data-row-id="${category.id}">
                            <td><input type="checkbox" name="chkName" value="${category.id}"/></td>
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