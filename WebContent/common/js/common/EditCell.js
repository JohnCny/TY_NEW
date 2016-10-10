   var   curSrc=null;   
  function     EditCell(){   
    var   i=0,j=0;   
    var   wid=0;   
    var   Val='';   
    var   Scr=event.srcElement;   
    if(Scr.tagName=="TD"){   
 		wid=0;   
        Val=Scr.innerHTML;   
        if(Val=="&nbsp;"){
        	Val="";
        }
        Scr.innerHTML="<input   id='InputText' size='12' style='text-align: right;border:0px   none;   BACKGROUND-COLOR:   #3399FF;   height:14px;font-family:宋体;font-size:12px;color:#000000'   onblur='return CellOut();'   type='text'   name='T1'    "  +"   Value='"   +Val+"'>";   
  		//InputText.value=Val;   
  		curSrc=Scr;   
        Scr.children[0].focus();   
	}   
}   
  function   CellOut()   
  {   
    var   Scr=event.srcElement;   
    var   Val='';   
  	if(curSrc){   
  		Val=Scr.value;   
  		if(Val==""){
  			Val="&nbsp;"
  		}
  		curSrc.innerHTML=Val;   
  	}   
}   