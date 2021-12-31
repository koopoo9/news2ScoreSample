/**
  * Copyright (c) 2020 KCB.
  * All right reserved.
  *
  * This software is the confidential and proprietary information of 회사명.
  * You shall not disclose such Confidential Information and
  * shall use it only in accordance with the terms of the license agreement
  * you entered into with KCB.
 */
package god.dag.common.util;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;

/**
 * 설명 : camel case를 underscores로 바꿔지고 다시 대문자로 변환한다.
 *
 * @author 이민구 (t17047)
 * created on 2017. 10. 26.
*/
public class CamelCaseToUpperCaseUnderScoreStrategy extends PropertyNamingStrategy.SnakeCaseStrategy {
	private static final long serialVersionUID = 1L;

	@Override
	public String translate(String input) {
		String upperCase = super.translate(input);
		return upperCase.toUpperCase();
	}
}
