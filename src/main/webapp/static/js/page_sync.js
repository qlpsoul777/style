var PageSync = {
	size:10,
	current:0,
	pageId:"pageDiv",
	formId:"queryForm",
	init:function(currentPage,pageSize,totalSize,pageId,formId){
		currentPage = parseInt(currentPage,10);
		pageSize = parseInt(pageSize,10);
		totalSize = parseInt(totalSize,10);
		if(currentPage <= 0){
			currentPage = this.current;
		}
		currentPage+=1;
		if(totalSize <=1){
			return;
		}
		if(pageSize <=0){
			pageSize = this.size;
		}
		if(!pageId){
			pageId = this.pageId;
		}
		if(!formId){
			formId = this.formId;
		}
		this.createPage(currentPage,pageSize,totalSize,pageId,formId);
		this.action(currentPage,pageId,formId);
	},
	createPage:function(currentPage,pageSize,totalSize,pageId,formId){
		var html = "";
		if(currentPage > 1){
			html+="<li><a class='previous' data='"+(currentPage-1)+"'>上一页</a></li>";
		}
		if(totalSize <= 10){
			for(var i=1;i<=totalSize;i++){
				html+="<li><a data='"+i+"'>"+i+"</a></li>";
			}
		}else{
			if(currentPage <=6){
				for(var i=1;i<=10;i++){
					html+="<li><a data='"+i+"'>"+i+"</a></li>";
				}
			}else if(currentPage > totalSize-10){
				for(var i=totalSize-10;i<=totalSize;i++){
					html+="<li><a data='"+i+"'>"+i+"</a></li>";
				}
			}else{
				for(var i=currentPage-5;i<currentPage+5;i++){
					html+="<li><a data='"+i+"'>"+i+"</a></li>";
				}
			}
		}
		if(currentPage != totalSize){
			html+="<li><a class='next' data='"+(currentPage+1)+"'>下一页</a></li>";
		}
		$('#'+pageId).html(html);
	},
	action:function(currentPage,pageId,formId){
		$('#'+pageId+' a[data]').bind('click',function(){
			var target = parseInt($(this).attr('data'),10)-1;
			$('#currentPage').val(target);
			$('#'+formId).submit();
		});
		$('#'+pageId+' a[data="'+currentPage+'"]').unbind('click');
		$('#'+pageId+' a[data="'+currentPage+'"]').addClass('current');
	}
};