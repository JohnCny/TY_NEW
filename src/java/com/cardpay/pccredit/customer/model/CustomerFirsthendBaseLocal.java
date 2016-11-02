package com.cardpay.pccredit.customer.model;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 
 * @author 贺珈
 * 原始客户信息主表（太原）
 *
 */
@ModelParam(table = "ty_customer_base_local",generator=IDType.assigned)
public class CustomerFirsthendBaseLocal extends BusinessModel{
	private static final long serialVersionUID = 1L;
	private String khnm;//客户内码,个人第一位A,法人第一位为B
	private String khmc;//客户名称
	private String khh;//客户号
	private String rh;//是否入户
	private String khlx;//客户类型
	private String zjlx;//证件类型
	private String zjhm;//证件号码
	private String qfjg;  //签发机构
	private String sfcq;//是否长期
	private String qfrq;//签发日期
	private String dqrq;//到期日期
	private String gltx;//管理特征
	private String pqdz;//片区地址
	private String csrq;//出生日期
	private String xb;//性别
	private String gtspmc;//个体商铺名称
	private String gtyyzzhm;//个体营业执照号码
	private String zxyzsj;//最新验照时间
	private String yydz; //营业地址
	private String xxdz;//详细住址
	private String yzbm;//邮政编码
	private String hjdz;//户籍地址
	private String jtdh;//家庭电话
	private String cz;//传真
	private String sj;//手机
	private String dzyx;//电子邮箱
	private String gj;//国籍
	private String mz;//民族
	private String zzmm;//政治面貌
	private String xl;//学历
	private String xw;//学位
	private String jkzk;//健康状况
	private String hyzk;//婚姻状况
	private String xydj;//信用等级
	private String xydjsxrq;//信用等级生效日期
	private String xydjdqrq;//信用等级到期日期
	private String sfxyh;//是否信用户
	private String sxed;//授信额度
	private String sxjzrq;//授信截至日期
	private String yxye;//用信余额
	private String bldkye;//不良贷款余额
	private String nnjhsxed;//年内计划授信额度
	private String jhyssxed;//计划压缩授信额度
	private String zyyw;//主营业务
	private String cshy;//从属行业
	private String sfbhzg;//是否本行职工
	private String sfgd;//是否股东
	private String sfgjgzry;//是否国家公职人员
	private String sfhz;//是否户主
	private String poxm;//配偶姓名
	private String posfzh;//配偶身份证号
	private String podh;//配偶电话
	private String pogzdw;//配偶工作单位
	private String ghjl;//管户经理
	private String zbjg;//主办机构
	private String djry;//登记人员
	private String djrq;//登记日期
	private String zhxgry;//最后修改人员
	private String zhxgsj;//最后修改时间
	public String getKhnm() {
		return khnm;
	}
	public void setKhnm(String khnm) {
		this.khnm = khnm;
	}
	public String getKhmc() {
		return khmc;
	}
	public void setKhmc(String khmc) {
		this.khmc = khmc;
	}
	public String getKhh() {
		return khh;
	}
	public void setKhh(String khh) {
		this.khh = khh;
	}
	public String getRh() {
		return rh;
	}
	public void setRh(String rh) {
		this.rh = rh;
	}
	public String getKhlx() {
		return khlx;
	}
	public void setKhlx(String khlx) {
		this.khlx = khlx;
	}
	public String getZjlx() {
		return zjlx;
	}
	public void setZjlx(String zjlx) {
		this.zjlx = zjlx;
	}
	public String getZjhm() {
		return zjhm;
	}
	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}
	public String getQfjg() {
		return qfjg;
	}
	public void setQfjg(String qfjg) {
		this.qfjg = qfjg;
	}
	public String getSfcq() {
		return sfcq;
	}
	public void setSfcq(String sfcq) {
		this.sfcq = sfcq;
	}
	public String getQfrq() {
		return qfrq;
	}
	public void setQfrq(String qfrq) {
		this.qfrq = qfrq;
	}
	public String getDqrq() {
		return dqrq;
	}
	public void setDqrq(String dqrq) {
		this.dqrq = dqrq;
	}
	public String getGltx() {
		return gltx;
	}
	public void setGltx(String gltx) {
		this.gltx = gltx;
	}
	public String getPqdz() {
		return pqdz;
	}
	public void setPqdz(String pqdz) {
		this.pqdz = pqdz;
	}
	public String getCsrq() {
		return csrq;
	}
	public void setCsrq(String csrq) {
		this.csrq = csrq;
	}
	public String getXb() {
		return xb;
	}
	public void setXb(String xb) {
		this.xb = xb;
	}
	public String getGtspmc() {
		return gtspmc;
	}
	public void setGtspmc(String gtspmc) {
		this.gtspmc = gtspmc;
	}
	public String getGtyyzzhm() {
		return gtyyzzhm;
	}
	public void setGtyyzzhm(String gtyyzzhm) {
		this.gtyyzzhm = gtyyzzhm;
	}
	public String getZxyzsj() {
		return zxyzsj;
	}
	public void setZxyzsj(String zxyzsj) {
		this.zxyzsj = zxyzsj;
	}
	public String getYydz() {
		return yydz;
	}
	public void setYydz(String yydz) {
		this.yydz = yydz;
	}
	public String getXxdz() {
		return xxdz;
	}
	public void setXxdz(String xxdz) {
		this.xxdz = xxdz;
	}
	public String getYzbm() {
		return yzbm;
	}
	public void setYzbm(String yzbm) {
		this.yzbm = yzbm;
	}
	public String getHjdz() {
		return hjdz;
	}
	public void setHjdz(String hjdz) {
		this.hjdz = hjdz;
	}
	public String getJtdh() {
		return jtdh;
	}
	public void setJtdh(String jtdh) {
		this.jtdh = jtdh;
	}
	public String getCz() {
		return cz;
	}
	public void setCz(String cz) {
		this.cz = cz;
	}
	public String getSj() {
		return sj;
	}
	public void setSj(String sj) {
		this.sj = sj;
	}
	public String getDzyx() {
		return dzyx;
	}
	public void setDzyx(String dzyx) {
		this.dzyx = dzyx;
	}
	public String getGj() {
		return gj;
	}
	public void setGj(String gj) {
		this.gj = gj;
	}
	public String getMz() {
		return mz;
	}
	public void setMz(String mz) {
		this.mz = mz;
	}
	public String getZzmm() {
		return zzmm;
	}
	public void setZzmm(String zzmm) {
		this.zzmm = zzmm;
	}
	public String getXl() {
		return xl;
	}
	public void setXl(String xl) {
		this.xl = xl;
	}
	public String getXw() {
		return xw;
	}
	public void setXw(String xw) {
		this.xw = xw;
	}
	public String getJkzk() {
		return jkzk;
	}
	public void setJkzk(String jkzk) {
		this.jkzk = jkzk;
	}
	public String getHyzk() {
		return hyzk;
	}
	public void setHyzk(String hyzk) {
		this.hyzk = hyzk;
	}
	public String getXydj() {
		return xydj;
	}
	public void setXydj(String xydj) {
		this.xydj = xydj;
	}
	public String getXydjsxrq() {
		return xydjsxrq;
	}
	public void setXydjsxrq(String xydjsxrq) {
		this.xydjsxrq = xydjsxrq;
	}
	public String getXydjdqrq() {
		return xydjdqrq;
	}
	public void setXydjdqrq(String xydjdqrq) {
		this.xydjdqrq = xydjdqrq;
	}
	public String getSfxyh() {
		return sfxyh;
	}
	public void setSfxyh(String sfxyh) {
		this.sfxyh = sfxyh;
	}
	public String getSxed() {
		return sxed;
	}
	public void setSxed(String sxed) {
		this.sxed = sxed;
	}
	public String getSxjzrq() {
		return sxjzrq;
	}
	public void setSxjzrq(String sxjzrq) {
		this.sxjzrq = sxjzrq;
	}
	public String getYxye() {
		return yxye;
	}
	public void setYxye(String yxye) {
		this.yxye = yxye;
	}
	public String getBldkye() {
		return bldkye;
	}
	public void setBldkye(String bldkye) {
		this.bldkye = bldkye;
	}
	public String getNnjhsxed() {
		return nnjhsxed;
	}
	public void setNnjhsxed(String nnjhsxed) {
		this.nnjhsxed = nnjhsxed;
	}
	public String getJhyssxed() {
		return jhyssxed;
	}
	public void setJhyssxed(String jhyssxed) {
		this.jhyssxed = jhyssxed;
	}
	public String getZyyw() {
		return zyyw;
	}
	public void setZyyw(String zyyw) {
		this.zyyw = zyyw;
	}
	public String getCshy() {
		return cshy;
	}
	public void setCshy(String cshy) {
		this.cshy = cshy;
	}
	public String getSfbhzg() {
		return sfbhzg;
	}
	public void setSfbhzg(String sfbhzg) {
		this.sfbhzg = sfbhzg;
	}
	public String getSfgd() {
		return sfgd;
	}
	public void setSfgd(String sfgd) {
		this.sfgd = sfgd;
	}
	public String getSfgjgzry() {
		return sfgjgzry;
	}
	public void setSfgjgzry(String sfgjgzry) {
		this.sfgjgzry = sfgjgzry;
	}
	public String getSfhz() {
		return sfhz;
	}
	public void setSfhz(String sfhz) {
		this.sfhz = sfhz;
	}
	public String getPoxm() {
		return poxm;
	}
	public void setPoxm(String poxm) {
		this.poxm = poxm;
	}
	public String getPosfzh() {
		return posfzh;
	}
	public void setPosfzh(String posfzh) {
		this.posfzh = posfzh;
	}
	public String getPodh() {
		return podh;
	}
	public void setPodh(String podh) {
		this.podh = podh;
	}
	public String getPogzdw() {
		return pogzdw;
	}
	public void setPogzdw(String pogzdw) {
		this.pogzdw = pogzdw;
	}
	public String getGhjl() {
		return ghjl;
	}
	public void setGhjl(String ghjl) {
		this.ghjl = ghjl;
	}
	public String getZbjg() {
		return zbjg;
	}
	public void setZbjg(String zbjg) {
		this.zbjg = zbjg;
	}
	public String getDjry() {
		return djry;
	}
	public void setDjry(String djry) {
		this.djry = djry;
	}
	public String getDjrq() {
		return djrq;
	}
	public void setDjrq(String djrq) {
		this.djrq = djrq;
	}
	public String getZhxgry() {
		return zhxgry;
	}
	public void setZhxgry(String zhxgry) {
		this.zhxgry = zhxgry;
	}
	public String getZhxgsj() {
		return zhxgsj;
	}
	public void setZhxgsj(String zhxgsj) {
		this.zhxgsj = zhxgsj;
	}
	
}
