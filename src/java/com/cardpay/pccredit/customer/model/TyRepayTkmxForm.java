package com.cardpay.pccredit.customer.model;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 
 * @author 贺珈
 * 借据表（太原）
 *
 */
public class TyRepayTkmxForm{
	private String ywbh;//业务编号
	private String khh;//客户号
	private String zhtbh;//主合同编号
	private String jjh;//借据号
	private String jzll;//基准利率
	private String sfbl;//上浮比率
	private String htll;//合同利率
	private String tkbh;//本次提款编号
	private String ffje;//本次发放金额
	private String tkyt;//本次提款用途
	private String jkrq;//借款日期
	private String dqrq;//到期日期
	private String dkkm;//贷款科目
	private String xgry;//最新修改人员
	private String xgsj;//最新修改日期
	private String dkqx;//贷款期限
	private String zqdqr;//展期到期日
	private String sfzq;//是否展期
	private String sfjq;//是否结清
	private String dkzl;//贷款种类
	private String dkfs;//贷款方式
	private String bljg;//办理机构
	private String sfzf;//是否作废
	private String khjl;//客户经理
	private String zqhth;//展期合同号
	private String dkzh;//贷款账号
	private String sfjz;//是否记账
	private String dkfl;//贷款分类
	private String dklx;//贷款类型
	private String jjbh;//借据编号
	private String cpmc;//产品名称
	private String dkxz;//贷款性质
	private String dkyt;//贷款用途
	private String dktx;//贷款投向
	private String llzl;//利率种类
	private String jxfs;//结息方式
	private String hkly;//还款来源
	private String hkfs;//还款方式
	private String jqrq;//结清日期
	private String ywlx;//业务类型
	private String zqrq;//展期日期
	private String hbzl;//货币种类
	private String ytmx;//贷款用途明细
	private String sfxedk;//是否为小额贷款
	private String yqsf;//逾期上浮比例
	private String shyj;//审核意见
	private String shry;//审核人员
	private String shsj;//审核时间
	private String txlx;//贴现利息
	private String yqll;//逾期利率
	private String sftxje;//实付贴现金额
	private String nyjfbl;//挪用加罚比例
	private String blry;//办理人员
	private String yjjh;//原借据号
	private String sfstdk;//是否社团贷款
	private String ystxyh;//银社团协议号
	private String sfyxdk;//是否营销贷款
	private String yxr;//营销人
	private String flcorzlc;//分流程还是主流程,2分流程,1为主流程
	private String createTime;
	
	private String productName;
	private String zjhm;
	public String getJjh() {
		return jjh;
	}
	public void setJjh(String jjh) {
		this.jjh = jjh;
	}
	public String getYwbh() {
		return ywbh;
	}
	public void setYwbh(String ywbh) {
		this.ywbh = ywbh;
	}
	public String getKhh() {
		return khh;
	}
	public void setKhh(String khh) {
		this.khh = khh;
	}
	public String getZhtbh() {
		return zhtbh;
	}
	public void setZhtbh(String zhtbh) {
		this.zhtbh = zhtbh;
	}
	public String getJzll() {
		return jzll;
	}
	public void setJzll(String jzll) {
		this.jzll = jzll;
	}
	public String getSfbl() {
		return sfbl;
	}
	public void setSfbl(String sfbl) {
		this.sfbl = sfbl;
	}
	public String getHtll() {
		return htll;
	}
	public void setHtll(String htll) {
		this.htll = htll;
	}
	public String getTkbh() {
		return tkbh;
	}
	public void setTkbh(String tkbh) {
		this.tkbh = tkbh;
	}
	public String getFfje() {
		return ffje;
	}
	public void setFfje(String ffje) {
		this.ffje = ffje;
	}
	public String getTkyt() {
		return tkyt;
	}
	public void setTkyt(String tkyt) {
		this.tkyt = tkyt;
	}
	public String getJkrq() {
		return jkrq;
	}
	public void setJkrq(String jkrq) {
		this.jkrq = jkrq;
	}
	public String getDqrq() {
		return dqrq;
	}
	public void setDqrq(String dqrq) {
		this.dqrq = dqrq;
	}
	public String getDkkm() {
		return dkkm;
	}
	public void setDkkm(String dkkm) {
		this.dkkm = dkkm;
	}
	public String getXgry() {
		return xgry;
	}
	public void setXgry(String xgry) {
		this.xgry = xgry;
	}
	public String getXgsj() {
		return xgsj;
	}
	public void setXgsj(String xgsj) {
		this.xgsj = xgsj;
	}
	public String getDkqx() {
		return dkqx;
	}
	public void setDkqx(String dkqx) {
		this.dkqx = dkqx;
	}
	public String getZqdqr() {
		return zqdqr;
	}
	public void setZqdqr(String zqdqr) {
		this.zqdqr = zqdqr;
	}
	public String getSfzq() {
		return sfzq;
	}
	public void setSfzq(String sfzq) {
		this.sfzq = sfzq;
	}
	public String getSfjq() {
		return sfjq;
	}
	public void setSfjq(String sfjq) {
		this.sfjq = sfjq;
	}
	public String getDkzl() {
		return dkzl;
	}
	public void setDkzl(String dkzl) {
		this.dkzl = dkzl;
	}
	public String getDkfs() {
		return dkfs;
	}
	public void setDkfs(String dkfs) {
		this.dkfs = dkfs;
	}
	public String getBljg() {
		return bljg;
	}
	public void setBljg(String bljg) {
		this.bljg = bljg;
	}

