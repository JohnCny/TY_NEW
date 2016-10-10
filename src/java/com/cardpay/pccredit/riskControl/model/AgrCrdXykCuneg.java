package com.cardpay.pccredit.riskControl.model;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BaseModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 黑名单数据资料
 * @author chenzhifang
 *
 * 2014年11月27日   上午11:36:02
 */
@ModelParam(table = "f_agr_crd_xyk_cuneg",generator=IDType.uuid32)
public class AgrCrdXykCuneg extends BaseModel {
	private static final long serialVersionUID = 693517148960639826L;
	private String id;
	private String bank;

    private String fileType;

    private String cunegSts;

    private String custrNbr;

    private String nameExtnd;

    private String custrRef;

    private String applnref;

    private String product;

    private String reasnCode;

    private String reasnDesc;

    private String inpSource;

    private String scorNote;

    private String chgDay;

    private String chgTime;

    private String chgEmploy;

    private String instNo;

    private String createdTime;
    
    private String khmc;
    
    private String khnm;
    
    private String sxrq;
    
    private String tcrq;
    
    private String sbyy;

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank == null ? null : bank.trim();
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
    }

    public String getCunegSts() {
        return cunegSts;
    }

    public void setCunegSts(String cunegSts) {
        this.cunegSts = cunegSts == null ? null : cunegSts.trim();
    }

    public String getCustrNbr() {
        return custrNbr;
    }

    public void setCustrNbr(String custrNbr) {
        this.custrNbr = custrNbr == null ? null : custrNbr.trim();
    }

    public String getNameExtnd() {
        return nameExtnd;
    }

    public void setNameExtnd(String nameExtnd) {
        this.nameExtnd = nameExtnd == null ? null : nameExtnd.trim();
    }

    public String getCustrRef() {
        return custrRef;
    }

    public void setCustrRef(String custrRef) {
        this.custrRef = custrRef == null ? null : custrRef.trim();
    }

    public String getApplnref() {
        return applnref;
    }

    public void setApplnref(String applnref) {
        this.applnref = applnref == null ? null : applnref.trim();
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product == null ? null : product.trim();
    }

    public String getReasnCode() {
        return reasnCode;
    }

    public void setReasnCode(String reasnCode) {
        this.reasnCode = reasnCode == null ? null : reasnCode.trim();
    }

    public String getReasnDesc() {
        return reasnDesc;
    }

    public void setReasnDesc(String reasnDesc) {
        this.reasnDesc = reasnDesc == null ? null : reasnDesc.trim();
    }

    public String getInpSource() {
        return inpSource;
    }

    public void setInpSource(String inpSource) {
        this.inpSource = inpSource == null ? null : inpSource.trim();
    }

    public String getScorNote() {
        return scorNote;
    }

    public void setScorNote(String scorNote) {
        this.scorNote = scorNote == null ? null : scorNote.trim();
    }

    public String getChgDay() {
        return chgDay;
    }

    public void setChgDay(String chgDay) {
        this.chgDay = chgDay == null ? null : chgDay.trim();
    }

    public String getChgTime() {
        return chgTime;
    }

    public void setChgTime(String chgTime) {
        this.chgTime = chgTime == null ? null : chgTime.trim();
    }

    public String getChgEmploy() {
        return chgEmploy;
    }

    public void setChgEmploy(String chgEmploy) {
        this.chgEmploy = chgEmploy == null ? null : chgEmploy.trim();
    }

    public String getInstNo() {
        return instNo;
    }

    public void setInstNo(String instNo) {
        this.instNo = instNo == null ? null : instNo.trim();
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime == null ? null : createdTime.trim();
    }

	public String getKhmc() {
		return khmc;
	}

	public void setKhmc(String khmc) {
		this.khmc = khmc;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKhnm() {
		return khnm;
	}

	public void setKhnm(String khnm) {
		this.khnm = khnm;
	}

	public String getSxrq() {
		return sxrq;
	}

	public void setSxrq(String sxrq) {
		this.sxrq = sxrq;
	}

	public String getTcrq() {
		return tcrq;
	}

	public void setTcrq(String tcrq) {
		this.tcrq = tcrq;
	}

	public String getSbyy() {
		return sbyy;
	}

	public void setSbyy(String sbyy) {
		this.sbyy = sbyy;
	}
	
}