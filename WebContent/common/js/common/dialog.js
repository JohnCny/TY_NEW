var Dialog = function() {
	return {
		message : function(value, title, fn) {
			var dia = window.top.$("#message_diaog");
			var parent = dia.parent(".ui-dialog");
			if (dia.css("display") == "block" && parent.css("display") == "block") {
				return;
			}
			if (dia.length == 0) {
				var str='<div id="message_diaog" style="display: none;" class="message_diaog"><p style="text-align: center;width:360px;margin-top: 25px;"></p></div>';
				window.top.$("body").append(str);
				dia = window.top.$("#message_diaog");
			} else {
				$("p", dia).html("");
			}
			$("p", dia).html(value);
			var height = 150;
			dia.dialog({
				modal : true,
				zIndex : 2000,
				width : 400,
				autoOpen : false,
				height : height,
				title : title || "消息",
				position : 'center',
				style : "text-align:center;",
				open : Dialog.open,
				close : function() {
					Dialog.close();
					if (fn) {
						fn();
					}
				},
				buttons : {
					确定 : function() {
						dia.dialog("close");
					}
				}
			});
			dia.dialog("open");
		},
		notice : function(text, time, title, width, height) {

			var dia = window.top.$("#message_notice");

			this.layer = {
				'width' : width || 200,
				'height' : height || 100
			};
			this.title = title ? title.substring(0, 20) : '消息提示';
			this.text = text.toString();
			this.speed = 600;
			this.time = time || 2000;

			if (dia.length == 0) {
				window.top
						.$("body")
						.append(
								'<div id="message_notice" style="border:#b9c9ef 1px solid;z-index:9000;width:'
										+ this.layer.width
										+ 'px;height:'
										+ this.layer.height
										+ 'px;position:absolute; display:none;background:#cfdef4; bottom:0; right:0; overflow:hidden;"><div style="border:1px solid #fff;border-bottom:none;width:100%;height:25px;font-size:12px;overflow:hidden;color:#1f336b;"><span id="message_close" style="float:right;padding:5px 0 5px 0;width:16px;line-height:auto;color:red;font-size:12px;font-weight:bold;text-align:center;cursor:pointer;overflow:hidden;">×</span><p id="message_title"style="padding:5px 0 5px 5px;width:100px;line-height:18px;text-align:left;overflow:hidden;"></p><div style="clear:both;"></div></div> <div style="padding-bottom:5px;border:1px solid #fff;border-top:none;width:100%;height:auto;font-size:12px;"><p id="message_content" style="margin:0 5px 0 5px;border:#b9c9ef 1px solid;padding:10px 0 10px 5px;font-size:12px;width:'
										+ (this.layer.width - 17) + 'px;height:' + (this.layer.height - 50)
										+ 'px;color:#1f336b;text-align:left;overflow:hidden;"></p></div></div>');
				dia = window.top.$("#message_notice");

			} else {
				$("#message_content", dia).html("");
				$("#message_title", dia).html("");
			}
			$("#message_content", dia).html(this.text);
			$("#message_title", dia).html(this.title);

			$("#message_close", dia).click(function() {

				dia.slideUp(this.speed);

			});
			dia.hover(function() {
				clearTimeout(timer1);
				timer1 = null;
			}, function() {
				timer1 = setTimeout(function() {
					dia.slideUp(this.speed);
				}, this.time);
			});
			dia.slideDown(this.speed);
			timer1 = window.top.setTimeout(function() {
				dia.slideUp(this.speed);
			}, this.time);
		},
		load : function(value) {
			var dia = window.top.$("#load_diaog");
			if (dia.length == 0) {
				window.top.$("body").append(
						'<div id="load_diaog" style="display: none;" class="load_diaog"> <center style="margin-top: 30px;vertical-align: middle;"><img src="'
								+ uicasePath + '/images/load01.gif"style="vertical-align: middle; margin-right: 10px"></center></div>');
				dia = window.top.$("#load_diaog");
			}
			dia.dialog({
				modal : true,
				zIndex : 2000,
				title : value || "正在处理中...",
				position : 'center',
				style : "text-align:center;",
				autoOpen : false,
				open : Dialog.open,
				close : Dialog.close
			});
			$("button", dia).parent("div").hide();
			dia.dialog("open");
		},
		closeLoad : function() {
			if (window.top.$("#load_diaog").parent("div").length == 1) {
				window.top.$("#load_diaog").dialog("close");
			}
		},
		/**
		 * conform方法
		 * 
		 * @param value
		 *            显示的消息
		 * @param title
		 *            标题
		 * @param yes
		 *            是 按钮执行的方法
		 * @param no
		 *            否 按钮执行的方法
		 */
		confirm : function(value, title, yes, no) {
			var dia = window.top.$("#confirm_diaog");

			if (dia.length == 0) {
				window.top.$("body").append(
						'<div id="confirm_diaog" style="display: none;" class="confirm_diaog"><p style="text-align: center; margin-top: 35px;"></p></div>');
				dia = window.top.$("#confirm_diaog");
			} else {
				$("p", dia).html("");
			}

			var dialogHeight = 150 + value.toString().length / 30 * 20;
			$("p", dia).html(value);
			dia.dialog({
				modal : true,
				zIndex : 2000,
				width : 400,
				autoOpen : false,
				height : dialogHeight,
				title : title || "消息",
				position : 'center',
				style : "text-align:center;",
				open : Dialog.open,
				close : Dialog.close,
				buttons : [ {
					text : "是",
					click : function() {
						if (yes) {
							yes();
						}
						dia.dialog("close");
					}
				}, {
					text : "否",
					'class' : "delete",
					click : function() {
						if (no) {
							no();
						}
						dia.dialog("close");
					}
				} ]
			});
			dia.dialog("open");
		},
		table : function(url, title, type, pra, width, height) {
			var dia = window.top.$("#table_diaog");

			if (dia.length == 0) {
				window.top.$("body").append('<div id="table_diaog" style="display: none;" class="table_diaog"></div>');
				dia = window.top.$("#table_diaog");
			} else {
				dia.html("");
			}
			$.ajax({
				url : url,
				type : type || "POST",
				data : pra || {},
				dataType : 'html',
				success : function(data) {
					if (data) {
						dia.html(data);
						dia.dialog({
							modal : true,
							zIndex : 2000,
							width : width || 800,
							autoOpen : false,
							height : height || 500,
							title : title || "消息",
							position : "center",
							style : "text-align:center;",
							close : Dialog.close,
							open : Dialog.open,
							buttons : {
								确定 : function() {
									dia.dialog("close");
								}
							}
						});
						dia.dialog("open");
					}
				}
			});
		},
		tablePage : function(url, title, type, pra,confirm, width, height) {
			var dia = window.top.$("#tablePage_diaog");

			if (dia.length == 0) {
				window.top.$("body").append('<div id="tablePage_diaog" style="display: none;" class="table_diaog"><div id="content"></div><input type="hidden" id="selectval" value=""/><input type="hidden" id="url" value=""/></div>');
				dia = window.top.$("#tablePage_diaog");
			} else {
				$("#content", dia).html("");
			}
			$.ajax({
				url : url,
				type : type || "POST",
				data : pra || {},
				dataType : 'html',
				success : function(data) {
					if (data) {
						$("#content", dia).html(data);
						$("#url", dia).val(url);
						dia.dialog({
							modal : true,
							zIndex : 2000,
							width : width || 800,
							autoOpen : false,
							height : height || 500,
							title : title || "消息",
							position : 'center',
							style : "text-align:center;",
							close : Dialog.close,
							open : Dialog.open,
							buttons : [  {
								text : "确定",
								click : function() {
									if (confirm) {
										if (confirm()) {
											dia.dialog("close");
										}
									} else {
										dia.dialog("close");
									}
								}
							} ]
						});
						dia.dialog("open");
					}
				}
			});
		},
		form : function(fromId, title, save, cancel, width, height) {
			var dia = window.top.$("#form_diaog");

			if (dia.length == 0) {
				window.top.$("body").append('<div id="form_diaog" style="display: none;" class="form_diaog"></div>');
				dia = window.top.$("#form_diaog");
			} else {
				dia.html("");
			}
			dia.html($(fromId).html());
			dia.dialog({
				modal : true,
				zIndex : 2000,
				width : width || 800,
				autoOpen : false,
				height : height || 500,
				title : title || "消息",
				position : 'center',
				style : "text-align:center;",
				open : Dialog.open,
				close : Dialog.close,
				buttons : [ {
					text : "保存",
					click : function() {
						if (save) {
							if (save()) {
								dia.dialog("close");
							}
						} else {
							dia.dialog("close");
						}
					}
				}, {
					text : "取消",
					'class' : "delete",
					click : function() {
						if (cancel) {
							cancel();
						}
						dia.dialog("close");
					}
				} ]
			});
			dia.dialog("open");
		},
		formPosition : function(fromId, title, save, cancel, width, height, pleft, ptop) {
			var dia = window.top.$("#form_diaog");

			if (dia.length == 0) {
				window.top.$("body").append('<div id="form_diaog" style="display: none;" class="form_diaog"></div>');
				dia = window.top.$("#form_diaog");
			} else {
				dia.html("");
			}
			dia.html($(fromId).html());
			dia.dialog({
				modal : true,
				zIndex : 2000,
				width : width || 800,
				autoOpen : false,
				height : height || 500,
				title : title || "消息",
				position : [pleft, ptop],
				style : "text-align:center;",
				open : Dialog.open,
				close : Dialog.close,
				buttons : [ {
					text : "保存",
					click : function() {
						if (save) {
							if (save()) {
								dia.dialog("close");
							}
						} else {
							dia.dialog("close");
						}
					}
				}, {
					text : "取消",
					'class' : "delete",
					click : function() {
						if (cancel) {
							cancel();
						}
						dia.dialog("close");
					}
				} ]
			});
			dia.dialog("open");
		},
		ajaxForm : function(url, title, type, pra, save, cancel, width, height) {
			var dia = window.top.$("#ajax_form_diaog");

			if (dia.length == 0) {
				window.top.$("body").append('<div id="ajax_form_diaog" style="display: none;" class="ajax_form_diaog"></div>');
				dia = window.top.$("#ajax_form_diaog");
			} else {
				dia.html("");
			}

			$.ajax({
				url : url,
				type : type || "POST",
				data : pra || {},
				dataType : 'html',
				success : function(data) {
					if (data) {
						dia.html(data);
						dia.dialog({
							modal : true,
							zIndex : 2000,
							width : width || 800,
							autoOpen : false,
							height : height || 500,
							title : title || "消息",
							position : 'center',
							style : "text-align:center;",
							open : Dialog.open,
							close : Dialog.close,
							buttons : [ {
								text : "保存",
								click : function() {
									if (save) {
										if (save()) {
											dia.dialog("close");
										}
									} else {
										dia.dialog("close");
									}
								}
							}, {
								text : "取消",
								'class' : "delete",
								click : function() {
									if (cancel) {
										cancel();
									}
									dia.dialog("close");
								}
							} ]
						});
						dia.dialog("open");
					}
				}
			});
		},
		ajaxFormLoad : function(url, title, type, pra, save, cancel, width, height) {
			   Dialog.load(title);
			var dia = window.top.$("#ajax_form_diaog");

			if (dia.length == 0) {
				window.top.$("body").append('<div id="ajax_form_diaog" style="display: none;" class="ajax_form_diaog"></div>');
				dia = window.top.$("#ajax_form_diaog");
			} else {
				dia.html("");
			}

			$.ajax({
				url : url,
				type : type || "POST",
				data : pra || {},
				dataType : 'html',
				success : function(data) {
					if (data) {
						 window.top.$("#load_diaog").dialog("close");
						dia.html(data);
						dia.dialog({
							modal : true,
							zIndex : 2000,
							width : width || 800,
							autoOpen : false,
							height : height || 500,
							title : title || "消息",
							position : 'center',
							style : "text-align:center;",
							open : Dialog.open,
							close : Dialog.close,
							buttons : [ {
								text : "保存",
								click : function() {
									if (save) {
										if (save()) {
											dia.dialog("close");
										}
									} else {
										dia.dialog("close");
									}
								}
							}, {
								text : "取消",
								'class' : "delete",
								click : function() {
									if (cancel) {
										cancel();
									}
									dia.dialog("close");
								}
							} ]
						});
						dia.dialog("open");
					}
				}
			});
		},
		img : function(url, title, width, height) {
			var dia = window.top.$("#img_diaog");

			if (dia.length == 0) {
				window.top.$("body").append('<div id="img_diaog" style="display: none;" class="img_diaog"><img></img></div>');
				dia = window.top.$("#img_diaog");
			} else {
				$("img", dia).attr("src", "");
				$("img", dia).unbind();
			}

			dia.dialog({
				modal : true,
				zIndex : 2000,
				width : width || 800,
				autoOpen : false,
				height : height || 500,
				title : title || "消息",
				position : 'center',
				style : "text-align:center;",
				open : Dialog.open,
				close : Dialog.close,
				buttons : {
					确定 : function() {
						dia.dialog("close");
					}
				}
			});
			$("img", dia).load(function() {
				dia.dialog("open");
			});
			$("img", dia).attr("src", url);
		},
		open : function() {
			var height = $(this).height();
			$(this).children().each(function() {
				height += $(this).height();
			});
			$(this).height(height);
			if ($.browser.msie && $.browser.version == "6.0") {
				if(window.top.$("#DialogMask,#DialogMask div").length==0){
					var mask='<div id="DialogMask" style="display:none;border:none;Z-INDEX: 98; BACKGROUND: none transparent scroll repeat 0% 0%; LEFT: 0px; WIDTH: 100%; POSITION: absolute; TOP: 0px; HEIGHT: 100%"><div style="display:none;border:none; Z-INDEX: -1; FILTER: alpha(opacity=70); LEFT: 0px; WIDTH: 100%; POSITION: absolute; TOP: 0px; HEIGHT: 100%; BACKGROUND-COLOR: #eee; opacity: 0.7"><iframe src="" width="100%" height="100%" style="border:none;"></iframe></div></div>';
					window.top.$("body").append(mask);
				}	
				window.top.$("#DialogMask,#DialogMask div").show();
			}
		},
		close : function() {
			if ($.browser.msie && $.browser.version == "6.0") {
				window.top.$("#DialogMask,#DialogMask div").hide();
			}
		}
	};
}();
