package com.cardpay.pccredit.riskControl.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author chenzhifang
 *
 * 2014-10-30下午5:49:12
 * <HEAD>
		<VER>1.0</VER>
		<SRC>202010000000</SRC>
		<DES>100000000000</DES>
		<APP>联网核查公民身份信息系统</APP>
		<MsgNo>0001</MsgNo>
		<MsgID>20051024092733000440</MsgID>
		<MsgRef>20051024092733000440</MsgRef>
		<WorkDate>2001-12-17</WorkDate>
		<Reserve>String</Reserve>
	</HEAD>
 */
@XStreamAlias("HEAD")
public class Head {
	// 版本号
	@XStreamAlias("VER")
	private String ver;
	
	// 发起节点代码
	@XStreamAlias("SRC")
	private String src;
	
	// 接收节点代码
	@XStreamAlias("DES")
	private String des;
	
	// 应用名称(填写为“联网核查公民身份信息系统)
	@XStreamAlias("APP")
	private String app;
	
	// 报文编号
	@XStreamAlias("MsgNo")
	private String msgNo;
	
	// 报文标识号
	@XStreamAlias("MsgID")
	private String msgID;
	
	// 报文参考号
	@XStreamAlias("MsgRef")
	private String msgRef;
	
	// 工作日期
	@XStreamAlias("WorkDate")
	private String workDate;
	
	// 预留字段
	@XStreamAlias("Reserve")
	private String reserve;

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getMsgNo() {
		return msgNo;
	}

	public void setMsgNo(String msgNo) {
		this.msgNo = msgNo;
	}

	public String getMsgID() {
		return msgID;
	}

	public void setMsgID(String msgID) {
		this.msgID = msgID;
	}

	public String getMsgRef() {
		return msgRef;
	}

	public void setMsgRef(String msgRef) {
		this.msgRef = msgRef;
	}

	public String getWorkDate() {
		return workDate;
	}

	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}
}
