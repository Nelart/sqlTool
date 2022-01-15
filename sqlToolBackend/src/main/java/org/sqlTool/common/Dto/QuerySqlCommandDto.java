package org.sqlTool.common.Dto;

import lombok.Data;

/**
 * @author Gunnel
 * @Date: 2021/6/28
 * @Time: 2:35 PM
 * @Description:
 */

@Data
public class QuerySqlCommandDto {

    /**
     * sql command from query request
     * */
    private String sqlCommand;
    private String hint;
}
