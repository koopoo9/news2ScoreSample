package god.dag.common.rule;

import java.util.HashMap;
import java.util.Map;

import god.dag.common.constants.News2scoreConstants;

public class Rule1 {
	public Map<String, Object> excuteRule1(Map<String, Object> parameters){
		Map<String, Object> outParameters = new HashMap<>();
		int rsprtnRt = (Integer) parameters.get("rsprtnRt");
		int rsprtnRtScr = 0;
		String spo2SclCd = (String) parameters.get("spo2SclCd");
		int spo2Scl1Prct = 0;
		int spo2Scl2Prct = 0;
		if(spo2SclCd.equals(News2scoreConstants.spo2SclCode.spo2SclCode_00.getCode())) {
			spo2Scl1Prct = (Integer) parameters.get("spo2Scl1Prct");
		}else if (spo2SclCd.equals(News2scoreConstants.spo2SclCode.spo2SclCode_01.getCode())){
			spo2Scl2Prct = (Integer) parameters.get("spo2Scl2Prct");	
		}
		int spo2Scl1PrctScr = 0;
		int spo2Scl2PrctScr = 0;
		String airOxgnCd = (String) parameters.get("airOxgnCd");
		int airOxgnCdScr = 0;
		int systlcBldPrssr = (Integer) parameters.get("systlcBldPrssr");
		int systlcBldPrssrScr = 0;
		int pls = (Integer) parameters.get("pls");
		int plsScr = 0;
		String cnscsnss = (String) parameters.get("cnscsnss");
		int cnscsnssScr = 0;
		int tmpratr = (Integer) parameters.get("tmpratr");
		int tmpratrScr = 0;
		int rsltScr = 0;

		
		try {
			if(rsprtnRt <=8) {
				rsprtnRtScr = 3; 
			}else if(rsprtnRt >= 9 && rsprtnRt <= 11) {
				rsprtnRtScr = 1; 
			}else if(rsprtnRt >= 12 && rsprtnRt <= 20) {
				rsprtnRtScr = 0; 
			}else if(rsprtnRt >= 21 && rsprtnRt <= 24) {
				rsprtnRtScr = 2; 
			}else if(rsprtnRt >= 25) {
				rsprtnRtScr = 3; 
			}else {
				throw new Exception();
			}
			
			if(spo2SclCd.equals(News2scoreConstants.spo2SclCode.spo2SclCode_00.getCode())) {
				if(spo2Scl1Prct <=91) {
					spo2Scl1PrctScr =3;
				}else if(spo2Scl1Prct >=92 && spo2Scl1Prct <= 93) {
					spo2Scl1PrctScr =2;
				}else if(spo2Scl1Prct >=94 && spo2Scl1Prct <= 95) {
					spo2Scl1PrctScr =1;
				}else if(spo2Scl1Prct >=96) {
					spo2Scl1PrctScr =0;
				}
			}else if (spo2SclCd.equals(News2scoreConstants.spo2SclCode.spo2SclCode_01.getCode())){
				if(spo2Scl2Prct <=83) {
					spo2Scl2PrctScr =3;
				}else if(spo2Scl2Prct >=84 && spo2Scl2Prct <= 85) {
					spo2Scl2PrctScr =2;
				}else if(spo2Scl2Prct >=86 && spo2Scl2Prct <= 87) {
					spo2Scl2PrctScr =1;
				}else if((spo2Scl2Prct >=88 && spo2Scl2Prct <= 92) || (airOxgnCd.equals(News2scoreConstants.airOxgnCode.airOxgnCode_00.getCode())) ) {
					spo2Scl2PrctScr =0;
				}else if(airOxgnCd.equals(News2scoreConstants.airOxgnCode.airOxgnCode_01.getCode()) && (spo2Scl2Prct >=93 && spo2Scl2Prct <= 94)) {
					spo2Scl2PrctScr =1;
				}else if(airOxgnCd.equals(News2scoreConstants.airOxgnCode.airOxgnCode_01.getCode()) && (spo2Scl2Prct >=95 && spo2Scl2Prct <= 96)) {
					spo2Scl2PrctScr =2;
				}else if(airOxgnCd.equals(News2scoreConstants.airOxgnCode.airOxgnCode_01.getCode()) && (spo2Scl2Prct >=97)) {
					spo2Scl2PrctScr =3;
				}
			}	
			
			if(airOxgnCd.equals(News2scoreConstants.airOxgnCode.airOxgnCode_00.getCode())) {
				airOxgnCdScr = 2;
			}else if(airOxgnCd.equals(News2scoreConstants.airOxgnCode.airOxgnCode_01.getCode())) {
				airOxgnCdScr = 0;
			} 
			
			if(systlcBldPrssr <=90) {
				systlcBldPrssrScr =3;
			}else if(systlcBldPrssr >=91 && systlcBldPrssr <= 100) {
				systlcBldPrssrScr =2;
			}else if(systlcBldPrssr >=101 && systlcBldPrssr <= 110) {
				systlcBldPrssrScr =1;
			}else if(systlcBldPrssr >=111 && systlcBldPrssr <= 219) {
				systlcBldPrssrScr =0;
			}else if(systlcBldPrssr >=220) {
				systlcBldPrssrScr =3;
			}
			
			if(pls <=40) {
				plsScr =3;
			}else if(pls >=41 && pls <= 50) {
				plsScr =1;
			}else if(pls >=51 && pls <= 90) {
				plsScr =0;
			}else if(pls >=91 && pls <= 110) {
				plsScr =1;
			}else if(pls >=111 && pls <= 130) {
				plsScr =2;
			}else if(pls >=131) {
				plsScr =3;
			}
			
			if(cnscsnss.equals(News2scoreConstants.cnscsnssCode.cnscsnssCode_00.getCode())) {
				cnscsnssScr = 0;
			}else if(cnscsnss.equals(News2scoreConstants.cnscsnssCode.cnscsnssCode_01.getCode())) {
				cnscsnssScr = 3;
			} 
			
			if(tmpratr <=35.0) {
				tmpratrScr =3;
			}else if(tmpratr >=35.1 && tmpratr <= 36.0) {
				tmpratrScr =1;
			}else if(tmpratr >=36.1 && tmpratr <= 38.0) {
				tmpratrScr =0;
			}else if(tmpratr >=38.1 && tmpratr <= 39.0) {
				tmpratrScr =1;
			}else if(tmpratr >=39.1) {
				tmpratrScr =2;
			}
			
			if(spo2SclCd.equals(News2scoreConstants.spo2SclCode.spo2SclCode_00.getCode())) {
				rsltScr = rsprtnRtScr+spo2Scl1PrctScr+airOxgnCdScr+systlcBldPrssrScr+plsScr+cnscsnssScr+tmpratrScr;
			}else if (spo2SclCd.equals(News2scoreConstants.spo2SclCode.spo2SclCode_01.getCode())){
				rsltScr = rsprtnRtScr+spo2Scl2PrctScr+airOxgnCdScr+systlcBldPrssrScr+plsScr+cnscsnssScr+tmpratrScr;
			}
			
			outParameters.put("rsprtnRtScr", rsprtnRtScr);
			outParameters.put("spo2Scl1PrctScr", spo2Scl1PrctScr);
			outParameters.put("spo2Scl2PrctScr", spo2Scl2PrctScr);
			outParameters.put("airOxgnCdScr", airOxgnCdScr);
			outParameters.put("systlcBldPrssrScr", systlcBldPrssrScr);
			outParameters.put("plsScr", plsScr);
			outParameters.put("cnscsnssScr", cnscsnssScr);
			outParameters.put("tmpratrScr", tmpratrScr);
			outParameters.put("rsltScr", rsltScr);
		}catch(Exception e){
			e.printStackTrace();
		}
		return outParameters;
	}
}
