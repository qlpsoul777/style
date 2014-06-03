<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="locale" value="${pageContext.request.locale}"/>
<!DOCTYPE html>
<html>
<head>
    <title>编辑用户</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${ctx}/static/css/bootstrap.css" media="screen">
    <link rel="stylesheet" href="${ctx}/static/css/font-awesome.css">
    <link rel="stylesheet" href="${ctx}/static/css/qlp.css">
    <script src="${ctx}/static/js/jquery.js"></script>
    <script src="${ctx}/static/js/bootstrap.js"></script>
    <script type="text/javascript">
        //回显用户的角色名
        $(function () {
            var allIds = document.getElementsByName("roleChk");
            var selectedIds = '${selectedIds}';
            selectedIds = selectedIds.substring(0, selectedIds.length - 1);
            var selectedId = selectedIds.split(',');
            for (i = 0; i < allIds.length; i++) {
                for (j = 0; j < selectedId.length; j++) {
                    if (allIds[i].value == selectedId[j]) {
                        allIds[i].checked = 'checked';
                    }
                }
            }
        });

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
        }
    </script>
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span1"></div>
        <div class="span10">
            <div>
                <h3>修改用户</h3>
            </div>
            <form id="createForm" action="${ctx}/user/index/updateSave?id=${user.id}" method="post"
                  class="form-horizontal">
                <div class="toolbar">
                    <ul>
                        <li>
                            <button class="btn btn-success" type="submit" onclick="sub()">保存</button>
                        </li>
                        <li>
                            <a href="${ctx}/user/index/list/${type}" class="btn btn-warning">取消</a>
                        </li>
                    </ul>
                </div>
                <table class="table">
                    <tr>
                        <td>
                            <div class="control-group">
                                <label class="control-label" for="loginName">登录名：</label>

                                <div class="controls">
                                    <input id="loginName" type="text" name="loginName" value="${user.loginName}"
                                           disabled="disabled"/>
                                </div>
                            </div>
                        </td>
                        <td>
                            <div class="control-group">
                                <label class="control-label" for="loginName">姓名：</label>

                                <div class="controls">
                                    <input id="name" type="text" name="name" value="${user.name}" disabled="disabled"/>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="control-group">
                                <label class="control-label" for="email">邮箱：</label>

                                <div class="controls">
                                    <input id="email" type="text" name="email" value="${user.email}"
                                           disabled="disabled"/>
                                </div>
                            </div>
                        </td>
                        <td>
                            <div class="control-group">
                                <label class="control-label" for="loginName">联系方式：</label>

                                <div class="controls">
                                    <input id="phoneNum" type="text" name="phoneNum" value="${user.phoneNum}"
                                           disabled="disabled"/>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="control-group">
                                <label class="control-label" for="sex">性别：</label>

                                <div class="controls">
                                    <input id="sex" type="text" name="sex" value="${user.sex}" disabled="disabled"/>
                                </div>
                            </div>
                        </td>
                        <td>
                            <div class="control-group">
                                <label class="control-label" for="birthday">出生日期：</label>

                                <div class="controls">
                                    <input id="birthday" type="text" name="birthday"
                                           value="<fmt:formatDate value="${user.birthday}" pattern="yyyy-MM-dd" ></fmt:formatDate>"
                                           disabled="disabled"/>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <div class="control-group">
                                <label class="control-label" for="address">联系地址：</label>

                                <div class="controls">
                                    <input id="address" style="width: 750px;" type="text" name="address"
                                           value="${user.address}" disabled="disabled"/>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="control-group">
                                <label class="control-label">是否启用：</label>

                                <div class="controls">
                                    <label class="radio">
                                        <input type="radio" value="ENABLE" name="state"
                                               <c:if test="${user.state eq 'ENABLE'}">checked="checked"</c:if> />启用
                                    </label>
                                    <label class="radio">
                                        <input type="radio" value="DISENABLE" name="state"
                                               <c:if test="${user.state eq 'DISENABLE'}">checked="checked"</c:if> />不启用
                                    </label>
                                </div>
                            </div>
                        </td>
                        <td>
                            <div class="control-group">
                                <label class="control-label">角色名：</label>

                                <div class="controls">
                                    <c:forEach items="${roles}" var="role">
                                        <label class="checkbox">
                                            <input type="checkbox" value="${role.id}" name="roleChk"/>${role.name}
                                        </label>
                                    </c:forEach>
                                    <input type="hidden" id="roleIds" name="roleIds"/>
                                </div>
                            </div>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <div class="span1"></div>
    </div>
</div>
</body>
</html>