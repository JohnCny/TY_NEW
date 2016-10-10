var validator = $($formName).validate({
	rules:
    { 
		productName:{required:true},
		purposeLoan:{required:true},
		prodCreditRange:{required:true},
		
		prodLimitTime:{required:true},
		loanTimeLimit:{required:true}
     },
messages:
    {
		productName:{required:"产品名称不能为空"},
		purposeLoan:{required:"贷款用途不能为空"},
		prodCreditRange:{required:"产品授信区间不能为空"},
		
		prodLimitTime:{required:"产品期限不能为空"},
		loanTimeLimit:{required:"贷款期限不能为空"}
   },
	errorPlacement : function(error, element) {
		element.after(error);
		if(layout){
			layout.resizeLayout();
		}
	}
});