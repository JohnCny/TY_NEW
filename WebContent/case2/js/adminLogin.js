
$(document).ready(function() {
	$("a.login_btn").click(checkUserInfo);
	$("input[name='username'],input[name='password']").keydown(inputKeyDown);
	
	var errorMsg = getUrlParam("errorMsg");
	if(errorMsg != null && errorMsg != "" && errorMsg.length > 0){
		Dialog.message(errorMsg);
	}
	
//	$("input[name='loginType']").click(function(){
//		var loginType = $("input[name='loginType']:checked").val();
//		if(loginType == "2"){
//			window.location.href = $("#redirectToCas").val();
//		} 
//	});
});

function getUrlParam(name){  
    //构造一个含有目标参数的正则表达式对象  
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");  
    //匹配目标参数  
    var r = window.location.search.substr(1).match(reg);  
    //返回参数值  
    if (r!=null) return unescape(r[2]);  
    return null;  
} 

function inputKeyDown(e){
	if (e.which == "13") {
		checkUserInfo();
	}
}


// check username && password
function checkUserInfo(e) {
	if ($("input[name='username']").val() == "") {
		$("span.notice").text("*请输入用户名");
	} else if ($("input[name='password']").val() == "") {
		$("span.notice").text("*请输入密码");
//	} else if ($("input[name='validateCode']").val() == "") {
//		$("span.notice").text("*请输入验证码");
	} else {
		$.post($("form#userInfo").attr("action"), $("form#userInfo").serialize(), function(
				data, status, jqxhr) {
			if (data.success == true) {
				var src = window.location.href;
				window.location.href = src.replace("login.html","main.page");
			} else if (data.success == false) {				
				Dialog.message(data.errors.errors[0].message);
			}
			
		});
	}
}
