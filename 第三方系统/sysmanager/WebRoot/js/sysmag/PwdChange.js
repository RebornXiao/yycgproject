function changePwd() {
	Ext.BLANK_IMAGE_URL = 'image/s.gif';
	Ext.QuickTips.init();
	var pwdWin = new Ext.Window({
				title : '用户表单',
				layout:'form',
				items : [{
							id : 'password',
							inputType : 'password',
							name : "password",
							fieldLabel : "密码",
							maxLength : 12
						}, {
							id : 'password2',
							inputType : 'password',
							name : "password2",
							fieldLabel : "密码确认",
							maxLength : 12
						}],
				width : 565,
				modal : true,
				resizable : false
			});
	pwdWin.show();
}