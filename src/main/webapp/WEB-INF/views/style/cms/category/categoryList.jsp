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
    <style type="text/css">
        div#rMenu {position:absolute; visibility:hidden; top:0; background-color: #555;text-align: left;padding: 0px;}
        div#rMenu ul li{
            margin: 0;
            padding:0;
            cursor: pointer;
            list-style: none outside none;
            background-color: #DFDFDF;
        }
    </style>
    <script src="${ctx}/static/js/jquery.js"></script>
    <script src="${ctx}/static/js/bootstrap.js"></script>
    <script src="${ctx}/static/js/jquery.ztree.all-3.5.js"></script>
    <script type="text/javascript">
        var setting = {
            view: {
                dblClickExpand: true
            },
            check: {
                enable: false
            },
            callback: {
                onRightClick: OnRightClick,
                onClick : onClick
            }
        };

        var zNodes = eval('${treeNodes}');
        //右键菜单
        function OnRightClick(event, treeId, treeNode) {
            if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
                zTree.cancelSelectedNode();
                showRMenu("${siteId}", event.clientX, event.clientY,treeNode);
            } else if (treeNode && !treeNode.noR) {
                zTree.selectNode(treeNode);
                showRMenu("node", event.clientX, event.clientY,treeNode);
            }
        }
        //显示或隐藏右键菜单
        function showRMenu(type, x, y,treeNode) {
            $("#rMenu ul").show();
            var pid = treeNode.getParentNode();
            if (pid==null) {
                $("#m_add_next").show();
                $("#m_add_brother").hide();
                $("#m_del").hide();
                $("#m_update").hide();
                $("#m_publish").hide();
            } else {
                $("#m_add_next").show();
                $("#m_add_brother").show();
                $("#m_del").show();
                $("#m_update").show();
                $("#m_publish").show();
            }
            rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});
            $("body").bind("mousedown", onBodyMouseDown);
        }
        //隐藏右键菜单
        function hideRMenu() {
            if (rMenu) rMenu.css({"visibility": "hidden"});
            $("body").unbind("mousedown", onBodyMouseDown);
        }
        function onBodyMouseDown(event){
            if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
                rMenu.css({"visibility" : "hidden"});
            }
        }
        //节点单击事件
        function onClick(event, treeId, treeNode, clickFlag){
            var allFlag;
            var nodes = zTree.getSelectedNodes();
            var currentNodeId = nodes[0].id;
            if(currentNodeId == 'root'){
                currentNodeId = '${siteId}';
                allFlag = 'all';
            }
            $('#categoryFrame').attr('src','${ctx}/cms/category/childrenList?currentNodeId='+currentNodeId+'&allFlag='+allFlag
            );
        }
        //新增下级栏目
        function addChild(){
            hideRMenu();
            var nodes = zTree.getSelectedNodes();
            var pid = nodes[0].id;
            $('#categoryFrame').attr('src','${ctx}/cms/category/create?nodeId='+pid);
        }
        //新增同级栏目
        function addBrother(){
            hideRMenu();
            var nodes = zTree.getSelectedNodes();
            var pid = nodes[0].getParentNode().id;
            $('#categoryFrame').attr('src','${ctx}/cms/category/create?nodeId='+pid);
        }
        //删除栏目
        function del(){

        }
        //修改栏目
        function update(){
            hideRMenu();
            var nodes = zTree.getSelectedNodes();
            var pid = nodes[0].id;
            $('#categoryFrame').attr('src','${ctx}/cms/category/update?nodeId='+pid);
        }
        //发布栏目
        function publish(){
            hideRMenu();
            var nodes = zTree.getSelectedNodes();
            var pid = nodes[0].id;
            $('#categoryFrame').attr('src','${ctx}/cms/category/publish?nodeId='+pid);
        }
        var zTree, rMenu;
        $(document).ready(function(){
            $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            zTree = $.fn.zTree.getZTreeObj("treeDemo");
            zTree.expandAll(true);
            rMenu = $("#rMenu");
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
            <div id="rMenu" class="rmenu">
                <ul style="width:150px;margin:0;padding:0;">
                    <li id="m_add_next" onclick="addChild()">
                        <i class="icon-plus"></i>新增下级栏目
                    </li>
                    <li id="m_add_brother" onclick="addBrother()">
                        <i class="icon-plus"></i>新增同级栏目
                    </li>
                    <li id="m_del" onclick="del()">
                        <i class="icon-remove"></i>删除栏目
                    </li>
                    <li id="m_update" onclick="update()">
                        <i class="icon-pencil"></i>修改栏目
                    </li>
                    <li id="m_publish" onclick="publish()">
                        <i class="icon-pencil"></i>发布栏目
                    </li>
                </ul>
            </div>
        </div>
        <div class="span9">
            <iframe id="categoryFrame" name="categoryFrame"  src="" frameborder="0" scrolling="auto" width="100%"
                    height="auto" style="position: absolute; margin: 0px; left: 200px; right: 0px; top: 10px;
                     bottom: 0px; height: 533px; z-index: 0; display:
                      block; visibility: visible; padding-left:0px; background-color:#FFFFFF"></iframe>
        </div>
    </div>
</div>
</body>
</html>