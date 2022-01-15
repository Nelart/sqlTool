package org.sqlTool.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.sqlTool.App;

import java.sql.SQLException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class MySqlToolBizServiceImplTest {

    @Test
    public void query() {
    }

    @Test
    public void bizExecuteQuery() throws SQLException {
        MySqlToolBizServiceImpl mySqlToolBizService = new MySqlToolBizServiceImpl();
        String query = "select * from test";
        String hint = "/*+ USE_NL(u p a c) */";
        mySqlToolBizService.query(query, hint);
    }

}