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
    <link rel="stylesheet" href="${ctx}/static/css/font-awesome.css">
    <link rel="stylesheet" href="${ctx}/static/js/uploadify/uploadify.css">

    <script src="${ctx}/static/js/jquery.js"></script>
    <script src="${ctx}/static/js/uploadify/jquery.uploadify.js"></script>
    <script src="${ctx}/static/js/bootstrap.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#uploadify").uploadify({
                //指定swf文件
                'swf': '${ctx}/static/js/uploadify/uploadify.swf',
                //后台处理的页面
                'uploader': '${ctx}/cms/version/fileUpload',
                //按钮显示的文字
                'buttonText': '上传图片',
                //显示的高度和宽度，默认 height 30；width 120
                //'height': 15,
                //'width': 80,
                //上传文件的类型  默认为所有文件    'All Files'  ;  '*.*'
                //在浏览窗口底部的文件类型下拉菜单中显示的文本
                'fileTypeDesc': 'Image Files',
                //允许上传的文件后缀
                'fileTypeExts': '*.gif; *.jpg; *.png',
                //发送给后台的其他参数通过formData指定
                //'formData': { 'someKey': 'someValue', 'someOtherKey': 1 },
                //上传文件页面中，你想要用来作为文件队列的元素的id, 默认为false  自动生成,  不带#
                //'queueID': 'fileQueue',
                //选择文件后自动上传
                'auto': true,
                //设置为true将允许多文件上传
                'multi': true,
                'onUploadSuccess':function(file,data,response){
//                    var versionsArray = [];
//                    versionsArray.push(data.id);
//                    var ids = versionsArray.join(",");
                    alert(data.id);
                }
            });
        });

    </script>
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <form action="/user/index/first" method="post" enctype="multipart/form-data">
            <div id="fileQueue">
            </div>
            <input type="file" name="uploadify" id="uploadify" />
            <p>
                <a href="javascript:$('#uploadify').uploadify('upload')">上传</a>|
                <a href="javascript:$('#uploadify').uploadify('cancel')">取消上传</a>
            </p>
        </form>
    </div>
</div>
</body>
</html>