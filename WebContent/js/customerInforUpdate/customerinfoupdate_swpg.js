/*
 * 生存状况table
 * 作者：王海东
 *
 */

var hj = 0.25; // 户籍
var fc = 0.5; // 房产
var hy = 0.15; // 婚姻
var zwzc = 0.1;// 职位职称

$(document).ready(function() {
	//输入框格式控制
	$(".rinpsty").each(function (index, domEle) { 
		  // domEle == this 
		  $(domEle).moneyFormat();
	});
});
// 户籍
function caculateHJ() {
	var i = $('#huji option:selected').val();
	if (i != "") {
		document.getElementById("registerValue").value = i;
		document.getElementById("registerWeight").value = i * hj;
	} else {
		document.getElementById("registerValue").value = null;
		document.getElementById("registerWeight").value = null;
	}
	sczkhbz();
	sczkhzz();
	zzed();
}
// 房产
function caculateFC() {
	var i = $('#fcqk option:selected').val();
	if (i != "") {
		
		document.getElementById("houseValue").value = i;
		document.getElementById("houseWeight").value =Math.round(i * fc*100)/100;
	} else {
		document.getElementById("houseValue").value = null;
		document.getElementById("houseWeight").value = null;
	}
	sczkhbz();
	sczkhzz();
	zzed();
}
// 婚姻
function caculateHYZK() {
	var i = $('#hyzk option:selected').val();
	if (i != "") {
		document.getElementById("marriageValue").value = i;
		document.getElementById("marriageWeight").value =Math.round( i * hy*100)/100;
	} else {
		document.getElementById("marriageValue").value = null;
		document.getElementById("marriageWeight").value = null;
	}
	sczkhbz();
	sczkhzz();
	zzed();
}

// 职位职称
function caculateZWZC() {
	var i = $('#zwzc option:selected').val();
	if (i != "") {
		document.getElementById("titleValue").value = i;
		document.getElementById("titleWeight").value =  Math.round( i * zwzc*100)/100;
	} else {
		document.getElementById("titleValue").value = null;
		document.getElementById("titleWeight").value = null;
	}
	sczkhbz();
	sczkhzz();
	zzed();
}
// 生存状况合并值
function sczkhbz() {
	var huji = $("#registerWeight").attr("value");
	var fcqk = $("#houseWeight").attr("value");
	var hyzk = $("#marriageWeight").attr("value");
	var zwzc = $("#titleWeight").attr("value");
	var total = Number(huji) + Number(fcqk) + Number(hyzk) + Number(zwzc);
	document.getElementById("livingCombinedValue").value = total;
}
// 生存状况终值
function sczkhzz() {
	var total = $("#livingCombinedValue").attr("value");
	var livingT = $("#livingT").attr("value");
	var livingFinal = Math.round(total * livingT*100)/100;
	document.getElementById("livingFinal").value = livingFinal;
}

/*
 * 道德品质table 作者：王海东
 * 
 */
var bnyq = 0.2; // 半年预期次数
var lxyq = 0.2; // 连续逾期次数
var dcyqje = 0.2; // 单次逾期金额
var yqzq = 0.15; // 逾期总期数
var fzbqz = 0.15; // 负债比权重
var gzzz = 0.5;// 工作/住宅更换频率
var bnncx = 0.5;// 半年内被查询记录成功的比例

// 半年逾期次数
function caculateBNYQ() {
	var i = $('#bnyq option:selected').val();
	if (i != "") {
		document.getElementById("sixMonthsOverdueValue").value = i;
		document.getElementById("sixMonthsOverdueWeight").value =  Math.round(i * bnyq*100)/100;
	} else {
		document.getElementById("sixMonthsOverdueValue").value = null;
		document.getElementById("sixMonthsOverdueWeight").value = null;
	}
	ddpzhz();
	ddpzzz();
	zzed();
}
// 连续逾期次数
function caculateLXYQ() {
	var i = $('#lxyq option:selected').val();
	if (i != "") {
		document.getElementById("successiveLateValue").value = i;
		document.getElementById("successiveLateWeight").value = Math.round(i * lxyq*100)/100;
	} else {
		document.getElementById("successiveLateValue").value = null;
		document.getElementById("successiveLateWeight").value = null;
	}
	ddpzhz();
	ddpzzz();
	zzed();
}
// 单次逾期金额
function caculateDCYQ() {
	var i = $('#dcyq option:selected').val();
	if (i != "") {
		document.getElementById("singleLateValue").value = i;
		document.getElementById("singleLateWeight").value = Math.round(i * dcyqje*100)/100;
	} else {
		document.getElementById("singleLateValue").value = null;
		document.getElementById("singleLateWeight").value = null;
	}
	ddpzhz();
	ddpzzz();
	zzed();
}
// 逾期总期数
function caculateYQZQS() {
	var i = $('#yqzqs option:selected').val();
	if (i != "") {
		document.getElementById("allLateValue").value = i;
		document.getElementById("allLateWeight").value = Math.round(i * yqzq*100)/100;
	} else {
		document.getElementById("allLateValue").value = null;
		document.getElementById("allLateWeight").value = null;
	}
	ddpzhz();
	ddpzzz();
	zzed();
}
// 负债比权重
function caculateFZB() {
	var i = $('#fzb option:selected').val();
	if (i != "") {
		document.getElementById("responsibleValue").value = i;
		document.getElementById("responsibleWeight").value = Math.round(i * fzbqz*100)/100;
	} else {
		document.getElementById("responsibleValue").value = null;
		document.getElementById("responsibleWeight").value = null;
	}
	ddpzhz();
	ddpzzz();
	zzed();
}

