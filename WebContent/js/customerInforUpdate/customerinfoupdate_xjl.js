$(document).ready(function() {
	
$(".rinpsty").live("keydown",function(e){
		
		$(this).moneyFormat();
		
	});
	
});

$(".addbtn").click(function(){
	
	var trid = $(this).parent().parent().attr("id");
	var nexttrid = Number(trid) + Number(1);
	var childtrid = trid + "_1"	;
	var str =' <tr id="'+ childtrid + '"><td><input type="text" class="rinpstyname" name="items_name_'+ trid +'" value="" style="text-align: center;" onchange="isempty(this)"/></td><td><input class="rinpsty" type="text" id="" name="month_1_'+ trid +'" value="" onchange="count1(this,' + trid +',1)" /></td><td><input class="rinpsty" type="text" id="" name="month_2_'+ trid +'" value="" onchange="count1(this,' + trid +',2)" /></td><td><input class="rinpsty" type="text" id="" name="month_3_'+ trid +'" value="" onchange="count1(this,' + trid +',3)" /></td><td><input class="rinpsty" type="text" id="" name="month_4_'+ trid +'" value="" onchange="count1(this,' + trid +',4)"  /></td><td><input class="rinpsty" type="text" id="" name="month_5_'+ trid +'" value="" onchange="count1(this,' + trid +',5)" /></td><td><input class="rinpsty" type="text" id="" name="month_6_'+ trid +'" value="" onchange="count1(this,' + trid +',6)" /></td><td><input class="rinpsty" type="text" id="" name="month_7_'+ trid +'" value="" onchange="count1(this,' + trid +',7)"/> </td><td><input class="rinpsty" type="text" id="" name="month_8_'+ trid +'" value="" onchange="count1(this,' + trid +',8)"/> </td><td><input class="rinpsty" type="text" id="" name="month_9_'+ trid +'" value="" onchange="count1(this,' + trid +',9)" /></td><td><input class="rinpsty" type="text" id="" name="month_10_'+ trid +'" value="" onchange="count1(this,' + trid +',10)"/> </td><td><input class="rinpsty" type="text" id="" name="month_11_'+ trid +'" value="" onchange="count1(this,' + trid +',11)"/> </td><td><input class="rinpsty" type="text" id="" name="month_12_'+ trid +'" value="" onchange="count1(this,' + trid +',12)"/> </td><td><input class="rinpstybc" type="text" id="" name="total_'+ trid +'" value=""  readonly="readonly"/></td><td><input class="rinpstybc" type="text" id="" name="pre_month_'+ trid +'" value="" readonly="readonly"/></td></tr>';
	$("#"+ nexttrid).prev().after(str);
//    var rowscount = $("#formsId").find("tr").length;
//    $("#rowscount").attr("rowspan",rowscount - 2) ;
						 
});

