package god.dag.common.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import kcb.passauth.cpchannel.vo.domain.ApiModelProperty;
import kcb.passauth.cpchannel.vo.domain.CommonResVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * 설명 : PASS인증서 - Notice 공통
 *
*/
@ApiModel
@Data
@NoArgsConstructor
@ToString(callSuper=true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class News2ScoreResVO{
	private static final long serialVersionUID = -6104092830354021939L;

	@ApiModelProperty
	private String rsltCd;
	
	@ApiModelProperty
	private String rsltMsg;
	
	public void setResult(String rsltCd, String rsltMsg) {
		this.rsltCd = rsltCd;
		this.rsltMsg = rsltMsg;
	}
	
}
