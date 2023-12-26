package com.vo;

/**
 * layui 分页对象
 *
 * @Author zqx
 * @Date 2023-11-20
 */
public class PageVo extends ResponseData {
    /**
     * 当前查询的总记录数
     */
    private Long count;

    public PageVo() {

    }

    public PageVo(Long count) {
        this.count = count;
    }

    public PageVo(Integer code, String msg, Object data, Long count) {
        super(code, msg, data);
        this.count = count;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
