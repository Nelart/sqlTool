package org.sqlTool;

import static org.junit.Assert.assertTrue;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Unit test for simple App.
 */
@Slf4j
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        log.info("load jdbc configure successful!");

        String url = "jdbc:mysql://1.117.200.132:3306/mysite";
        String name = "root";
        String psw = "American94#";
        Connection con = DriverManager.getConnection(url,name,psw);
        log.info("connect database successful!");

        Statement sta = con.createStatement();
        log.info("create session");




        assertTrue( true );
    }
}
