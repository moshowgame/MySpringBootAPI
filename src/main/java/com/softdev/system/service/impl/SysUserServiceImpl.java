package com.softdev.system.service.impl;

import com.softdev.system.entity.SysUser;
import com.softdev.system.exception.BusinessException;
import com.softdev.system.mapper.SysUserMapper;
import com.softdev.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public SysUser getByUsername(String username) {
        return sysUserMapper.selectByUsername(username);
    }

    @Override
    public SysUser getById(Long id) {
        return sysUserMapper.selectById(id);
    }

    @Override
    public SysUser getByToken(String token) {
        return sysUserMapper.selectByToken(token);
    }

    @Override
    @Transactional
    public void updateUser(SysUser user) {
        sysUserMapper.updateById(user);
    }

    @Override
    @Transactional
    public void register(SysUser user) {
        SysUser existing = sysUserMapper.selectByUsername(user.getUsername());
        if (existing != null) {
            throw new BusinessException("用户名已存在");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(1);
        sysUserMapper.insert(user);
    }
}
