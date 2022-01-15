package org.sqlTool.common;

import lombok.Data;

/**
 * @author Gunnel
 * @Date: 2021/6/25
 * @Time: 3:46 PM
 * @Description:
 */

@Data
public class BaseResponse {

    private int code;
    private String msg;
    private Object data;

    public BaseResponse() {
        this.msg = "ok";
    }
}
