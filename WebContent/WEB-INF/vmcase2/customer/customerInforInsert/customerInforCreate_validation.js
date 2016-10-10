var validator = $($formName).validate({
	rules : {
		chineseName : {
			required : true
		},
		cardType : {
			required : true
		},
		cardId : {
			required : true
		}
	},
	messages : {
		chineseName : {
			required : "<br>中文姓名不能为空"
		},
		cardType : {
			required : "<br>身份证件类型不能为空"
		},
		cardId : {
			required : "<br>证件号码不能为空"
		}
	},
	errorPlacement : function(error, element) {
		element.after(error);
		if (layout) {
			layout.resizeLayout();
		}
	}
});