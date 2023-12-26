package com.vo;

/**
 * 统一响应对象 - 把响应的数据、状态码、消息封装在一起，便于与前端人员沟通
 *
 * @Author zqx
 * @Date 2023-11-16
 */
public class ResponseData {
    /**
     * 响应状态码 - 便于前端人员作判断
     */
    private Integer code ;

    /**
     * 响应消息 - 给前端提示内容
     */
    private String msg ;

    /**
     * 响应数据 - 页面回显的具体数据
     */
    private Object data ;

    public ResponseData() {
    }

    public ResponseData(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
