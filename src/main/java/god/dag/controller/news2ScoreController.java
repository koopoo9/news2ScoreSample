package god.dag.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import god.dag.common.vo.News2ScoreReqVO;
import god.dag.common.vo.News2ScoreResVO;
import god.dag.service.MbrComService;
import god.dag.service.News2ScoreService;
import god.dag.service.impl.New2ScoreServiceImpl;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/news2-score")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class news2ScoreController {
    @Autowired
    private New2ScoreServiceImpl new2ScoreServiceImpl;

    /**
     *
     * @param param client로 부터 전달받은 Request 객체
     * @return json 타입의 처리 결과 String
     * @throws Exception 예외
     */
    @RequestMapping(value ="/check-patient", method = RequestMethod.POST)
    public News2ScoreResVO checkPatient(@RequestBody News2ScoreReqVO news2ScoreReqVO) throws Exception {
    
    	return new2ScoreServiceImpl.checkPatient(news2ScoreReqVO);
    	
    }
}
