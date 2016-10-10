$(document).ready(function(){
	
	//获得产品的名称
	$("#customerId").live("change",function(){
		var customerId = $("#formsId select[name='customerId'] option:selected").val();
		  $.ajax({
				url : contextPath+"/accountabilitycontrol/accountability/selectProductDisplayNameById.json",
					type : "post",
					data : {"customerId": customerId},
				dataType : "json",
				 success : function(data) {
					 $("#formsId select[name='productId'] option").remove();
					    $.each(data.results,function(index, ele){
					    	$("#formsId select[name='productId']").append("<option value="+ele.productId+">" + ele.productName + "</option>");
					    });
					}
				});

	});
	
	
	
	
	
});
//获得客户经理的名称
$(function() {
	$("#displayName").autocomplete(contextPath+"/accountabilitycontrol/accountability/selectByLike.json", {
		minChars: 0,
		autoFill: true,
		max:100,
		matchContains: false,
		scrollHeight: 220,
		width:300,
		dataType:'json',
		parse:function(data){
			var parsed = [];
			for(var i = 0; i < data.length; i++){
				parsed[i] = {
					data : data[i] ,
					value : data[i].displayName,
					result: data[i].displayName
				};
			}
			return parsed;
		}, 
		formatItem: function(item) {// 显示下拉列表的内容
			return "<div style='width:50%;float:left;'> " + item.displayName + "</div><div style='width:40%float:left;;display:none;'>" +  item.id + "</div>"; 
		}
	}).result(function(event, data, formatted) {
		//获取客户名称
		$("#customerManagerId").val(data.id);
		  $.ajax({
				url :contextPath+"/accountabilitycontrol/accountability/selectDisplayNameById.json",
					type : "post",
					data : {"customerManagerId": data.id},
				dataType : "json",
				 success : function(data) {
					 $("#formsId select[name='customerId'] option").remove();
						$("#formsId select[name='productId'] option").remove();
					    $.each(data.results,function(index, ele){
					    	$("#formsId select[name='customerId']").append("<option value="+ele.id+">"+ele.chineseName+"</option>");
					    });
					}
				});

		            });
});


