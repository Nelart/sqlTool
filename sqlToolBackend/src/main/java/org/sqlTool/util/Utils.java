package org.sqlTool.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.sqlTool.config.JdbcConfig;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;

@Slf4j
@Component
public class Utils {

    @Resource
    DataSource dataSource;

    @Resource
    JdbcTemplate jdbcTemplate;

    @Resource
    JdbcConfig jdbcConfig;

    public Connection getConnection(){
        Connection con=null;
        try{
            Class.forName(jdbcConfig.getDriverClassName());  // Define driver
            con = DriverManager.getConnection(jdbcConfig.getUrl(), jdbcConfig.getUsername(), jdbcConfig.getPassword());  // Get connection of sql
            log.info("JDBC connect successfully!");
        }catch(Exception e){
            e.printStackTrace();
        }
        return con;  // return connect
    }
}
