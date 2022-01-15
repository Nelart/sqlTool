package org.sqlTool.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.sqlTool.Bean.ExplainPlanBean;
import org.sqlTool.common.Bo.QueryBo;
import org.sqlTool.dal.MySqlToolDalService;
import org.sqlTool.service.MySqlToolBizService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Gunnel
 * @Date: 2021/6/25
 * @Time: 3:52 PM
 * @Description:
 */
@Slf4j
@Service
public class MySqlToolBizServiceImpl implements MySqlToolBizService {

    @Resource
    MySqlToolDalService mySqlToolDalService;

    @Override
    public QueryBo query(String sqlCommand, String hint) throws SQLException {
        String queryName = sqlCommand.substring(0, sqlCommand.indexOf(' '));
        if(!hint.isEmpty()){
            switch (hint){
                case "hintNL":
                    hint = " /*+ USE_NL(u p a c) */ ";
                    break;
                case "hintHASH":
                    hint = " /*+ USE_HASH(u p a c) */ ";
                    break;
                case "hintMERGE":
                    hint = "/*+ USE_MERGE(u p a c) */";
                    break;
                default:
                    hint = "";
            }
            String queryLast = sqlCommand.substring(sqlCommand.indexOf(' ')+ 1);
            sqlCommand = queryName + hint + queryLast;
        }
        double queryTime = mySqlToolDalService.executeQuery(sqlCommand, queryName, 3, false);
        Boolean isSucc = true;
        Long affectRow = 0L;
        if(queryTime < 0){
            isSucc = false;
            affectRow = -1L;
        }

        double query6digit = BigDecimal.valueOf(queryTime).setScale(2, RoundingMode.HALF_UP).doubleValue();
        log.info("6 digits of the query time :{}" ,query6digit);


        QueryBo queryBo = new QueryBo();
        queryBo.setMeasuredTime(query6digit);
        queryBo.setCommandStatus(isSucc);
        queryBo.setAffectedRowNum(affectRow);
        queryBo.setQuery(sqlCommand);
        return queryBo;
    }

    @Override
    public ArrayList<ExplainPlanBean> explainPlan(String query) throws Exception {
        return mySqlToolDalService.explainPlanFor(query);
    }
}
