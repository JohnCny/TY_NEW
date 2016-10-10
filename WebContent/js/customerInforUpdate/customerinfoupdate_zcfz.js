$(document).ready(function() {
	
	$(".inpl").live("keydown",function(e){
		
		$(this).moneyFormat();
		
	});
   $(".inpr").live("keydown",function(e){
		
		$(this).moneyFormat();
		
	});

});


$(".addbtnl").click(function(){
	
	var name ="";
	var num = "";
	var count = 0;
	var thisparent = this;
	var trid = $(this).parent().parent().attr("id");
	var childname = trid + "_1";
	var childnamer = Number(inpId) + Number(1) + "_1";
	var nexttrid = Number(trid) + Number(20);
	var id = trid + 1;
	var inpId = trid.substring(0,1);
	if(inpId == 1) inpId = trid.substring(0,2);
	 count = $("input[name ='" + inpId + "']").val();
	var childname =  inpId + "_1";
	var inpIdr = Number(inpId) + Number(1);
	var childnamer =  inpIdr + "_1";
	
	//Dialog.form("#addform", "增加", function(){
		
		// name = window.top.$("#name").val();
		// num = window.top.$("#num").val();	
		 
		 var leng = $(thisparent).parent().parent().nextAll("#" + id).length;
			if(leng > 0){
		    var nexttr = $(thisparent).parent().parent().next();
			for(i = 0; i < leng;i++){
					var labval = nexttr.find(".labl").val();
					if(labval){	 
					if( i < leng -1){	
				     nexttr =  nexttr.next();
					 }else{
						 
	                  var str = '<tr id="'+ id +'"><td><input  id="" name="" class="labl" value="" style="text-align: center; " onchange="isempty(this)"/></td><td><input type="text" id="" name="'+ childname + '" value="" class="inpl" onchange="count(this,\''+ childname+'\',\''+ inpId +'\')"/></td><td><input  id="" name="" class="labr" style="text-align: center;display:none;" onchange="isempty(this)"/></td><td><input type="text" id="" name="' + childnamer + '" value="" class="inpr" onchange="count(this,\'' + childnamer +'\',\'' + inpIdr + '\')" style="display:none;"/></td></tr>';
			
			         $(thisparent).parent().parent().siblings("#"+ nexttrid).prev().after(str);
						
						 }
						 
					}else{
				    nexttr.find(".labl").css('display',''); 
				    nexttr.find(".inpl").css('display',''); 	
	                nexttr.find(".labl").val(name);
				    nexttr.find(".inpl").val(num);
					break;
					}

				}
				
			}else{
				 var str = '<tr id="'+ id +'"><td><input  id="" name="" class="labl" value="" style="text-align: center;" onchange="isempty(this)"/></td><td><input type="text" id="" name="'+ childname + '" value="" class="inpl" onchange="count(this,\'' + childname +'\',\'' + inpId + '\')"/></td><td><input  id="" name="" class="labr" style="text-align: center;display:none;" onchange="isempty(this)"/></td><td><input type="text" id="" name="'+ childnamer + '" value="" class="inpr" onchange="count(this,\'' + childnamer +'\',\'' + inpIdr + '\')" style="display:none;"/></td></tr>';
			
			      $(thisparent).parent().parent().siblings("#"+ nexttrid).prev().after(str);
				
				
				}
//			  if(num != ""){
//					
//					count = Number(count) + Number(num);
				 // $("input[name ='" + inpId + "']").val(count);
				 // countTotal2();
//				}
				

//			 window.top.$("#form_diaog").dialog("close");
		 
		
//	}, null, 400, 150);
	
});
$(".addbtnr").click(function(){
	
	var name ="";
	var num = "";
	var count = 0;
	var thisparent = this;
	var trid = $(this).parent().parent().attr("id");
	var nexttrid = Number(trid) + Number(20);
	var id = trid + 1;
	var inpId = trid.substring(0,1);
	if(inpId == 1) inpId = trid.substring(0,2);
	var inpIdl = inpId;
	var childname = inpIdl + "_1";
	 inpId = Number(inpId) + Number(1);
	var childnamer = inpId + "_1";
	 count = $("input[name ='" + inpId + "']").val();
//	Dialog.form("#addform", "增加", function(){
//		
//		 name = window.top.$("#name").val();
//		 num = window.top.$("#num").val();	
		 var leng = $(thisparent).parent().parent().nextAll("#" + id).length;
			if(leng > 0){
		    var nexttr = $(thisparent).parent().parent().next();
			for(i = 0; i < leng;i++){
					var labval = nexttr.find(".labr").val();
					if(labval){	
					if(i < leng -1){
				     nexttr =  nexttr.next();
					 }else{
						 
	 var str = '<tr id="'+ id +'"><td><input  id="" name="" class="labl" style="text-align: center;display:none;" onchange="isempty(this)"/></td><td><input type="text" id="" name="'+ childname + '" value="" class="inpl" onchange="count(this,\'' + childname +'\',\'' + inpIdl + '\')"  style="display:none;"/></td><td><input  id="" name="" class="labr" value="" style="text-align: center;" onchange="isempty(this)"/></td><td><input type="text" id="" name="'+ childnamer + '" value="" class="inpr" onchange="count(this,\'' + childnamer +'\',\'' + inpId + '\')"/></td></tr>';
			
			 $(thisparent).parent().parent().siblings("#"+ nexttrid).prev().after(str);
						 
						 }
					}else{
					nexttr.find(".labr").css('display',''); 
					nexttr.find(".inpr").css('display',''); 
	                nexttr.find(".labr").val(name);
				    nexttr.find(".inpr").val(num);
					break;
					}

				}
				
				
			}else{
			 var str = '<tr id="'+ id +'"><td><input  id="" name="" class="labl" style="text-align: center;display:none;" onchange="isempty(this)"></input></td><td><input type="text" id="" name="'+ childname + '" value="" class="inpl" onchange="count(this,\'' + childname +'\',\'' + inpIdl + '\')" style="display:none;"/></td><td><input id="" name="" class="labr" value="" style="text-align: center;" onchange="isempty(this)"></input></td><td><input type="text" id="" name="'+ childnamer + '" value="" class="inpr" onchange="count(this,\'' + childnamer +'\',\'' + inpId + '\')"/></td></tr>';
			 $(thisparent).parent().parent().siblings("#"+ nexttrid).prev().after(str);
				}
//	       if(num != ""){
//				
//				count = Number(count) + Number(num);
//			  $("input[name ='" + inpId + "']").val(count);
//			  countTotal2();
//			}
			

//		 window.top.$("#form_diaog").dialog("close");
//		
//	}, null, 400, 150);
//	
});
$(".delbtnl").click(function(){
	var thisparent = this;
	var trid = $(this).parent().parent().attr("id");
	var nexttrid = Number(trid) + Number(20);
	var num = 0;
	var countsum = 0 ,countsumtotal = 0;
	var id = trid + 1;
	var inpId = trid.substring(0,1);
	if(inpId == 1) inpId = trid.substring(0,2);
	 count = $("input[name ='" + inpId + "']").val();
	var leng = $(thisparent).parent().parent().nextAll("#" + id).length;
	if(leng > 0){
		
		 var pretr = $(thisparent).parent().parent().siblings("#"+ nexttrid).prev();
		   for(i = leng; i > 0 ;i--){
			   
			   var labvalr = pretr.find(".labr").val();
			   var labvall = pretr.find(".labl").val();
			   num = pretr.find(".inpl").val();
			if(labvalr){	
				
				if(labvall){
					 pretr.find(".labl").css('display','none'); 
					 pretr.find(".inpl").css('display','none'); 
				      pretr.find(".labl").val("");
		              pretr.find(".inpl").val("");
					 break;
				    }else{	
		                  if( i > 1)
		                  {				 
		                    pretr =  pretr.prev();}
				         else{
					         break;
						     }
					     }

			  }else{
	        	   $(thisparent).parent().parent().siblings("#"+ nexttrid).prev().remove();
	         	  break;
	 			 }

		      }
		   if(num != ""){

				count = Number(count) - Number(num);
			  $("input[name ='" + inpId + "']").val(count);
			  countTotal2();
			}
			}
			   
		
});

