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
 * 文件：com.cardpay.workflow.models.WfStatusResult.java
 * 日 期：Tue Dec 02 08:59:49 CST 2014
 */
package com.cardpay.workflow.models;

import com.wicresoft.jrad.base.database.model.BaseModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**   
 * @Title: WfStatusResult.java 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 谭文华   
 * @date 2014-12-4 上午9:22:02
*/
@ModelParam(table = "WF_STATUS_RESULT")
public class WfStatusResult extends BaseModel {
	
	private static final long serialVersionUID = 1L;

	private String currentStatus;

	private String examineResult;

	private String nextStatus;
	
	public WfStatusResult() {
		super();
	}

	/**
	 * @return the currentStatus
	 */
	public String getCurrentStatus() {
		return this.currentStatus;
	}
	
	/**
	 * @param currentStatus the currentStatus to set
	 */
	public void setCurrentStatus(String value) {
		this.currentStatus = value;
	}

	/**
	 * @return the examineResult
	 */
	public String getExamineResult() {
		return this.examineResult;
	}
	
	/**
	 * @param examineResult the examineResult to set
	 */
	public void setExamineResult(String value) {
		this.examineResult = value;
	}

	/**
	 * @return the nextStatus
	 */
	public String getNextStatus() {
		return this.nextStatus;
	}
	
	/**
	 * @param nextStatus the nextStatus to set
	 */
	public void setNextStatus(String value) {
		this.nextStatus = value;
	}

}