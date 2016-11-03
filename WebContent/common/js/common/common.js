topWin = (function(p,c){
	while(p!=c){
		c=p;
		p=p.parent;
	}
	return c;
})(window.parent,window);

var submitFlag = 0;

_post = $.post;
_get = $.get;
_flag = new Object;

$.post = function(e, r, i, o, n) {
    $.reloadAjax(e, r, i, o, "post");
};

$.get = function(e, r, i, o) {
    $.reloadAjax(e, r, i, o, "get");
};

$.extend({
    reloadAjax: function(e, r, i, o, n) {
        if (_flag[e] != r) {
            try {
                $(".error").remove();
                var tmp = i.toString();
                if ($.isFunction(r)) {
                    tmp = r.toString();
                }
                _flag[e] = r;
                tmp = tmp.substring(0, tmp.length - 1);
                tmp += ";err(arguments[0]);_flag[\""+e+"\"] = false;" + " console.log(\""+e+"\");}";
                eval("i =" + tmp);
                $.isFunction(r) && (o = o || i, i = r, r = {});
                $.ajax({
                    url: e,
                    type: n,
                    dataType: o,
                    data: r,
                    success: i
                });
            } catch(ex) {
                _flag[e] = false;
            }
        } else {
            return;
        }
    }
});

function err(data){
	
	if(data.success){
		if (data.messages && data.messages.globalMessages && data.messages.globalMessages[0]) {
			// 成功
//			window.top.Dialog.notice(data.messages.globalMessages[0].message, 2000);
			Dialog.message(data.messages.globalMessages[0].message);
		}
	}else{ 
		if(data.errors.globalErrors){
			var p="";
			$.each(data.errors.globalErrors, function(i, item) {
		     if(item.message){
			    p+=item.message+"\n";
				 }
			 });
			if(p!=""){
				Dialog.message(p);
			}			 
		}else{
		 $.each(data.errors.errors, function(i, item) {
			 var name=item.field;
			 var message=item.message;
			 var fieldObj = $("[name="+name+"]");
			 if (fieldObj != null) {
				 fieldObj.after("<label class='error' for='"+name+"' generated='true' >"+message+"</label>");
			 }
			 else {
				 Dialog.message(message);
			 }
		 });
		}
	}
	if(window["layout"]){
		layout.resizeLayout();
	}
	
}	

function navigateTo(url) {
    window.location.href = url;
}

// JSON转换为字符串
function JSONstringify(Json) {
   var result = null;
    if ($.browser.msie) {
        if ($.browser.version == "7.0" || $.browser.version == "6.0") {
            result = jQuery.parseJSON(Json);
        } else {
            result = JSON.stringify(Json);
        }
    } else {
    	result = JSON.stringify(Json);
    }
    return result;
}

function JSONstringify1(Json) {
	 result = JSON.stringify(Json);
	 return result;
}

function showDate() {
    var inp = document.getElementById("hmsText");
    var repeater = {
        rep: function(fun, interval) {
            this.fun = fun;
            this.interval = interval;
        },
        start: function() {
            this._repeat(this.fun, this.interval);
        },
        stop: function() {
            window.clearTimeout(this.sid);
        },
        _repeat: function(fun, interval, cb) {
            var arg = arguments,
            callee = arguments.callee,
            _this = this;
            fun.apply();
            this.sid = setTimeout(function() {
                callee.apply(_this, arg);
            },
            interval);
        }
    };

    var prefixZero = function(num) {
        if (num < 10 && num >= 0) {
            return "0" + num;
        } else {
            return num;
        }
    };

    repeater.rep(function() {
        var date = new Date(),
        year = date.getFullYear(),
        month = date.getMonth(),
        day = date.getDay(),
        hour = date.getHours(),
        minite = date.getMinutes(),
        second = date.getSeconds();

        inp.innerHTML = prefixZero(hour) + ":" + prefixZero(minite) + ":" + prefixZero(second);

    },
    200);
    repeater.start();
};

function prepareListCheckboxs() {
    $(":checkbox.checkbox").each(function() {
        $(this).click(function() {
            var thisElem = this;
            $(":checkbox.checkbox").each(function() {
                if (thisElem != this) {
                    $(this).removeAttr("checked");
                }
            });
        });
    });
}

