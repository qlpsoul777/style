DateTools={
	languages:['zh_CN','en_US'],
	common:function($element,language){
		var index = this.languages.indexOf(language);
		var i18n_obj;
		if(index < 0 ){
			language = this.languages[0];
			switch(language){
			case this.languages[0]:
				i18n_obj = {
					months:['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
			        weekdays:['星期天','星期一','星期二','星期三','星期四','星期五','星期六'],
			        weekdaysShort:['周日','周一','周二','周三','周四','周五','周六']
				};
				break;
			default:
				i18n_obj = {
					months:['January','February','March','April','May','June','July','August','September','October','November','December'],
			        weekdays:['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'],
			        weekdaysShort:['Sun','Mon','Tue','Wed','Thu','Fri','Sat']
				};
				break;
			}
		}
		if($element instanceof Object){
			$element.pikaday({
				firstDay: 1,
		        i18n:i18n_obj,
			});
		}
	}
		
};