$(".delbtn").click(function(){
	var trid = $(this).parent().parent().attr("id");
	var nexttrid = Number(trid) + Number(1);
	var preid = $("#"+ nexttrid).prev().attr("id");
	var childtrid = trid + "_1"	;
	if(preid == childtrid)
	 $("#"+ nexttrid).prev().remove();
	 var rowscount = $("#formsId").find("tr").length;
	    $("#rowscount").attr("rowspan",rowscount - 2) ;
		for(i=1;i<13;i++){
			total(i);
			sumpre(5);
			sumpre(9);
			sumpre(19);
			sumpre(20);
			sumpre(21);
			sumpre(27);
			sumpre(32);
			sumpre(36);
			sumpre(37);
			sumpre(38);
		}
		
		
	


});
//计算行总额和平均
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
//计算新增总计和月平均
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
//计算列总计
function total(month){
	var name = "month_" + month + "_";
	var sum1= 0,a1=0;
	var sum2= 0,a2=0;
	var sum3= 0,a3=0,sum6=0,sum7=0,sum8=0,sum9=0,sum11=0;
	var val_1 = 0,val_22 = 0,val_23 = 0,val_25 = 0,val_28 = 0,val_30 = 0 ,val_34 = 0 ,val_35 = 0;
	var sum_18 = 0,sum_24 = 0,sum_26 = 0,sum_29 = 0,sum_31 = 0, sum_33 = 0,sum4=0,sum5=0;
	//小计（1）
	for(i=2;i <= 4;i++){
		a1=$("input[name="+name+i+"]").val();
		sum1  =  Number(sum1) + Number(a1);	
	}
	$("input[name="+name+"5]").val(sum1);
	//小计（2）
	for(i=6;i <= 8;i++){
		a2=$("input[name="+name+i+"]").val();
		sum2  =  Number(sum2) + Number(a2);	
	}
	$("input[name="+name+"9]").val(sum2);	
	//小计（3）
	for(i=10;i<=17;i++){
		a3=$("input[name="+name+i+"]").val();
		sum3  =  Number(sum3) + Number(a3);	
	}
	sum_18 = addtr("18",name);
	sum3  =  Number(sum3) + Number(sum_18);
	$("input[name="+name+"19]").val(sum3);
	// 付款总额（4）营业活动现金（5）
	sum4  =  Number(sum2) + Number(sum3);
	$("input[name="+name+"20]").val(sum4);
	sum5  =  Number(sum1) - Number(sum4);
	$("input[name="+name+"21]").val(sum5);
	//(6)
	sum_24 = addtr("24",name);
	sum_26 = addtr("26",name);
	 val_22 = $("input[name="+name+"22]").val();
	 val_23 = $("input[name="+name+"23]").val();
	 val_25 = $("input[name="+name+"25]").val();
	 sum6  =  Number(val_22) + Number(val_23) +  Number(val_25) + Number(sum_24) + Number(sum_26);	
	 $("input[name="+name+"27]").val(sum6);
	 
	//(7)
	
	   sum_29 = addtr("29",name);
		sum_31 = addtr("31",name);
		 val_28 = $("input[name="+name+"28]").val();
		 val_30 = $("input[name="+name+"30]").val();
		 sum7  =  Number(val_28) + Number(val_30) + Number(sum_29) + Number(sum_31);	
		 $("input[name="+name+"32]").val(sum7);
		 
		//(8)
			
		   sum_33 = addtr("33",name);
			 val_34 = $("input[name="+name+"34]").val();
			 val_35 = $("input[name="+name+"35]").val();
			 sum8  =  Number(val_34) + Number(val_35) + Number(sum_33) ;	
			 $("input[name="+name+"36]").val(sum8);
		//(9)(11)
	      sum9= Number(sum5) + Number(sum6) +  Number(sum7) + Number(sum8) ;
	      $("input[name="+name+"37]").val(sum9);
	      val_1 = $("input[name="+name+"1]").val();
	      sum11= Number(sum9) + Number( val_1)  ;	
	      $("input[name="+name+"38]").val(sum11.toFixed(2));
}
function addtr(num,name){
	var sum = 0;
	$("input[name="+name + num+"]").each(
			function(){
//				if(this.value=="")						
//					sum=sum;
//				else
//					sum=sum+parseInt(this.value);
				sum = Number(sum) + Number(this.value);
			}
		);
	
	return sum;
};

function count(obj,num,month){
	sumpre0(obj,num);
	total(month);
	sumpre(5);
	sumpre(9);
	sumpre(19);
	sumpre(20);
	sumpre(21);
	sumpre(27);
	sumpre(32);
	sumpre(36);
	sumpre(37);
	sumpre(38);
};
function count1(obj,num,month){
	sumpre0(obj,num);
	total(month);
	sumpre(5);
	sumpre(9);
	sumpre(19);
	sumpre(20);
	sumpre(21);
	sumpre(27);
	sumpre(32);
	sumpre(36);
	sumpre(37);
	sumpre(38);
	
	var inpname ="";
	var objval = 0;
	var message ="名称不能为空";
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