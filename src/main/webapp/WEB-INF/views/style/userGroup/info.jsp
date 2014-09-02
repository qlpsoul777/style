<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="locale" value="${pageContext.request.locale}"/>
<!DOCTYPE html>
<html>
<head>
    <title>用户组详情</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${ctx}/static/css/bootstrap.css" media="screen">
    <link rel="stylesheet" href="${ctx}/static/css/qlp.css">
    <script src="${ctx}/static/js/jquery.js"></script>
    <script src="${ctx}/static/js/bootstrap.js"></script>
    <script type="text/javascript">
        $(function() {
            $('#agreeSelect').on('click',function(){
                var array = new Array();
                var a = $(window.frames["myIframe"].document).find("#s2 option");
                $(a).each(function(){
                    var val = $(this).val();
                    array.push(val);
                });
                $('#userIds').val(array);
                alert(array);
                window.close();
            });
        });
    </script>
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <%--<div class="span1"></div>--%>
        <div class="span12">
            <div>
                <h3>用户组详情</h3>
            </div>
            <form id="createForm" action="" method="post" class="form-horizontal">
                <input type="hidden" id="userIds" name="userIds" value=""/>
                <div class="toolbar">
                    <ul>
                        <li>
                            <button class="btn btn-success" type="submit">保存</button>
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
                                <label class="control-label">用户组成员：</label>

                                <div class="controls">
                                    <table>
                                        <tbody>
                                        <tr><td colspan="5">
                                            <a href="#myModal" role="button" class="btn btn-large btn-block" data-toggle="modal">编辑用户组成员</a>
                                        </td></tr>
                                        <c:forEach items="${users}" var="busi" varStatus="ind">
                                            <c:if test="${ind.count%5!=0 }">
                                                <td align="left">${busi.name}(${busi.email})</td>&nbsp;&nbsp;
                                            </c:if>
                                            <c:if test="${ind.count%5==0 }">
                                                <td align="left">${busi.name}(${busi.email})</td>&nbsp;&nbsp;
                                                </tr>
                                                <tr>
                                            </c:if>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
            <div id="myModal" class="modal hide fade" tabindex="-1" style="width: 810px;" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h3 id="myModalLabel">用户组编辑</h3>
                </div>
                <div class="modal-body">
                    <iframe id = "myIframe" name="myIframe" frameborder="0" scrolling="auto" width="800px" height="300px" src="${ctx}/role/selectUser?id=${role.id}">
                    </iframe>
                </div>
                <div class="modal-footer">
                    <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
                    <button class="btn btn-primary" id="agreeSelect">确定</button>
                </div>
            </div>
       <%-- <div class="span1"></div>--%>
    </div>
</div>
</body>
</html>