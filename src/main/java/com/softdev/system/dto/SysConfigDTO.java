package com.softdev.system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SysConfigDTO {

    @NotNull(message = "更新时ID不能为空", groups = Update.class)
    private Long id;

    @NotBlank(message = "参数键不能为空")
    @Size(max = 50, message = "参数键最长50字符")
    private String paramKey;

    @NotBlank(message = "参数值不能为空")
    @Size(max = 2000, message = "参数值最长2000字符")
    private String paramValue;

    private Integer status;

    @Size(max = 500, message = "备注最长500字符")
    private String remark;

    public interface Update {}
}
