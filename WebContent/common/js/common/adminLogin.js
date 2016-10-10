
$(document).ready(function() {
	$("div.login_btn").click(checkUserInfo);
	$("input[name='username'],input[name='password']").keydown(inputKeyDown);
});

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
				//$("span.notice").text("*"+data.message);
				Messages_dialog(data.message);
			}
			
		});
	}
}
