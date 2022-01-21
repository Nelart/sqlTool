package org.sqlTool.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.sqlTool.Bean.ExplainPlanBean;
import org.sqlTool.common.Bo.QueryBo;
import org.sqlTool.dal.MySqlToolDalService;
import org.sqlTool.service.MySqlToolBizService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;

/** @author Gunnel @Date: 2021/6/25 @Time: 3:52 PM @Description: */
@Slf4j
@Service
public class MySqlToolBizServiceImpl implements MySqlToolBizService {

  @Resource MySqlToolDalService mySqlToolDalService;

  @Override
  public QueryBo query(String sqlCommand, String hint) throws SQLException {

    String resultSqlCommand = restructureScript(sqlCommand, hint);

    double queryTime = mySqlToolDalService.executeQuery(resultSqlCommand, 10, false);
    Boolean isSucc = true;
    Long affectRow = 0L;
    if (queryTime < 0) {
      isSucc = false;
      affectRow = -1L;
    }

    double query6digit =
        BigDecimal.valueOf(queryTime).setScale(2, RoundingMode.HALF_UP).doubleValue();
    log.info("6 digits of the query time :{}", query6digit);

    QueryBo queryBo = new QueryBo();
    queryBo.setMeasuredTime(query6digit);
    queryBo.setCommandStatus(isSucc);
    queryBo.setAffectedRowNum(affectRow);
    queryBo.setQuery(resultSqlCommand);
    return queryBo;
  }

  @Override
  public ArrayList<ExplainPlanBean> explainPlan(String query) throws Exception {
    return mySqlToolDalService.explainPlanFor(query);
  }

  public String restructureScript(String sqlCommand, String hint) {
    if (!StringUtils.hasLength(hint))
      return sqlCommand;

    String[] unionSplits = sqlCommand.split("(?i)union");
    boolean hasUnion = unionSplits.length > 1;

    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < unionSplits.length; i++) {
      builder.append(addHintsForOneScript(unionSplits[i].trim(), hint));
      if (hasUnion && i < unionSplits.length - 1)
        builder.append(" union ");
    }

    return builder.toString();
  }

  private String addHintsForOneScript(String sqlCommand, String hint) {

    int cutSelect = sqlCommand.toLowerCase().indexOf("select") + 6;

    String queryName = sqlCommand.substring(0, cutSelect);

    String tableAlias = getTableAlias(sqlCommand);

    if (!hint.isEmpty()) {
      switch (hint) {
        case "hintNL":
          hint = " /*+ USE_NL(" + tableAlias + ") */ ";
          break;
        case "hintHASH":
          hint = " /*+ USE_HASH(" + tableAlias + ") */ ";
          break;
        case "hintMERGE":
          hint = "/*+ USE_MERGE(" + tableAlias + ") */";
          break;
        default:
          hint = "";
      }
      String queryLast = sqlCommand.substring(cutSelect + 1);
      return queryName + hint + queryLast;
    }
    return sqlCommand;
  }

  private String getTableAlias(String sqlCommand) {
    String result = "";

    String lowerCase = sqlCommand.toLowerCase();
    int fromIndex = lowerCase.indexOf("from");
    int whereIndex = lowerCase.indexOf("where");

    if (fromIndex < 0) return result;
    if (whereIndex < 0) {
      whereIndex = sqlCommand.length() - 1;
    }

    // employees u, job_history p, departments a
    // employees e INNER JOIN JOB_HISTORY jh on e.employee_id=jh.employee_id , JOBS j
    String tables = sqlCommand.substring(fromIndex + 5, whereIndex);

    String[] splits = tables.trim().split(",|(?i)join");
    for (int i = 0; i < splits.length; i++) {
      String[] subs = splits[i].trim().split("\\s+");
      if (subs.length == 1) {
        result += subs[0];
      } else if (subs.length > 1) {
        result += subs[1].equalsIgnoreCase("on")?subs[0]:subs[1];
      }

      if (i < splits.length - 1) result += " ";
    }

    return result;
  }
}
