package org.sqlTool.Bean;

import lombok.Data;

/**
 * @author Gunnel
 * @Date: 2021-08-17
 * @Time: 8:07 a.m.
 * @Description:
 */
@Data
public class ExplainPlanBean {

    private Long bytes;
    private Long cardinality;
    private Long cost;
    private Long depth;
    private Long id;
    private String objectName;
    private String operation;
    private String options;
    private Long parentId;
    private Long tempSpace;
    private Long time;



}
