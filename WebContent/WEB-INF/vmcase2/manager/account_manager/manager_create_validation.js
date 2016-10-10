var validator = $($formName).validate({
	rules : {
		levelInformation : {
			required : true
		},
		userId :{
			required : true
		}
	},
	messages : {
		levelInformation : {
			required : "客户经理层级信息"
		},
		userId :{
			required : "客户经理姓名"
		}
	},
	errorPlacement : function(error, element) {
		element.after(error);
		if (layout) {
			layout.resizeLayout();
		}
	}
});