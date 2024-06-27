package com.softdev.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Data
public class SysConfig  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * configId
     */
    @TableId(type = IdType.AUTO)
    Integer id;
    String paramKey;
    String paramValue;
    Integer status;
    String remark;
}
