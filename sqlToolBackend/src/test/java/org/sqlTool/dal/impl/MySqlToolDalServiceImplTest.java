package org.sqlTool.dal.impl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

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
        mySqlToolDalService.executeQuery(query, execNumber, omitFirstExec);
    }

    @Test
    public void executeQueryUpdate() throws SQLException {
        MySqlToolDalServiceImpl mySqlToolDalService = new MySqlToolDalServiceImpl();
        String query = "";
        String queryName = "update book set isbn=123 where id = 2";
        int execNumber = 3;
        boolean omitFirstExec = false;
        mySqlToolDalService.executeQuery(query, execNumber, omitFirstExec);
    }


    @Test
    public void explainPlanFor() throws Exception{
        MySqlToolDalServiceImpl mySqlToolDalService = new MySqlToolDalServiceImpl();
        mySqlToolDalService.explainPlanFor("select * from test");


    }
}