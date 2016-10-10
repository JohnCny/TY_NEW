function transferInDisplay(){
	var status = $("#status").val();
	if(status === 'turn'){
		Dialog.message("禁止重复移交");
		return;
	}
	var id = $("#id").val();
	Dialog.load();
	$.ajax({
		type:'GET',
		url:"transfer.json",
		data:'id='+id,
		success:function(msg){
			Dialog.closeLoad();
			if(msg.success == true){
				Dialog.message(msg.message);
			}else{
				Dialog.message(msg.message);
			}
			window.location.href=url;
		}
	});
}



function transferInTr(id){
	Dialog.load();
	$.ajax({
		type:'GET',
		url:"transfer.json",
		data:'id='+id,
		success:function(msg){
			Dialog.closeLoad();
			if(msg.success == true){
				Dialog.message(msg.message);
			}else{
				Dialog.message(msg.message);
			}
			window.location.href=url;
		}
	});
}

function transfer(){
	if ($(".checkbox :checked").length == 1) {
		var status = $(".checkbox :checked").next("input:hidden").val();
		if(status === 'turn'){
			Dialog.message("移交操作中...禁止重复移交");
			return;
		}
        var rowid = $($(".checkbox :checked")[0]).attr("value");
        Dialog.load();
    	$.ajax({
    		type:'GET',
    		url:"transfer.json",
    		data:'id='+rowid,
    		success:function(msg){
    			Dialog.closeLoad();
    			if(msg.success == true){
    				Dialog.message(msg.message);
    			}else{
    				Dialog.message(msg.message);
    			}
    			window.location.href=url;
    		}
    	});
	}else{
		Dialog.message("请选择一行");
	}
}

function displaycardinfomation(){
	if ($(".checkbox :checked").length == 1) {
        var rowid = $($(".checkbox :checked")[0]).attr("value");
        var id=rowid.split('_')[1];
        self.location.href= contextPath + "/customer/basiccustomerinfor/displayaccountinfolist.page?customerId=" + id;
	}else{
		
	}
}
