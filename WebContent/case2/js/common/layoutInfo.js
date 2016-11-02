var LayoutInfo = function() {
	this.height;
	this.width;
	this.window;
};
// 初始化方法
LayoutInfo.prototype.init = function(currentWindow) {
	this.width = document.body.clientWidth;
	this.height = document.body.clientHeight;
	if (currentWindow) {
		this.window = currentWindow;
		this.resizeLayout();
	}
};

LayoutInfo.prototype.setWindow = function(currentWindow) {
	this.window = currentWindow;
};

LayoutInfo.prototype.resizeLayout = function() {
	this.init();
	var xflag = this.width > this.window.domainWidth;
	var yflag = this.height > this.window.domainHeight;
	this.window.resizeIframeScroll(xflag, yflag);
};

TableLayout = function(mainCount,placeHeight,coefficient) {
	this.tableHeight;
	this.tableWidth;
	this.placeHeight;
	this.mainCount = mainCount;
	// 导航
	this.daohangCSS = "place";
	// 状态栏
	this.pagebarCSS = "pagebar";
	// 搜索
	this.searchCSS = "search";
	// tab
	this.tabSelector = "tabplace";
	// 表格
	this.tableCSS = "inquiry_list";
	// 按钮
	this.buttonCSS = "control";
	this.buttonArray = [];
	// 默认table总宽度98%
	this.coefficient = 1;
	this.topDiv = "#topDiv";
	this.leftDiv = "#leftDiv";
	this.rightDiv = "#rightDiv";
	this.downDiv = "#downDiv";
	this.treeDiv = "#treeDiv";
	this.parentDiv = ".inquiry";
	LayoutInfo.call(this);
	var thisLayout = this;
	if(placeHeight){
		this.placeHeight = placeHeight;
	}
	if(coefficient){
		this.coefficient = coefficient;
	}
	if (mainCount == 2) {
		$(this.downDiv).scroll(function() {
			$(thisLayout.topDiv).scrollLeft(this.scrollLeft);
		});
	} else if (mainCount == 4) {
		$(this.rightDiv).scroll(function() {
			$(thisLayout.leftDiv).scrollTop(this.scrollTop);
			$(thisLayout.topDiv).scrollLeft(this.scrollLeft);
		});
	}else if (mainCount == 3) {
		$(this.downDiv).scroll(function() {
			$(thisLayout.topDiv).scrollLeft(this.scrollLeft);
		});
	}
};

TableLayout.prototype = new LayoutInfo();

TableLayout.prototype.init = function(currentWindow) {
	this.width = document.body.clientWidth;
	this.height = document.body.clientHeight;
	if (currentWindow) {
		this.window = currentWindow;
		this.resizeLayout();
	}
	this.tableHeight = this.window.domainHeight - $("." + this.daohangCSS).outerHeight(true)
			- $("." + this.pagebarCSS).outerHeight(true) - 1 - $("." + this.tabSelector).outerHeight(true) ;;
	this.tableWidth = this.window.domainWidth * this.coefficient - 1;
	this.tableWidth -= $(this.parentDiv).outerWidth(true) - $(this.parentDiv).width();
	this.tableHeight -= $(this.parentDiv).outerHeight(true) - $(this.parentDiv).height();
	if(this.placeHeight){
	    var p_inquiry = window.parent.document.getElementById('inquiry');
		this.tableHeight -= this.placeHeight + ($(p_inquiry).outerHeight(true) - $(p_inquiry).height());
	}
	$(this.parentDiv).width(this.tableWidth);
	$(this.parentDiv).height(this.tableHeight); 

	this.tableHeight -= $("." + this.searchCSS).outerHeight(true);
};

TableLayout.prototype.resizeLayout = function() {
	this.init();
	switch (this.mainCount) {
	case 2:
		return this.resizeScroll.scroll2(this);
	case 3:
		return this.resizeScroll.scroll3(this);
	case 4:
		return this.resizeScroll.scroll4(this);
	default:
		return this.resizeScroll.scroll(this);
	}
};

