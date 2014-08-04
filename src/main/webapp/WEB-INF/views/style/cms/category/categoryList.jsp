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
                dblClickExpand: false
            },
            check: {
                enable: true
            },
            callback: {
                onRightClick: OnRightClick
            }
        };

        var zNodes =[
            {id:1, name:"无右键菜单 1", open:true, noR:true,
                children:[
                    {id:11, name:"节点 1-1", noR:true},
                    {id:12, name:"节点 1-2", noR:true}

                ]},
            {id:2, name:"右键操作 2", open:true,
                children:[
                    {id:21, name:"节点 2-1"},
                    {id:22, name:"节点 2-2"},
                    {id:23, name:"节点 2-3"},
                    {id:24, name:"节点 2-4"}
                ]},
            {id:3, name:"右键操作 3", open:true,
                children:[
                    {id:31, name:"节点 3-1"},
                    {id:32, name:"节点 3-2"},
                    {id:33, name:"节点 3-3"},
                    {id:34, name:"节点 3-4"}
                ]}
        ];

        function OnRightClick(event, treeId, treeNode) {
            if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
                zTree.cancelSelectedNode();
                showRMenu("root", event.clientX, event.clientY);
            } else if (treeNode && !treeNode.noR) {
                zTree.selectNode(treeNode);
                showRMenu("node", event.clientX, event.clientY);
            }
        }

        function showRMenu(type, x, y) {
            $("#rMenu ul").show();
            if (type=="root") {
                $("#m_del").hide();
                $("#m_check").hide();
                $("#m_unCheck").hide();
            } else {
                $("#m_del").show();
                $("#m_check").show();
                $("#m_unCheck").show();
            }
            rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});

            $("body").bind("mousedown", onBodyMouseDown);
        }
        function hideRMenu() {
            if (rMenu) rMenu.css({"visibility": "hidden"});
            $("body").unbind("mousedown", onBodyMouseDown);
        }
        function onBodyMouseDown(event){
            if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
                rMenu.css({"visibility" : "hidden"});
            }
        }
        var addCount = 1;
        function addTreeNode() {
            hideRMenu();
            var newNode = { name:"增加" + (addCount++)};
            if (zTree.getSelectedNodes()[0]) {
                newNode.checked = zTree.getSelectedNodes()[0].checked;
                zTree.addNodes(zTree.getSelectedNodes()[0], newNode);
            } else {
                zTree.addNodes(null, newNode);
            }
        }
        function removeTreeNode() {
            hideRMenu();
            var nodes = zTree.getSelectedNodes();
            if (nodes && nodes.length>0) {
                if (nodes[0].children && nodes[0].children.length > 0) {
                    var msg = "要删除的节点是父节点，如果删除将连同子节点一起删掉。\n\n请确认！";
                    if (confirm(msg)==true){
                        zTree.removeNode(nodes[0]);
                    }
                } else {
                    zTree.removeNode(nodes[0]);
                }
            }
        }
        function checkTreeNode(checked) {
            var nodes = zTree.getSelectedNodes();
            if (nodes && nodes.length>0) {
                zTree.checkNode(nodes[0], checked, true);
            }
            hideRMenu();
        }
        function resetTree() {
            hideRMenu();
            $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        }

        var zTree, rMenu;
        $(document).ready(function(){
            $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            zTree = $.fn.zTree.getZTreeObj("treeDemo");
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
                    <li id="m_add" onclick="addChild()">
                        <i class="icon-plus"></i>新增下级栏目
                    </li>
                    <li id="m_add_next" onclick="addBrother()">
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
           <%-- <iframe id="categoryFrame" name="categoryFrame"  src="" frameborder="0" scrolling="auto" width="100%"
                    height="auto" style="position: absolute; margin: 0px; left: 199px; right: 0px; top: 109px;
                     bottom: 0px; height: 533px; z-index: 0; display:
                      block; visibility: visible; padding-left:0px; background-color:#FFFFFF"></iframe>--%>
        </div>
    </div>
</div>
</body>
</html>