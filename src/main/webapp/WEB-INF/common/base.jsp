<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="qlp" uri="/qlpTagLib" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>
		<qlp:fill name="title"></qlp:fill>
	</title>
	<link rel="stylesheet" href="${ctx}/static/css/platform_index.css" type="text/css"/>
	<qlp:fill name="css"></qlp:fill>	
	<script type="text/javascript" src="${ctx}/static/js/jquery-1.7.min.js"></script>
	<qlp:fill name="javascript"></qlp:fill>
</head>
<body>
<qlp:fill name="body"></qlp:fill>       
</body>
</html>
