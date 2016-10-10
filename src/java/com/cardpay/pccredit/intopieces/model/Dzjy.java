package com.cardpay.pccredit.intopieces.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 调查模板-建议
 */
@ModelParam(table = "TY_DZ_MODEL_JY")
public class Dzjy extends BusinessModel{
	private static final long serialVersionUID = 1L;
	
	private String  id;               
	private String  customer_id;    
	private String  product_id;     
	private String  application_id; 
	private String  sqrxm;          
	private String  sqje;           
	private String  dkyt;           
	private String  sqqx;           
	private String  xmzje;          
	private String  zyzj;           
	private String  zyzjly;         
	private String  change;         
	private String  fxfx_ys;        
	private String  fxfx_ns;       
	private String  fxfx_lxfx;      
	private String  khjljy;         
	private String  reason1;        
	private String  jytg_je;        
	private String  jytg_qx;        
	private String  jytg_cp;        
	private String  jytg_lv;        
	private String  jytg_myhk;      
	private String  jytg_bl;        
	private String  jytg_jkr;       
	private String  jytg_khgx;      
	private String  jytg_dbr;       
	private String  jytg_dbrgx;     
	private String  jytg_dy;        
	private String  jytg_wq;        
	private String  zbkhjl_sign;    
	private String  xbkhjl_sign;    
	private String  rq;             
	private String  sqr_xm;         
	private String  sqr_sex;        
	private String  sqr_zjhm;       
	private String  sqr_hy;         
	private String  sqr_hjd;        
	private String  sqr_hjxx;       
	private String  sqr_xl;         
	private String  sqr_mobile;     
	private String  sqr_address;    
	private String  jzhj_type;      
	private String  jzhj_type4_qx;  
	private String  jzhj_type4_zj;  
	private String  jzhj_type_other;
	private String  jzhj_mj1;       
	private String  jzhj_mj2;       
	private String  jzhj_mj3;       
	private String  jzhj_zf;        
	private String  jzhj_zf_select; 
	private String  jzhj_gj;        
	private String  jzhj_gj_room;   
	private String  jzhj_gj_ting;   
	private String  jzhj_jzrq;      
	private String  jzhj_dzfs;      
	private String  jzhj_select;    
	private String  zyfc_num;       
	private String  aj_num;         
	private String  fcgmrq;         
	private String  fcgmjg;         
	private String  fcmj;           
	private String  ajdkye;         
	private String  address;        
	private String  zycl_num;       
	private String  dk_num;         
	private String  gmrq;           
	private String  gmjg;           
	private String  cp;             
	private String  other_work;     
	private String  other_income;   
	private String  po_name;        
	private String  po_code;        
	private String  po_mobile;      
	private String  po_yicome;      
	private String  po_unit;        
	private String  p_bank_numm;    
	private String  khh_1;          
	private String  zh1;            
	private String  fzc;            
	private String  khh_2;          
	private String  zh2;            
	private String  fzfz;
	
	
	//add
	private String jzhj_type_select;
	
	
	public String getJzhj_type_select() {
		return jzhj_type_select;
	}
	public void setJzhj_type_select(String jzhj_type_select) {
		this.jzhj_type_select = jzhj_type_select;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getApplication_id() {
		return application_id;
	}
	public void setApplication_id(String application_id) {
		this.application_id = application_id;
	}
	public String getSqrxm() {
		return sqrxm;
	}
	public void setSqrxm(String sqrxm) {
		this.sqrxm = sqrxm;
	}
	public String getSqje() {
		return sqje;
	}
	public void setSqje(String sqje) {
		this.sqje = sqje;
	}
	public String getDkyt() {
		return dkyt;
	}
	public void setDkyt(String dkyt) {
		this.dkyt = dkyt;
	}
	public String getSqqx() {
		return sqqx;
	}
	public void setSqqx(String sqqx) {
		this.sqqx = sqqx;
	}
	public String getXmzje() {
		return xmzje;
	}
	public void setXmzje(String xmzje) {
		this.xmzje = xmzje;
	}
	public String getZyzj() {
		return zyzj;
	}
	public void setZyzj(String zyzj) {
		this.zyzj = zyzj;
	}
	public String getZyzjly() {
		return zyzjly;
	}
	public void setZyzjly(String zyzjly) {
		this.zyzjly = zyzjly;
	}
	public String getChange() {
		return change;
	}
	public void setChange(String change) {
		this.change = change;
	}
	public String getFxfx_ys() {
		return fxfx_ys;
	}
	public void setFxfx_ys(String fxfx_ys) {
		this.fxfx_ys = fxfx_ys;
	}
	public String getFxfx_ns() {
		return fxfx_ns;
	}
	public void setFxfx_ns(String fxfx_ns) {
		this.fxfx_ns = fxfx_ns;
	}
	public String getFxfx_lxfx() {
		return fxfx_lxfx;
	}
	public void setFxfx_lxfx(String fxfx_lxfx) {
		this.fxfx_lxfx = fxfx_lxfx;
	}
	public String getKhjljy() {
		return khjljy;
	}
	public void setKhjljy(String khjljy) {
		this.khjljy = khjljy;
	}
	public String getReason1() {
		return reason1;
	}
	public void setReason1(String reason1) {
		this.reason1 = reason1;
	}
	public String getJytg_je() {
		return jytg_je;
	}
	public void setJytg_je(String jytg_je) {
		this.jytg_je = jytg_je;
	}
	public String getJytg_qx() {
		return jytg_qx;
	}
	public void setJytg_qx(String jytg_qx) {
		this.jytg_qx = jytg_qx;
	}
	public String getJytg_cp() {
		return jytg_cp;
	}
	public void setJytg_cp(String jytg_cp) {
		this.jytg_cp = jytg_cp;
	}
	public String getJytg_lv() {
		return jytg_lv;
	}
	public void setJytg_lv(String jytg_lv) {
		this.jytg_lv = jytg_lv;
	}
	public String getJytg_myhk() {
		return jytg_myhk;
	}
	public void setJytg_myhk(String jytg_myhk) {
		this.jytg_myhk = jytg_myhk;
	}
	public String getJytg_bl() {
		return jytg_bl;
	}
	public void setJytg_bl(String jytg_bl) {
		this.jytg_bl = jytg_bl;
	}
	public String getJytg_jkr() {
		return jytg_jkr;
	}
	public void setJytg_jkr(String jytg_jkr) {
		this.jytg_jkr = jytg_jkr;
	}
	public String getJytg_khgx() {
		return jytg_khgx;
	}
	public void setJytg_khgx(String jytg_khgx) {
		this.jytg_khgx = jytg_khgx;
	}
	public String getJytg_dbr() {
		return jytg_dbr;
	}
	public void setJytg_dbr(String jytg_dbr) {
		this.jytg_dbr = jytg_dbr;
	}
	public String getJytg_dbrgx() {
		return jytg_dbrgx;
	}
	public void setJytg_dbrgx(String jytg_dbrgx) {
		this.jytg_dbrgx = jytg_dbrgx;
	}
	public String getJytg_dy() {
		return jytg_dy;
	}
	public void setJytg_dy(String jytg_dy) {
		this.jytg_dy = jytg_dy;
	}
	public String getJytg_wq() {
		return jytg_wq;
	}
	public void setJytg_wq(String jytg_wq) {
		this.jytg_wq = jytg_wq;
	}
	public String getZbkhjl_sign() {
		return zbkhjl_sign;
	}
	public void setZbkhjl_sign(String zbkhjl_sign) {
		this.zbkhjl_sign = zbkhjl_sign;
	}
	public String getXbkhjl_sign() {
		return xbkhjl_sign;
	}
	public void setXbkhjl_sign(String xbkhjl_sign) {
		this.xbkhjl_sign = xbkhjl_sign;
	}
	public String getRq() {
		return rq;
	}
	public void setRq(String rq) {
		this.rq = rq;
	}
	public String getSqr_xm() {
		return sqr_xm;
	}
	public void setSqr_xm(String sqr_xm) {
		this.sqr_xm = sqr_xm;
	}
	public String getSqr_sex() {
		return sqr_sex;
	}
	public void setSqr_sex(String sqr_sex) {
		this.sqr_sex = sqr_sex;
	}
	public String getSqr_zjhm() {
		return sqr_zjhm;
	}
	public void setSqr_zjhm(String sqr_zjhm) {
		this.sqr_zjhm = sqr_zjhm;
	}
	public String getSqr_hy() {
		return sqr_hy;
	}
	public void setSqr_hy(String sqr_hy) {
		this.sqr_hy = sqr_hy;
	}
	public String getSqr_hjd() {
		return sqr_hjd;
	}
	public void setSqr_hjd(String sqr_hjd) {
		this.sqr_hjd = sqr_hjd;
	}
	public String getSqr_hjxx() {
		return sqr_hjxx;
	}
	public void setSqr_hjxx(String sqr_hjxx) {
		this.sqr_hjxx = sqr_hjxx;
	}
	public String getSqr_xl() {
		return sqr_xl;
	}
	public void setSqr_xl(String sqr_xl) {
		this.sqr_xl = sqr_xl;
	}
	public String getSqr_mobile() {
		return sqr_mobile;
	}
	public void setSqr_mobile(String sqr_mobile) {
		this.sqr_mobile = sqr_mobile;
	}
	public String getSqr_address() {
		return sqr_address;
	}
	public void setSqr_address(String sqr_address) {
		this.sqr_address = sqr_address;
	}
	public String getJzhj_type() {
		return jzhj_type;
	}
	public void setJzhj_type(String jzhj_type) {
		this.jzhj_type = jzhj_type;
	}
	public String getJzhj_type4_qx() {
		return jzhj_type4_qx;
	}
	public void setJzhj_type4_qx(String jzhj_type4_qx) {
		this.jzhj_type4_qx = jzhj_type4_qx;
	}
	public String getJzhj_type4_zj() {
		return jzhj_type4_zj;
	}
	public void setJzhj_type4_zj(String jzhj_type4_zj) {
		this.jzhj_type4_zj = jzhj_type4_zj;
	}
	public String getJzhj_type_other() {
		return jzhj_type_other;
	}
	public void setJzhj_type_other(String jzhj_type_other) {
		this.jzhj_type_other = jzhj_type_other;
	}
	public String getJzhj_mj1() {
		return jzhj_mj1;
	}
	public void setJzhj_mj1(String jzhj_mj1) {
		this.jzhj_mj1 = jzhj_mj1;
	}
	public String getJzhj_mj2() {
		return jzhj_mj2;
	}
	public void setJzhj_mj2(String jzhj_mj2) {
		this.jzhj_mj2 = jzhj_mj2;
	}
	public String getJzhj_mj3() {
		return jzhj_mj3;
	}
	public void setJzhj_mj3(String jzhj_mj3) {
		this.jzhj_mj3 = jzhj_mj3;
	}
	public String getJzhj_zf() {
		return jzhj_zf;
	}
	public void setJzhj_zf(String jzhj_zf) {
		this.jzhj_zf = jzhj_zf;
	}
	public String getJzhj_zf_select() {
		return jzhj_zf_select;
	}
	public void setJzhj_zf_select(String jzhj_zf_select) {
		this.jzhj_zf_select = jzhj_zf_select;
	}
	public String getJzhj_gj() {
		return jzhj_gj;
	}
	public void setJzhj_gj(String jzhj_gj) {
		this.jzhj_gj = jzhj_gj;
	}
	public String getJzhj_gj_room() {
		return jzhj_gj_room;
	}
	public void setJzhj_gj_room(String jzhj_gj_room) {
		this.jzhj_gj_room = jzhj_gj_room;
	}
	public String getJzhj_gj_ting() {
		return jzhj_gj_ting;
	}
	public void setJzhj_gj_ting(String jzhj_gj_ting) {
		this.jzhj_gj_ting = jzhj_gj_ting;
	}
	public String getJzhj_jzrq() {
		return jzhj_jzrq;
	}
	public void setJzhj_jzrq(String jzhj_jzrq) {
		this.jzhj_jzrq = jzhj_jzrq;
	}
	public String getJzhj_dzfs() {
		return jzhj_dzfs;
	}
	public void setJzhj_dzfs(String jzhj_dzfs) {
		this.jzhj_dzfs = jzhj_dzfs;
	}
	public String getJzhj_select() {
		return jzhj_select;
	}
	public void setJzhj_select(String jzhj_select) {
		this.jzhj_select = jzhj_select;
	}
	public String getZyfc_num() {
		return zyfc_num;
	}
	public void setZyfc_num(String zyfc_num) {
		this.zyfc_num = zyfc_num;
	}
	public String getAj_num() {
		return aj_num;
	}
	public void setAj_num(String aj_num) {
		this.aj_num = aj_num;
	}
	public String getFcgmrq() {
		return fcgmrq;
	}
	public void setFcgmrq(String fcgmrq) {
		this.fcgmrq = fcgmrq;
	}
	public String getFcgmjg() {
		return fcgmjg;
	}
	public void setFcgmjg(String fcgmjg) {
		this.fcgmjg = fcgmjg;
	}
	public String getFcmj() {
		return fcmj;
	}
	public void setFcmj(String fcmj) {
		this.fcmj = fcmj;
	}
	public String getAjdkye() {
		return ajdkye;
	}
	public void setAjdkye(String ajdkye) {
		this.ajdkye = ajdkye;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZycl_num() {
		return zycl_num;
	}
	public void setZycl_num(String zycl_num) {
		this.zycl_num = zycl_num;
	}
	public String getDk_num() {
		return dk_num;
	}
	public void setDk_num(String dk_num) {
		this.dk_num = dk_num;
	}
	public String getGmrq() {
		return gmrq;
	}
	public void setGmrq(String gmrq) {
		this.gmrq = gmrq;
	}
	public String getGmjg() {
		return gmjg;
	}
	public void setGmjg(String gmjg) {
		this.gmjg = gmjg;
	}
	public String getCp() {
		return cp;
	}
	public void setCp(String cp) {
		this.cp = cp;
	}
	public String getOther_work() {
		return other_work;
	}
	public void setOther_work(String other_work) {
		this.other_work = other_work;
	}
	public String getOther_income() {
		return other_income;
	}
	public void setOther_income(String other_income) {
		this.other_income = other_income;
	}
	public String getPo_name() {
		return po_name;
	}
	public void setPo_name(String po_name) {
		this.po_name = po_name;
	}
	public String getPo_code() {
		return po_code;
	}
	public void setPo_code(String po_code) {
		this.po_code = po_code;
	}
	public String getPo_mobile() {
		return po_mobile;
	}
	public void setPo_mobile(String po_mobile) {
		this.po_mobile = po_mobile;
	}
	public String getPo_yicome() {
		return po_yicome;
	}
	public void setPo_yicome(String po_yicome) {
		this.po_yicome = po_yicome;
	}
	public String getPo_unit() {
		return po_unit;
	}
	public void setPo_unit(String po_unit) {
		this.po_unit = po_unit;
	}
	public String getP_bank_numm() {
		return p_bank_numm;
	}
	public void setP_bank_numm(String p_bank_numm) {
		this.p_bank_numm = p_bank_numm;
	}
	public String getKhh_1() {
		return khh_1;
	}
	public void setKhh_1(String khh_1) {
		this.khh_1 = khh_1;
	}
	public String getZh1() {
		return zh1;
	}
	public void setZh1(String zh1) {
		this.zh1 = zh1;
	}
	public String getFzc() {
		return fzc;
	}
	public void setFzc(String fzc) {
		this.fzc = fzc;
	}
	public String getKhh_2() {
		return khh_2;
	}
	public void setKhh_2(String khh_2) {
		this.khh_2 = khh_2;
	}
	public String getZh2() {
		return zh2;
	}
	public void setZh2(String zh2) {
		this.zh2 = zh2;
	}
	public String getFzfz() {
		return fzfz;
	}
	public void setFzfz(String fzfz) {
		this.fzfz = fzfz;
	}           
	
	
	

}