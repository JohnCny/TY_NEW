
$(document).ready(function() {
	
$(".rinpsty").live("keydown",function(e){
		
		$(this).moneyFormat();
		
	});

$(".rinpstynum").live("keydown",function(e){
	
	$(this).numFormat();
	
});
	
});
$.fn.extend({
	moneyFormat : function () {
		return this.each(function () {
			$(this).keyup(function () {
				var reg = /^\d*\.?\d{0,2}$/,
				reg2 = /(?:\d*\.\d{0,2}|\d+)/,
				reg3 = /[^.0-9]+/;
				var _val = $(this).val(),
				isPlus = /^-/.test(_val),
				_val = isPlus ? _val.substr(1) : _val;
				if (!reg.test(_val)) {
					_val = _val.replace(reg3, "").match(reg2);
					_val = _val == null ? "" : _val[0];
					$(this).val(isPlus ? ("-" + _val) : _val);
				}
			}).blur(function () {
				var reg1 = /^\d+$/,
				reg2 = /^\.\d{0,2}$/,
				reg3 = /^\d+\.\d{0,2}$/,
				reg4 = /^0+(?:[1-9]\d*|0)\.\d{0,2}$/,
				reg5 = /^0+((?:[1-9]\d*|0)\.\d{0,2})$/;
				var _val = $(this).val(),
				isPlus = /^-/.test(_val),
				_val = isPlus ? _val.substr(1) : _val;
				if (reg1.test(_val)) {
					_val = _val + ".00";
				}
				if (reg4.test(_val)) {
					_val = _val.replace(reg5, "$1");
				}
				if (reg2.test(_val)) {
					_val = "0" + _val;
				}
				if (reg3.test(_val)) {
					var len = _val.length - _val.indexOf(".") - 1,
					str = "";
					for (var i = 0; i < 2 - len; i++) {
						str += "0";
					}
					_val += str;
				}
				$(this).val(isPlus ? ("-" + _val) : _val);
			});
		});
	}
});
$.fn.extend({
	numFormat : function () {
		return this.each(function () {
			$(this).keyup(function () {
				var reg = /^\d+$/;
				var _val = $(this).val();
				if (reg.test(_val)) {
					_val = _val
				}else{
					
					_val = ""
				}
				$(this).val(_val);
			}).blur(function () {
				var reg1 = /^\d+$/;
				if (reg1.test(_val)) {
					_val = _val ;
				}else{
					
					_val = ""
				}
			
				$(this).val(_val);
			});
		});
	}
});