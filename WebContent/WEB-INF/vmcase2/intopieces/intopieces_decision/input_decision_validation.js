var validator = $($formName).validate({
	rules : {
		decisionAmount : {
			number : true
		},
		decisionRate : {
			number : true,max:100,min:0
		},
		decisionAmount:{
			required : true
		},
		decisionRate:{
			required : true
		}
	},
	messages : {
		decisionAmount : {
			number : "金额只能为数字"
		},
		decisionRate : {
			number : "利率只能为数字",max:"利率不能超过100",min:"利率不能小于0"
		},
		decisionAmount : {
			required : "请输入金额"
		},
		decisionRate : {
			required : "请输入利率"
		}
	},
	errorPlacement : function(error, element) {
		element.after(error);
		if (layout) {
			layout.resizeLayout();
		}
	}
});