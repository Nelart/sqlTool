package org.sqlTool.dal.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.sqlTool.service.impl.MySqlToolBizServiceImpl;

import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MySqlToolDalServiceImplTest {

    @Test
    public void query() {
        System.out.println("can test");
    }

    @Test
    public void executeQuery() throws SQLException {
        MySqlToolDalServiceImpl mySqlToolDalService = new MySqlToolDalServiceImpl();
        String query = "select * from test";
        String queryName = "select";
        int execNumber = 3;
        boolean omitFirstExec = false;
        mySqlToolDalService.executeQuery(query, queryName, execNumber, omitFirstExec);
    }

    @Test
    public void executeQueryUpdate() throws SQLException {
        MySqlToolDalServiceImpl mySqlToolDalService = new MySqlToolDalServiceImpl();
        String query = "";
        String queryName = "update book set isbn=123 where id = 2";
        int execNumber = 3;
        boolean omitFirstExec = false;
        mySqlToolDalService.executeQuery(query, queryName, execNumber, omitFirstExec);
    }


    @Test
    public void explainPlanFor() throws Exception{
        MySqlToolDalServiceImpl mySqlToolDalService = new MySqlToolDalServiceImpl();
        mySqlToolDalService.explainPlanFor("select * from test");


    }
}