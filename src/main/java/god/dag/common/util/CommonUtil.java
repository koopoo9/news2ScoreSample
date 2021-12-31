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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 설명 : 공통적 또는 범용적으로 많이 사용될 기능들에 대해 모아놓은 유틸 클래스
 *
 * @author 이민구 (t17047)
 * created on 2017. 7. 6.
 */
@Component
@Scope("singleton")
public class CommonUtil {
	private static final String emptyStr = "";
	
	@Autowired private StringUtil stringUtil;
	
	
	/**
	 * MD5 해쉬 데이터 생성하고 리턴한다.
	 */
	public String getMd5ToHash(String str) {
		String MD5 = "";

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes(StandardCharsets.UTF_8));

			byte[] byteData = md.digest();
			StringBuilder sb = new StringBuilder();

			for (byte byteDatum : byteData) {
				sb.append(Integer.toString((byteDatum & 0xff) + 0x100, 16).substring(1));
			}
			MD5 = sb.toString();

		} catch (Exception e) {
			MD5 = null;
		}
		return MD5;
	}
	
	/**
	 * MD5 해쉬 데이터 비교하고 결과 리턴한다.
	 */
	public boolean verifyMd5ToHash(String orgSignature, String targetSignature) {
		boolean verifyCheck = false;
		
		byte[] orgSignatureBytes = orgSignature.getBytes();
		byte[] targetSignatureBytes = targetSignature.getBytes();
		
		if (Arrays.equals(orgSignatureBytes, targetSignatureBytes)) {
			verifyCheck = true;
		}
		return verifyCheck;
	}	
	
	/**
	 * Client IP를 확인한다.
	 */
	public String getClientIp(HttpServletRequest request) {
        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("client-ip");
            
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
    }	
	
	/**
	 * 이메일 주소 마스킹 (정규식을 이용)
	 *   
	 *   처리내용 :
	 *   userId 길이를 기준으로 세글자 이상인 경우 뒤 두자리를 마스킹 처리하고,
	 *   세글자 미만인 경우 모두 마스킹 처리
	 * 
	 * @param email String 이메일
	 * @return maskedEmailAddress
    */
	public String getMaskedEmail(String email) {
		String regex = "\\b(\\S+)+@(\\S+.\\S+)";
		Matcher matcher = Pattern.compile(regex).matcher(email);
	
		if (matcher.find()) {
			String userId = matcher.group(1);  // 마스킹 처리할 부분인 userId 
			int length = userId.length();
	         
			if (length < 3) {
	            char[] c = new char[length];
	            
	            Arrays.fill(c, '*');
	            return email.replace(userId, String.valueOf(c));
	            
			} else {
				return email.replaceAll("\\b(\\S+)[^@][^@]+@(\\S+)", "$1**@$2");
			}
		}
		return email;
	}
	
	/**
	 * 휴대폰 번호 마스킹 처리
	 * @param phoneNum String 휴대폰번호
	 * @return maskedCellPhoneNumber
     */
	public String getMaskedPhoneNum(String phoneNum) {
		String regex = "(01[016789])(\\d{3,4})\\d{4}$";
		Matcher matcher = Pattern.compile(regex).matcher(phoneNum);
		
		if (matcher.find()) {
			String replaceTarget = matcher.group(2);
			
			char[] c = new char[replaceTarget.length()];
			Arrays.fill(c, '*');
			
			return phoneNum.replace(replaceTarget, String.valueOf(c));
		}
		return phoneNum;
	}	
	
	/**
	 * request의 헤더내용 출력해 준다.
	 */
	@SuppressWarnings("rawtypes")
	public String getRequestHeder(HttpServletRequest request) {
		Map<String, String> map = new HashMap<>();
        Enumeration headerNames = request.getHeaderNames();
        
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map.toString();
	}
	
	/**
	 * request의 바디내용 출력해 준다.
	 */	
	public String getRequestBody(HttpServletRequest request) throws IOException {
	    String body;
	    StringBuilder stringBuilder = new StringBuilder();
	    BufferedReader bufferedReader = null;

	    try (InputStream inputStream = request.getInputStream()) {
	        if (inputStream != null) {
	            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
	            
	            char[] charBuffer = new char[128];
	            int bytesRead;
	            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
	                stringBuilder.append(charBuffer, 0, bytesRead);
	            }
	        }
		} finally {
	        if (bufferedReader != null) {
				bufferedReader.close();
			}
	    }
	    
	    body = stringBuilder.toString();
	    return body;
	}
	
	/**
	 * 설명 : request 모든 파라미터를 뽑아서 Map에 담아 리턴한다.
	 *
	 * @param request HttpServletRequest
	 * @return Map
	 */
	public Map<String, Object> getParametersToMap(HttpServletRequest request) {
		Map<String, Object> parametersToMap = new LinkedHashMap<>();
		
		Enumeration<String> enumeration = request.getParameterNames();
		while(enumeration.hasMoreElements()) {
			String parameterName = enumeration.nextElement();
			String parameterValue = stringUtil.notEmpty(request.getParameter(parameterName));
			
			parametersToMap.put(parameterName, parameterValue);
		}
		return parametersToMap;
	}	
	
	/**
	 * <pre>
	 * 1. 메소드명 : toHtml
	 * 2. 작성자 : SUNGJIN, YANG
	 * 3. 작성일 : 2016. 12. 4.
	 * 4. 설명 : HTML 형식으로 변환한다.
	 * </pre>
	 * @param str String
	 * @return String
	 */
	public String toHtml(final String str) {
		String result;

		String gt_str;
		String lt_str;
		String sp_str;
		String cr_str;
		String sq_str;
		
		gt_str 	= "&gt;";
		lt_str 	= "&lt;";
		sp_str 	= "&nbsp;";
		cr_str 	= "<br>;";
		sq_str	= "&#39;";
		
		if (str == null) {
			return	"";
			
		} else {
			StringBuilder sb = new StringBuilder();
			char ch;

			for (int i = 0; i < str.length(); i++) {
				ch = str.charAt(i);

				switch (ch)	{
					case ('<') :
						sb.append(lt_str);
						break;
					case ('>') :
						sb.append(gt_str);
						break;
					case (' ') :
						sb.append(sp_str);
						break;
					case ('\r') :
					case ('\n') :
						sb.append(cr_str);
						break;
					case ('\'') :
						sb.append(sq_str);
						break;	
					default :
						sb.append(ch);
				}
			}
			
			result = sb.toString();
		}
		return result;
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 메소드명 : replaceEnter
	 * 2. 작성자 : SUNGJIN, YANG
	 * 3. 작성일 : 2016. 12. 4.
	 * 4. 설명 :  \r\n 개행문자를 <br>로 변경하여 반환
	 * </pre>
	 * @param value String
	 * @return String
	 */
	public String replaceCarigeToBr(final String value) {
		return value.replaceAll("(\r\n|\r|\n|\n\r)", "<br />");
	}	
	
	/**
	 * 
	 * <pre>
	 * 1. 메소드명 : replaceBrToCarige
	 * 2. 작성자 : SUNGJIN, YANG
	 * 3. 작성일 : 2016. 12. 4.
	 * 4. 설명 : <br> 을 개행문자(\r\n)로 변환
	 * </pre>
	 * @param value String
	 * @return String
	 */
	public String replaceBrToCarige(final String value) {
		return value.replaceAll("<br />", "\r\n");
	}
	
	/**
	 * 주민번호앞 7자리로 출생년도(yyyyMMdd), 성별, 내/외국인을 찾는다.
	 * 
	 * @param value 주민번호앞 7자리
	 * @return String[]
	 */	
	public static String[] findBirthday(String value) {
		String[] result = new String[3];
		
		int[] jumin = new int[value.length()];
		for(int i=0; i < jumin.length; i++){
			jumin[i] = Integer.parseInt(value.substring(i, i+1));
		}
		
		int year = 0, month = 0, day = 0, sex = 0, local = 0;
		switch(jumin[6]) {
			case 1: year = 1900; sex = 0; local = 0; break;
			case 2: year = 1900; sex = 1; local = 0; break;
			case 3: year = 2000; sex = 0; local = 0; break;
			case 4: year = 2000; sex = 1; local = 0; break;
			case 5: year = 1900; sex = 0; local = 1; break;
			case 6: year = 1900; sex = 1; local = 1; break;
			case 7: year = 2000; sex = 0; local = 1; break;
			case 8: year = 2000; sex = 1; local = 1; break;
		    case 9: year = 1800; sex = 0; local = 0; break;
		    case 0: year = 1800; sex = 1; local = 0; break;
		}
		
		year += jumin[0] * 10 + jumin[1];
		month = jumin[2] * 10 + jumin[3];
		day = jumin[4] * 10 + jumin[5];
		
		result[0] = String.valueOf(year) + month + day;

		result[1] = sex != 1 ? "M" : "F";
		result[2] = local != 1 ? "N" : "Y";
				
		return result;
	}
	
	/**
	* @param strAny Strin  : 원본 데이터
	* 
	* @return 파라미터로 받은 String 널체크 후 리턴한다.
	 */		 
	public String stringNullCheck(final String strAny) {
		boolean result;
		
		result = strAny == null || "null".equals(strAny.trim()) || "".equals(strAny.trim());
		
		return (result ?  emptyStr : strAny);
	}
	
	/**
	* @param strAny String : 원본 데이터
	* @param arg String : Null 일경우 리턴할 문자열
	* 
	* @return 파라미터로 받은 String 널체크 후 리턴한다.
	 */
	public String stringNullCheck(final String strAny, final String arg) {
		boolean result = strAny == null || "null".equals(strAny.trim()) || "".equals(strAny.trim());

		return (result ?  arg : strAny);
	}

    /**
    * String[] 이 null 인 경우 ""로 리턴합니다.
	 */
	public String[] stringNullCheck(String[] strAny) {
		for(int i=0; i<strAny.length; i++) {
			if(strAny[i] == null || "null".equals(strAny[i].trim()) || "".equals(strAny[i].trim())) {
				strAny[i] = emptyStr;
			}
		}

		return strAny;
	}	
	
	/**
	* @param date String : yyyymmdd 형식의 날짜 데이터
	* @param fomat String : delim 문자
	* 
	* @return yyyymmdd 형식의 날짜 데이터 길이가 8보다 작을 날짜 yy-mm-dd  형식으로 8자인 경우는 yyyy-mm-dd 형식으로 변환 후 리턴한다.
	 */
	public String getConvertDate(final String date, final String fomat) {
		StringBuilder strBuild;
		String result = (date == null ? emptyStr : date.trim());

		if (!emptyStr.equals(result)) {
			strBuild = new StringBuilder();

			if (result.length() < 8) {
				result = strBuild.append(result, 0, 2).append(fomat).append(result, 2, 4).append(fomat).append(result, 4, 6).toString();
			} else {
				result = strBuild.append(result, 0, 4).append(fomat).append(result, 4, 6).append(fomat).append(result, 6, 8).toString();
			}
		}

		return result;
	}

	/**
	* @param date String : yyyymmddhhmmss 형식의 날짜
	* @param fomat1 String : delim 문자1
	* @param fomat2 String : delim 문자2
	* 
	* @return yyyymmddhhmmss 형식의 날짜를 yyyy-mm-dd hh:mm:ss 형식으로 변환. format1, 2는 사용자에 맡게 변경할 수 있음.
	 */
	public String getConvertFullDateTime(final String date, final String fomat1, final String fomat2) {
		StringBuilder strBuild;
		String result = (date == null ? emptyStr : date.trim());
			
		if (!"".equals(result)) {
			if (result.length() == 14) {
				strBuild = new StringBuilder();
				strBuild.append(result, 0, 4).append(fomat1).append(result, 4, 6).append(fomat1).append(result, 6, 8);
				strBuild.append(" ");
				strBuild.append(result, 8, 10).append(fomat2).append(result, 10, 12).append(fomat2).append(result, 12, 14);

				result = strBuild.toString();
			}
		}

		return	result;
	}
	
	/**
	 * 숫자에 천단위 콤마찍기
	 */
	public String getCommaWon(String amount) {
		if (amount != null)
			return new DecimalFormat("#,###,###,##0.00").format(new BigDecimal(amount));
		
		return "";
	}	
	
	/**
	* 금액 값으로 리턴합니다.(123,123,111)
	*/
	public String getWon(final double w) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("###,##0");
		return df.format(w);
	}

	public String getWon(final String w) {
		double d = 0;
		try {
			d = Double.parseDouble(w);
			
		} catch(Exception e) {
			d = 0;
		}
		return getWon(d);
	}
	
	/**
	 * 달러금액변환 (30000 -> 300.00 으로 변환한다.)
	 *
	 * @param w double
	 * @return String
	 */
	public String getUSD(final double w) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("###,###.00");
        return df.format(w);
    }

	/**
	 * 달러금액변환 (30000 -> 300.00 으로 변환한다.)
	 *
	 * @param w String
	 * @return String
	 */
    public String getUSD(final String w) {
        double d 		= 0.0;
        String result 	= null;
        
        try {            
            if( w.length() >= 3 ) {
            	result = w.substring( 0, w.length() - 2) +  "." + w.substring(w.length() - 2);
                d = Double.parseDouble(result);
            }
            
        } catch(Exception e) {
            d = 0.0;
        }

        return getUSD(d);
    }	
	
    /**
    * String 에 작은 따옴표를 달아줍니다.
    */
    public String makeQuote(final String condition) {
  	     return "'" + condition + "'";
    }	
    
	/**
	 * 00:00:00 타입 시간을 초로 계산
	 */
	public long getStringTimeSec(String time){
		int splitCnt = 0;
		long trackPlayTm = 0;
		
		String[] splitTime = time.split(":");
		splitCnt = splitTime.length;
		
		switch (splitCnt) {
		case 1:
			trackPlayTm = Long.parseLong(splitTime[0]);
			break;

		case 2:
			trackPlayTm = (Long.parseLong(splitTime[0]) * 60) + Long.parseLong(splitTime[1]);
			break;

		case 3:
			trackPlayTm = (Long.parseLong(splitTime[0]) * 60 * 60) + (Long.parseLong(splitTime[1]) * 60) + Long.parseLong(splitTime[2]);
			break;
		}
		return trackPlayTm;
	}
	
	/**
	 * 설명 : 모바일 환경 여부 반환
	 * @param request HttpServletRequest
	 * @return boolean
	 */
	public boolean isMobile(HttpServletRequest request) {
		if(request != null && request.getHeader("User-Agent") != null) {
			String userAgent = request.getHeader("User-Agent");
			boolean mobile1 = userAgent.matches(".*(iPhone|iPod|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|Opera Mobi|POLARIS|IEMbile|lgtelecom|nokia|SonyEricsson).*");
			boolean mobile2 = userAgent.matches(".*(LG|SAMSUNG|Samsung).*");
			return (userAgent.contains("Mobile")) || mobile1 || mobile2;
		}
		return false;
	}
	
	public String redirectTo(String url) {
		return "redirect:" + url;
	}
	
	public String forwardTo(String url) {
		return "forward:" + url;
	}
	
	public String returnTo(String url) {
		return url + ".info";
	}
	
	/**
	 * 설명 : 랜덤으로 UUID 생성한다. 
	 *        단, 숫자로 된 문자열(32 바이트)이며, 총 36개 십진수 문자열 중, '-' 문자는 제거하고 리턴한다. 
	 *
	 * Name : getUUID
	 * @return String
	 */
	public String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	public Double getDouble(String value) {
		try {
			return Double.valueOf(value);
			
		} catch (Exception e) {
			return null;
		}
	}
	
	public boolean numeric(String a, String b) {
		return a.equals(b);
	}
	
	/**
	 * 설명 : 쿠키값 반환
	 *
	 * @param request HttpServletRequest
	 * @return String
	 */
	public String getCookie(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();
		
		if(cookies == null) return null;
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals(cookieName)) return cookie.getValue();
		}
		return null;
	}
	
	/**
	 * 설명 : 쿠키값 삭제
	 *
	 * @param request HttpServletRequest
	 */
	public void deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
		Cookie[] cookies = request.getCookies();
		
		if(cookies != null && cookies.length > 0) {
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals(cookieName)) {
					cookie.setValue(null);
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
		}
	}
	
	/**
	 * 설명 : request로부터 파라미터 정보를 뽑아내고 맵에 담아 리턴한다.
	 *
	 * @param request HttpServletRequest
	 * @return Map<String, String>
	 */
	public Map<String, String> getParams(HttpServletRequest request) {
		Enumeration<String> parameterNames = request.getParameterNames();
		Map<String, String> paramMap = new HashMap<String, String>();
		
		while(parameterNames.hasMoreElements()) {
			String key = parameterNames.nextElement();
			paramMap.put(key, request.getParameter(key));
		}
		
		return paramMap;
	}	
	
	/**
	 * Map 에 있는 내용을 하나씩 뽑는다.
	 * @param parameterMap Map<String, Object>
	 */
	public String printMap(Map<String, Object> parameterMap) {
		StringBuilder sb = new StringBuilder();
		for (String key : parameterMap.keySet()) {
			Object obj = parameterMap.get(key);
			sb.append("key: ").append(key);
			
			if (obj == null) {
				sb.append(" value: value is null ");
			} else if(obj.getClass().equals(String.class)) {
				sb.append(" String value: ").append(obj);
			} else if(obj.getClass().equals(String[].class)) {
				String[] objArr = (String[]) obj;
				for (int i = 0; i < objArr.length; i++) {
					sb.append("String[] value[").append(i).append("] :").append(objArr[i]);
				}
			} else {
				// 추가해야함
				sb.append(" class [").append(obj.getClass()).append("] 미정의 :").append(obj);
			}
			sb.append(System.getProperty("line.separator"));
		}
		return sb.toString();
	}
	
	/**
	 * 주어진 인자의 값 Bit가 설정되어있는지를 리턴한다.
	 * @param val int
	 * @return boolean
	 */
	public boolean containsInTpBit(String inTpBit, int val) {
		if (stringUtil.isNullOrEmpty(inTpBit)) return false;
		int inTpBitVal = 0;
		try {
			inTpBitVal = Integer.parseInt(inTpBit);
		}
		catch (Exception ignored) {}
		
		return (inTpBitVal & val) == val; 
	}
	
	/**
	 * 값이 있는 데이터를 Map에 담는다.
	 * @param map Map<String, Object>
	 * @param paramName String
	 * @param data String
	 */
	public void putMap(Map<String, Object> map, String paramName, String data){
		if (!stringUtil.isNullOrEmpty(data)) {
			map.put(paramName, data);
		}
	}
	
	public String getMapString(Map<String, Object> map, String paramName){
		if (map.containsKey(paramName)) {
			return (String)map.get(paramName);
		}
		
		return null;
	}
	
	public String getMapIntToString(Map<String, Object> map, String paramName){
		if (map.containsKey(paramName)) {
			return Integer.toString((Integer)map.get(paramName));
		}
		
		return null;
	}
}
