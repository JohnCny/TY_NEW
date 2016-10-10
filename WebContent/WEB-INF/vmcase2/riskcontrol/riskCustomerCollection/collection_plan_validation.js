var validator = $($formName).validate({
	rules : {
		collectionTime : {
			required : true, number:true
		},
		customerManagerId :{
			required : true
		},
		customerId :{
			required : true
		}
	},
	messages : {
		collectionTime : {
			required : "维护天数不能为空",number : "请输入整数"
		},
		customerManagerId :{
			required : "客户经理不能为空"
		},
		customerId :{
			required : "客户名称不能为空"
		}
	},
	errorPlacement : function(error, element) {
		element.after(error);
		if (layout) {
			layout.resizeLayout();
		}
	}
});