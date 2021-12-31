package god.dag.common.biz;

import god.dag.common.vo.TBNS00A;
import god.dag.common.vo.TBNS01A;
import god.dag.common.vo.TBNS02A;


public interface CommonBizService {
	
	/**
	 * desc : insert TBNS01A  
	 *
	 * @Method Name : inserTBNS01A
	 * @param vo TBNS01AVO
	 * @return int
	 * @throws Exception SQLException
	 */
	int insertTBNS01A(TBNS01A vo) throws Exception;
	
	/**
	 * desc : insert TBNS02A  
	 *
	 * @Method Name : inserTBNS02A
	 * @param vo TBNS02AVO
	 * @return int
	 * @throws Exception SQLException
	 */
	int insertTBNS02A(TBNS02A vo) throws Exception;
	
	/**
	 * desc : update TBNS00A  
	 *
	 * @Method Name : inserTBNS00A
	 * @param vo TBNS00AVO
	 * @return int
	 * @throws Exception SQLException
	 */
	int updateTBNS00A(TBNS00A vo) throws Exception;
}
