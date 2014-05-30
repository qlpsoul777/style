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
        function sub() {
            var names = document.getElementsByName("roleChk");
            var ids = "";
            if (names.length >= 1) {
                for (var i = 0; i < names.length; i++) {
                    if (names[i].checked == true) {
                        ids = ids + names[i].value + ",";
                    }
                }
                ids = ids.substring(0, ids.length - 1);
            }
            $('#roleIds').val(ids);
            alert(ids);
        }
    </script>
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span1"></div>
        <div class="span10">
            <div>
                <h3>新增用户</h3>
            </div>
            <form id="createForm" action="${ctx}/user/index/saveInner" method="post" class="form-horizontal">
                <div class="toolbar">
                    <ul>
                        <li>
                            <button class="btn btn-success" type="submit" onclick="sub()">保存</button>
                        </li>
                        <li>
                            <a href="${ctx}/user/index/list/INNER" class="btn btn-inverse">取消</a>
                        </li>
                    </ul>
                </div>
                <div class="control-group">
                    <label class="control-label" for="loginName">登录名：</label>

                    <div class="controls">
                        <input id="loginName" type="text" name="loginName"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">角色名：</label>

                    <div class="controls">
                        <c:forEach items="${roles}" var="role">
                            <label class="checkbox">
                                <input type="checkbox" value="${role.id}" name="roleChk"/>${role.name}
                            </label>
                        </c:forEach>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">是否启用：</label>

                    <div class="controls">
                        <label class="radio">
                            <input type="radio" value="ENABLE" name="state" checked="checked"/>启用
                            <input type="hidden" id="roleIds" name="roleIds"/>
                        </label>
                        <label class="radio">
                            <input type="radio" value="DISENABLE" name="state"/>不启用
                        </label>
                    </div>
                </div>
            </form>
        </div>
        <div class="span1"></div>
    </div>
</div>
</body>
</html>