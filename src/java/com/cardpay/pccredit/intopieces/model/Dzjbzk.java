package com.cardpay.pccredit.intopieces.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 调查模板-基本状况
 *
 */
@ModelParam(table = "TY_DZ_MODEL_JBZK")
public class Dzjbzk extends BusinessModel{
	private static final long serialVersionUID = 1L;
	
	private String  id;               
	private String  customer_id;    
	private String  product_id;     
	private String  application_id; 
	
	private String mx;                 
	private String zj_type;             
	private String mj;                  
	private String fr;                  
	private String kzr;                 
	private String zb;                  
	private String gy;                  
	private String ywfw;                
	private String yezz;                
	private String yezz_select;         
	private String zm;                  
	private String ksrq;                
	private String address;             
	private String phone;               
	private String dp_type;             
	private String dp_select;           
	private String dp_qx;               
	private String dp_zj;               
	private String dp_other;            
	private String dp_zx;               
	private String dp_zx_select;        
	private String dp_qy_rq;            
	private String zh;                  
	private String m_bank;              
	private String m_zh;                
	private String bank2;               
	private String zh2;                 
	private String jl;                  
	private String ms;                  
	private String zx_zk;               
	private String zx_select;           
	private String zx_sm;               
	private String zx_card_num;        
	private String zx_creddit;          
	private String zx_yy;               
	private String zx_tz;               
	private String zx_yq;               
	private String zx_ls_num;           
	private String zx_wdq_num;          
	private String zx_dk_yy;            
	private String zx_yq_qk;            
	private String zx_dbr_ornot;        
	private String zx_db_ye;            
	private String zx_db_yt;            
	private String zx_db_qx;            
	private String zx_zx_seach_num;     
	private String sqr_family;          
	private String sqr_family_relation; 
	private String sqr_eco_num;         
	private String sqr_bad_hobby;       
	private String sqr_bad_habit;       
	private String sqr_work_pl;         
	private String sqr_zz_change_pl;    
	private String sqr_mark;            
	private String sqr_zz_qk;           
	private String sqr_bx;             
	private String sqr_child_qk;        
	private String sqr_child_edu;       
	private String sqr_child_unit;      
	private String sqr_society_gx;      
	private String sqr_soc_gy;          
	private String sqr_wf;              
	private String sqr_syfm;            
	private String sqr_qq_zk;           
	private String sqr_jr_pj;           
	private String sqr_lj_pj;           
	private String sqr_lxr_pj;          
	private String sqr_sy_pj;           
	private String sqr_q_person;        
	private String sqr_q_bus;           
	private String sqr_w_person;        
	private String sqr_w_bus;           
	private String sqr_ztpj;            
	private String hy_work;             
	private String hy_xz;               
	private String hy_fx;               
	private String hy_qs;              
	private String hy_zz;               
	private String hy_ys_gl;            
	private String hy_gd_gl;            
	private String hy_pj_sale;          
	private String hy_pj_jl;            
	private String hy_gz;               
	private String hy_fy;               
	private String hy_yclje;            
	private String hy_policy;           
	private String hy_desc;             
	private String hy_pj_ml;
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
	public String getMx() {
		return mx;
	}
	public void setMx(String mx) {
		this.mx = mx;
	}
	public String getZj_type() {
		return zj_type;
	}
	public void setZj_type(String zj_type) {
		this.zj_type = zj_type;
	}
	public String getMj() {
		return mj;
	}
	public void setMj(String mj) {
		this.mj = mj;
	}
	public String getFr() {
		return fr;
	}
	public void setFr(String fr) {
		this.fr = fr;
	}
	public String getKzr() {
		return kzr;
	}
	public void setKzr(String kzr) {
		this.kzr = kzr;
	}
	public String getZb() {
		return zb;
	}
	public void setZb(String zb) {
		this.zb = zb;
	}
	public String getGy() {
		return gy;
	}
	public void setGy(String gy) {
		this.gy = gy;
	}
	public String getYwfw() {
		return ywfw;
	}
	public void setYwfw(String ywfw) {
		this.ywfw = ywfw;
	}
	public String getYezz() {
		return yezz;
	}
	public void setYezz(String yezz) {
		this.yezz = yezz;
	}
	public String getYezz_select() {
		return yezz_select;
	}
	public void setYezz_select(String yezz_select) {
		this.yezz_select = yezz_select;
	}
	public String getZm() {
		return zm;
	}
	public void setZm(String zm) {
		this.zm = zm;
	}
	public String getKsrq() {
		return ksrq;
	}
	public void setKsrq(String ksrq) {
		this.ksrq = ksrq;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDp_type() {
		return dp_type;
	}
	public void setDp_type(String dp_type) {
		this.dp_type = dp_type;
	}
	public String getDp_select() {
		return dp_select;
	}
	public void setDp_select(String dp_select) {
		this.dp_select = dp_select;
	}
	public String getDp_qx() {
		return dp_qx;
	}
	public void setDp_qx(String dp_qx) {
		this.dp_qx = dp_qx;
	}
	public String getDp_zj() {
		return dp_zj;
	}
	public void setDp_zj(String dp_zj) {
		this.dp_zj = dp_zj;
	}
	public String getDp_other() {
		return dp_other;
	}
	public void setDp_other(String dp_other) {
		this.dp_other = dp_other;
	}
	public String getDp_zx() {
		return dp_zx;
	}
	public void setDp_zx(String dp_zx) {
		this.dp_zx = dp_zx;
	}
	public String getDp_zx_select() {
		return dp_zx_select;
	}
	public void setDp_zx_select(String dp_zx_select) {
		this.dp_zx_select = dp_zx_select;
	}
	public String getDp_qy_rq() {
		return dp_qy_rq;
	}
	public void setDp_qy_rq(String dp_qy_rq) {
		this.dp_qy_rq = dp_qy_rq;
	}
	public String getZh() {
		return zh;
	}
	public void setZh(String zh) {
		this.zh = zh;
	}
	public String getM_bank() {
		return m_bank;
	}
	public void setM_bank(String m_bank) {
		this.m_bank = m_bank;
	}
	public String getM_zh() {
		return m_zh;
	}
	public void setM_zh(String m_zh) {
		this.m_zh = m_zh;
	}
	public String getBank2() {
		return bank2;
	}
	public void setBank2(String bank2) {
		this.bank2 = bank2;
	}
	public String getZh2() {
		return zh2;
	}
	public void setZh2(String zh2) {
		this.zh2 = zh2;
	}
	public String getJl() {
		return jl;
	}
	public void setJl(String jl) {
		this.jl = jl;
	}
	public String getMs() {
		return ms;
	}
	public void setMs(String ms) {
		this.ms = ms;
	}
	public String getZx_zk() {
		return zx_zk;
	}
	public void setZx_zk(String zx_zk) {
		this.zx_zk = zx_zk;
	}
	public String getZx_select() {
		return zx_select;
	}
	public void setZx_select(String zx_select) {
		this.zx_select = zx_select;
	}
	public String getZx_sm() {
		return zx_sm;
	}
	public void setZx_sm(String zx_sm) {
		this.zx_sm = zx_sm;
	}
	public String getZx_card_num() {
		return zx_card_num;
	}
	public void setZx_card_num(String zx_card_num) {
		this.zx_card_num = zx_card_num;
	}
	public String getZx_creddit() {
		return zx_creddit;
	}
	public void setZx_creddit(String zx_creddit) {
		this.zx_creddit = zx_creddit;
	}
	public String getZx_yy() {
		return zx_yy;
	}
	public void setZx_yy(String zx_yy) {
		this.zx_yy = zx_yy;
	}
	public String getZx_tz() {
		return zx_tz;
	}
	public void setZx_tz(String zx_tz) {
		this.zx_tz = zx_tz;
	}
	public String getZx_yq() {
		return zx_yq;
	}
	public void setZx_yq(String zx_yq) {
		this.zx_yq = zx_yq;
	}
	public String getZx_ls_num() {
		return zx_ls_num;
	}
	public void setZx_ls_num(String zx_ls_num) {
		this.zx_ls_num = zx_ls_num;
	}
	public String getZx_wdq_num() {
		return zx_wdq_num;
	}
	public void setZx_wdq_num(String zx_wdq_num) {
		this.zx_wdq_num = zx_wdq_num;
	}
	public String getZx_dk_yy() {
		return zx_dk_yy;
	}
	public void setZx_dk_yy(String zx_dk_yy) {
		this.zx_dk_yy = zx_dk_yy;
	}
	public String getZx_yq_qk() {
		return zx_yq_qk;
	}
	public void setZx_yq_qk(String zx_yq_qk) {
		this.zx_yq_qk = zx_yq_qk;
	}
	public String getZx_dbr_ornot() {
		return zx_dbr_ornot;
	}
	public void setZx_dbr_ornot(String zx_dbr_ornot) {
		this.zx_dbr_ornot = zx_dbr_ornot;
	}
	public String getZx_db_ye() {
		return zx_db_ye;
	}
	public void setZx_db_ye(String zx_db_ye) {
		this.zx_db_ye = zx_db_ye;
	}
	public String getZx_db_yt() {
		return zx_db_yt;
	}
	public void setZx_db_yt(String zx_db_yt) {
		this.zx_db_yt = zx_db_yt;
	}
	public String getZx_db_qx() {
		return zx_db_qx;
	}
	public void setZx_db_qx(String zx_db_qx) {
		this.zx_db_qx = zx_db_qx;
	}
	public String getZx_zx_seach_num() {
		return zx_zx_seach_num;
	}
	public void setZx_zx_seach_num(String zx_zx_seach_num) {
		this.zx_zx_seach_num = zx_zx_seach_num;
	}
	public String getSqr_family() {
		return sqr_family;
	}
	public void setSqr_family(String sqr_family) {
		this.sqr_family = sqr_family;
	}
	public String getSqr_family_relation() {
		return sqr_family_relation;
	}
	public void setSqr_family_relation(String sqr_family_relation) {
		this.sqr_family_relation = sqr_family_relation;
	}
	public String getSqr_eco_num() {
		return sqr_eco_num;
	}
	public void setSqr_eco_num(String sqr_eco_num) {
		this.sqr_eco_num = sqr_eco_num;
	}
	public String getSqr_bad_hobby() {
		return sqr_bad_hobby;
	}
	public void setSqr_bad_hobby(String sqr_bad_hobby) {
		this.sqr_bad_hobby = sqr_bad_hobby;
	}
	public String getSqr_bad_habit() {
		return sqr_bad_habit;
	}
	public void setSqr_bad_habit(String sqr_bad_habit) {
		this.sqr_bad_habit = sqr_bad_habit;
	}
	public String getSqr_work_pl() {
		return sqr_work_pl;
	}
	public void setSqr_work_pl(String sqr_work_pl) {
		this.sqr_work_pl = sqr_work_pl;
	}
	public String getSqr_zz_change_pl() {
		return sqr_zz_change_pl;
	}
	public void setSqr_zz_change_pl(String sqr_zz_change_pl) {
		this.sqr_zz_change_pl = sqr_zz_change_pl;
	}
	public String getSqr_mark() {
		return sqr_mark;
	}
	public void setSqr_mark(String sqr_mark) {
		this.sqr_mark = sqr_mark;
	}
	public String getSqr_zz_qk() {
		return sqr_zz_qk;
	}
	public void setSqr_zz_qk(String sqr_zz_qk) {
		this.sqr_zz_qk = sqr_zz_qk;
	}
	public String getSqr_bx() {
		return sqr_bx;
	}
	public void setSqr_bx(String sqr_bx) {
		this.sqr_bx = sqr_bx;
	}
	public String getSqr_child_qk() {
		return sqr_child_qk;
	}
	public void setSqr_child_qk(String sqr_child_qk) {
		this.sqr_child_qk = sqr_child_qk;
	}
	public String getSqr_child_edu() {
		return sqr_child_edu;
	}
	public void setSqr_child_edu(String sqr_child_edu) {
		this.sqr_child_edu = sqr_child_edu;
	}
	public String getSqr_child_unit() {
		return sqr_child_unit;
	}
	public void setSqr_child_unit(String sqr_child_unit) {
		this.sqr_child_unit = sqr_child_unit;
	}
	public String getSqr_society_gx() {
		return sqr_society_gx;
	}
	public void setSqr_society_gx(String sqr_society_gx) {
		this.sqr_society_gx = sqr_society_gx;
	}
	public String getSqr_soc_gy() {
		return sqr_soc_gy;
	}
	public void setSqr_soc_gy(String sqr_soc_gy) {
		this.sqr_soc_gy = sqr_soc_gy;
	}
	public String getSqr_wf() {
		return sqr_wf;
	}
	public void setSqr_wf(String sqr_wf) {
		this.sqr_wf = sqr_wf;
	}
	public String getSqr_syfm() {
		return sqr_syfm;
	}
	public void setSqr_syfm(String sqr_syfm) {
		this.sqr_syfm = sqr_syfm;
	}
	public String getSqr_qq_zk() {
		return sqr_qq_zk;
	}
	public void setSqr_qq_zk(String sqr_qq_zk) {
		this.sqr_qq_zk = sqr_qq_zk;
	}
	public String getSqr_jr_pj() {
		return sqr_jr_pj;
	}
	public void setSqr_jr_pj(String sqr_jr_pj) {
		this.sqr_jr_pj = sqr_jr_pj;
	}
	public String getSqr_lj_pj() {
		return sqr_lj_pj;
	}
	public void setSqr_lj_pj(String sqr_lj_pj) {
		this.sqr_lj_pj = sqr_lj_pj;
	}
	public String getSqr_lxr_pj() {
		return sqr_lxr_pj;
	}
	public void setSqr_lxr_pj(String sqr_lxr_pj) {
		this.sqr_lxr_pj = sqr_lxr_pj;
	}
	public String getSqr_sy_pj() {
		return sqr_sy_pj;
	}
	public void setSqr_sy_pj(String sqr_sy_pj) {
		this.sqr_sy_pj = sqr_sy_pj;
	}
	public String getSqr_q_person() {
		return sqr_q_person;
	}
	public void setSqr_q_person(String sqr_q_person) {
		this.sqr_q_person = sqr_q_person;
	}
	public String getSqr_q_bus() {
		return sqr_q_bus;
	}
	public void setSqr_q_bus(String sqr_q_bus) {
		this.sqr_q_bus = sqr_q_bus;
	}
	public String getSqr_w_person() {
		return sqr_w_person;
	}
	public void setSqr_w_person(String sqr_w_person) {
		this.sqr_w_person = sqr_w_person;
	}
	public String getSqr_w_bus() {
		return sqr_w_bus;
	}
	public void setSqr_w_bus(String sqr_w_bus) {
		this.sqr_w_bus = sqr_w_bus;
	}
	public String getSqr_ztpj() {
		return sqr_ztpj;
	}
	public void setSqr_ztpj(String sqr_ztpj) {
		this.sqr_ztpj = sqr_ztpj;
	}
	public String getHy_work() {
		return hy_work;
	}
	public void setHy_work(String hy_work) {
		this.hy_work = hy_work;
	}
	public String getHy_xz() {
		return hy_xz;
	}
	public void setHy_xz(String hy_xz) {
		this.hy_xz = hy_xz;
	}
	public String getHy_fx() {
		return hy_fx;
	}
	public void setHy_fx(String hy_fx) {
		this.hy_fx = hy_fx;
	}
	public String getHy_qs() {
		return hy_qs;
	}
	public void setHy_qs(String hy_qs) {
		this.hy_qs = hy_qs;
	}
	public String getHy_zz() {
		return hy_zz;
	}
	public void setHy_zz(String hy_zz) {
		this.hy_zz = hy_zz;
	}
	public String getHy_ys_gl() {
		return hy_ys_gl;
	}
	public void setHy_ys_gl(String hy_ys_gl) {
		this.hy_ys_gl = hy_ys_gl;
	}
	public String getHy_gd_gl() {
		return hy_gd_gl;
	}
	public void setHy_gd_gl(String hy_gd_gl) {
		this.hy_gd_gl = hy_gd_gl;
	}
	public String getHy_pj_sale() {
		return hy_pj_sale;
	}
	public void setHy_pj_sale(String hy_pj_sale) {
		this.hy_pj_sale = hy_pj_sale;
	}
	public String getHy_pj_jl() {
		return hy_pj_jl;
	}
	public void setHy_pj_jl(String hy_pj_jl) {
		this.hy_pj_jl = hy_pj_jl;
	}
	public String getHy_gz() {
		return hy_gz;
	}
	public void setHy_gz(String hy_gz) {
		this.hy_gz = hy_gz;
	}
	public String getHy_fy() {
		return hy_fy;
	}
	public void setHy_fy(String hy_fy) {
		this.hy_fy = hy_fy;
	}
	public String getHy_yclje() {
		return hy_yclje;
	}
	public void setHy_yclje(String hy_yclje) {
		this.hy_yclje = hy_yclje;
	}
	public String getHy_policy() {
		return hy_policy;
	}
	public void setHy_policy(String hy_policy) {
		this.hy_policy = hy_policy;
	}
	public String getHy_desc() {
		return hy_desc;
	}
	public void setHy_desc(String hy_desc) {
		this.hy_desc = hy_desc;
	}
	public String getHy_pj_ml() {
		return hy_pj_ml;
	}
	public void setHy_pj_ml(String hy_pj_ml) {
		this.hy_pj_ml = hy_pj_ml;
	}

}