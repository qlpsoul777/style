<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="locale" value="${pageContext.request.locale}"/>
<!DOCTYPE html>
<html>
<head>
    <title>角色管理</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${ctx}/static/css/bootstrap.css" media="screen">
    <link rel="stylesheet" href="${ctx}/static/css/zTreeStyle/zTreeStyle.css">
    <link rel="stylesheet" href="${ctx}/static/css/qlp.css">
    <script src="${ctx}/static/js/jquery.js"></script>
    <script src="${ctx}/static/js/bootstrap.js"></script>
    <script src="${ctx}/static/js/jquery.ztree.all-3.5.js"></script>
    <script type="text/javascript">
        var zTreeObj;
        // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
        var setting = {
            data:{
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pid",
                    rootPId: "0"
                }
            }
        };
        // zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
        var zNodes = eval('${jsonObj}');
        $(document).ready(function () {
            zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        });

    </script>
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span1"></div>
        <div class="span10">
            <div>
                <h3>角色详情</h3>
            </div>
            <form id="createForm" action="${ctx}/role/createSave" method="post" class="form-horizontal">
                <input type="hidden" name="aid" id="aid"/>

                <div class="toolbar">
                    <ul>
                        <li>
                            <a href="${ctx}/role/create" class="btn btn-success">新增</a>
                        </li>
                        <li>
                            <a href="${ctx}/role/update?id=${role.id}" class="btn btn-primary">修改</a>
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
                                <label class="control-label">角色名:</label>

                                <div class="controls">
                                    ${role.name}
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="control-group">
                                <label class="control-label">是否启用：</label>

                                <div class="controls">
                                    <c:if test="${role.state eq 'DISENABLE'}">否</c:if>
                                    <c:if test="${role.state eq 'ENABLE'}">是</c:if>
                                </div>
                            </div>
                        </td>
                        <td>
                            <div class="control-group">
                                <label class="control-label">角色类型：</label>

                                <div class="controls">
                                    <c:if test="${role.type eq 'INNER'}">内部角色</c:if>
                                    <c:if test="${role.type eq 'OUTER'}">外部角色</c:if>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <div class="control-group">
                                <label class="control-label">角色描述：</label>

                                <div class="controls">
                                    ${role.description}
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