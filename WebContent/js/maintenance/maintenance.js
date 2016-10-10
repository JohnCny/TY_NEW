$(document).ready(function(){
	/*创建option，设置文本为请选择，value为""*/
	var addSelOption = function(jq){
		$("<option/>").text("请选择...").attr("value","").prependTo(jq);
	};
	var requestUrl = "getManager.json";
	$.ajax({
		    type:"GET",
			url:requestUrl,
			dataType:"json",
			success:function(json){
				$("#customerManagerId").empty();
				$.each(json,function(i,n){
					$("<option/>").text(n.displayName).attr("value",n.userId).appendTo($("#customerManagerId"));
					});
				addSelOption($("#customerManagerId"));
				getCustomer();
			}
	});
	$("#customerManagerId").change(function(){
		var customerManagerId = $(this).val();
		if(customerManagerId != "null" && customerManagerId!=null){
			getCustomer();
		}else{
			$("#customerId").empty();
		}
	});
	function getCustomer(requestUrl){
		$.ajax({
		    type:"GET",
			url:"getCustomer.json",
			data:{id:$("#customerManagerId").val()},
			dataType:"json",
			success:function(json){
				$("#customerId").empty();
				$.each(json,function(i,n){
					$("<option/>").text(n.chineseName).attr("value",n.id).appendTo($("#customerId"));
					});
				addSelOption($("#customerId"));
			}
	});
	}
});
