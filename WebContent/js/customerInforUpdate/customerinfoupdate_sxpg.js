$(document).ready(function() {
	
});
//计算净资产——有房客户-自有
 function fuzhai1(){
	
	var fuzhaitotal = 0;
	var jingzichanNum = 0;
	var edushangxin =0;
	var table = $("#ownHouse");
	//负债
	var debitRemainingValue= $("#debitRemainingValue",table).val();
	var totalUsedAmountMax= $("#totalUsedAmountMax",table).val();
	var totalCreditAmount= $("#totalCreditAmount",table).val();
	var applicantExternalAmount= $("#applicantExternalAmount",table).val();
	var otherDebitValue= $("#otherDebitValue",table).val();
	
	fuzhaitotal = Number(debitRemainingValue) + Number(totalUsedAmountMax) + Number(totalCreditAmount) + Number(applicantExternalAmount) * 0.5 + Number(otherDebitValue); 
	//净资产
	var houseValue= $("#houseValue",table).val();
	var carValue= $("#carValue",table).val() >= 200000 ? $("#carValue",table).val() : 0 ;
	var otherValue= $("#otherValue",table).val();
	
	 jingzichanNum = Number(houseValue) + Number(carValue) + Number(otherValue) - Number(fuzhaitotal) ; 
	
     $("#wetValue",table).val(jingzichanNum);
     
     if(jingzichanNum < 400000){
    	 edushangxin = 100000;
    	 $("#amountLimit",table).val(edushangxin); 
     }
     if( 400000 <= jingzichanNum && jingzichanNum  < 700000){
    	 edushangxin = 150000;
    	 $("#amountLimit",table).val(edushangxin); 
     }
     if( 700000 <= jingzichanNum && jingzichanNum  < 1000000){
    	 edushangxin = 200000;
    	 $("#amountLimit",table).val(edushangxin); 
     }
     if( 1000000 <= jingzichanNum && jingzichanNum < 1300000){
    	 edushangxin = 250000;
    	 $("#amountLimit",table).val(edushangxin); 
     }
     if( jingzichanNum >= 1300000){
    	 edushangxin = 300000;
    	 $("#amountLimit",table).val(edushangxin); 
     }
	
}
 
//计算净资产——有房客户-家庭所有
 function fuzhai2(){
	
	var fuzhaitotal = 0;
	var jingzichanNum = 0;
	var edushangxin =0;
	var table = $("#familyHouse");
	//负债
	var familyDebitRemainingValue= $("#familyDebitRemainingValue",table).val();
	var familyTotalUsedAmountMax= $("#familyTotalUsedAmountMax",table).val();
	var familyTotalCreditAmount= $("#familyTotalCreditAmount",table).val();
	var familyApplicantExternalAmount= $("#familyApplicantExternalAmount",table).val();
	var familyOtherDebitValue= $("#familyOtherDebitValue",table).val();
	
	fuzhaitotal = Number(familyDebitRemainingValue) + Number(familyTotalUsedAmountMax) + Number(familyTotalCreditAmount) + Number(familyApplicantExternalAmount) * 0.5 + Number(familyOtherDebitValue); 
	//净资产
	var familyHouseValue= $("#familyHouseValue",table).val();
	var familyCarValue= $("#familyCarValue",table).val() >= 200000 ? $("#familyCarValue",table).val() : 0 ;
	var familyOtherValue= $("#familyOtherValue",table).val();
	
	 jingzichanNum = Number(familyHouseValue) + Number(familyCarValue) + Number(familyOtherValue) - Number(fuzhaitotal) ; 
	
     $("#familyWetValue",table).val(jingzichanNum);
     
     if(jingzichanNum < 600000){
    	 edushangxin = 100000;
    	 $("#amountLimit",table).val(edushangxin); 
     }
     if( 600000 <= jingzichanNum && jingzichanNum  < 1000000){
    	 edushangxin = 150000;
    	 $("#amountLimit",table).val(edushangxin); 
     }
     if( 1000000 <= jingzichanNum && jingzichanNum  < 1400000){
    	 edushangxin = 200000;
    	 $("#amountLimit",table).val(edushangxin); 
     }
     if( 1400000 <= jingzichanNum && jingzichanNum < 1800000){
    	 edushangxin = 250000;
    	 $("#amountLimit",table).val(edushangxin); 
     }
     if( jingzichanNum >= 1800000){
    	 edushangxin = 300000;
    	 $("#amountLimit",table).val(edushangxin); 
     }
	
}
 
