var validator = $($formName).validate({
	rules : {
		trainingLocation : {
			required : true, 
			maxlength:100.0
		},
		trainingTime :{
			required : true
		},
		trainingPeople :{
			required : true,
			maxlength:100.0
		},
		trainingMethod :{
			maxlength:100.0
		},
		trainingObjective :{
			maxlength:100.0
		},
		trainingContent:{
			maxlength:100.0
		}
	},
	messages : {
		trainingLocation : {
			required : "培训地点不能为空", 
			maxlength: "培训地点长度最大为100"
		},
		trainingTime :{
			required : "培训时间不能为空"
		},
		trainingPeople :{
			required : "培训实施人不能为空",
			maxlength: "培训实施人长度最大为100"
		},
		trainingMethod :{
			maxlength : "培训方式长度最大为100"
		},
		trainingObjective :{
			maxlength : "培训目标长度最大为100"
		},
		trainingContent:{
			maxlength : "培训内容长度最大为100"
		}
	},
	errorPlacement : function(error, element) {
		element.after(error);
		if (layout) {
			layout.resizeLayout();
		}
	}
});