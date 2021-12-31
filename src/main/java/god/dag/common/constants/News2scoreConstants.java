package god.dag.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class News2scoreConstants {

    public static final int RESULT_SUCCESS = 1; // 
    public News2scoreConstants() {
    	super();
    }
	public enum responseCode {
		RSLT_CD_B000("B000", "Succes"),
		RSLT_CD_B099("B099", "Fail");
		private String code;
		private String msg;
		
		public void setCode(String code) {
			this.code = code;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		
		public String getCode() {
			return this.code;
		}
		public String getMsg() {
			return this.msg;
		}
	}
	
	
	@Getter
	public enum spo2SclCode {
		spo2SclCode_00("00", "spo2Scl1Prct"),
		spo2SclCode_01("01", "spo2Scl2Prct");
		
		private String code;
		
		public String getCode() {
			return this.code;
		}
		public void setCode(String code) {
			this.code = code;
		}
	}
	
	@Getter
	public enum airOxgnCode {
		airOxgnCode_00("00", "air"),
		airOxgnCode_01("01", "Oxygen");
		
		private String code;
		
		public String getCode() {
			return this.code;
		}
		public void setCode(String code) {
			this.code = code;
		}
	}
	
	@Getter
	public enum cnscsnssCode {
		cnscsnssCode_00("00", "Alert"),
		cnscsnssCode_01("01", "CYPU");
		
		private String code;
		
		public String getCode() {
			return this.code;
		}
		public void setCode(String code) {
			this.code = code;
		}
	}
	
}