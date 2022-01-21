package org.sqlTool.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.sqlTool.App;
import org.sqlTool.service.MySqlToolBizService;

import java.sql.SQLException;


@SpringBootTest(classes = App.class)
public class MySqlToolBizServiceImplTest {

  @Test
  public void query() {}

  @Test
  public void bizExecuteQuery() throws SQLException {
    MySqlToolBizServiceImpl mySqlToolBizService = new MySqlToolBizServiceImpl();
    String query = "select * from test";
    String hint = "/*+ USE_NL(u p a c) */";
    mySqlToolBizService.query(query, hint);
  }

  @Autowired private MySqlToolBizService mySqlToolBizService;

  @org.junit.jupiter.api.Test
  void testQuery() throws SQLException {

    String sql = "SELECT e.employee_id, j.job_id " +
        "FROM employees e INNER JOIN JOB_HISTORY jh on e.employee_id=jh.employee_id , JOBS j " +
        "WHERE e.employee_id=111200 AND jh.job_id=j.job_id " +
        "UNION " +
        "SELECT e.employee_id, j.job_id " +
        "FROM employees e, JOB_HISTORY jh, JOBS j " +
        "WHERE e.employee_id=111201 AND e.employee_id=jh.employee_id AND jh.job_id=j.job_id ";

    mySqlToolBizService.query(sql, "hintNL");

  }
}
