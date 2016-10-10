$(document).ready(function() {
	
$(".rinpsty").live("keydown",function(e){
		
		$(this).moneyFormat();
		
	});
	
});

$(".addbtn").click(function(){
	
	var trid = $(this).parent().parent().attr("id");
	var nexttrid = Number(trid) + Number(1);
	var childtrid = trid + "_1"	;
	if(trid == 1 || trid == 3){
	var str =' <tr id="'+ childtrid + '"><td><input  class="rinpstyname" type="text" name="items_name_'+ trid +'" value="" style="text-align: center;" onchange="isempty(this)"/></td><td><input class="rinpsty" type="text" id="" name="month_1_'+ trid +'" value="" onchange="count0(this,' + trid +',1)" /></td><td><input class="rinpsty" type="text" id="" name="month_2_'+ trid +'" value="" onchange="count0(this,' + trid +',2)" /></td><td><input class="rinpsty" type="text" id="" name="month_3_'+ trid +'" value="" onchange="count0(this,' + trid +',3)" /></td><td><input class="rinpsty" type="text" id="" name="month_4_'+ trid +'" value="" onchange="count0(this,' + trid +',4)"  /></td><td><input class="rinpsty" type="text" id="" name="month_5_'+ trid +'" value="" onchange="count0(this,' + trid +',5)" /></td><td><input class="rinpsty" type="text" id="" name="month_6_'+ trid +'" value="" onchange="count0(this,' + trid +',6)" /></td><td><input class="rinpsty" type="text" id="" name="month_7_'+ trid +'" value="" onchange="count0(this,' + trid +',7)"/> </td><td><input class="rinpsty" type="text" id="" name="month_8_'+ trid +'" value="" onchange="count0(this,' + trid +',8)"/> </td><td><input class="rinpsty" type="text" id="" name="month_9_'+ trid +'" value="" onchange="count0(this,' + trid +',9)" /></td><td><input  class="rinpsty" type="text" id="" name="month_10_'+ trid +'" value="" onchange="count0(this,' + trid +',10)"/> </td><td><input class="rinpsty" type="text" id="" name="month_11_'+ trid +'" value="" onchange="count0(this,' + trid +',11)"/> </td><td><input  class="rinpsty" type="text" id="" name="month_12_'+ trid +'" value="" onchange="count0(this,' + trid +',12)"/> </td><td><input class="rinpstybc" type="text" id="" name="total_'+ trid +'" value=""  readonly="readonly"/></td><td><input class="rinpstybc" type="text" id="" name="pre_month_'+ trid +'" value="" readonly="readonly"/></td></tr>';
	}else{
	var str =' <tr id="'+ childtrid + '"><td><input class="rinpstyname" type="text" name="items_name_'+ trid +'" value="" style="text-align: center;" onchange="isempty(this)"/></td><td><input class="rinpsty" type="text" id="" name="month_1_'+ trid +'" value="" onchange="count1(this,' + trid +',1)" /></td><td><input class="rinpsty" type="text" id="" name="month_2_'+ trid +'" value="" onchange="count1(this,' + trid +',2)" /></td><td><input class="rinpsty" type="text" id="" name="month_3_'+ trid +'" value="" onchange="count1(this,' + trid +',3)" /></td><td><input class="rinpsty" type="text" id="" name="month_4_'+ trid +'" value="" onchange="count1(this,' + trid +',4)"  /></td><td><input class="rinpsty" type="text" id="" name="month_5_'+ trid +'" value="" onchange="count1(this,' + trid +',5)" /></td><td><input class="rinpsty" type="text" id="" name="month_6_'+ trid +'" value="" onchange="count1(this,' + trid +',6)" /></td><td><input  class="rinpsty" type="text" id="" name="month_7_'+ trid +'" value="" onchange="count1(this,' + trid +',7)"/> </td><td><input class="rinpsty" type="text" id="" name="month_8_'+ trid +'" value="" onchange="count1(this,' + trid +',8)"/> </td><td><input class="rinpsty" type="text" id="" name="month_9_'+ trid +'" value="" onchange="count1(this,' + trid +',9)" /></td><td><input class="rinpsty" type="text" id="" name="month_10_'+ trid +'" value="" onchange="count1(this,' + trid +',10)"/> </td><td><input class="rinpsty" type="text" id="" name="month_11_'+ trid +'" value="" onchange="count1(this,' + trid +',11)"/> </td><td><input class="rinpsty" type="text" id="" name="month_12_'+ trid +'" value="" onchange="count1(this,' + trid +',12)"/> </td><td><input class="rinpstybc" type="text" id="" name="total_'+ trid +'" value=""  readonly="readonly"/></td><td><input class="rinpstybc" type="text" id="" name="pre_month_'+ trid +'" value="" readonly="readonly"/></td></tr>';
	
	}
	$("#"+ nexttrid).prev().after(str);
   
						 
});

