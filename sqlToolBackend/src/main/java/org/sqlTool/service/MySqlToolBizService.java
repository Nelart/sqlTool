package org.sqlTool.service;

import org.sqlTool.Bean.ExplainPlanBean;
import org.sqlTool.common.Bo.QueryBo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author Gunnel
 * @Date: 2021/6/25
 * @Time: 3:52 PM
 * @Description:
 */


public interface MySqlToolBizService {

     public QueryBo query(String sqlCommand, String hint) throws SQLException;

     public ArrayList<ExplainPlanBean> explainPlan(String query) throws Exception;


     public static void main(String[] args) {
          HashSet<Integer> xSet = new HashSet<>();
          HashSet<Integer> ySet = new HashSet<>();

     }
}
