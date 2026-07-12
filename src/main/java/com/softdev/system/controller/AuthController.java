package com.softdev.system.controller;

import com.softdev.system.dto.LoginRequest;
import com.softdev.system.dto.RegisterRequest;
import com.softdev.system.entity.SysUser;
import com.softdev.system.security.JwtTokenProvider;
import com.softdev.system.service.SysUserService;
import com.softdev.system.util.ReturnUtil;
import com.softdev.system.vo.LoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "AuthController", description = "认证控制器")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public ReturnUtil<LoginVO> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        String token = jwtTokenProvider.generateToken(request.getUsername());
        return ReturnUtil.data(new LoginVO(token, request.getUsername()));
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public ReturnUtil<Void> register(@Valid @RequestBody RegisterRequest request) {
        SysUser user = new SysUser();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setNickname(request.getNickname());
        sysUserService.register(user);
        return ReturnUtil.success();
    }
}
