$(document).ready(function(){
	/*创建option，设置文本为请选择，value为""*/
	var addSelOption = function(jq){
		$("<option/>").text("请选择...").attr("value","").appendTo(jq);
	};
	var requestUrl = "getManager.json";
	$.ajax({
		    type:"GET",
			url:requestUrl,
			dataType:"json",
			success:function(json){
				$("#customerManagerId").empty();
				addSelOption($("#customerManagerId"));
				$.each(json,function(i,n){
					$("<option/>").text(n.displayName).attr("value",n.userId).appendTo($("#customerManagerId"));
					});
				getCustomer();
			}
	});
	$("#customerManagerId").change(function(){
		var customerManagerId = $(this).val();
		if(customerManagerId != ""){
			getCustomer();
		}else{
			$("#customerId").empty();
			addSelOption($("#customerId"));
		}
	});
	$("#customerId").change(function(){
		var customerId = $(this).val();
		if(customerId != ""){
			getProduct();
		}else{
			$("#productId").empty();
			addSelOption($("#productId"));
		}
	});
	function getProduct(){
		$.ajax({
		    type:"GET",
			url:"getProduct.json",
			data:{id:$("#customerId").val()},
			dataType:"json",
			success:function(json){
				$("#productId").empty();
				$.each(json,function(i,n){
					$("<option/>").text(n.typeName).attr("value",n.typeCode).appendTo($("#productId"));
		});
			}
	});
	}
	function getCustomer(){
		$.ajax({
		    type:"GET",
			url:"getCustomer.json",
			data:{id:$("#customerManagerId").val()},
			dataType:"json",
			success:function(json){
				$("#customerId").empty();
				addSelOption($("#customerId"));
				$.each(json,function(i,n){
					$("<option/>").text(n.typeName).attr("value",n.typeCode).appendTo($("#customerId"));
		});
			}
	});
	}
});

