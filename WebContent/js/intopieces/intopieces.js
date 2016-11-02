function mySave(flag) {
	var quota = $("input[name='applyQuota']").val();
	var productId = $("#productId").val();
	var customerBusinessType = $("input[name='customerBusinessType']:checked").val();
	if(customerBusinessType=="Type01"){
		var customerBusinessAptitude = null;
		$("input[name='customerBusinessAptitude']:checked").each(function() {
			customerBusinessAptitude = $(this).val();
		});
		
		if (customerBusinessAptitude==null){
			Dialog.message("客户商业类型为散件时候请选择对应的散件类型!");
		}
		/*判断当前用户能选择客户类型*/
		$("input[name='customerBusinessAptitude']:checked").each(function() {
			var value = $(this).val();
			$.ajax({
				dataType : 'json',
				type : "post",
				url : contextPath+"/intopieces/intopiecesquery/checkValue.json",
				data:{"value":value,"applyQuota":quota,"productId":productId},
				success : function(data) {
					if (!data.success) {
						Dialog.confirm(data.message, "是否移交进件对话框", function(){
							save("save",true);
							return false;
						},function(){
							/*save(flag,false);*/
							return false;
						});
					} else {
						save(flag,false);
						return false;
					}
				},
				error : function(data) {
					Dialog.closeLoad();
					Dialog.message("无法验证客户商业类型!");
					return false;
				}
			});
		});
	}else{
		checkApplyQuota(flag);
	}
}

function checkApplyQuota(flag){
	var quota = $("input[name='applyQuota']").val();
	var productId = $("#productId").val();
	/*检查授信额度*/
	$.ajax({
		dataType : 'json',
		type : "post",
		url : contextPath+"/intopieces/intopiecesquery/checkApplyQuota.json",
		data:{"applyQuota":quota,"productId":productId},
		success : function(data) {
			if (!data.success) {
				Dialog.confirm(data.message, "是否移交进件对话框", function(){
					save("save",true);
					return false;
				},function(){
					/*save(flag,false);*/
					return false;
				});
			} else {
				save(flag,false);
				return false;
			}
		},
		error : function(data) {
			Dialog.closeLoad();
			Dialog.message("授信额度检查失败!");
			return false;
		}
	});
}

function save(flag,checkFlag){
	$('#allInfoForm').ajaxSubmit({
		dataType : 'json',
		type : "post",
		data:{"operationFlag":flag,"checkFlag":checkFlag},
		success : function(data) {
			if (data.success) {
				Dialog.message(data.message,"提示", function() {
				 self.location.href=contextPath+"/intopieces/intopiecesquery/browse.page";
				 });
			} else {
				Dialog.message(data.message);
				return false;
			}
		},
		error : function(data) {
			Dialog.closeLoad();
			Dialog.message("操作失败");
			return false;
		}
	});
}



function saveCard(reloadUrl) {
	$('#allInfoForm').ajaxSubmit({
		dataType : 'json',
		type : "post",
		success : function(data) {
			if (data.success) {
				Dialog.message(data.message,"提示", function() {
				 self.location.href=reloadUrl;
				 });
			} else {
				Dialog.message(data.message);
				return false;
			}
		},
		error : function(data) {
			Dialog.closeLoad();
			Dialog.message("操作失败");
			return false;
		}
	});
}

function issuedCard(url,reloadUrl,flag){
	var id = null;
	var operationflag = true;
	if(flag){
		$("input[name='checkbox']:checked").each(function() {
			id = $(this).val();
			$(this).parent().parent().find("td").each(function(i){
				if(i==4){
					if($.trim($(this).html())=='未下发'){
						Dialog.message("未下发到机构的卡片不可以下发到客户!");
						operationflag = false;
						return false;
					}
				}
			});
		});
	}
	if(!operationflag){
		return false;
	 }	
	
	$("input[name='checkbox']:checked").each(function() {
		id = $(this).val();
		return false;
	});
	if(id!=null){
		$.ajax({
			dataType : 'json',
			type : "post",
			url : url,
			data:{"id":id},
			success : function(data) {
				if (data.success) {
					Dialog.message(data.message,"提示", function() {
						self.location.href = reloadUrl;
					 });
				} else {
					Dialog.message(data.message);
					return false;
				}
			},
			error : function(data) {
				Dialog.closeLoad();
				Dialog.message("操作失败");
				return false;
			}
		});
	}else{
		Dialog.message("请选择要下发的卡片!");
		return;
	}
}

