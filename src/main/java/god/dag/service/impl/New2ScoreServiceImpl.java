package god.dag.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import god.dag.common.biz.CommonBizService;
import god.dag.common.constants.News2scoreConstants;
import god.dag.common.rule.Rule1;
import god.dag.common.util.CommonUtil;
import god.dag.common.vo.News2ScoreReqVO;
import god.dag.common.vo.News2ScoreResVO;
import god.dag.common.vo.TBNS00A;
import god.dag.common.vo.TBNS01A;
import god.dag.common.vo.TBNS02A;
import god.dag.service.News2ScoreService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
public abstract class New2ScoreServiceImpl implements News2ScoreService {
	@Autowired private Rule1 rule1;
	@Autowired private CommonBizService commonBiz;
    public News2ScoreResVO checkPatient(News2ScoreReqVO news2ScoreReqVO) throws Exception {
    	Map<String, Object> parameters = new HashMap<>();
    	Map<String, Object> resultParameters = new HashMap<>();
    	News2ScoreResVO news2ScoreResVO = new News2ScoreResVO();
    	
    	try {
    		//vaildate input data    		
    		//vaildate(news2ScoreReqVO, errors);
    		UUID tx = UUID.randomUUID();
    		//get and set data
    		parameters.put("custId", news2ScoreReqVO.getCustId());
    		parameters.put("tx_id", tx.toString());
    		parameters.put("rsprtnRt", news2ScoreReqVO.getRsprtnRt());
    		parameters.put("spo2SclCd", news2ScoreReqVO.getSpo2SclCd());
    		parameters.put("spo2Scl1Prct", news2ScoreReqVO.getSpo2Scl1Prct());
    		parameters.put("spo2Scl2Prct", news2ScoreReqVO.getSpo2Scl2Prct());
    		parameters.put("airOxgnCd", news2ScoreReqVO.getAirOxgnCd());
    		parameters.put("systlcBldPrssr", news2ScoreReqVO.getSystlcBldPrssr());
    		parameters.put("pls", news2ScoreReqVO.getPls());
    		parameters.put("cnscsnss", news2ScoreReqVO.getCnscsnss());
    		parameters.put("tmpratr", news2ScoreReqVO.getTmpratr());
    		
    		//excute rule
    		resultParameters = rule1.excuteRule1(parameters);
    		
    		try {
    			//insertTBNS01A
    			TBNS01A tbns01aVo = new TBNS01A(); 
    			tbns01aVo.setCustId(news2ScoreReqVO.getCustId());
    			tbns01aVo.setTxId(tx.toString());
    			tbns01aVo.setRsprtnRt(news2ScoreReqVO.getRsprtnRt());
    			tbns01aVo.setSpo2SclCd(news2ScoreReqVO.getSpo2SclCd());
    			tbns01aVo.setSpo2Scl1Prct(news2ScoreReqVO.getSpo2Scl1Prct());
    			tbns01aVo.setSpo2Scl2Prct(news2ScoreReqVO.getSpo2Scl2Prct());
    			tbns01aVo.setAirOxgnCd(news2ScoreReqVO.getAirOxgnCd());
    			tbns01aVo.setSystlcBldPrssr(news2ScoreReqVO.getSystlcBldPrssr());
    			tbns01aVo.setPls(news2ScoreReqVO.getPls());
    			tbns01aVo.setCnscsnss(news2ScoreReqVO.getCnscsnss());
    			tbns01aVo.setTmpratr(news2ScoreReqVO.getTmpratr());
    			commonBiz.insertTBNS01A(tbns01aVo);
    			
    		}catch (Exception e) {
    			news2ScoreResVO.setResult(News2scoreConstants.responseCode.RSLT_CD_B099.getCode(), News2scoreConstants.responseCode.RSLT_CD_B099.getMsg());
			}
    		
    		try {
    			//insertTBNS02A
    			TBNS02A tbns02aVo = new TBNS02A(); 
    			tbns02aVo.setCustId(news2ScoreReqVO.getCustId());
    			tbns02aVo.setTxId(tx.toString());
    			tbns02aVo.setScrCd("00");
    			tbns02aVo.setRsprtnRtScr((String)resultParameters.get("rsprtnRtScr"));
    			tbns02aVo.setSpo2Scl1PrctScr((String)resultParameters.get("spo2Scl1PrctScr"));
    			tbns02aVo.setSpo2Scl2PrctScr((String)resultParameters.get("spo2Scl2PrctScr"));
    			tbns02aVo.setAirOxgnCdScr((String)resultParameters.get("airOxgnCdScr"));
    			tbns02aVo.setSystlcBldPrssrScr((String)resultParameters.get("systlcBldPrssrScr"));
    			tbns02aVo.setPlsScr((String)resultParameters.get("plsScr"));
    			tbns02aVo.setTmpratrScr((String)resultParameters.get("tmpratrScr"));
    			tbns02aVo.setCnscsnssScr((String)resultParameters.get("cnscsnssScr"));
    			tbns02aVo.setRsltScr((String)resultParameters.get("rsltScr"));
    			
    			commonBiz.insertTBNS02A(tbns02aVo);
    		}catch (Exception e) {
    			news2ScoreResVO.setResult(News2scoreConstants.responseCode.RSLT_CD_B099.getCode(), News2scoreConstants.responseCode.RSLT_CD_B099.getMsg());
			}
    		
    		try {
    			//updateTBNS00A
    			TBNS00A tbns00aVo = new TBNS00A(); 
    			tbns00aVo.setCustId(news2ScoreReqVO.getCustId());
    			tbns00aVo.setLstScore((String)resultParameters.get("rsltScr"));
    			tbns00aVo.setLstTxId(tx.toString());
    			
    			commonBiz.updateTBNS00A(tbns00aVo);
    		}catch (Exception e) {
    			news2ScoreResVO.setResult(News2scoreConstants.responseCode.RSLT_CD_B099.getCode(), News2scoreConstants.responseCode.RSLT_CD_B099.getMsg());
			}
    		
    		news2ScoreResVO.setResult(News2scoreConstants.responseCode.RSLT_CD_B000.getCode(), News2scoreConstants.responseCode.RSLT_CD_B000.getMsg());
    	}catch (Exception e) {
    		news2ScoreResVO.setResult(News2scoreConstants.responseCode.RSLT_CD_B099.getCode(), News2scoreConstants.responseCode.RSLT_CD_B099.getMsg());
		}
    	return news2ScoreResVO;
    }
}