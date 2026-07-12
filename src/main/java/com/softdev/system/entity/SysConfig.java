package com.softdev.system.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Schema(name = "SysConfig", title = "系统配置")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Data
public class SysConfig implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "配置ID")
    private Long id;

    private String paramKey;

    private String paramValue;

    private Integer status;

    private String remark;
}