function viewCard(url){
	var id = null;
	$("input[name='checkbox']:checked").each(function() {
		id = $(this).val();
		return false;
	});
	if(id!=null){
		self.location.href = url+"?id="+id;
	}else{
		Dialog.message("请选择要查看的卡片!");
		return;
	}
	
}


function reset(){
	$('#allInfoForm')[0].reset();
}

function chooseCustomer(flag){
	if(flag){
		self.location.href = contextPath+"/intopieces/intopiecesquery/input.page?customerCardId="+$("#cardId").val();
		return;
	}else{
		var id = null;
		$("input[name='checkbox']:checked").each(function() {
			id = $(this).val();
		});
		if(id!=null){
			self.location.href = contextPath+"/intopieces/intopiecesquery/input.page?customerId="+id;
			return;
		}else{
			Dialog.message("请选择一个客户!");
			return;
		}
	}
}

function dynamicAdd(tableId){
	var list = $("#allInfoForm").find("table[id^="+tableId+"Add]");
	var cloneTable = $("#"+tableId).clone(true);
	var radioName = $(cloneTable).find("input[type='radio']").attr("name");
	if(list !=null && list.size()>0){
		cloneTable.attr("id",tableId+"Add"+(list.size()+1));
		if(radioName!=null){
			$(cloneTable).find("input[type='radio']").attr("name",radioName).attr("name",radioName+(list.size()));
		}
		cloneTable.find("input").each(function(){
			 $(this).val("");
		}); 
		cloneTable.insertAfter("#"+tableId+"Add"+list.size());
	}else{
		if(radioName!=null){
			$(cloneTable).find("input[type='radio']").attr("name",radioName).attr("name",radioName+"0");
		}
		cloneTable.attr("id",tableId+"Add"+1);
		cloneTable.find("input").each(function(){
			 $(this).val("");
		});
		cloneTable.insertAfter("#"+tableId);
	}
}

function dynamicDelete(tableId){
	 var list = $("#allInfoForm").find("table[id^="+tableId+"Add]");
	 if(list!=null && list.size()>0){
		var obj = $("#"+tableId+"Add"+(list.size()));
		var  name = obj.find("tr:first").find("input[type='hidden']").attr("name");
		var value = obj.find("tr:first").find("input[type='hidden']").val();
		Dialog.confirm("你确认要删除吗?", "删除对话框", function(){
			$.ajax({
				dataType : 'json',
				type : "post",
				url : contextPath+"/intopieces/intopiecesquery/delete.json",
				data:{"name":name,"value":value},
				success : function(data) {
					if (data.success) {
						Dialog.message(data.message,"提示", function() {
							$("table").remove("#"+tableId+"Add"+(list.size()));
						 });
					} else {
						Dialog.message(data.message);
						return false;
					}
				},
				error : function(data) {
					Dialog.closeLoad();
					Dialog.message("删除失败");
					return false;
				}
			});
		});
     }
	 else{
	   /*至少留一个table*/
	  /*$("table").remove("#"+tableId);*/
  }
}

