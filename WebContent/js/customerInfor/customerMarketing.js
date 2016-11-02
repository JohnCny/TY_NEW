function initResource(){
	getCustomer();
	getProduct();
}
function changeResource(){
	$("#customerId").empty();
	$("#productId").empty();
	getCustomer();
	getProduct();
}
function getCustomer(){
	var customerManagerId = $("#customerManagerId").val();
	if(customerManagerId!=null && customerManagerId!="null"){
		$.ajax({
			type:"GET",
			url:"getCustomer.json",
			data:{id:$("#customerManagerId").val()},
			dataType:"json",
			success:function(data){
				$.each(data,function(index,comment){
					$("#customerId").append("<option value='"+comment['id']+"'>"+comment['chineseName']+"</option>");
				});
			}
		});
	}
}
function getProduct(){
	var customerManagerId = $("#customerManagerId").val();
	if(customerManagerId!=null && customerManagerId!="null"){
		$.ajax({
			type:"GET",
			url:"getProduct.json",
			data:{id:$("#customerManagerId").val()},
			dataType:"json",
			success:function(data){
				$.each(data,function(index,comment){
					$("#productId").append("<option value='"+comment['id']+"'>"+comment['productName']+"</option>");
				});
			}
		});
	}
}
