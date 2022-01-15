package org.sqlTool.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author Gunnel
 * @Date: 2021/6/25
 * @Time: 9:19 PM
 * @Description:
 */
@Slf4j
public class Timer{

    private long millis;

    private long lastEvent;

    private Date date = new Date();

    private int timeZoneOffset = 0;

    public Timer() {
        Timer();
    }
    /**
     * local
     * */

    public synchronized void Timer() {
        this.lastEvent =  System.currentTimeMillis() + timeZoneOffset * 60000;
    }

    public synchronized void stop() {
        final long thisEvent = System.currentTimeMillis() + timeZoneOffset * 60000;
        this.millis += thisEvent - this.lastEvent;
        this.lastEvent = thisEvent;

    }

    public double getMiliSeconds() {
        return this.millis;
    }

    public int getSeconds() {
        return (int) (this.getMiliSeconds() / 1000);
    }

    public int getMinutes() {
        return this.getSeconds() / 60;
    }


}