//无房客户-散件
 function edushangxin3(){
	
	
	var edushangxin =20000;
	var table = $("#noHouse_local_0");
	//
	var dailyFlowOver10000= $("select[name=dailyFlowOver10000]",table).val() == 'YES' ? 20000 : 0;
	var age3550= $("select[name=age3550]",table).val() == 'YES' ? 20000 : 0;
	var inland= $("select[name=inland]",table).val() == 'YES' ? 20000 : 0;
	var married= $("select[name=married]",table).val() == 'YES' ? 10000 : 0;
	var haveRecord= $("select[name=haveRecord]",table).val() == 'YES' ? 20000 : 0;
	var creidtBelow70per= $("select[name=creidtBelow70per]",table).val() == 'YES' ? 20000 : 0;
	var sexFemale= $("select[name=sexFemale]",table).val() == 'YES' ? 10000 : 0;
	var livelihood= $("select[name=livelihood]",table).val() == 'YES' ? 10000 : 0;
	var ourBusiness= $("select[name=ourBusiness]",table).val() == 'YES' ? 10000 : 0;
	var haveLoans= $("select[name=haveLoans]",table).val() == 'YES' ? 50000 : 0;
	var carValueOver5= $("select[name=carValueOver5]",table).val() == 'YES' ? 20000 : 0;
	var selfBusinessLifeOver1= $("select[name=selfBusinessLifeOver1]",table).val() == 'YES' ? 20000 : 0;
	
	
	edushangxin +=  Number(dailyFlowOver10000) + Number(age3550) + Number(inland) + Number(married)  + Number(haveRecord) + Number(creidtBelow70per) + Number(sexFemale) + Number(livelihood) + Number(ourBusiness) + Number(haveLoans) + Number(carValueOver5) + Number(selfBusinessLifeOver1); 
	
    $("#amountLimit",table).val(edushangxin); 
     
	
}
//无房客户-我行有存款
 function edushangxin4(){
	 
	var table = $("#noHouse_local_1");
	var averageDailyFlow= Number($("#averageDailyFlow",table).val()) * 0.5 <= 150000 ? Number($("#averageDailyFlow",table).val()) * 0.5  : 150000;
    $("#amountLimit",table).val(averageDailyFlow); 
     
	
}
//无房客户-我行有股金
 function edushangxin5(){
	 
	var table = $("#noHouse_local_2");
	var edushangxin = 0;
	var capitalValue= $("#capitalValue",table).val();
	var familyLineCredit= $("#familyLineCredit",table).val();
	edushangxin =  Number(capitalValue) - Number(familyLineCredit) ;
	
    $("#amountLimit",table).val(edushangxin); 
     
	
}
//无房需担保本地客户(有房)
 function fuzhai6(){
	
	var fuzhaitotal = 0;
	var jingzichanNum = 0;
	var edushangxin =0;
	var table = $("#noHouseLoc_assure");
	//负债
	var guarantorCreditLoan_1= $("#guarantorCreditLoan_1",table).val();
	var guarantorCreditUsedMax_1= $("#guarantorCreditUsedMax_1",table).val();
	var guarantorCreditTotalLimit_1= $("#guarantorCreditTotalLimit_1",table).val();
	var guarantorExternalAmount_1= $("#guarantorExternalAmount_1",table).val();
	var applicantCreditLoanSum_1= $("#applicantCreditLoanSum_1",table).val();
	var applicantCreditUsedMax_1= $("#applicantCreditUsedMax_1",table).val();
	var applicantCreditTotalQuota_1= $("#applicantCreditTotalQuota_1",table).val();
	var applicantExternalAmount_1= $("#applicantExternalAmount_1",table).val();
	
	
	
	fuzhaitotal = Number(guarantorCreditLoan_1) + Number(guarantorCreditUsedMax_1) + Number(guarantorCreditTotalLimit_1) + Number(guarantorExternalAmount_1) * 0.5 + Number(applicantCreditLoanSum_1) + Number(applicantCreditUsedMax_1) + Number(applicantCreditTotalQuota_1) + Number(applicantExternalAmount_1) * 0.5; 
	//净资产
	var guarantorPropertyValue_1= $("#guarantorPropertyValue_1",table).val();
	var guarantorCarValue_1= $("#guarantorCarValue_1",table).val() >= 200000 ? $("#guarantorCarValue_1",table).val() : 0 ;
	var guarantorOtherAssets_1= $("#guarantorOtherAssets_1",table).val();
	var applicantAssets_1= $("#applicantAssets_1",table).val();
	
	 jingzichanNum = Number(guarantorPropertyValue_1) + Number(guarantorCarValue_1) + Number(guarantorOtherAssets_1) + Number(applicantAssets_1) - Number(fuzhaitotal) ; 
	
     $("#wetValue_1",table).val(jingzichanNum);
     
     if(jingzichanNum < 600000){
    	 edushangxin = 80000;
    	 $("#amountLimithouse",table).val(edushangxin); 
     }
     if( 600000 <= jingzichanNum && jingzichanNum  < 1000000){
    	 edushangxin = 100000;
    	 $("#amountLimithouse",table).val(edushangxin); 
     }
     if( 1000000 <= jingzichanNum && jingzichanNum  < 1500000){
    	 edushangxin = 150000;
    	 $("#amountLimithouse",table).val(edushangxin); 
     }
    
     if( jingzichanNum >= 1500000){
    	 edushangxin = 200000;
    	 $("#amountLimithouse",table).val(edushangxin); 
     }
	
}
//无房需担保本地客户(无房)
 function edushangxin7(){
	 
	var table = $("#noHouseLoc_assure");
	var guarantorAmount_1= Number($("#guarantorAmount_1",table).val()) * 50000 <= 200000 ? Number($("#guarantorAmount_1",table).val()) * 50000  : 200000;
    $("#amountLimit",table).val(guarantorAmount_1); 
     
	
}
 
