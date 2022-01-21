package org.sqlTool;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author Gunnel
 * @Date: 2021-08-18
 * @Time: 1:35 a.m.
 * @Description:
 */
@SpringBootTest
public class oracleTest {


    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public void contextLoads() throws SQLException {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from test");
        System.out.println(dataSource.getClass());
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        System.out.println(maps);
        connection.close();
    }

}
