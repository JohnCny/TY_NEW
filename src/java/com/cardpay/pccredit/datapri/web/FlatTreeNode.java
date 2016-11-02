package com.cardpay.pccredit.datapri.web;

public class FlatTreeNode {

	private String id;
	
	private String name;
	
	// org, dept 
	private String type;
	// 计算的层级
	private Integer level;

	public FlatTreeNode(String id, String name, String type, Integer level) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.level = level;
	}

	public FlatTreeNode() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
}