//无房需担保外地客户 
 function fuzhai8(){
	
	var fuzhaitotal = 0;
	var jingzichanNum = 0;
	var edushangxin =0;
	var table = $("#noHouseNonloc_assure");
	//负债
	var guarantorCreditLoan= $("#guarantorCreditLoan",table).val();
	var guarantorCreditUsedMax= $("#guarantorCreditUsedMax",table).val();
	var guarantorCreditTotalLimit= $("#guarantorCreditTotalLimit",table).val();
	var guarantorExternalAmount= $("#guarantorExternalAmount",table).val();
	var applicantCreditLoanSum= $("#applicantCreditLoanSum",table).val();
	var applicantCreditUsedMax= $("#applicantCreditUsedMax",table).val();
	var applicantCreditTotalQuota= $("#applicantCreditTotalQuota",table).val();
	var applicantExternalAmount= $("#applicantExternalAmount",table).val();
	
	
	
	fuzhaitotal = Number(guarantorCreditLoan) + Number(guarantorCreditUsedMax) + Number(guarantorCreditTotalLimit) + Number(guarantorExternalAmount) * 0.5 + Number(applicantCreditLoanSum) + Number(applicantCreditUsedMax) + Number(applicantCreditTotalQuota) + Number(applicantExternalAmount) * 0.5; 
	//净资产
	var guarantorPropertyValue= $("#guarantorPropertyValue",table).val();
	var guarantorCarValue= $("#guarantorCarValue",table).val() >= 200000 ? $("#guarantorCarValue",table).val() : 0 ;
	var guarantorOtherAssets= $("#guarantorOtherAssets",table).val();
	var applicantAssets= $("#applicantAssets",table).val();
	
	 jingzichanNum = Number(guarantorPropertyValue) + Number(guarantorCarValue) + Number(guarantorOtherAssets) + Number(applicantAssets) - Number(fuzhaitotal) ; 
	
     $("#wetValue",table).val(jingzichanNum);
     
     if(jingzichanNum < 800000){
    	 edushangxin = 80000;
    	 $("#amountLimit",table).val(edushangxin); 
     }
     if( 800000 <= jingzichanNum && jingzichanNum  < 1400000){
    	 edushangxin = 100000;
    	 $("#amountLimit",table).val(edushangxin); 
     }
     if( 1400000 <= jingzichanNum && jingzichanNum  < 2000000){
    	 edushangxin = 150000;
    	 $("#amountLimit",table).val(edushangxin); 
     }
    
     if( jingzichanNum >= 2000000){
    	 edushangxin = 200000;
    	 $("#amountLimit",table).val(edushangxin); 
     }
	
}
//无房优质行业客户
 function edushangxin9(){
	
	
	var edushangxin =0;
	var table = $("#noHouse_profession");
	//
	var highQuailtyIndustry= $("select[name=highQuailtyIndustry]",table).val();
	if(highQuailtyIndustry =='normal'){
		
		edushangxin = 30000_100000;
	}
   if(highQuailtyIndustry =='middle'){
		
		edushangxin = 50000_200000;
	}
   if(highQuailtyIndustry =='high'){
	
	edushangxin = 50000_300000;
   }
	
    $("#amountLimit",table).val(edushangxin); 
     
	
}