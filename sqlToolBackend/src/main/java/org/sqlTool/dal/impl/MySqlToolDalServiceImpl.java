package org.sqlTool.dal.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sqlTool.Bean.ExplainPlanBean;
import org.sqlTool.Bean.QueryBean;
import org.sqlTool.dal.MySqlToolDalService;
import org.sqlTool.util.QueryHelper;
import org.sqlTool.util.SessionHelper;
import org.sqlTool.util.Timer;
import org.sqlTool.util.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


/**
 * @author Gunnel
 * @Date: 2021/6/25
 * @Time: 3:54 PM
 * @Description:
 */

@Slf4j
@Service
public class MySqlToolDalServiceImpl implements MySqlToolDalService {

    @Autowired Utils utils;
    /**
     * This is a method which executes the query
     *
     * @Param query is sql command
     * @Param queryName is the name of query
     * @Param execNumber is the number of execute the sql command
     * @Param
     * */

    @Override
    public double executeQuery(String query, String queryName, int execNumber, boolean omitFirstExec) throws SQLException {

        log.info("executeQuery(name=" + queryName  + ", execNumber=" + execNumber
                + ", omitFirstExec=" + omitFirstExec + "):\n" + query);

        Statement s = utils.getConnection().createStatement();

        Timer[] times = new Timer[execNumber];
        for (int i = 0; i < execNumber; i++) {
            Timer t = new Timer();
            s.execute(query);
            int updateCount = s.getUpdateCount();
            log.info("get affected row is :{}", updateCount);
            t.stop();
            times[i] = t;
            log.info("Pass [" + i + "] - " + t.getMiliSeconds());
        }

        s.close();

        int offset = omitFirstExec ? 1 : 0;
        int size = omitFirstExec ? execNumber - 1 : execNumber;
        double result = 0;
        for (int i = offset; i < execNumber; i++) {
            result += times[i].getMiliSeconds();
        }
        result = result / size;

        // save query info into history (in session)
        QueryBean q = new QueryBean();
        q.setName(queryName);
        q.setQuery(query);
        q.setExecutionTime(result);
        QueryHelper.saveQueryInfo(q);
        log.info("Average time: " + result + " [ms]");

        return result;
    }

    @Override
    public ArrayList<ExplainPlanBean> explainPlanFor(String query) throws Exception {

        ArrayList<ExplainPlanBean> planList = new ArrayList<ExplainPlanBean>();

        try {
            Statement s = utils.getConnection().createStatement();
            String sessionIdFragment =  SessionHelper.getSessionId().substring(0, 30);

            // clear plan_table data
            int affectedRows = s.executeUpdate("DELETE FROM plan_table WHERE statement_id = '" + sessionIdFragment + "'");
            log.info("Plan table for statement_id = '" + sessionIdFragment + "' cleaned, " + affectedRows + " rows affected");


           // execute explain plan
            s.execute("EXPLAIN PLAN SET statement_id =  '"+  sessionIdFragment +  "' FOR " + query); // many queries
            log.info("Explain plan executed successfully" );


            // get explain plan results
            ResultSet rs = s.executeQuery("SELECT  * FROM plan_table WHERE statement_id = '" + sessionIdFragment + "'");

            ExplainPlanBean plan = null;

            while (rs.next()){
                plan = new ExplainPlanBean();
                plan.setBytes(rs.getLong("bytes"));
                plan.setCardinality(rs.getLong("cardinality"));
                plan.setCost(rs.getLong("cost"));
                plan.setDepth(rs.getLong("depth"));
                plan.setId(rs.getLong("id"));
                plan.setObjectName(rs.getString("object_name"));
                plan.setOperation(rs.getString("operation"));
                plan.setOptions(rs.getString("options"));
                Long parentId = rs.getLong("parent_id");
                parentId = rs.wasNull() ? null : parentId;
                plan.setParentId(parentId);
                plan.setTempSpace(rs.getLong("temp_space"));
                plan.setTime(getDateFromMillis(rs.getLong("time")));
                planList.add(plan);
            }

            log.info("the size of planList is:{}", planList.size());

            rs.close();
            s.close();
            log.debug("List: " + planList);

        } catch (Exception e) {
            log.warn("Exception occured: ", e);
            throw e;
        }

        return planList;//ExplainPlanHelper.getPlan(planList);
    }

    public String getDateFromMillis(long seconds) {
        if(seconds == 0)
            return "";

        long HH = seconds / 3600;
        long MM = (seconds % 3600) / 60;
        long SS = seconds % 60;

        String timeInHHMMSS = String.format("%02d:%02d:%02d", HH, MM, SS);
        return timeInHHMMSS;
    }

}

