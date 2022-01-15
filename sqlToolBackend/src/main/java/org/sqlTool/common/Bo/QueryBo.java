package org.sqlTool.common.Bo;

import lombok.Data;

/**
 * @author Gunnel
 * @Date: 2021/6/28
 * @Time: 3:55 PM
 * @Description:
 */

@Data
public class QueryBo {

    Boolean commandStatus;

    Double measuredTime;

    Long affectedRowNum;

    String query;

}
