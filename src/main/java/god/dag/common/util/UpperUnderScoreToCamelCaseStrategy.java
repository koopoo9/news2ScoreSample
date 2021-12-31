/**
  * Copyright (c) 2020 KCB.
  * All right reserved.
  *
  * This software is the confidential and proprietary information of KCB.
  * You shall not disclose such Confidential Information and
  * shall use it only in accordance with the terms of the license agreement
  * you entered into with KCB.
 */
package god.dag.common.util;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;

/**
 * 설명 : JSON with underscores to camel case 
 *
 * @author 이민구 (t17047)
 * created on 2018. 4. 30.
*/
public class UpperUnderScoreToCamelCaseStrategy extends PropertyNamingStrategy {
	private static final long serialVersionUID = 1L;

	@Override
	public String nameForField(MapperConfig config, AnnotatedField field, String defaultName) {
		return convert(defaultName);
	}
	
	@Override
	public String nameForGetterMethod(MapperConfig config, AnnotatedMethod method, String defaultName) {
		return convert(defaultName);
	}

	@Override
	public String nameForSetterMethod(MapperConfig config, AnnotatedMethod method, String defaultName) {
		return convert(defaultName);
	}

	public String convert(String input) {
		input = input.toLowerCase();
		
	   char[] arr = input.toCharArray();
	   StringBuilder nameToReturn = new StringBuilder();

	   for(int i=0; i< arr.length; i++) {
		   if(arr[i] == '_') {
			   nameToReturn.append(Character.toUpperCase(arr[i+1]));
			   i++;
			   
		   } else {
			   nameToReturn.append(arr[i]);
		   }
	   }
	   return nameToReturn.toString();
	}	
}
