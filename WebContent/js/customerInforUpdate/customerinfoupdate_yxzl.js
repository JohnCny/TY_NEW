function mySave(reloadUrl) {
		$('#myForm').ajaxSubmit({
			dataType : 'json',
			type : "post",
			success : function(data) {
				if (data.success) {
					Dialog.message(data.message,"提示", function() {
					 self.location.href=reloadUrl;
					 });
				} else {
					Dialog.message(data.message);
					return false;
				}
			},
			error : function(data) {
				Dialog.closeLoad();
				Dialog.message("操作失败");
				return false;
			}
		});
	}
	
	function deleteVideo(url,reloadUrl) {
		var id = null;
		$("input[name='checkbox']:checked").each(function() {
			id = $(this).val();
		});
		if(id!=null){
			url=url+"?id="+id;
		}else{
			Dialog.message("请选择一条数据!");
			return false;
		}
		$.ajax({
			dataType : 'json',
			type : "post",
			url:url,
			success : function(data) {
				if (data.success) {
					Dialog.message(data.message,"提示", function() {
					 self.location.href=reloadUrl;
					 });
				} else {
					Dialog.message(data.message);
					return false;
				}
			},
			error : function(data) {
				Dialog.closeLoad();
				Dialog.message("操作失败");
				return false;
			}
		});
	}
	
	function downLoadVideo(url) {
		var id = null;
		$("input[name='checkbox']:checked").each(function() {
			id = $(this).val();
		});
		if(id!=null){
			url=url+"?id="+id;
		}else{
			Dialog.message("请选择一条数据!");
			return false;
		}
		self.location.href=url;
	}
	
	function viewOidPicture(url){
		 Dialog.img(url,"原始图片","650","120");
	}
	