$(".delbtnr").click(function(){
	var thisparent = this;
	var trid = $(this).parent().parent().attr("id");
	var nexttrid = Number(trid) + Number(20);
	var id = trid + 1;
	var num = 0;
	var countsum = 0 ,countsumtotal = 0;
	var inpId = trid.substring(0,1);
	if(inpId == 1) inpId = trid.substring(0,2);
	 inpId = Number(inpId) + Number(1);
	 count = $("input[name ='" + inpId + "']").val();
	var leng = $(thisparent).parent().parent().nextAll("#" + id).length;
	if(leng > 0){
		
		 var pretr = $(thisparent).parent().parent().siblings("#"+ nexttrid).prev();
		   for(i = leng; i > 0 ;i--){
			   
			   var labvalr = pretr.find(".labr").val();
			   var labvall = pretr.find(".labl").val();
			    num = pretr.find(".inpr").val();
			if(labvall){	
				
				if(labvalr){
					 pretr.find(".labr").css('display','none'); 
					 pretr.find(".inpr").css('display','none'); 
				      pretr.find(".labr").val("");
		              pretr.find(".inpr").val("");
					 break;
				    }else{	
		                  if( i > 1)
		                  {				 
		                    pretr =  pretr.prev();}
				         else{
					         break;
						     }
					     }

			  }else{
	        	   $(thisparent).parent().parent().siblings("#"+ nexttrid).prev().remove();
	         	  break;
	 			 }

		      }
		   if(num != ""){

				count = Number(count) - Number(num);
			  $("input[name ='" + inpId + "']").val(count);
			  countTotal2(); 
			}
			}
});
function countTotal1(id1,id2,id3,id4){
	
	var count1 = 0,count2 =0,count3 = 0,count4 = 0;
	var sumCount = 0;
	 count1 = $("input[name ='" + id1 + "']").val();
	 count2 = $("input[name ='" + id2 + "']").val();
	 count3 = $("input[name ='" + id3 + "']").val();
	 count4 = $("input[name ='" + id4 + "']").val();
	 if(id4){
		sumCount = Number(count1) + Number(count2)+ Number(count3)+ Number(count4);
	 }else{
		 sumCount = Number(count1) + Number(count2)+ Number(count3); 
	 }
     return sumCount;
};
function countTotal2(){
	var countsum1 = 0 ,countsumtotal1 = 0;
	var countsum2 = 0 ,countsumtotal2 = 0;
	var zczj =0, fzzj=0,syzqy = 0,fzl = 0,sdbl = 0;
	var xjjyh = 0,yszk = 0; dqfz =0,ldzc = 0 ,dqfz2 =0;
	var numb1 = 0,numb2 =0 ,numb3 =0,num4 = 0;
	//流动资产
	  countsum1 = countTotal1("2","4","6","8");
	  $("input[name ='0']").val(countsum1); 
	  
	  //资产总计
	    countsumtotal1 = countTotal1("0","10","12");
	   zczj = $("input[name ='14']").val(countsumtotal1.toFixed(2)); 
	   
	   //短期负债
	  countsum2 = countTotal1("3","5","7");
	  $("input[name ='1']").val(countsum2); 
	 //负债总计
	    countsumtotal2 = countTotal1("1","9","11");
	   fzzj  =  $("input[name ='13']").val(countsumtotal2.toFixed(2)); 
	   //所有者权益
	   syzqy = Number(countsumtotal1) - Number(countsumtotal2);
	   $("input[name ='15']").val(syzqy.toFixed(2));
		 
		  //负债率%
      if(countsumtotal1 != 0){
         fzl = Number(countsumtotal2)/ Number(countsumtotal1) *100;
        }
      
          $("input[name ='fzbl']").val(fzl.toFixed(2));
       //速动比率%
       xjjyh = $("input[name ='2']").val();
       yszk  = $("input[name ='4']").val();
       dqfz  = $("input[name ='1']").val();
       numb1 = Number(xjjyh) + Number(yszk);
         if(dqfz != 0){
	       numb2 = Number(numb1) / Number(dqfz) * 100;
	     }
      
           $("input[name ='zdbl']").val(numb2.toFixed(2));
         //流动比率
           ldzc = $("input[name ='0']").val();
           dqfz2 = $("input[name ='1']").val();
           if(dqfz2 != 0){
     		   numb3 = Number(ldzc) / Number(dqfz2) ;
     		   }
            $("input[name ='16']").val(numb3.toFixed(2));
          //负债和所有者权益总计
            numb4 = Number(countsumtotal2) + Number(syzqy);
            $("input[name ='17']").val(numb4.toFixed(2));
         return;
};
function count(obj,name,num){
	var sumval = 0;
	var message ="名称不能为空";
	var labvalue ="";
	var inpval ="";
	$("input[name=" + name + "]").each(function(){
		sumval = Number(sumval) + Number(this.value);
	}
	
);
	  $("input[name ='" + num + "']").val(sumval);
	 var lab = $(obj).parent().prev().find("input"); 
	  inpval = $(obj).val();
	    
	    labvalue = lab.val();
	 if(inpval !="" && labvalue == "")
	 {
		 var leng = $(obj).parent().prev().find("label").length; 
		 if (leng == 0)
	     lab.after("<label class='error' generated='true' >" + message + "</label>");
	 }else{
		$(obj).parent().prev().find("label").remove(); 
	 }
	 //numberformat(obj);
	 countTotal2(); 
};
function isempty(obj){
	var labval ="";
	var inpval ="";
	var message ="名称不能为空";
	inpval = $(obj).parent().next().find("input").val();
	labval = $(obj).val(); 
	
	if(inpval !="" && labval == "")
	 { 
		 var leng = $(obj).parent().find("label").length; 
		 if (leng == 0)
		$(obj).after("<label class='error' generated='true' >" + message + "</label>");
	 }else{
	    $(obj).parent().find("label").remove(); 
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