package org.sqlTool.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.sqlTool.Bean.ExplainPlanBean;
import org.sqlTool.common.BaseResponse;
import org.sqlTool.common.Bo.QueryBo;
import org.sqlTool.common.Dto.QuerySqlCommandDto;
import org.sqlTool.service.MySqlToolBizService;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author Gunnel
 * @Date: 2021/6/24
 * @Time: 10:59 PM
 * @Description:
 */
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/sqlTools/api")
public class CommandController {

    @Resource
    MySqlToolBizService mySqlToolBizService;

    @PostMapping("/query")
    public BaseResponse query(@RequestBody QuerySqlCommandDto querySqlCommandDto) throws Exception {
        String sqlCommand = querySqlCommandDto.getSqlCommand();
        String hint = querySqlCommandDto.getHint();
        log.info("sql command from query request is:{}, hint is:{}", sqlCommand, hint);
        BaseResponse response = new BaseResponse();
        // make exception or error in a better view
        try{
            QueryBo query = mySqlToolBizService.query(sqlCommand, hint);
            log.info(" the query time is :{}", query.getMeasuredTime());
            response.setData(query);
        }catch (Exception ex){
            log.info(ex.toString());
            response.setMsg(ex.toString());
        }

        return response;
    }

    @PostMapping("/explainPlan")
    public BaseResponse explainPlan(@RequestBody QuerySqlCommandDto querySqlCommandDto) throws Exception {
        String query = querySqlCommandDto.getSqlCommand();
        log.info("query of explainPlan is:{}", query);
        BaseResponse response = new BaseResponse();
        try{
            ArrayList<ExplainPlanBean> planBeans = mySqlToolBizService.explainPlan(query);
            log.info("result of explain plan is:{}", planBeans);
            response.setData(planBeans);
        }catch (Exception ex){
            log.info(ex.toString());
            response.setMsg(ex.toString());
        }

        return response;
    }

}
