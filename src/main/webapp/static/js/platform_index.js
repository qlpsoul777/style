$(function(){
	$('#userinfo').on('click',function(){
		if(!$(this).hasClass('active')){
			$('#userinfodrop').show();
			$(this).addClass('active');
		}else{
			$('#userinfodrop').hide();
			$(this).removeClass('active');
		}
	});
	
	$(document).bind('click',function(e){
		var target = $(e.target);
		if(target.closest("#userinfo").length == 0){
			$('#userinfodrop').hide();
			$('#userinfo').removeClass('active');
		} 
		e.stopPropagation();
	});

	//一级菜单点击事件
	$("a[data='outmenu']").on('click',function(e){
		var url = $(this).attr('href');
		if($(url).is(':visible')) {
			if(!$(this).parents('div').hasClass('menucoll') &&
			   !$(this).parents('div').hasClass('menucoll2')){
				$(url).slideUp();
			}
		} else {
			$('.vernav2 ul ul').each(function(){
					$(this).slideUp();
			});
			if(!$(this).parents('div').hasClass('menucoll') &&
			   !$(this).parents('div').hasClass('menucoll2'))
					$(url).slideDown();
		}
		$(this).parent().addClass('current');
		$(this).parent().siblings().removeClass('current');
		e.stopPropagation();
	});
	
	//二级菜单点击事件
	$("a[data='inmenu']").on('click',function(e){
		$("a[data='inmenu']").parent().removeClass('current');
		$(this).parent().addClass('current');
		e.stopPropagation();
	});
	
	$('.menucoll > ul > li, .menucoll2 > ul > li').live('mouseenter mouseleave',function(e){
		if(e.type == 'mouseenter') {
			$(this).addClass('hover');
			$(this).find('ul').show();	
		} else {
			$(this).removeClass('hover').find('ul').hide();	
		}
	});
	
	$('.togglemenu').click(function(){
		if(!$(this).hasClass('togglemenu_collapsed')) {
			if($('.vernav').length > 0) {
				if($('.vernav').hasClass('iconmenu')) {
					$('body').addClass('withmenucoll');
					$('.iconmenu').addClass('menucoll');
				} else {
					$('body').addClass('withmenucoll');
					$('.vernav').addClass('menucoll').find('ul').hide();
				}
			} else if($('.vernav2').length > 0) {
				$('body').addClass('withmenucoll2');
				$('.iconmenu').addClass('menucoll2');
			}
			$(this).addClass('togglemenu_collapsed');
			$('.iconmenu > ul > li > a').each(function(){
				var label = $(this).text();
				$('<li><span>'+label+'</span></li>')
					.insertBefore($(this).parent().find('ul li:first-child'));
			});
		} else {
			if($('.vernav').length > 0) {
				if($('.vernav').hasClass('iconmenu')) {
					$('body').removeClass('withmenucoll');
					$('.iconmenu').removeClass('menucoll');
				} else {
					$('body').removeClass('withmenucoll');
					$('.vernav').removeClass('menucoll').find('ul').show();
				}
			} else if($('.vernav2').length > 0) {	
				$('body').removeClass('withmenucoll2');
				$('.iconmenu').removeClass('menucoll2');
			}
			$(this).removeClass('togglemenu_collapsed');	
			$('.iconmenu ul ul li:first-child').remove();
		}
	});
	
$('.hornav a').click(function(){
		
		//this is only applicable when window size below 450px
		if($(this).parents('.more').length == 0)
			$('.hornav li.more ul').hide();
		
		//remove current menu
		$('.hornav li').each(function(){
			$(this).removeClass('current');
		});
		
		$(this).parent().addClass('current');	// set as current menu
		
		var url = $(this).attr('href');
		if($(url).length > 0) {
			$('.contentwrapper .subcontent').hide();
			$(url).show();
		} else {
			$.post(url, function(data){
				$('#contentwrapper').html(data);
				$('.stdtable input:checkbox').uniform();	//restyling checkbox
			});
		}
		return false;
	});
	
	if($(document).width() < 640) {
		$('.togglemenu').addClass('togglemenu_collapsed');
		if($('.vernav').length > 0) {
			$('.iconmenu').addClass('menucoll');
			$('body').addClass('withmenucoll');
			$('.centercontent').css({marginLeft: '56px'});
			if($('.iconmenu').length == 0) {
				$('.togglemenu').removeClass('togglemenu_collapsed');
			} else {
				$('.iconmenu > ul > li > a').each(function(){
					var label = $(this).text();
					$('<li><span>'+label+'</span></li>')
						.insertBefore($(this).parent().find('ul li:first-child'));
				});		
			}
		} else {
			$('.iconmenu').addClass('menucoll2');
			$('body').addClass('withmenucoll2');
			$('.centercontent').css({marginLeft: '36px'});
			$('.iconmenu > ul > li > a').each(function(){
				var label = $(this).text();
				$('<li><span>'+label+'</span></li>')
					.insertBefore($(this).parent().find('ul li:first-child'));
			});		
		}
	}
	
	$(window).resize(function(){
		if($(window).width() > 640){
			$('.centercontent').removeAttr('style');
		}	
	});
	
});