$(".delbtn").click(function(){
	var trid = $(this).parent().parent().attr("id");
	var nexttrid = Number(trid) + Number(1);
	var preid = $("#"+ nexttrid).prev().attr("id");
	var childtrid = trid + "_1"	;
	if(preid == childtrid)
	 $("#"+ nexttrid).prev().remove();

	if(trid == 1 || trid == 3){
		for(var i=1;i<13;i++){	
			var sum =0;
		$("input[name=month_"+ i +"_" + trid +"]").each(
				function(){
//					if(this.value=="")						
//						sum=sum;
//					else
//						sum=sum+parseInt(this.value);
					sum = Number(sum) + Number(this.value);
				}
			);
		$("input[name=month_"+ i +"_" + nexttrid +"]").val(sum);
		sumpre(nexttrid);
		 total3(i);
		 total4(i);
		}
	
	}else{
		for(var i=1;i<13;i++){

			var name = "month_" + i + "_";
			total2(name);
			total3(i);
			total4(i);
		}
		
		
	}


});
//计算总和的总额和平均
function sumpre(num){
	var sum=0;
	var pre =0;
		for(i=1;i<13;i++){
			var a=$("input[name=month_"+i+"_"+num+"]").val();
			if(a=="")						
				sum=sum;
			else
				sum= Number(sum)+Number(a);
		}
		pre = Number(sum) / Number(12);
	$("input[name=total_"+num+"]").val(sum.toFixed(2));
	$("input[name=pre_month_"+num+"]").val(pre.toFixed(2));
}
//计算总计和月平均
function sumpre0(obj,num){
	var sum=0;
	var pre =0;
		for(i=1;i<13;i++){
			var a=$(obj).parent().parent().find("input[name=month_"+i+"_"+num+"]").val();
			if(a=="")						
				sum=sum;
			else
				sum= Number(sum)+Number(a);
		}
		pre = Number(sum) / Number(12);
	$(obj).parent().parent().find("input[name=total_"+num+"]").val(sum.toFixed(2));
	$(obj).parent().parent().find("input[name=pre_month_"+num+"]").val(pre.toFixed(2));
}
//计算新增tr的总和
function total1(obj,month){
	
	var trid = $(obj).parent().parent().attr("id");
	var parenttrid = trid.substring(0,trid.length - 2);
	var nexttrid = Number(parenttrid) + Number(1);
	var val =0;
	var sumval =0;
	$("input[name=month_"+ month +"_" + parenttrid +"]").each(function(){
//				if(this.value=="")						
//					sumval=sumval;
//				else
//					sumval=sumval+parseInt(this.value);
		sumval = Number(sumval) + Number(this.value);
			}
		);
	 $(obj).parent().parent().siblings("#"+ nexttrid).find("input[name=month_"+ month +"_" + nexttrid +"]").val(sumval);
	
	 sumpre(nexttrid);
	
};

