package com.softdev.system.service;

import com.softdev.system.entity.SysUser;

public interface SysUserService {

    SysUser getByUsername(String username);

    SysUser getById(Long id);

    void register(SysUser user);
}
