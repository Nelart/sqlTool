package org.sqlTool.dal;

import org.sqlTool.Bean.ExplainPlanBean;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Gunnel
 * @Date: 2021/6/25
 * @Time: 3:53 PM
 * @Description:
 */

public interface MySqlToolDalService {

    public double executeQuery(String query, String queryName, int execNumber, boolean omitFirstExec) throws SQLException;


    public ArrayList<ExplainPlanBean> explainPlanFor(String query) throws Exception;


}
