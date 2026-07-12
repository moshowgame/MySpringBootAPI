package com.softdev.system.service;

import com.softdev.system.entity.SysUser;

public interface SysUserService {

    SysUser getByUsername(String username);

    SysUser getById(Long id);

    SysUser getByToken(String token);

    void updateUser(SysUser user);

    void register(SysUser user);
}
