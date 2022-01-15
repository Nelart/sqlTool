package org.sqlTool.util;

import org.sqlTool.Bean.ExplainPlanBean;

import java.util.ArrayList;

/**
 * @author Gunnel
 * @Date: 2021-08-18
 * @Time: 2:20 p.m.
 * @Description:
 */

public class ExplainPlanHelper {


    public static ExplainPlanBean getPlan(ArrayList<ExplainPlanBean> planList){
        for(ExplainPlanBean planBean : planList){
            return planBean;
        }
        return null;
    }
}
