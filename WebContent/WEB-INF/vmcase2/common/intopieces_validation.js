var validator = $($formName).validate({
	rules : {
		chineseName : {
			required : true,
			rangelength : [ 1, 50 ]
		},
		sex : {
			required : true
		},
		pinyinenglishName : {
			required : true,
			rangelength : [ 1, 50 ]
		},
		cardType : {
			required : true
		},
		cardId : {
			required : true
		},
		productId :{
			required : true
		},
		applyQuota :{
			required : true,
			number:true
		},
		customerName:{
			required : true
		},
		identityType:{
			required : true
		},
		customerIdentity:{
			required : true
		},
		cardNumber:{
			required : true
		},
		cardOrganization:{
			required : true
		}
		
		
		
		
	},
	messages : {
		chineseName : {
			required : "姓名不能为空",
			rangelength : "的长度在{0}到{1}之间"
		},
		sex : {
			required : "请选择一个类型"
		},
		pinyinenglishName : {
			required : "拼音或英文姓名不能为空",
			rangelength : "姓名的长度在{0}到{1}之间"
		},
		cardType : {
			required : "证件类型不能为空"
		},
		cardId : {
			required : "证件号码不能为空"
		},
		productId : {
			required : "请选择一款产品"
		},
		applyQuota : {
			required : "申请额度不能为空",
			number:"申请额度请填写数字"
		},
		customerName:{
			required : "客户姓名不能为空"
		},
		identityType:{
			required : "客户证件类型不能为空"
		},
		customerIdentity:{
			required : "客户证件号码不能为空"
		},
		cardNumber:{
			required : "卡码不能为空"
		},
		cardOrganization:{
			required : "组织结构不能为空"
		}
		
		
	},
	errorPlacement : function(error, element) {
		element.after(error);
	}
});