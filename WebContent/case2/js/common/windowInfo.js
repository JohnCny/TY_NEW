var WindowInfo = function(menu, layout, minWidth, minHeight, divHeight) {
	this.domainHeight;
	this.domainWidth;
	this.minHeight = 500;
	this.minWidth = 1000;
	this.divHeight = 110;
	this.menu = menu;
	this.layout = layout;
	if (minWidth) {
		this.minWidth = minWidth;
	}

	if (minHeight) {
		this.minHeight = minHeight;
	}

	if (divHeight) {
		this.divHeight = divHeight;
	}
};

// 初始化方法
WindowInfo.prototype.init = function() {
	this.menu.init();
	if (this.layout) {
		this.layout = layout;
		this.layout.init(this);
	}
	$("html").css("min-height", this.minHeight + this.divHeight);
	$("html").css("min-width", this.minWidth);
	var thisWindow = this;
	// 计算左侧菜单单个展开的高度
	$(window).resize(function() {
		thisWindow.setDomain();
		thisWindow.menu.resizeMenu(thisWindow.domainHeight);
		if (thisWindow.layout) {
			thisWindow.layout.resizeLayout();
		}
	});
	$(window).resize();
};

WindowInfo.prototype.setLayout = function(layout) {
	this.layout = layout;
	this.layout.init(this);
};

WindowInfo.prototype.getMaxLiHeight = function() {
	return this.maxLiHeight;
};

WindowInfo.prototype.setDomain = function() {
	var innerHeight = window.innerHeight;
	this.domainHeight = typeof innerHeight == "number" ? innerHeight - this.divHeight : document.documentElement.clientHeight - this.divHeight;
	this.domainHeight = this.domainHeight > this.minHeight ? this.domainHeight : this.minHeight;

	var innerWidth = window.innerWidth;
	this.domainWidth = typeof innerWidth == "number" ? innerWidth : document.documentElement.clientWidth;
	this.domainWidth = this.domainWidth > this.minWidth ? this.domainWidth : this.minWidth;
	this.domainWidth = this.domainWidth - this.menu.getWidth();

	this.resizeIframe();
};

WindowInfo.prototype.resizeIframe = function() {
	$("#backStageManagement").height(this.domainHeight);
	$("#backStageManagement").width(this.domainWidth);
	$("#backStageManagement").css("overflow-x", this.domainWidth <= this.minWidth ? "visible" : "no");
	$("#backStageManagement").css("overflow-y", this.domainHeight <= this.minHeight ? "visible" : "no");
	$("#backStageManagement").removeAttr("scrolling");
};

WindowInfo.prototype.resizeIframeScroll = function(isXScroll, isYScroll) {
	$("#backStageManagement").css("overflow-x", isXScroll ? "visible" : "no");
	$("#backStageManagement").css("overflow-y", isYScroll ? "visible" : "no");
	$("#backStageManagement").removeAttr("scrolling");
};

WindowInfo.prototype.getDomainWidth = function() {
	return this.domainWidth;
};

WindowInfo.prototype.getDomainHeight = function() {
	return this.domainHeight;
};

JRadWindow = function(menu, layout, minWidth, minHeight, divHeight) {
	this.mainDiv = ".main";
	this.divHeight = divHeight;
	if (!this.divHeight) {
		this.divHeight = 213;
	}
	WindowInfo.call(this, menu, layout, minWidth, minHeight, this.divHeight);
};

JRadWindow.prototype = new WindowInfo();

JRadWindow.prototype.setDomain = function() {

	var innerHeight = window.innerHeight;
	this.domainHeight = typeof innerHeight == "number" ? innerHeight : document.documentElement.clientHeight;
	this.domainHeight = this.domainHeight > this.minHeight ? this.domainHeight : this.minHeight;
	this.domainHeight = this.domainHeight - this.divHeight;
	this.domainHeight -= $(this.mainDiv).outerHeight(true) - $(this.mainDiv).height();

	// var innerWidth = window.innerWidth;
	// this.domainWidth = typeof innerWidth == "number" ? innerWidth :
	// document.documentElement.clientWidth;
	// this.domainWidth = this.domainWidth > this.minWidth ? this.domainWidth :
	// this.minWidth;
	// this.domainWidth -= $(this.mainDiv).outerWidth(true) -
	// $(this.mainDiv).width();
	this.domainWidth = $(this.mainDiv).width() - this.menu.getWidth();

	this.resizeIframe();
};
