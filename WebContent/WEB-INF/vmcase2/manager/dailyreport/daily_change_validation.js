var validator = $($formName).validate({
	rules : {
		todayplan : {
//			morningPlan : {
			required : true
		}
	},
	messages : {
		morningPlan : {
//			morningPlan : {
//			required : "上午计划不能为空"
				required : "今日计划不能为空"
		}
	},
	errorPlacement : function(error, element) {
		element.after(error);
		if (layout) {
			layout.resizeLayout();
		}
	}
});