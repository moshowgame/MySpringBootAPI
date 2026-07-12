package com.softdev.system.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String token;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
