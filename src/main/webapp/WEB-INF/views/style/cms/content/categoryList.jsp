<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="locale" value="${pageContext.request.locale}"/>
<!DOCTYPE html>
<html>
<head>
    <title>内容管理</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${ctx}/static/css/bootstrap.css" media="screen">
    <link rel="stylesheet" href="${ctx}/static/css/zTreeStyle/zTreeStyle.css">
    <link rel="stylesheet" href="${ctx}/static/css/qlp.css">
    <style type="text/css">
    </style>
    <script src="${ctx}/static/js/jquery.js"></script>
    <script src="${ctx}/static/js/bootstrap.js"></script>
    <script src="${ctx}/static/js/jquery.ztree.all-3.5.js"></script>
    <script src="${ctx}/static/js/cookie/jquery.cookie.min.js"></script>
    <script type="text/javascript">
        var setting = {
            view: {
                dblClickExpand: true
            },
            check: {
                enable: false
            },
            callback: {
                onClick : onClick
            }
        };

        var zNodes = eval('${treeNodes}');

        //节点单击事件
        function onClick(event, treeId, treeNode, clickFlag){
            var allFlag = '';
            var nodes = zTree.getSelectedNodes();
            var currentNodeId = nodes[0].id;
            if(currentNodeId == 'root'){
                currentNodeId = '${siteId}';
                allFlag = 'all';
            }
            $('#categoryFrame').attr('src','${ctx}/cms/content/contentList?currentNodeId='+currentNodeId+'&allFlag='+allFlag
            );
        }

        var zTree;
        $(document).ready(function(){
            $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            zTree = $.fn.zTree.getZTreeObj("treeDemo");
            zTree.expandAll(true);
        });
        $(function(){
            //将站点id存入cookie中（会话级cookie，关闭浏览器后删除）
            $.cookie('siteId', '${siteId}');
        });
    </script>
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span3">
            <div>
                <h3>新闻栏目</h3>
            </div>
            <ul id="treeDemo" class="ztree"></ul>
        </div>
        <div class="span9">
            <iframe id="categoryFrame" name="categoryFrame"  src="${ctx}/cms/content/contentList?currentNodeId=${siteId}&&allFlag=all" frameborder="0" scrolling="auto" width="100%"
                    height="auto" style="position: absolute; margin: 0px; left: 200px; right: 0px; top: 10px;
                     bottom: 0px; height: 533px; z-index: 0; display:
                      block; visibility: visible; padding-left:0px; background-color:#FFFFFF"></iframe>
        </div>
    </div>
</div>
</body>
</html>