// 工作/住宅更换频率
function caculateGZZZ() {
	var i = $('#gzzz option:selected').val();
	if (i != "") {
		document.getElementById("homeJobChangeValue").value = i;
		document.getElementById("homeJobChangeWeight").value = Math.round(i * gzzz*100)/100;
	} else {
		document.getElementById("homeJobChangeValue").value = null;
		document.getElementById("homeJobChangeWeight").value = null;
	}
	ddpzhz();
	ddpzzz();
	zzed();
}
// 半年内被查询记录成功的比例
function caculateBNNCX() {
	var i = $('#bnncx option:selected').val();
	if (i != "") {
		document.getElementById("sixMonthsSucceValue").value = i;
		document.getElementById("sixMonthsSucceWeight").value = Math.round(i * bnncx*100)/100;
	} else {
		document.getElementById("sixMonthsSucceValue").value = null;
		document.getElementById("sixMonthsSucceWeight").value = null;
	}
	ddpzhz();
	ddpzzz();
	zzed();
}

// 道德品质合值
function ddpzhz() {
	var bnyq = $("#sixMonthsOverdueWeight").attr("value");
	var lxyq = $("#successiveLateWeight").attr("value");
	var dcyq = $("#singleLateWeight").attr("value");
	var yqzqs = $("#allLateWeight").attr("value");
	var fzb = $("#responsibleWeight").attr("value");
	var gzzz = $("#homeJobChangeWeight").attr("value");
	var bnncx = $("#sixMonthsSucceWeight").attr("value");
	var total = Number(bnyq) + Number(lxyq) + Number(dcyq) + Number(yqzqs) + Number(fzb) + Number(gzzz) + Number(bnncx);
	document.getElementById("charaterAllValue").value = total;
}
// 道德品质终值
function ddpzzz() {
	var total = $("#charaterAllValue").attr("value");
	var charaterT = $("#charaterT").attr("value");
	var charaterFinalValue = Math.round(total * charaterT*100)/100;
	
	document.getElementById("charaterFinalValue").value = charaterFinalValue;
}

/*
 * 还款能力table 作者：王海东
 * 
 */
function hknlzz() {
	var annualNetIncome = $("#annualNetIncome").attr("value");
	var capacityT = $("#capacityT").attr("value");
	var capacityFinal = Math.round(annualNetIncome * capacityT*100)/100;
	document.getElementById("capacityFinal").value = capacityFinal;
	zzed();
}
/*
 * 行业情况table 作者：王海东
 * 
 */
function hyzz() {
	var i = $('#industryRiskFactor option:selected').val();
	var industryT = $("#industryT").attr("value");
	var industryFinalValue = Number(i) + Number(industryT);
	document.getElementById("industryFinalValue").value = industryFinalValue;
	zzed();
}

/*
 * 最终额度 作者：王海东
 * 
 */
var parameter = 0.0001;
function zzed() {
	var charaterFinalValue = $("#charaterFinalValue").attr("value"); // 道德品质终值
	var industryFinalValue = $("#industryFinalValue").attr("value"); // 行业终值
	var livingFinal = $("#livingFinal").attr("value");// 生存状况终值
	var capacityFinal = $("#capacityFinal").attr("value");// 还款能力终值
	var denialValue = $("#denialValue").attr("value");// 拒绝值
	var finalValue = Math.round(charaterFinalValue * industryFinalValue * livingFinal * capacityFinal * denialValue * parameter*100)/100;
	document.getElementById("finalValue").value = finalValue;
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
