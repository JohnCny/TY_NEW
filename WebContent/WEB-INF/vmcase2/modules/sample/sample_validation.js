var validator = $($formName).validate({
	rules:
    {name:
           {required:true,
           rangelength:[5, 50]},
     type:
          {required:true},
     native_name:
           {required:true,
           rangelength:[5, 50]},
     sample_date:
            {required:true,
            maxlength:50.0},
     description:
            {required:true,
            maxlength:1000.0}
     },
messages:
    {name:
        {required:"姓名不能为空",
        rangelength:"的长度在{0}到{1}之间"},
    type:
        {required:"请选择一个类型"},
    native_name:
        {required:"显示名称不能为空",
        rangelength:"姓名的长度在20到50之间"},
   sample_date:
       {required:"内容不能为空",
       maxlength:"内容的长度不能超过50"},
   description:{required:"描述信息不能为空",
       maxlength:"描述信息的长度不能超过50"}
 },
	errorPlacement : function(error, element) {
		element.after(error);
		if(layout){
			layout.resizeLayout();
		}
	}
});