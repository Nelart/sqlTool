package org.sqlTool.util;

import org.junit.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;



public class TimerTest {

    private Date date = new Date();

    private int timeZoneOffset = 0;


    @Test
    public void timer() throws InterruptedException {
        Timer timer = new Timer();
        TimeUnit.SECONDS.sleep(10);
        timer.stop();
        System.out.println(timer.getMiliSeconds());

    }
}