package com.softdev.system.vo;

import lombok.Data;

@Data
public class UserVerifyVO {
    private Long id;
    private String username;
    private String nickname;
    private String email;
    private Integer status;
}
