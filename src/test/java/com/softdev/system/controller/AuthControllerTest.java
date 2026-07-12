package com.softdev.system.controller;

import com.softdev.system.dto.LoginRequest;
import com.softdev.system.dto.RegisterRequest;
import com.softdev.system.entity.SysUser;
import com.softdev.system.security.JwtTokenProvider;
import com.softdev.system.service.SysUserService;
import com.softdev.system.util.ReturnUtil;
import com.softdev.system.vo.LoginVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private SysUserService sysUserService;

    @InjectMocks
    private AuthController authController;

    @Test
    void login_success() {
        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("admin123");

        SysUser mockUser = new SysUser();
        mockUser.setId(1L);
        mockUser.setUsername("admin");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mock(org.springframework.security.core.Authentication.class));
        when(jwtTokenProvider.generateToken("admin")).thenReturn("test-jwt-token");
        when(sysUserService.getByUsername("admin")).thenReturn(mockUser);

        ReturnUtil<LoginVO> result = authController.login(request);

        assertEquals(200, result.getCode());
        assertNotNull(result.getData());
        assertEquals("test-jwt-token", result.getData().getToken());
        assertEquals("admin", result.getData().getUsername());
        assertNotNull(result.getData().getStaticToken());
        verify(sysUserService).updateUser(any(SysUser.class));
    }

    @Test
    void login_invalidCredentials() {
        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("wrong");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        assertThrows(BadCredentialsException.class, () -> authController.login(request));
    }

    @Test
    void register_success() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("newuser");
        request.setPassword("password123");
        request.setNickname("New User");

        ReturnUtil<Void> result = authController.register(request);

        assertEquals(200, result.getCode());
        verify(sysUserService).register(any(SysUser.class));
    }

    @Test
    void verifyToken_valid() {
        SysUser user = new SysUser();
        user.setId(1L);
        user.setUsername("admin");
        user.setToken("abc123");
        user.setStatus(1);
        when(sysUserService.getByToken("abc123")).thenReturn(user);

        ReturnUtil<SysUser> result = authController.verifyToken("abc123");

        assertEquals(200, result.getCode());
        assertNotNull(result.getData());
        assertEquals("admin", result.getData().getUsername());
        assertNull(result.getData().getPassword());
    }

    @Test
    void verifyToken_invalid() {
        when(sysUserService.getByToken("invalid")).thenReturn(null);

        ReturnUtil<SysUser> result = authController.verifyToken("invalid");

        assertEquals(500, result.getCode());
        assertNull(result.getData());
    }

    @Test
    void verifyToken_disabledUser() {
        SysUser user = new SysUser();
        user.setId(1L);
        user.setUsername("admin");
        user.setStatus(0);
        when(sysUserService.getByToken("token123")).thenReturn(user);

        ReturnUtil<SysUser> result = authController.verifyToken("token123");

        assertEquals(500, result.getCode());
    }
}