//计算总计3
function total2(name){
	var sum=0;
	var a=0;
	for(i=6;i<21;i++){
		a=$("input[name="+name+i+"]").val();
		sum  =  Number(sum) + Number(a);	
	}
	$("input[name="+name+"21]").each(
		function(){
//			if(this.value=="")						
//				sum=sum;
//			else
//				sum=sum+parseInt(this.value);
			sum = Number(sum) + Number(this.value);
		}
	);
	var b=$("input[name="+name+"22]").val();
//	if(b=="")						
//		sum=sum;
//	else
//		sum=sum+parseInt(b);
	sum = Number(sum) + Number(b);
	$("input[name="+name+"23]").val(sum.toFixed(2));	
	 sumpre(23);
}
//计算净利润和毛利
function total3(month){

	var total1 = 0;
	var total2 =0;
	var total3 =0;
	var total4 =0;
	var fqhk = 0,jlr = 0;
	
	total1 = $("input[name=month_"+ month +"_2]").val();
	total2 = $("input[name=month_"+ month +"_4]").val();
	total3 =  Number(total1) - Number(total2);	
	 $("input[name=month_"+ month +"_5]").val(total3.toFixed(2));

	total4 = $("input[name=month_"+ month +"_23]").val();
	fqhk = $("input[name=month_"+ month +"_24]").val();
	jlr =  Number(total3) - Number(total4) - Number(fqhk);
	 $("input[name=month_"+ month +"_25]").val(jlr.toFixed(2));
	 sumpre(5);
	 sumpre(25);
};

//每月可支配资金总值
function total4(month){
	
	var sum=0;
	var a=$("input[name=month_"+month+"_25]").val();
	var b=$("input[name=month_"+month+"_26]").val();		
	var c=$("input[name=month_"+month+"_27]").val();		
	if(a==""){a=0;}
	else{a=Number(a);}
	//alert(b);
	if(b==""){b=0;}
	else{b=Number(b);}	
	if(c==""){c=0;}
	else{c=Number(c);}
	sum=a-b-c
	$("input[name=month_"+month+"_28]").each(
		function(){
//			if(this.value=="")						
//				sum=sum;
//			else
//				sum=sum-parseInt(this.value);
			//alert(parseInt(this.value))
			sum = Number(sum) - Number(this.value);
		}
	);
	$("input[name=month_"+month+"_29]").each(
		function(){
//			if(this.value=="")						
//				sum=sum;
//			else
//				sum=sum+parseInt(this.value);
			//alert(parseInt(this.value))
			sum = Number(sum) + Number(this.value);
		}
	);
	// alert(a);			
	$("input[name=month_"+month+"_30]").val(sum.toFixed(2));
	sumpre(30);
	
	
};
function count0(obj,num,month){
	
var name = "month_" + month + "_";
var inpname ="";
var objval = 0;
var message ="名称不能为空";	
	sumpre0(obj,num);
	total1(obj,month);
	total2(name);
	total3(month);
	total4(month);
	
	 var lab = $(obj).parent().parent().find(".rinpstyname"); 
	  objval = $(obj).parent().parent().find("input:last").val();
	 inpname = lab.val();
	 if(objval != 0  && inpname == "")
	 {   var leng = $(obj).parent().parent().find("label").length; 
	     if (leng == 0)
		 lab.after("<label class='error' generated='true' >" + message + "</label>");
	 }else{
		$(obj).parent().parent().find("label").remove(); 
	 }
	
	
};
function isempty(obj){
	var labval ="";
	var inpval ="";
	var objval = 0;
	var message ="名称不能为空";
	labval = $(obj).val(); 
	 objval = $(obj).parent().parent().find("input:last").val();
	if( objval != 0 && labval == "" )
	 { 
		 var leng = $(obj).parent().parent().find("label").length; 
	     if (leng == 0)
		$(obj).after("<label class='error' generated='true' >" + message + "</label>");
	 }else{
		 $(obj).parent().parent().find("label").remove();  
	 }
	
};

function count(obj,num,month){
	var name = "month_" + month + "_";
	
	sumpre0(obj,num);
	total2(name);
	total3(month);
	total4(month);

};
function count1(obj,num,month){
	var name = "month_" + month + "_";
	var inpname ="";
	var objval = 0;
	var message ="名称不能为空";	
	sumpre0(obj,num);
	total2(name);
	total3(month);
	total4(month);
	
	 var lab = $(obj).parent().parent().find(".rinpstyname"); 
	 inpname = lab.val();
	 objval = $(obj).parent().parent().find("input:last").val();
	 if(objval !=0 && inpname == "")
	 {   var leng = $(obj).parent().parent().find("label").length; 
     if (leng == 0)
		 lab.after("<label class='error' generated='true' >" + message + "</label>");
	 }else{
		$(obj).parent().parent().find("label").remove(); 
	 }

};
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