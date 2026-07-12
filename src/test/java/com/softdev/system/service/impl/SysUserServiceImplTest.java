package com.softdev.system.service.impl;

import com.softdev.system.entity.SysUser;
import com.softdev.system.exception.BusinessException;
import com.softdev.system.mapper.SysUserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SysUserServiceImplTest {

    @Mock
    private SysUserMapper sysUserMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private SysUserServiceImpl sysUserService;

    @Test
    void register_success() {
        SysUser user = new SysUser();
        user.setUsername("newuser");
        user.setPassword("password123");

        when(sysUserMapper.selectByUsername("newuser")).thenReturn(null);
        when(passwordEncoder.encode("password123")).thenReturn("$2a$10$encoded");
        when(sysUserMapper.insert(any(SysUser.class))).thenReturn(1);

        assertDoesNotThrow(() -> sysUserService.register(user));
        verify(passwordEncoder).encode("password123");
        verify(sysUserMapper).insert(any(SysUser.class));
    }

    @Test
    void register_duplicateUsername() {
        SysUser existing = new SysUser();
        existing.setUsername("admin");

        SysUser user = new SysUser();
        user.setUsername("admin");
        user.setPassword("password123");

        when(sysUserMapper.selectByUsername("admin")).thenReturn(existing);

        assertThrows(BusinessException.class, () -> sysUserService.register(user));
        verify(sysUserMapper, never()).insert(any());
    }

    @Test
    void getByUsername() {
        SysUser user = new SysUser();
        user.setId(1L);
        user.setUsername("admin");
        when(sysUserMapper.selectByUsername("admin")).thenReturn(user);

        SysUser result = sysUserService.getByUsername("admin");

        assertNotNull(result);
        assertEquals("admin", result.getUsername());
    }

    @Test
    void getById() {
        SysUser user = new SysUser();
        user.setId(1L);
        user.setUsername("admin");
        when(sysUserMapper.selectById(1L)).thenReturn(user);

        SysUser result = sysUserService.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }
}
