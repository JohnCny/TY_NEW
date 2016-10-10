/*
* 获取页面数据
*/
function getData(){
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

/*
* 添加一行
*/
function addRowItem(jqObj){
	var rowHtml = ' <div class="form-block clearfix">'
				+ '	<div class="form-l">{1}：</div>'
			 	+ '	<div class="form-r">'
			 	+ '		<input name="CONTENTS_TEXT_NUMBERS" type="text" style="width: 125px;" onblur="collectItem($(this).parent().parent().parent())"/> 元'
			 	+ '	</div>'
			 	+ '</div>';
	var inputHtml = '<input name="NAMES" type="text" maxlength="25" style="width: 125px;margin-top:-3px;" />';
	var jqRowObj = $(rowHtml.replace('{1}',inputHtml));
	// 添加格式化事件
	getTextNumbers(jqRowObj).moneyFormat();
	jqObj.append(jqRowObj);
	// 改变高度
	//changeHeight(jqObj, jqRowObj);
}

/*
* 删除一行
*/
function deleteRowItem(jqObj){
	var rows = jqObj.children();
	var len = rows.length;
	if(len > 1){
		var jqRowObj = $(rows[len-1]);
		// 删除最后一行
		jqRowObj.remove();
		// 改变高度
		//changeHeight(jqObj, jqRowObj);
	}
	//
	collectItem(jqObj);
}

/*
* 改变高度
*/
function changeHeight(jqObj, jqRowObj){
	var jqParentObj = jqObj.parent();
	var height = jqObj.height() + num;
	var childrens = jqParentObj.children();
	for(var i = 0; i < childrens.length; i++){
		$(childrens[i]).css('height', height);
	}
}

/*
 * 获取同种类型的总和(第一行除外)
 */
function collectItem(obj){
	var textNumbers = getTextNumbers(obj);//obj.find("input[name=CONTENTS_TEXT_NUMBERS]");
	
	var firstNumbersObj = textNumbers.filter(":eq(0)");
	var numberObjs = textNumbers.filter(":gt(0)");
	
	if(numberObjs){
		var total = 0;
		numberObjs.each(function (index, domEle) { 
			// domEle == this 
			total += getFloatValue($(domEle).val());
		});
		firstNumbersObj.val(total.toFixed(2));
	}
	
	if(obj.attr("loan_type") == "5"){
		// 分析期间累计收入
		getTextNumbers(10, ":eq(1)").val(firstNumbersObj.val());
	}
	// 计算并设置业务数据
	calculate();
}

/*
 * 计算并设置业务数据
 */
function calculate(){
	// 初期权益合计
	var cqqyhj = getTextNumbers(4, ":eq(0)");
	// 分析期间收入合计
	var fxqjhjsr = getTextNumbers(5, ":eq(0)");
	// 大项支出合计
	var dxzc = getTextNumbers(6, ":eq(0)");
	// 其他收入
	var qtsr = getTextNumbers(7, ":eq(0)");
	// 折旧(资产负债表内折旧)
	var zj = getTextNumbers(7, ":eq(1)");
	// 升值
	var sz = getTextNumbers(8, ":eq(0)");
	// 表外资产
	var bwzc = getTextNumbers(8, ":eq(1)");
	// 应有权益
	var yyqj = getTextNumbers(9, ":eq(0)");
	var value = getFloatValue(cqqyhj.val()) 
				+ getFloatValue(fxqjhjsr.val())
				- getFloatValue(dxzc.val())
				+ getFloatValue(qtsr.val())
				+ getFloatValue(sz.val())
				- getFloatValue(zj.val())
				- getFloatValue(bwzc.val());
		
	// 设置"应有权益"的值
	yyqj.val(value.toFixed(2));
	
	// 实际权益
	var sjqy = getTextNumbers(10, ":eq(0)");
	// 权益差额
	var qyce = getTextNumbers(9, ":eq(1)");
	// 设置"权益差额"的值
	qyce.val((getFloatValue(yyqj.val()) - getFloatValue(sjqy.val())).toFixed(2));
	
	// 权益交叉检验比率
	var qyjcjybl = getTextNumbers(9, ":eq(2)");
	// 设置"权益交叉检验比率"
	if(getFloatValue(fxqjhjsr.val()) == 0){
		qyjcjybl.val(0);
	}else{
		qyjcjybl
				.val((getFloatValue(qyce.val()) / getFloatValue(fxqjhjsr.val()))
						.toFixed(2));
	}
}

/*
 * 获取浮点数
 */
function getFloatValue(val){
	var number = parseFloat($.trim(val));
	if(isNaN(number)){
		return 0;
	}
	return number;
}

/*
 * 获取某种贷款类型下的数据
 */
function getTextNumbers(loanType, filter){
	var loanTypeObj = loanType;
	if(jQuery.type(loanType) === "number"){
		loanTypeObj = $("div[loan_type="+loanType+"]");
	}
	if(filter){
		return loanTypeObj.find("[name=CONTENTS_TEXT_NUMBERS]").filter(filter);
	}else{
		return loanTypeObj.find("[name=CONTENTS_TEXT_NUMBERS]");
	}
}

/*
 * 校验
 */
function validate(){
	var flag = true;
	var message ="该项不能为空";
	var itemObjs = $("#formsId :input").filter(":enabled");
	for(var i = 0; i < itemObjs.length; i++){
		if($.trim($(itemObjs[i]).val()) == ""){
			$(itemObjs[i]).after("<label class='error' generated='true' >" + message + "</label>");
			//$(itemObjs[i]).after(msg);
			Dialog.message(msg.replace("{0}",""));
			flag = false;
		}
	}
	return flag;
}

$.fn.extend({
	moneyFormat : function () {
		return this.each(function () {
			$(this).keyup(function () {
				var reg = /^\d*\.?\d{0,2}$/,
				reg2 = /(?:\d*\.\d{0,2}|\d+)/,
				reg3 = /[^.0-9]+/;
				var _val = $(this).val(),
				isPlus = /^-/.test(_val),
				_val = isPlus ? _val.substr(1) : _val;
				if (!reg.test(_val)) {
					_val = _val.replace(reg3, "").match(reg2);
					_val = _val == null ? "" : _val[0];
					$(this).val(isPlus ? ("-" + _val) : _val);
				}
			}).blur(function () {
				var reg1 = /^\d+$/,
				reg2 = /^\.\d{0,2}$/,
				reg3 = /^\d+\.\d{0,2}$/,
				reg4 = /^0+(?:[1-9]\d*|0)\.\d{0,2}$/,
				reg5 = /^0+((?:[1-9]\d*|0)\.\d{0,2})$/;
				var _val = $(this).val(),
				isPlus = /^-/.test(_val),
				_val = isPlus ? _val.substr(1) : _val;
				if (reg1.test(_val)) {
					_val = _val + ".00";
				}
				if (reg4.test(_val)) {
					_val = _val.replace(reg5, "$1");
				}
				if (reg2.test(_val)) {
					_val = "0" + _val;
				}
				if (reg3.test(_val)) {
					var len = _val.length - _val.indexOf(".") - 1,
					str = "";
					for (var i = 0; i < 2 - len; i++) {
						str += "0";
					}
					_val += str;
				}
				$(this).val(isPlus ? ("-" + _val) : _val);
			});
		});
	}
});
$("textarea").bind("keydown keyup",function(){
	var obj = $(this);
	var len = 100;
	if(obj.attr("maxlength") && len < parseInt(obj.attr("maxlength"))){
		len = obj.attr("maxlength");
	}
	var curLength = obj.val().length;
	if (curLength >= len) {
		obj.val(obj.val().substr(0, len - 1));
	}
});
