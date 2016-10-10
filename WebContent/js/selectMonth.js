		var currentDate= new Date();
		var temp= currentDate.getFullYear();
	
		
		function popupDiv(item,name,iyear,isShowCancel) {
			var flag=item;
            var strShowCancel = "";
			if ("n" == isShowCancel || "N" == isShowCancel 
				|| "no" == isShowCancel || "NO" == isShowCancel)
			{
                strShowCancel = "none;";
			}

			$("#"+name).empty();
			var currentDate= new Date();
			var martop = $("#"+flag).position().top;
			var marleft = $("#"+flag).position().left;
			$("#"+name).css({ "margin-left": marleft , "margin-top": martop - 52 });
			$("#"+name).append("<table cellspacing='1'><tr><td class='th1'><input type='button' value ='<<' onclick=changeYear('downPage','"+flag+"') ><input type='button' onclick=changeYear('downOne','"+flag+"') value='<'></td><td id='year"+flag+"' class='th2'></td><td class='th3'><input type='button' onclick=changeYear('upOne','"+flag+"') style='width:22' value='>'><input type='button' onclick=changeYear('upPage','"+flag+"') style='width:22' value='>>'></td></tr><tr><td><button onclick=hideDiv('"+item+"','"+name+"',1,'"+flag+"')>1</button></td><td><button onclick=hideDiv('"+item+"','"+name+"',2,'"+flag+"')>2</button></td><td><button onclick=hideDiv('"+item+"','"+name+"',3,'"+flag+"')>3</button></td></tr><tr><td><button onclick=hideDiv('"+item+"','"+name+"',4,'"+flag+"')>4</button></td><td><button onclick=hideDiv('"+item+"','"+name+"',5,'"+flag+"')>5</button></td><td><button onclick=hideDiv('"+item+"','"+name+"',6,'"+flag+"')>6</button></td></tr><tr><td><button onclick=hideDiv('"+item+"','"+name+"',7,'"+flag+"')>7</button></td><td><button onclick=hideDiv('"+item+"','"+name+"',8,'"+flag+"')>8</button></td><td><button onclick=hideDiv('"+item+"','"+name+"',9,'"+flag+"')>9</button></td></tr><tr><td><button onclick=hideDiv('"+item+"','"+name+"',10,'"+flag+"')>10</button></td><td><button onclick=hideDiv('"+item+"','"+name+"',11,'"+flag+"')>11</button></td><td><button onclick=hideDiv('"+item+"','"+name+"',12,'"+flag+"')>12</button></td></tr><tr><td colspan='3' align='center'><BUTTON onclick=hideCancel('"+name+"') style='display:"+strShowCancel+";'>取消</BUTTON></td></t></table>");
			if (null != iyear)
			{
                $("#year"+flag).append(iyear);
			}
			else
			{
			    $("#year"+flag).append(temp);
			}
			$("#"+name).show();
		} 

        function hideCancel(name)
		{
           $("#"+name).animate({opacity: "hide" }, "slow"); 
		   $("#"+name).empty();
		}

		function hideDiv(tag,name,item,flag) { 
			var time=$("#year"+flag).text();
			switch(item){
				case 1:
					time+="-01";
					break;
				case 2:
					time+="-02";
					break;
				case 3:
					time+="-03"
					break;
				case 4:
					time+="-04"
					break;
				case 5:
					time+="-05"
					break;
				case 6:
					time+="-06"
					break;
				case 7:
					time+="-07"
					break;
				case 8:
				time+="-08"
					break;
				case 9:
				time+="-09"
					break;
				case 10:
				time+="-10"
					break;
				case 11:
				time+="-11"
					break;
				case 12:
				time+="-12"
					break;
			}
			$("#"+tag).attr("value",time);
			$("#"+name).animate({opacity: "hide" }, "slow"); 
			$("#"+name).empty();
		}		
		
		function changeYear(item,flag){
			if(item=="upOne" || item=="up"){
				$("#year"+flag).empty();
				$("#year"+flag).append(++temp);
			}
			else if(item=="upPage")
			{
                $("#year"+flag).empty();
                temp += 10;
				$("#year"+flag).append(temp);
			}
			else if(item=="downOne" || item=="down")
			{
				$("#year"+flag).empty();
				var currentDate= new Date();
				$("#year"+flag).append(--temp);
			}
			else if (item=="downPage")
			{
                $("#year"+flag).empty();
				var currentDate= new Date();
                temp -= 10;
				$("#year"+flag).append(temp);
			}
		}
		function showTime(item,name,iyear,isShowCancel){
			popupDiv(item.id,name,iyear,isShowCancel);
		}