$(function() {
	 $("#chineseName").autocomplete(contextPath+"/intopieces/intopiecesquery/chooseCustomerName.json", {
			minChars: 0,
			max: 12,
			autoFill: true,
			//mustMatch: true,
			matchContains: false,
			scrollHeight: 220,
			width:250,
			dataType:'json',
			parse:function(data){
				var parsed = [];
				for(var i = 0; i < data.length; i++){
					parsed[i] = {
						data : data[i] ,
						value : data[i].chineseName,
						result: data[i].chineseName// 返回的结果显示内容 
					};
				}
				return parsed;
			}, 
			formatItem: function(item) {// 显示下拉列表的内容
				return "<div style='width:50%;float:left;'> " + item.chineseName + "</div><div style='width:50%;float:left;'>" + item.telephone + "</div>"; 
			}
		});
		
	$("#cardId").autocomplete(contextPath+"/intopieces/intopiecesquery/selectByLike.json", {
		minChars: 0,
		max: 12,
		autoFill: true,
		matchContains: false,
		scrollHeight: 220,
		width:250,
		dataType:'json',
		parse:function(data){
			var parsed = [];
			for(var i = 0; i < data.length; i++){
				parsed[i] = {
					data : data[i] ,
					value : data[i].cardId,
					result: data[i].cardId
				};
			}
			return parsed;
		}, 
		formatItem: function(item) {// 显示下拉列表的内容
			return "<div style='width:50%;float:left;'> " + item.cardId + "</div><div style='width:50%;float:left;'>" + item.chineseName + "</div>"; 
		}
	});
	
	
	/*第一次进来时候处理*/
	$("input[name='creditCard']:checked").each(function() {
		var value = $(this).val();
		if (value == '3') {
			$("input[name='howManyCard']").attr("readonly", false);
			$("input[name='maximumAmount']").attr("readonly", false);
			return false;
		} else {
			$("input[name='howManyCard']").val("");
			$("input[name='maximumAmount']").val("");
			$("input[name='howManyCard']").attr("readonly", "readonly");
			$("input[name='maximumAmount']").attr("readonly", "readonly");
			return false;
		}
	});
     /*值改变时候处理*/
	$("input[name='creditCard']").change(function() {
		$("input[name='creditCard']:checked").each(function() {
			var value = $(this).val();
			if (value == '3') {
				$("input[name='howManyCard']").attr("readonly", false);
				$("input[name='maximumAmount']").attr("readonly", false);
				return false;
			} else {
				$("input[name='howManyCard']").val("");
				$("input[name='maximumAmount']").val("");
				$("input[name='howManyCard']").attr("readonly", "readonly");
				$("input[name='maximumAmount']").attr("readonly", "readonly");
				return false;
			}
		});
	});
	
	$("#lastTr").hide();
	/*第一次进来时候处理*/
	$("input[name='customerBusinessType']:checked").each(function() {
		var value = $(this).val();
		if (value == 'Type01') {
			$("#lastTr").show();
			return false;
		}else{
			$("input[name='customerBusinessAptitude']").val("");
			$("#lastTr").hide();
		}
	});
    /*值改变时候处理*/
	$("input[name='customerBusinessType']").change(function() {
		$("input[name='customerBusinessType']:checked").each(function() {
			var value = $(this).val();
			if (value == 'Type01') {
				$("#lastTr").show();
				return false;
			}else{
				$("input[name='customerBusinessAptitude']").val("");
				$("#lastTr").hide();
			}
		});
	});
	
	/*第一次进来时候*/
	$("#payTypeTr").hide();
	$("input[name='isAutoPay']:checked").each(function(){
		var value = $(this).val();
		if(value=='1'){
			$("#payTypeTr").show();
		}else{
			$("#payTypeTr").hide();
			$("debitWay").val("");
			$("repaymentCardNumber").val("");
		}
	});
	
	/*值改变的时候*/
	$("input[name='isAutoPay']").change(function(){
		var value = $("input[name='isAutoPay']:checked").val();
		if(value=='1'){
			$("#payTypeTr").show();
		}else{
			$("#payTypeTr").hide();
			$("debitWay").val();
			$("repaymentCardNumber").val();
		}
	});
	
	
	
	
	
	
	
});

