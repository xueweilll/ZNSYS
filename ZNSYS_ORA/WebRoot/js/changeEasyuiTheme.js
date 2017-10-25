function changeThemeFun(themeName) {
	var $easyuiTheme = $('#easyuiTheme');
	var url = $easyuiTheme.attr('href');
	var href = url.substring(0, url.indexOf('themes')) + 'themes/' + themeName + '/easyui.css';
	$easyuiTheme.attr('href', href);

	var $iframe = $('iframe');
	if ($iframe.length > 0) {
		for ( var i = 0; i < $iframe.length; i++) {
			var ifr = $iframe[i];
			$(ifr).contents().find('#easyuiTheme').attr('href', href);
			//$(ifr).contents().changeThemeFun(themeName);
		}
	}
	
	$.cookie('easyuiThemeName', themeName, {
		expires : 365
	});
};

$(function(){
	//�޸�����
	if ($.cookie('easyuiThemeName')) {
		changeThemeFun($.cookie('easyuiThemeName'));
	}
});