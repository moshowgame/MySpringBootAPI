package com.softdev.system.vo;

import lombok.Data;

@Data
public class SysConfigVO {
    private Long id;
    private String paramKey;
    private String paramValue;
    private Integer status;
    private String remark;
}
