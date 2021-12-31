package god.dag.common.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import god.dag.common.biz.CommonBizService;
import god.dag.common.mapper.CommonMapper;
import god.dag.common.vo.TBNS00A;
import god.dag.common.vo.TBNS01A;




@Service
public abstract class CommonBizServiceImpl implements CommonBizService {

	@Autowired private CommonMapper commonMapper;
	
	public int insertTBNS00A(TBNS00A vo) throws Exception{
		return commonMapper.insertTBNS00A(vo);
	}
	
	public int insertTBNS01A(TBNS01A vo) throws Exception{
		return commonMapper.insertTBNS01A(vo);
	}
	
	public int updateTBNS00A(TBNS00A vo) throws Exception{
		return commonMapper.updateTBNS00A(vo);
	}
}