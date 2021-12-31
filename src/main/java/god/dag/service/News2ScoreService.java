package god.dag.service;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import god.dag.common.vo.News2ScoreResVO;

@Mapper
@Repository
public interface News2ScoreService {
	
	News2ScoreResVO checkPatient(Map<String, Object> paramMap)  throws Exception;
}
