package org.sqlTool.Bean;

import lombok.Data;

/**
 * @author Gunnel
 * @Date: 2021/6/25
 * @Time: 8:56 PM
 * @Description:
 */
@Data
public class QueryBean {

    private String Name;
    private String query;
    private Double ExecutionTime;
}
