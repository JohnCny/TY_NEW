$(document).ready(function() {
	
	$(".inpl").live("keydown",function(e){
		
		$(this).moneyFormat();
		
	});
   $(".inpr").live("keydown",function(e){
		
		$(this).moneyFormat();
		
	});

});

function countlrjb(){
	countTotal2();
}

function count1(id){
	var result = 0;
	result = Number($("input[name ='" + id + "']").val());
	return result;
}

function countTotal2(){
	var yzyincome =0; var kbincome =0;var mlpj=0;
	var income1 =0;var income2 =0;var income3 =0;
	var income4 =0;var income5 =0;var income6 =0;
	var income7 =0;var income8 =0;var income9 =0;
	var income10 =0;var income11 =0;var income12 =0;
	var income13 =0;var income14 =0;var income15 =0;
	var ywincome1 =0;var ywincome2 =0;var ywincome3 =0;var ywincome4 =0;
	var fqincome =0;var jlrcome =0;var fyincome =0; var fqincome =0;
	var fqkzincome =0;  var  qtsrincome =0;
	
	//月主营业务收入（1）总计
	yzyincome = count1("a6")*count1("a11")+count1("a7")*count1("a12")+count1("a8")*count1("a13")+count1("a10")*count1("a14");
	$("input[name ='a15']").val(yzyincome.toFixed(2)); 
	//月主营业务收入（1）月平均
	$("input[name ='a16']").val((yzyincome/12).toFixed(2)); 
	
	//可变成本（成本率 ）（2）总计
	kbincome = count1("a17") + count1("a18") + count1("a19") + count1("a20");
	$("input[name ='a21']").val(kbincome.toFixed(2)); 
	//可变成本（成本率 ）（2）月平均
	$("input[name ='a22']").val((kbincome/12).toFixed(2)); 
	
	//毛利淡季月份
	$("input[name ='a23']").val(count1("a11")-count1("a17"));
	//毛利一般月份
	$("input[name ='a24']").val(count1("a12")-count1("a18"));
	//毛利旺季月份
	$("input[name ='a25']").val(count1("a13")-count1("a19"));
	//毛利xx月份
	$("input[name ='a26']").val(count1("a14")-count1("a20"));
	
	//毛利= (1)-(2) 总计
	$("input[name ='a27']").val(yzyincome-kbincome);
	//毛利= (1)-(2) 月平均
	mlpj = (yzyincome/12)-(kbincome/12);
	$("input[name ='a28']").val(mlpj.toFixed(2));
	
	
	//1-
	income1 = count1("a30") + count1("a31")+ count1("a32")+ count1("a33");
	$("input[name ='a34']").val(income1);
	$("input[name ='a35']").val((income1/12).toFixed(2));
	
	//2-
	income2 = count1("a37") + count1("a38")+ count1("a39")+ count1("a40");
	$("input[name ='a41']").val(income2);
	$("input[name ='a42']").val((income2/12).toFixed(2));
	
	//3-
	income3 = count1("a44") + count1("a45")+ count1("a46")+ count1("a47");
	$("input[name ='a48']").val(income3);
	$("input[name ='a49']").val((income3/12).toFixed(2));
	
	//4-
	income4 = count1("a51") + count1("a52")+ count1("a53")+ count1("a54");
	$("input[name ='a55']").val(income4);
	$("input[name ='a56']").val((income4/12).toFixed(2));
	
	//5-
	income5 = count1("a58") + count1("a59")+ count1("a60")+ count1("a61");
	$("input[name ='a62']").val(income5);
	$("input[name ='a63']").val((income5/12).toFixed(2));
	
	//6-
	income6 = count1("a65") + count1("a66")+ count1("a67")+ count1("a68");
	$("input[name ='a69']").val(income6);
	$("input[name ='a70']").val((income6/12).toFixed(2));
	
	//7-
	income7 = count1("a72") + count1("a73")+ count1("a74")+ count1("a75");
	$("input[name ='a76']").val(income7);
	$("input[name ='a77']").val((income7/12).toFixed(2));
	
	
	//8-
	income8 = count1("a79") + count1("a80")+ count1("a81")+ count1("a82");
	$("input[name ='a83']").val(income8);
	$("input[name ='a84']").val((income8/12).toFixed(2));
	
	//9-
	income9 = count1("a86") + count1("a87")+ count1("a88")+ count1("a89");
	$("input[name ='a90']").val(income9);
	$("input[name ='a91']").val((income9/12).toFixed(2));
	
	//10-
	income10 = count1("a93") + count1("a94")+ count1("a95")+ count1("a96");
	$("input[name ='a97']").val(income10);
	$("input[name ='a98']").val((income10/12).toFixed(2));
	
	//11-
	income11 = count1("a100") + count1("a101")+ count1("a102")+ count1("a103");
	$("input[name ='a104']").val(income11);
	$("input[name ='a105']").val((income11/12).toFixed(2));
	
	//12-
	income12 = count1("a107") + count1("a108")+ count1("a109")+ count1("a110");
	$("input[name ='a111']").val(income12);
	$("input[name ='a112']").val((income12/12).toFixed(2));
	
	
	//13-
	income13 = count1("a114") + count1("a115")+ count1("a116")+ count1("a117");
	$("input[name ='a118']").val(income13);
	$("input[name ='a119']").val((income13/12).toFixed(2));
	
	//14-
	income14 = count1("a121") + count1("a122")+ count1("a123")+ count1("a124");
	$("input[name ='a125']").val(income14);
	$("input[name ='a126']").val((income14/12).toFixed(2));
	
	//15-
	income15 = count1("a128") + count1("a129")+ count1("a130")+ count1("a131");
	$("input[name ='a132']").val(income15);
	$("input[name ='a133']").val((income15/12).toFixed(2));
	
	//营业费用小计(3)
	ywincome1 = count1("a30") + count1("a37")+ count1("a44")+ count1("a51")+
			    count1("a58") + count1("a65")+ count1("a72")+ count1("a79")+
			    count1("a86") + count1("a93")+ count1("a100")+ count1("a107")+
			    count1("a114") + count1("a121")+ count1("a128");
	
	ywincome2 = count1("a31") + count1("a38")+ count1("a45")+ count1("a52")+
			    count1("a59") + count1("a66")+ count1("a73")+ count1("a80")+
			    count1("a87") + count1("a94")+ count1("a101")+ count1("a108")+
			    count1("a115") + count1("a122")+ count1("a129");
	
	ywincome3 = count1("a32") + count1("a39")+ count1("a46")+ count1("a53")+
			    count1("a60") + count1("a67")+ count1("a74")+ count1("a81")+
			    count1("a88") + count1("a95")+ count1("a102")+ count1("a109")+
			    count1("a116") + count1("a123")+ count1("a130");
	
	ywincome4 = count1("a33") + count1("a40")+ count1("a47")+ count1("a54")+
			    count1("a61") + count1("a68")+ count1("a75")+ count1("a82")+
			    count1("a89") + count1("a96")+ count1("a103")+ count1("a110")+
			    count1("a117") + count1("a124")+ count1("a131");
	
	$("input[name ='a134']").val(ywincome1.toFixed(2));
	$("input[name ='a135']").val(ywincome2.toFixed(2));
	$("input[name ='a136']").val(ywincome3.toFixed(2));
	$("input[name ='a137']").val(ywincome4.toFixed(2));
	
	//
	$("input[name ='a138']").val();
	$("input[name ='a139']").val();
	
	//分期还款（经营）(4)总计
	fqincome = count1("a140") + count1("a141")+ count1("a142")+ count1("a143")
	$("input[name ='a144']").val(fqincome);
	//分期还款（经营）(4)平均
	$("input[name ='a145']").val((fqincome/12).toFixed(2));
	
	//净利润=(1)-(2)-(3)-(4)
	$("input[name ='a146']").val(count1("a23")-count1("a134")-count1("a140"));
	$("input[name ='a147']").val(count1("a24")-count1("a135")-count1("a141"));
	$("input[name ='a148']").val(count1("a25")-count1("a136")-count1("a142"));
	$("input[name ='a149']").val(count1("a26")-count1("a137")-count1("a143"));
	
	//净利润总计
	jlrcome = count1("a146") + count1("a147") + count1("a148") + count1("a149");
	$("input[name ='a150']").val(jlrcome);
	//净利润平均
	$("input[name ='a151']").val((jlrcome/12).toFixed(2));
	
	//1 家庭开支-总计
	//1 家庭开支-平均 
	fyincome = count1("a152") + count1("a153") + count1("a154") + count1("a155");
	$("input[name ='a156']").val(fyincome);
	$("input[name ='a157']").val((fyincome/12).toFixed(2));
	
	//2 分期付款(私人用途)-总计
	//2 分期付款(私人用途)-平均
	fqincome = count1("a158") + count1("a159") + count1("a160") + count1("a161");
	$("input[name ='a162']").val(fqincome);
	$("input[name ='a163']").val((fqincome/12).toFixed(2));
	//3 其他开支-总计
	//3 其他开支-平均
	fqkzincome = count1("a164") + count1("a165") + count1("a166") + count1("a167");
	$("input[name ='a168']").val(fqkzincome);
	$("input[name ='a169']").val((fqkzincome/12).toFixed(2));
	//4 其它收入+总计
	//4 其它收入+平均
    qtsrincome = count1("a170") + count1("a171") + count1("a172") + count1("a173"); 
    $("input[name ='a174']").val(qtsrincome);
	$("input[name ='a175']").val((qtsrincome/12).toFixed(2));
	
	//每月可支配资金
	 $("input[name ='a176']").val(count1("a146")-count1("a152")-count1("a158")-count1("a164")+count1("a170"));
	 $("input[name ='a177']").val(count1("a147")-count1("a153")-count1("a159")-count1("a165")+count1("a171"));
	 $("input[name ='a178']").val(count1("a148")-count1("a154")-count1("a160")-count1("a166")+count1("a172"));
	 $("input[name ='a179']").val(count1("a149")-count1("a155")-count1("a161")-count1("a167")+count1("a173"));
	 
	 var zpincome = count1("a176") + count1("a177") + count1("a178") + count1("a179");
	 $("input[name ='a180']").val(zpincome);
	 $("input[name ='a181']").val((zpincome/12).toFixed(2));
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