TableLayout.prototype.resizeScroll = function() {
	return {
		scroll : function(layout) {
			layout.tableHeight -= $("." + layout.tableCSS).outerHeight(true) - $("." + layout.tableCSS).height();
			layout.tableWidth -= $("." + layout.tableCSS).outerWidth(true) - $("." + layout.tableCSS).width();
			var table = $("." + layout.tableCSS);
			table.height(layout.tableHeight);
			table.width(layout.tableWidth);
			table.css("overflow", "auto");
		},
		scroll2 : function(layout) {
			layout.tableWidth -= $(layout.downDiv).outerWidth(true) - $(layout.downDiv).width();
			layout.tableHeight -= $(layout.topDiv).outerHeight(true);
			layout.tableHeight -= $(layout.downDiv).outerHeight(true) - $(layout.downDiv).height();
			$(layout.topDiv).width(layout.tableWidth);
			$(layout.downDiv).width(layout.tableWidth);
			$(layout.downDiv).height(layout.tableHeight);
			$(layout.downDiv).css("overflow", "auto");
		},
		scroll3 : function(layout) {
			var p_treeDiv = window.parent.document.getElementById('treeDiv');
			$(p_treeDiv).height(layout.tableHeight + $("." + layout.pagebarCSS).height() + $("." + layout.searchCSS).outerHeight(true));
			layout.tableWidth -= $(layout.downDiv).outerWidth(true) - $(layout.downDiv).width();
			layout.tableHeight -= $(layout.topDiv).outerHeight(true);
			layout.tableHeight -= $(layout.downDiv).outerHeight(true) - $(layout.downDiv).height();
			$(layout.topDiv).width(layout.tableWidth);
			$(layout.downDiv).width(layout.tableWidth);
			$(layout.downDiv).height(layout.tableHeight);
			$(layout.downDiv).css("overflow", "auto");
		},
		scroll4 : function(layout) {
			layout.tableWidth -= $(layout.leftDiv).outerWidth(true);
			layout.tableWidth -= $(layout.rightDiv).outerWidth(true) - $(layout.rightDiv).width() + 1;
			layout.tableHeight -= $(layout.topDiv).outerHeight(true);
			layout.tableHeight -= $(layout.rightDiv).outerHeight(true) - $(layout.rightDiv).height() + 1;
			$(layout.rightDiv).width(layout.tableWidth);
			$(layout.topDiv).width(layout.tableWidth);
			$(layout.rightDiv).height(layout.tableHeight);
			$(layout.leftDiv).height(layout.tableHeight);
		}
	};
}();

TableLayout.prototype.resizeButton = function(isSingl) {
	$("." + this.tableCSS + "tr").live("click.layout", function() {
		$(":checkbox", this).click();
	});
	$("." + this.tableCSS + "tr :checkbox").live("change.layout", function() {
		if ($(this).parent("tr").children("th").length != 0 && !isSingl) {
			$(":checkbox").attr("checked", $(this).attr("checked"));
		} else if (isSingl) {
			if ($(this).attr("checked")) {
				$(":checkbox:checked").attr("checked", false);
				var buttons = $(this).attr("button");
				$("." + buttonCSS).children("a,div,input").each(function() {
					if (buttons.indexOf(this.id) >= 0) {
						$(this).show();
					} else {
						$(this).hide();
					}
				});
			} else {
				this.initButton();
			}
		}
	});
};

TableLayout.prototype.initButton = function() {
	if (arguments) {
		this.buttonArray = arguments;
	}
	$("." + buttonCSS).children("a,div,input").hide();
	for ( var i = 0; i < this.buttonArray.length; i++) {
		$("#" + this.buttonArray[i]).show();
	}
};

FormsLayout = function(formId,placeHeight,coefficient) {
	this.formsHeight;
	this.formsWidth;
	this.placeHeight;
	// 导航
	this.daohangSelector = "place";
	// tab
	this.tabSelector = "tabplace";
	// 按钮
	this.buttonSelector = "submit";
	if ($("#" + formId).length != 1) {
		Dialog.message("forms不存在");
	}
	this.formId = formId;
	// 默认forms总宽度98%
	this.coefficient = 1;
	LayoutInfo.call(this);
	if(coefficient){
		this.coefficient = coefficient;
	}
	if(placeHeight){
		this.placeHeight = placeHeight;
	}
};
FormsLayout.prototype = new LayoutInfo();

FormsLayout.prototype.init = function(currentWindow) {
	this.width = document.body.clientWidth;
	this.height = document.body.clientHeight;
	if (currentWindow) {
		this.window = currentWindow;
		this.resizeLayout();
	}

	this.formsHeight = this.window.domainHeight - $("." + this.daohangSelector).outerHeight(true)
			- $("." + this.buttonSelector).outerHeight(true) - $("." + this.tabSelector).outerHeight(true) ;
	if(this.placeHeight){
		this.formsHeight -= this.placeHeight ;	
		var p_treeDiv = window.parent.document.getElementById('treeDiv');
		 var p_inquiry = window.parent.document.getElementById('inquiry');
		$(p_treeDiv).height(this.formsHeight + $("." + this.buttonSelector).outerHeight(true) - $(p_inquiry).outerHeight(true) + $(p_inquiry).height());
		
	}
	this.formsHeight -= $("#" + this.formId).outerHeight(true) - $("#" + this.formId).height();
	var innerHeight = 0;
	$("#" + this.formId).children().each(function() {
		innerHeight += $(this).outerHeight(true) ;
		
	});
		$("#" + this.formId).find("caption").each(function() {
			innerHeight += $(this).outerHeight(true) ;
			
	});
	
	this.formsHeight = this.formsHeight > innerHeight ? innerHeight : this.formsHeight;

	this.formsWidth = this.window.domainWidth * this.coefficient;
	this.formsWidth -= $("#" + this.formId).outerWidth(true) - $("#" + this.formId).width();
};

FormsLayout.prototype.resizeLayout = function() {
	this.init();
	$("#" + this.formId).height(this.formsHeight);
	$("#" + this.formId).width(this.formsWidth);
	$("#" + this.tabSelector).width(this.formsWidth);
	$("#" + this.formId).css("overflow", "auto");
};
