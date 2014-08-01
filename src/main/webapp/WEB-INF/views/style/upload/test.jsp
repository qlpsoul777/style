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
    <title>角色管理</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${ctx}/static/css/bootstrap.css" media="screen">
    <link rel="stylesheet" href="${ctx}/static/css/font-awesome.css">
    <link rel="stylesheet" href="${ctx}/static/js/uploadify/uploadify.css">

    <script src="${ctx}/static/js/jquery.js"></script>
    <script src="${ctx}/static/js/bootstrap.js"></script>
    <script src="${ctx}/static/js/uploadify/jquery.uploadify.js"></script>
    <script type="text/javascript">
        $(function () {
            var oldVersionIds = [];
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
                    var str = '{files:'+data+'}';
                    var jsonObj = eval('(' + str + ')');
                    var ids = jsonObj.files[0].id;
                    oldVersionIds.push(ids);
                    $('#fileTable').show();
                    $('#fileOperate').append('<tr id="'+jsonObj.files[0].id+'"><td>'+jsonObj.files[0].name+'</td><td>'
                            +jsonObj.files[0].size+'B</td>'
                    +'<td><a class="btn btn-danger" onclick="deleteFile(\'' + jsonObj.files[0].id
                            + '\')">删除</a></td></tr>');
                },
                'onQueueComplete':function(status){
                    $('#versionIds').val(oldVersionIds.join(","));
                }
            });
        });
        function deleteFile(id){
            $.get("${ctx}/cms/version/delete/"+id,function(data){
                if(data == "success"){
                   $('#'+id).remove();
                   var trCount= $("#fileOperate").find("tr").length;
                   alert(trCount);
                  if(trCount == 0){
                        $('#fileTable').hide();
                   }
                }else{
                    alert("删除文件失败");
                }
            });
        }
    </script>
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
       <%-- <form:form action="" modelAttribute="" method="post">
            <form:hidden path="id"/>
            <input type="hidden" id="versionIds" name="versionIds"/>
        </form:form>--%>
                <div id="fileQueue">
                </div>
                <input type="file" name="uploadify" id="uploadify" />
                <p >
                    <a href="javascript:$('#uploadify').uploadify('upload')">上传</a>|
                    <a href="javascript:$('#uploadify').uploadify('cancel')">取消上传</a>
                </p>
                <div id="fileTable" style="display: none">
                    <table class="table" width="60%">
                        <thead>
                          <th>文件名</th>
                          <th>大小</th>
                          <th>操作</th>
                        </thead >
                        <tbody id="fileOperate">
                        </tbody>
                    </table>
                </div>
    </div>
</div>
</body>
</html>