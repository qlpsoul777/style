// JavaScript Document

//左侧面板导航

$(document).ready(function() {
			var Menu_Out = $('.OutMenu li a'),
				Menu_In = $('.OutMenu li .InMenu');
			Menu_Out.first().addClass('active').next().slideDown('normal');
			Menu_Out.on('click', function(event) {
				event.preventDefault();
				if ($(this).attr('class') != 'active'){
					Menu_In.slideUp('normal');
					$(this).next().stop(true,true).slideToggle('normal');
					Menu_Out.removeClass('active');
					$(this).addClass('active');
				}
			});
});
