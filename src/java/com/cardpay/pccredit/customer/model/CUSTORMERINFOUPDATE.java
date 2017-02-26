package com.cardpay.pccredit.customer.model;

import java.util.Date;

public class CUSTORMERINFOUPDATE {
		
			  	private String id;
				private String cardid;
				private String chinesename;
				private String zdname;
				private String originalvalue;
				private String modifiedvalue;
				private Date updatetime;
				private String updatetime1;
				public String getUpdatetime1() {
					return updatetime1;
				}
				public void setUpdatetime1(String updatetime1) {
					this.updatetime1 = updatetime1;
				}
				public String getId() {
					return id;
				}
				public void setId(String id) {
					this.id = id;
				}
				public String getCardid() {
					return cardid;
				}
				public void setCardid(String cardid) {
					this.cardid = cardid;
				}
				public String getChinesename() {
					return chinesename;
				}
				public void setChinesename(String chinesename) {
					this.chinesename = chinesename;
				}
				public String getZdname() {
					return zdname;
				}
				public void setZdname(String zdname) {
					this.zdname = zdname;
				}
				public String getOriginalvalue() {
					return originalvalue;
				}
				public void setOriginalvalue(String originalvalue) {
					this.originalvalue = originalvalue;
				}
				public String getModifiedvalue() {
					return modifiedvalue;
				}
				public void setModifiedvalue(String modifiedvalue) {
					this.modifiedvalue = modifiedvalue;
				}
				public Date getUpdatetime() {
					return updatetime;
				}
				public void setUpdatetime(Date updatetime) {
					this.updatetime = updatetime;
				}

}