	public String getSfzf() {
		return sfzf;
	}
	public void setSfzf(String sfzf) {
		this.sfzf = sfzf;
	}
	public String getKhjl() {
		return khjl;
	}
	public void setKhjl(String khjl) {
		this.khjl = khjl;
	}
	public String getZqhth() {
		return zqhth;
	}
	public void setZqhth(String zqhth) {
		this.zqhth = zqhth;
	}
	public String getDkzh() {
		return dkzh;
	}
	public void setDkzh(String dkzh) {
		this.dkzh = dkzh;
	}
	public String getSfjz() {
		return sfjz;
	}
	public void setSfjz(String sfjz) {
		this.sfjz = sfjz;
	}
	public String getDkfl() {
		return dkfl;
	}
	public void setDkfl(String dkfl) {
		this.dkfl = dkfl;
	}
	public String getDklx() {
		return dklx;
	}
	public void setDklx(String dklx) {
		this.dklx = dklx;
	}
	public String getJjbh() {
		return jjbh;
	}
	public void setJjbh(String jjbh) {
		this.jjbh = jjbh;
	}
	public String getCpmc() {
		return cpmc;
	}
	public void setCpmc(String cpmc) {
		this.cpmc = cpmc;
	}
	public String getDkxz() {
		return dkxz;
	}
	public void setDkxz(String dkxz) {
		this.dkxz = dkxz;
	}
	public String getDkyt() {
		return dkyt;
	}
	public void setDkyt(String dkyt) {
		this.dkyt = dkyt;
	}
	public String getDktx() {
		return dktx;
	}
	public void setDktx(String dktx) {
		this.dktx = dktx;
	}
	public String getLlzl() {
		return llzl;
	}
	public void setLlzl(String llzl) {
		this.llzl = llzl;
	}
	public String getJxfs() {
		return jxfs;
	}
	public void setJxfs(String jxfs) {
		this.jxfs = jxfs;
	}
	public String getHkly() {
		return hkly;
	}
	public void setHkly(String hkly) {
		this.hkly = hkly;
	}
	public String getHkfs() {
		return hkfs;
	}
	public void setHkfs(String hkfs) {
		this.hkfs = hkfs;
	}
	public String getJqrq() {
		return jqrq;
	}
	public void setJqrq(String jqrq) {
		this.jqrq = jqrq;
	}
	public String getYwlx() {
		return ywlx;
	}
	public void setYwlx(String ywlx) {
		this.ywlx = ywlx;
	}
	public String getZqrq() {
		return zqrq;
	}
	public void setZqrq(String zqrq) {
		this.zqrq = zqrq;
	}
	public String getHbzl() {
		return hbzl;
	}
	public void setHbzl(String hbzl) {
		this.hbzl = hbzl;
	}
	public String getYtmx() {
		return ytmx;
	}
	public void setYtmx(String ytmx) {
		this.ytmx = ytmx;
	}
	public String getSfxedk() {
		return sfxedk;
	}
	public void setSfxedk(String sfxedk) {
		this.sfxedk = sfxedk;
	}
	public String getYqsf() {
		return yqsf;
	}
	public void setYqsf(String yqsf) {
		this.yqsf = yqsf;
	}
	public String getShyj() {
		return shyj;
	}
	public void setShyj(String shyj) {
		this.shyj = shyj;
	}
	public String getShry() {
		return shry;
	}
	public void setShry(String shry) {
		this.shry = shry;
	}
	public String getShsj() {
		return shsj;
	}
	public void setShsj(String shsj) {
		this.shsj = shsj;
	}
	public String getTxlx() {
		return txlx;
	}
	public void setTxlx(String txlx) {
		this.txlx = txlx;
	}
	public String getYqll() {
		return yqll;
	}
	public void setYqll(String yqll) {
		this.yqll = yqll;
	}
	public String getSftxje() {
		return sftxje;
	}
	public void setSftxje(String sftxje) {
		this.sftxje = sftxje;
	}
	public String getNyjfbl() {
		return nyjfbl;
	}
	public void setNyjfbl(String nyjfbl) {
		this.nyjfbl = nyjfbl;
	}
	public String getBlry() {
		return blry;
	}
	public void setBlry(String blry) {
		this.blry = blry;
	}
	public String getYjjh() {
		return yjjh;
	}
	public void setYjjh(String yjjh) {
		this.yjjh = yjjh;
	}
	public String getSfstdk() {
		return sfstdk;
	}
	public void setSfstdk(String sfstdk) {
		this.sfstdk = sfstdk;
	}
	public String getYstxyh() {
		return ystxyh;
	}
	public void setYstxyh(String ystxyh) {
		this.ystxyh = ystxyh;
	}
	public String getSfyxdk() {
		return sfyxdk;
	}
	public void setSfyxdk(String sfyxdk) {
		this.sfyxdk = sfyxdk;
	}
	public String getYxr() {
		return yxr;
	}
	public void setYxr(String yxr) {
		this.yxr = yxr;
	}
	public String getFlcorzlc() {
		return flcorzlc;
	}
	public void setFlcorzlc(String flcorzlc) {
		this.flcorzlc = flcorzlc;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getZjhm() {
		return zjhm;
	}
	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}

}
