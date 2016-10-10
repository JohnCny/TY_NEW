/*
 * Copyright 2010-2016 the original author or authors.
 * 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 文件：com.cardpay.workflow.models.WfProcessType.java
 * 日 期：Tue Dec 02 08:59:49 CST 2014
 */
package com.cardpay.workflow.models;

import com.wicresoft.jrad.base.database.model.BaseModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**   
 * @Title: WfProcessType.java 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 谭文华   
 * @date 2014-12-4 上午9:21:47
*/
@ModelParam(table = "WF_PROCESS_TYPE")
public class WfProcessType extends BaseModel {
	
	private static final long serialVersionUID = 1L;
	
	private String typeName;
	
	public WfProcessType() {
		super();
	}

	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return this.typeName;
	}
	
	/**
	 * @param typeName the typeName to set
	 */
	public void setTypeName(String value) {
		this.typeName = value;
	}
	
}