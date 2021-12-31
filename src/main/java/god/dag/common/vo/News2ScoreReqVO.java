/**
  * Copyright (c) 2020 KCB.
  * All right reserved.
  *
  * This software is the confidential and proprietary information of KCB.
  * You shall not disclose such Confidential Information and
  * shall use it only in accordance with the terms of the license agreement
  * you entered into with KCB.
 */
package god.dag.common.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import god.dag.common.util.CamelCaseToUpperCaseUnderScoreStrategy;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel
@JsonNaming(CamelCaseToUpperCaseUnderScoreStrategy.class)
@Data
@EqualsAndHashCode(callSuper=false)
@ToString(callSuper=true)
public class News2ScoreReqVO {
	private static final long serialVersionUID = -1227166453151430154L;

	@ApiModelProperty(required = true)
	@Size(max = 100)
	private String custId;
	
	@ApiModelProperty(required = true)
	@Size(max = 100)
	private String txId;
		
	@ApiModelProperty(required = true)
	@Size(max = 14)
	private String checkDtTm;
	
	@ApiModelProperty(required = true)
	@Size(max = 100)
	private String rsprtnRt;
	
	@ApiModelProperty(required = true)
	@Size(max = 2)
	private String spo2SclCd;
	
	@ApiModelProperty(required = true)
	@Size(max = 3)
	private String spo2Scl1Prct;
	
	@ApiModelProperty(required = true)
	@Size(max = 3)
	private String spo2Scl2Prct;
	
	@ApiModelProperty(required = true)
	@Size(max = 2)
	private String airOxgnCd;
	
	@ApiModelProperty(required = true)
	@Size(max = 3)
	private String systlcBldPrssr;
	
	@ApiModelProperty(required = true)
	@Size(max = 3)
	private String pls;
	
	@ApiModelProperty(required = true)
	@Size(max = 100)
	private String cnscsnss;

	@ApiModelProperty(required = true)
	@Size(max = 3)
	private String tmpratr;
}
