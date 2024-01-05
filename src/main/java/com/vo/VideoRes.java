package com.vo;

import lombok.Data;

/**
 * @Author Cc
 * @Date 2024-01-04
 */
@Data
public class VideoRes {
    private int errno;
    private Object data;

    public VideoRes(int i, Object videoVo) {
        this.errno = i;
        this.data = videoVo;
    }
}
