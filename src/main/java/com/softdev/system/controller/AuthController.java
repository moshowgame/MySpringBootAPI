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
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
        String jwtToken = jwtTokenProvider.generateToken(request.getUsername());

        // generate and save a static token for the user
        String staticToken = UUID.randomUUID().toString().replace("-", "");
        SysUser user = sysUserService.getByUsername(request.getUsername());
        user.setToken(staticToken);
        sysUserService.updateUser(user);

        return ReturnUtil.data(new LoginVO(jwtToken, request.getUsername(), staticToken));
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

    @GetMapping("/verify")
    @Operation(summary = "Token验证（公开接口）")
    public ReturnUtil<SysUser> verifyToken(@RequestParam String token) {
        SysUser user = sysUserService.getByToken(token);
        if (user == null) {
            return ReturnUtil.define(500, "无效的token", null);
        }
        if (user.getStatus() != null && user.getStatus() == 0) {
            return ReturnUtil.define(500, "用户已禁用", null);
        }
        user.setPassword(null);
        return ReturnUtil.data(user);
    }
}
