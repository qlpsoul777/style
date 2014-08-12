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
    <style type="text/css" rel="stylesheet">
        .error{
            color: red;
        }
    </style>
    <script src="${ctx}/static/js/jquery.js"></script>
    <script src="${ctx}/static/js/bootstrap.js"></script>
    <script src="${ctx}/static/js/validation/jquery.validate.js"></script>
    <script type="text/javascript">
       /* $(function(){
            $("#editForm").validate({
                errorElement:"em",
                rules: {
                    cName: "required",
                    path: {
                        required: true,
                        url: true
                    },
                    domianName: "required"
                },
                messages: {
                    cName: "不能为空",
                    path: {
                        required: "不能为空",
                        url: "请输入合法的网址"
                    },
                    domianName: "不能为空"
                }
            });
        })*/
    </script>
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span1"></div>
        <div class="span10">
            <div>
                <h3>栏目编辑</h3>
            </div>
            <form:form id="editForm" action="${ctx}/cms/category/save" method="post" modelAttribute="category" cssClass="form-horizontal">
                <form:hidden path="id" />
                <input type="hidden" name="nodeId" value="${nodeId}"/>
                <div class="toolbar">
                    <ul>
                        <li>
                            <button class="btn btn-success" type="submit">保存</button>
                        </li>
                        <li>
                            <a href="${ctx}/cms/category/list" class="btn btn-warning">取消</a>
                        </li>
                    </ul>
                </div>
            <table class="table">
                <tr>
                    <td>
                        <div class="control-group">
                            <%--@declare id="name"--%><label class="control-label" for="name">栏目名称：</label>
                            <div class="controls">
                                <form:input path="name"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="control-group">
                            <%--@declare id="path"--%><label class="control-label" for="path">访问路径：</label>
                            <div class="controls">
                                <form:input path="path"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="control-group">
                            <%--@declare id="type"--%><label class="control-label" for="type">栏目类型：</label>
                            <div class="controls">
                                <form:select path="type">
                                    <form:option value="SIMPLE">普通栏目</form:option>
                                    <form:option value="LINK">链接栏目</form:option>
                                </form:select>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="control-group">
                            <%--@declare id="sort"--%><label class="control-label" for="sort">排序字段：</label>
                            <div class="controls">
                                <form:input path="sort"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="control-group">
                            <%--@declare id="visiable"--%><label class="control-label" for="visiable">是否发布：</label>
                            <div class="controls">
                                <form:radiobutton path="visiable" value="0"/>未发布
                                <form:radiobutton path="visiable" value="1"/>已发布
                            </div>
                        </div>
                    </td>
                </tr>
                <%--<tr>
                    <td colspan="2">
                        <div class="control-group">
                            &lt;%&ndash;@declare id="description"&ndash;%&gt;<label class="control-label" for="description">站点简介：</label>

                            <div class="controls">
                                <form:textarea path="description" style="width: 750px;height: 150px;resize: none"/>
                            </div>
                        </div>
                    </td>
                </tr>--%>
            </form:form>
        </div>
        <div class="span1"></div>
    </div>
</div>
</body>
</html>