<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="locale" value="${pageContext.request.locale}"/>
<!DOCTYPE html>
<html>
<head>
    <title>用户组管理</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${ctx}/static/css/bootstrap.css" media="screen">
    <link rel="stylesheet" href="${ctx}/static/css/font-awesome.css">
    <link rel="stylesheet" href="${ctx}/static/css/qlp.css">
    <style type="text/css">
        .s1{width:120px;}
        #d1{width:510px;height:320px;background-color:#F5DEB3;margin:0 auto;}
        #d2{height:30px;font-size:24px;background-color:blue;color:white;}
        #d3{padding-left:30px;}
    </style>
    <script src="${ctx}/static/js/jquery.js"></script>
    <script src="${ctx}/static/js/bootstrap.js"></script>
    <script src="${ctx}/static/js/selected/doubleSelect.js"></script>
    <script type="text/javascript">
        //回显用户的角色名
        $(function () {

        });
    </script>
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <%--<div class="span1"></div>--%>
        <div class="span12">
            <form id="createForm" action="${ctx}/user/index/updateSave?id=${user.id}" method="post"
                  class="form-horizontal">
                <div id="d1">
                    <div id="d2">选择用户</div>
                    <div id="d3">
                        <table cellpadding="0" cellspacing="8">
                            <tr>
                                <td>可选用户</td>
                                <td>&nbsp;</td>
                                <td>已选用户</td>
                            </tr>
                            <tr>
                                <td>
                                    <select id="s1" name="s1" style="width:150px; height:220px;" multiple="multiple">
                                        <c:forEach items="${noSelectedUsers}" var="no">
                                            <option value="${no.id}">${no.name}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <p><input id="b1" type="button" class="s1" value="--&gt;" /></p>
                                    <p><input type="button" id="b2" class="s1" value="--&gt;&gt;" /></p>
                                    <p><input type="button" id="b3" class="s1" value="&lt;--" /></p>
                                    <p><input type="button" id="b4" class="s1" value="&lt;&lt;--" /></p>
                                </td>
                                <td>
                                    <select id="s2" name="s2" style="width:150px;height:220px;" multiple="multiple">
                                        <c:forEach items="${users}" var="user">
                                            <option value="${user.id}">${user.name}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>

            </form>
        </div>
    </div>
</div>
</body>
</html>