package org.sqlTool.util;


import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;


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