function initClickEvent() {
    stopPropagation(".inquiry_list tr .checkbox :radio,.inquiry_list tr .checkbox :checkbox", "click");
    $(".inquiry_list tr").live("click.event",
    function() {
        if ($("th", this).length == 0) {
            if ($(":radio", this).length == 1) {
                $(":radio", this).attr("checked", true);
            } else if ($(":checkbox", this).length == 1) {
                $(":checkbox", this).attr("checked", !$(":checkbox", this).attr("checked"));
            } else {
                var index = $(this).index();
                $(".inquiry_list tr:has(.checkbox):eq(" + index + ")").click();
            }
        }
    });
}

/**
 * 阻止事件冒泡，支持IE、chrome、Firefox、Opera
 * 
 * @param selector
 *            选择器
 * @param eventName
 *            阻止冒泡的事件
 */
function stopPropagation(selector, eventName) {
    $(selector).bind(eventName + ".stopPropagation",
    function(event) {
        if (window.event) {
            window.event.cancelBubble = true;
        } else {
            event.stopPropagation();
        }
    });
}

/**
 * 准备常用操作
 * 
 * @param opsObj
 */
function prepareOperations(opsObj) {
    // prepareListCheckboxs();
    initClickEvent();

    if (opsObj.createUrl) {
        $("#id_create_button").click(function() {
            var url = opsObj.createUrl;
            if (opsObj.formObj) {
                // 回调URL
                var callBackUrlObj = opsObj.formObj.find("div.pagebar div.page a.current");
                if (callBackUrlObj) {
                	if(url.indexOf("?")>=0){
                		url += "&" + $.param({
	                        'callBackUrl': callBackUrlObj.length == 0 ? window.location.href: callBackUrlObj[0].href
	                    });
                	}else{
	                    url += "?" + $.param({
	                        'callBackUrl': callBackUrlObj.length == 0 ? window.location.href: callBackUrlObj[0].href
	                    });
                	}
                }
            }
            window.location.href = url;
        });
    }

    if (opsObj.changeUrl) {
        $("#id_change_button").click(function() {
            if ($(".checkbox :checked").length == 1) {
                $("#id_change_button").unbind();
                var rowid = $($(".checkbox :checked")[0]).attr("value");
                var url ="";
                
                if(opsObj.changeUrl.indexOf("?")>=0){
                	url = opsObj.changeUrl + "&id=" + rowid;
                }else{
                	url = opsObj.changeUrl + "?id=" + rowid;
                }
                if (opsObj.formObj) {
                    // 回调URL
                    var callBackUrlObj = opsObj.formObj.find("div.pagebar div.page a.current");
                    if (callBackUrlObj) {
                        url += "&" + $.param({
                            'callBackUrl': callBackUrlObj.length == 0 ? window.location.href: callBackUrlObj[0].href
                        });
                    }
                }
                window.location.href = url;
            } else {
                Dialog.message("请选择一行");
            }
        });
    }

    if (opsObj.displayUrl) {
        $("#id_display_button").click(function() {
            if ($(".checkbox :checked").length == 1) {
                var rowid = $($(".checkbox :checked")[0]).attr("value");
                var url ="";
                
                if(opsObj.displayUrl.indexOf("?")>=0){
                	url = opsObj.displayUrl + "&id=" + rowid;
                }else{
                	url = opsObj.displayUrl + "?id=" + rowid;
                }
                if (opsObj.formObj) {
                    // 回调URL
                    var callBackUrlObj = opsObj.formObj.find("div.pagebar div.page a.current");
                    if (callBackUrlObj) {
                        url += "&" + $.param({
                            'callBackUrl': callBackUrlObj.length == 0 ? window.location.href: callBackUrlObj[0].href
                        });
                    }
                }
                window.location.href = url;
            } else {
                Dialog.message("请选择一行");
            }
        });
    }

    if (opsObj.deleteUrl) {
        $("#id_delete_button").click(function() {
            if ($(".checkbox :checked").length == 1) {
                var rowid = $($(".checkbox :checked")[0]).attr("value");
                Dialog.confirm("确定删除所选行吗？", "提示",
                function() {
                    $.post(opsObj.deleteUrl, {
                        id: rowid
                    },
                    function(data, textStatus, jqXhr) {
                        if (data.success) {
                            window.location.reload(true);
                        } else {
                            //Dialog.message(data.message);
                        }
                    });
                });
            } else {
                Dialog.message("请选择一行");
            }
        });
    }

    if (opsObj.batchDeleteUrl) {
        $("#id_batchDelete_button").click(function() {

            var keyList = "";
            $('input[name="checkbox"]:checked').each(function() {
                keyList += $(this).val() + ",";
            });
            if (keyList == "") {
                Dialog.message("请选择要删除的行!");
                return false;
            } else {
                var key = keyList.substring(0, keyList.length - 1);
                Dialog.confirm("确定删除所选行吗？", "提示",
                function() {
                    $.post(opsObj.batchDeleteUrl, {
                        id: key
                    },
                    function(data, textStatus, jqXhr) {
                        if (data.success) {
                            window.location.reload(true);
                        } else {
                            //Dialog.message(data.message);
                        }
                    });
                });
            }
        });
    }

    if (opsObj.browseUrl) {
        $("#id_browse_button").click(function() {
            var url = opsObj.browseUrl;
            if (opsObj.formObj) {
                opsObj.formObj.action = opsObj.browseUrl;
                opsObj.formObj.submit();
            }
        });
    }

    if (opsObj.exportUrl) {
        $("#id_export_button").click(function() {
            var url = opsObj.exportUrl;
            var submitForm = opsObj.formObj[0];
            if (submitForm) {
                submitForm.action = opsObj.exportUrl;
                submitForm.submit();
            }
        });
    }

    
    /*
	 * if (opsObj.openUrl) { $("#id_open_button").click(function() { if
	 * ($(".checkbox :checked").length == 1) { var rowid = $($(".checkbox
	 * :checked")[0]).attr("value"); Dialog.confirm("确定开启所选行吗？", function() {
	 * $.post(opsObj.openUrl, { id: rowid }, function(data, textStatus, jqXhr) {
	 * if (data.success) { window.location.reload(true); } else {
	 * Dialog.message(data.message); } }); }); } else {
	 * Dialog.message("请选择开启的行"); } }); }
	 * 
	 * if (opsObj.lockUrl) { $("#id_lock_button").click(function() { if
	 * ($(".checkbox :checked").length == 1) { var rowid = $($(".checkbox
	 * :checked")[0]).attr("value"); Dialog.confirm("确定锁定所选行吗？", function() {
	 * $.post(opsObj.lockUrl, { id: rowid }, function(data, textStatus, jqXhr) {
	 * if (data.success) { window.location.reload(true); } else {
	 * Dialog.message(data.message); } }); }); } else {
	 * Dialog.message("请选择锁定的行"); } }); }
	 */    
    
}

