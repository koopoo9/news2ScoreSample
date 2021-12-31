package god.dag.common.mapper;

import org.apache.ibatis.annotations.Mapper;

import god.dag.common.vo.TBNS00A;
import god.dag.common.vo.TBNS01A;

import java.sql.SQLException;

/**
 * 설명 : 공통 DB 맵핑 인터페이스
 *
*/
@Mapper
public interface CommonMapper {
	
	int insertTBNS00A(TBNS00A vo) throws SQLException;
	
	int insertTBNS01A(TBNS01A vo) throws SQLException;
	
	int updateTBNS00A(TBNS00A vo) throws SQLException;
}