function selectCustomerChange(url,productId,applicationId){
	$.ajax({
		dataType : 'json',
		type : "post",
		url:url,
		data:{'productId':productId,'applicationId':applicationId},
		success : function(data) {
			if (data.success) {
					var addString = null;
					var list = $("#allInfoForm").find("table[id^=appendix]");
					if(list!=null && list.size()>0){
						list.each(function(){
							$(this).remove();
						});
						if(data.resultList1.length>0){
							addString = "<table class='rule' id='appendix' border='1' bordercolor='#CCCCCC'><caption>产品附件</caption><colgroup><col width='10%'><col width='40%'><col width='10%'><col width='40%'></colgroup>";
							$.each(data.resultList1,function(){
								var parent = this;
								addString += "<tr><td><label>文件名称</label></td><td><input type='text' name='productAccessoryName' readonly='readonly' style='width: 260px;' value='"+parent.typeName+"'>";
								$.each(data.resultList2,function(){
									if(parent.typeCode==this.appendixTypeCode){
										addString +="<font color='red'>已上传资料</font><input type='hidden' name='addressAccessoriesId' value='"+this.id+"'>";
										return false;
									}
								});
								addString +="</td>&nbsp;<td><label>产品附件</label></td><td><input type='file' name='file_"+parent.typeCode+"'><input type='hidden' name='appendixTypeCode' value='"+parent.typeCode+"'></td></tr>";
							});
							addString +="</table>";
							$("#allForm").append(addString);
						}
					}else{
						if(data.resultList1.length>0){
							addString = "<table class='rule' id='appendix' border='1' bordercolor='#CCCCCC'><caption>产品附件</caption><colgroup><col width='10%'><col width='40%'><col width='10%'><col width='40%'></colgroup>";
							$.each(data.resultList1,function(){
								var parent = this;
								addString += "<tr><td><label>文件名称</label></td><td><input type='text' name='productAccessoryName' readonly='readonly' style='width: 260px;' value='"+parent.typeName+"'>";
								$.each(data.resultList2,function(){
									if(parent.typeCode==this.appendixTypeCode){
										addString +="<font color='red'>已上传资料</font><input type='hidden' name='addressAccessoriesId' value='"+this.id+"'>";
									    return false;
									}
								});
								addString +="</td>&nbsp;<td><label>产品附件</label></td><td><input type='file' name='file_"+parent.typeCode+"'><input type='hidden' name='appendixTypeCode' value='"+parent.typeCode+"'></td></tr>";
							});
							addString +="</table>";
							$("#allForm").append(addString);
						}
					}	
			} else {
				Dialog.message(data.message);
				return false;
			}
		},
		error : function(data) {
			Dialog.closeLoad();
			Dialog.message("操作失败");
			return false;
		}
	});
}

function updateIntoPices(){
	var id = null;
	var flag = true;
	$("input[name='checkbox']:checked").each(function() {
		id = $(this).val();
		$(this).parent().parent().find("td").each(function(i){
			if(i==5){
				if($.trim($(this).html())!='暂存'&&$.trim($(this).html())!='申请未通过'){
					Dialog.message("当前状态的进件不可以修改!");
					flag = false;
					return false;
				}
			}
		});
	});
	if(id!=null&&flag){
		self.location.href=contextPath+"/intopieces/intopiecesquery/input.page?intoPicesidAndCustorId="+id;
		return;
	}else{
		Dialog.message("请选择要被修改的进件!");
		return;
	}
}

function viewIntoPices(flag){
	var id = null;
	$("input[name='checkbox']:checked").each(function() {
		id = $(this).val();
	});
	if(id!=null&&flag){
		self.location.href=contextPath+"/intopieces/intopiecesquery/display.page?intoPicesidAndCustorId="+id+"&viewFlag="+flag;
		return;
	}else{
		Dialog.message("请选择要被查看的进件!");
		return;
	}
}


function linkImport(url){
	self.location.href = url;
}

function importData(url) {
	var id = null;
	var flag = true;
	$("input[name='checkbox']:checked").each(function() {
		id = $(this).val();
		$(this).parent().parent().find("td").each(function(i) {
			if (i == 6) {
				if ($.trim($(this).html()) != '审批结束') {
					Dialog.message("只有审批结束的数据才可以导出数据!");
					flag = false;
					return false;
				}
			}
		});
	});
	if(id==null){
		Dialog.message("请选择要导出的数据!");
		return false;
	}
	if (flag) {
		$.ajax({
			dataType : 'json',
			url : url + "?intoPicesidAndCustorId=" + id,
			success : function(data) {
				Dialog.message(data.message);
				return false;
			},
			error : function(data) {
				Dialog.closeLoad();
				Dialog.message("操作失败");
				return false;
			}
		});
	}
}

function importCustomer(reloadUrl){
	if($("#file").val()==""){
		Dialog.message("请选择导入文件");
		return;
	}
	var uploadTip = document.getElementById("uploadTip");
	var uploadButton = document.getElementById("uploadButton");
	uploadTip.style.display='inline';
	uploadButton.disabled='true';
	Dialog.load();
	$("#excelForm").ajaxSubmit({
		dataType : 'json',
		type : "post",
		success : function(data) {
			Dialog.closeLoad();
			if (data.success) {
				topWin.Dialog.message(data.message,"提示", function() {
				 self.location.href = reloadUrl;
				 });
			} else {
				topWin.Dialog.message(data.message);
				uploadTip.style.display='none';
				uploadButton.disabled='';
				return false;
			}
		},
		error : function(data) {
			topWin.Dialog.closeLoad();
			topWin.Dialog.message("导入失败");
			uploadTip.style.display='none';
			uploadButton.disabled='';
			return false;
		}
	});
}

