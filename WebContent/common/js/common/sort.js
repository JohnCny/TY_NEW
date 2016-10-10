/*
	*  排序表格
	*/
	function sortTable(colHeadObj){		
		// 当前点击的列
		var $newCurrCol = $(colHeadObj).find("a");		
		
		var flag = false;
		if(!$newCurrCol.attr("class")){
			$("#topDiv table tr th a").attr("class","");
			$newCurrCol.attr("class","desc");
		}else if($newCurrCol.attr("class") == "desc"){
			flag = true;
			$("#topDiv table tr th a").attr("class","");
			$newCurrCol.attr("class","asc");
		}else{
			$("#topDiv table tr th a").attr("class","");
			$newCurrCol.attr("class","desc");
		}
		sortData($("#leftDiv table")[0], $("#rightDiv table")[0], colHeadObj.cellIndex, flag);		
	}
	
	/*
	*  排序表格数据
	*/
	function sortData(leftTableObj, rightTableObj, colIndex, isDesc){
	    var rightColRows = rightTableObj.rows;  
	    var rightRowArr = new Array();  
        //将将得到的列放入数组，备用  
	    for (var i=0; i < rightColRows.length; i++) {  
		   rightRowArr[i] = rightColRows[i];  
	    }
        
	    //如果不是同一列，使用数组的sort方法，传进排序函数  
	    var dataType = "float";
	    // 排序右边表格
	    rightRowArr.sort(compareEle(colIndex, dataType, isDesc));
	    
	    var rightFragment = document.createDocumentFragment();  
	    for (var i=0; i < rightRowArr.length; i++) {  
	    	rightFragment.appendChild(rightRowArr[i]);  
	    } 
	    rightTableObj.appendChild(rightFragment);
	    
	    
	    var leftColRows = leftTableObj.rows;  
	    var leftRowArr = new Array();  
         //将将得到的列放入数组，备用  
   		var leftFragment = document.createDocumentFragment();
    	for(var j=0; j < rightRowArr.length; j++){
    		 for (var i=0; i < leftColRows.length; i++) {
	    		if(rightRowArr[j].attributes["unitId"].nodeValue == leftColRows[i].attributes["unitId"].nodeValue){
	    			leftRowArr[i] = leftColRows[i];
	    			leftFragment.appendChild(leftRowArr[i]);  
	    		}
	    	 }
	    } 
    	leftTableObj.appendChild(leftFragment); 
	}
	
	//排序函数，iCol表示列索引，dataType表示该列的数据类型  
	function compareEle(iCol, dataType, isDesc) {  
	    return  function (oTR1, oTR2) { 
	        var vValue1 = convert(ieOrFireFox(oTR1.cells[iCol]), dataType);  
	        var vValue2 = convert(ieOrFireFox(oTR2.cells[iCol]), dataType);  
	        
	        if(isDesc){
	        	if (vValue1 < vValue2){
		            return -1;  
		        } else if (vValue1 > vValue2){
		            return 1;  
		        } else {
		            return 0;  
		        } 
	        }else{
	        	if (vValue1 < vValue2){
		            return 1;  
		        } else if (vValue1 > vValue2){
		            return -1;  
		        } else {
		            return 0;  
		        } 
	        }
	    };  
	}  
	
	//将列的类型转化成相应的可以排列的数据类型  
	function convert(sValue, dataType) {  
	    switch(dataType) {  
	    case "int":  
	        return parseInt(sValue);  
	    case "float":  
	        return parseFloat(sValue);  
	    case "date":  
	        return new Date(Date.parse(sValue));  
	    default:  
	        return sValue.toString();  
	    }  
	} 
	
	function ieOrFireFox(ob) {
		if (ob.textContent != null)
			return ob.textContent;
		var s = ob.innerText;
		return s.substring(0, s.length);
	}