<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="qlp" uri="/qlpTagLib" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<qlp:extends name="css">
	<link rel="stylesheet" href="${ctx}/static/css/alert/jquery.alerts.css" type="text/css"/>
</qlp:extends>
<qlp:extends name="javascript">
	<script type="text/javascript" src="${ctx}/static/js/platform_index.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/page_sync.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/alert/jquery.alerts.js"></script>
</qlp:extends>
<qlp:extends name="body">
<div>
	<div class="pageheader">
	    <h1 class="pagetitle">
	    	<qlp:fill name="pagetitle"></qlp:fill>
	    </h1>
	</div>
	<div id="contentwrapper" class="contentwrapper">
		  <qlp:fill name="searchForm"></qlp:fill>
			 <table class="stdtable stdtablecb" border="0" cellpadding="0" cellspacing="0">
                    <thead>
                        <tr>
                        	<qlp:fill name="headTr"></qlp:fill>
                        </tr>
                    </thead>
                    <tfoot>
                        <tr>
                            <qlp:fill name="footTr"></qlp:fill>
                        </tr>
                    </tfoot>
                    <tbody>
               			<qlp:fill name="bodyTr"></qlp:fill>
                    </tbody>
                </table>
	</div> 
	<br clear="all">        
</div>
</qlp:extends>
<jsp:include page="base.jsp"/>