var validator = $($formName).validate({
	rules : {
		maintenanceDay : {
			required : true, number:true
		},
		maintenanceEndTime :{
			required : true
		},
		maintenanceStartTime :{
			required : true
		}
	},
	messages : {
		maintenanceDay : {
			required : "维护天数不能为空",number : "请输入整数"
		},
		maintenanceEndTime :{
			required : "实施维护结束时间不能为空"
		},
		maintenanceStartTime :{
			required : "实施维护开始时间不能为空"
		}
	},
	errorPlacement : function(error, element) {
		element.after(error);
		if (layout) {
			layout.resizeLayout();
		}
	}
});