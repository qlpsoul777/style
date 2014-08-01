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
    <script src="${ctx}/static/js/jquery.js"></script>
    <script src="${ctx}/static/js/bootstrap.js"></script>
    <script type="text/javascript">
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
                            <label class="control-label" for="cName">站点中文名：</label>
                            <div class="controls">
                                <form:input path="cName"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="control-group">
                            <label class="control-label" for="eName">站点英文名：</label>
                            <div class="controls">
                                <form:input path="eName"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="control-group">
                            <label class="control-label" for="eName">站点英文名：</label>
                            <div class="controls">
                                <form:input path="eName"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="control-group">
                            <label class="control-label" for="eName">站点英文名：</label>
                            <div class="controls">
                                <form:input path="eName"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="control-group">
                            <label class="control-label" for="eName">站点英文名：</label>
                            <div class="controls">
                                <form:input path="eName"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="control-group">
                            <label class="control-label" for="eName">站点英文名：</label>
                            <div class="controls">
                                <form:input path="eName"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <div class="control-group">
                            <label class="control-label" for="description">角色描述：</label>

                            <div class="controls">
                                <textarea id="description" name="description"
                                          style="width: 750px;height: 150px;resize: none">${role.description}</textarea>
                            </div>
                        </div>
                    </td>
                </tr>
            </form:form>
            <form id="createForm" action="${ctx}/role/updateSave" method="post" class="form-horizontal">
                <input type="hidden" name="aid" id="aid"/>
                <input type="hidden" name="id" id="id" value="${role.id}"/>
                <div class="toolbar">
                    <ul>
                        <li>
                            <button class="btn btn-success" type="submit" onclick="sub()">保存</button>
                        </li>
                        <li>
                            <a href="${ctx}/role/roleList" class="btn btn-warning">取消</a>
                        </li>
                    </ul>
                </div>
                <table class="table">
                    <tr>
                        <td colspan="2">
                            <div class="control-group">
                                <label class="control-label" for="name">角色名：</label>
                                <div class="controls">
                                    <input id="name" type="text" name="roleName" value="${role.name}"/>
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
                                        <input type="radio" value="ENABLE" name="state"<c:if test="${role.state eq 'ENABLE'}">checked="checked"</c:if>/>启用
                                    </label>
                                    <label class="radio">
                                        <input type="radio" value="DISENABLE" name="state" <c:if test="${role.state eq 'DISENABLE'}">checked="checked"</c:if>/>不启用
                                    </label>
                                </div>
                            </div>
                        </td>
                        <td>
                            <div class="control-group">
                                <label class="control-label">角色类型：</label>

                                <div class="controls">
                                    <select name="roleType" id="roleType">
                                        <option name="INNER" id="INNER" value="INNER"<c:if test="${role.type eq 'INNER'}"> selected="selected"</c:if> disabled="disabled">内部角色</option>
                                        <option name="OUTER" id="OUTER" value="OUTER" <c:if test="${role.type eq 'OUTER'}"> selected="selected"</c:if> disabled="disabled">外部角色</option>
                                    </select>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <div class="control-group">
                                <label class="control-label" for="description">角色描述：</label>

                                <div class="controls">
                                    <textarea id="description" name="description"
                                              style="width: 750px;height: 150px;resize: none">${role.description}</textarea>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr id="permission">
                        <td colspan="2">
                            <div class="control-group">
                                <label class="control-label">角色权限：</label>

                                <div class="controls">
                                    <ul id="treeDemo" class="ztree"></ul>
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