function getCurrentRowId() {
    if ($(".checkbox :checked").length == 1) {
        var rowid = $($(".checkbox :checked")[0]).attr("value");
        
        if (rowid != null && rowid.length > 0) {
            return rowid;
        }
    } 
    
    Dialog.message("请选择一行");
    
    return null;
}

function attachClickEvent() { // 触发导航栏item，content元素改变
    $("input[date-dp='single']").datepicker();
    $("input[date-dp='start']").datepicker({
        maxDate: new Date(),
        onClose: function(selectedDate) {
            var target = $(this).attr("date-dp-target");
            $("#" + target).datepicker("option", "minDate", selectedDate);
        }
    });
    $("input[date-dp='end']").datepicker({
        maxDate: new Date(),
        onClose: function(selectedDate) {
            var target = $(this).attr("date-dp-target");
            $("#" + target).datepicker("option", "maxDate", selectedDate);
        }
    });
}

// 文本框只能输入正整数
function inputNumber(e, max, min) {
    e.bind("change.myevents paste.myevents",
    function() {
        var old = this.value;
        if (this.value.length == 1) {
            this.value = this.value.replace(/[^1-9]/g, '');
        } else {
            this.value = this.value.replace(/\D/g, '');
        }
        if (old != this.value) {
            e.change();
            return;
        }
        old = Math.abs(old);
        if (max) {
            max = Math.abs(max);
            if (old > max) {
                this.value = max;
                e.change();
            }
        }
        if (min) {
            min = Math.abs(min);
            if (old < min) {
                this.value = min;
                e.change();
            }
        }

    });
    e.css("ime-mode", "disabled");
}

/**
 * 时间控件，onmouseover事件、onfocus等事件触发; 参数默认{ btnBar:false } 可为{ format:'yyyy-MM-dd
 * HH:mm:ss' }、{ maxDate:'#end',btnBar:false }、{ minDate:'#start',btnBar:false }
 */
function _calendar(){
	_args=arguments[0]||{ btnBar:false };
	 var thisEvent = window.event;
     if (thisEvent == undefined) {
         var caller = arguments.callee.caller;
         while (caller.caller != null) { caller = caller.caller; }
         thisEvent = caller.arguments[0];
     }
    var thisObj=thisEvent.target||thisEvent.srcElement;
	$(thisObj).calendar(_args);
	$(thisObj).removeAttr("on"+thisEvent.type);
}