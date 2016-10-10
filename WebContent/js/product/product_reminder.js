/*
* 获取页面数据
*/
function getCollectionData(){
	var jsonArrObj = [];
	var loanTypes = $("div[loan_type]");
	
	var customerId = $("#customerId").val();
	var namesObj = null;
	for(var i = 0; i < loanTypes.length; i++){
		var items = $(loanTypes[i]).children();
		var num = 1;
		for(var j=0; j < items.length; j++){
			var jsonObj = {};
			var item = $(items[j]);
			jsonObj.customerId = customerId;
			jsonObj.loanType = $(loanTypes[i]).attr("loan_type");
			namesObj = item.find("[name=NAMES]")[0];
			if(!namesObj){
				continue;
			}
			if(namesObj.tagName == "INPUT"){
				jsonObj.names = $(namesObj).val();
			}else{
				jsonObj.names = $(namesObj).html();
			}
			jsonObj.contentsTextNumbers = getTextNumbers(item).val();
			jsonObj.no = num++;
			jsonArrObj.push(jsonObj);
		}
	}
	return jsonArrObj;
}
