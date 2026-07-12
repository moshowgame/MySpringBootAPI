package com.softdev.system.controller;

import com.softdev.system.dto.SysConfigDTO;
import com.softdev.system.entity.SysConfig;
import com.softdev.system.service.SysConfigService;
import com.softdev.system.util.ReturnUtil;
import com.softdev.system.vo.SysConfigVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "SysConfigController", description = "系统配置控制器")
@RestController
@RequestMapping("/config")
public class SysConfigController {

    private final SysConfigService sysConfigService;

    public SysConfigController(SysConfigService sysConfigService) {
        this.sysConfigService = sysConfigService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询配置")
    public ReturnUtil<SysConfigVO> getById(@PathVariable Long id) {
        return ReturnUtil.data(sysConfigService.getById(id));
    }

    @GetMapping("/list")
    @Operation(summary = "查询所有配置")
    public ReturnUtil<List<SysConfigVO>> list() {
        return ReturnUtil.data(sysConfigService.list());
    }

    @GetMapping("/key/{paramKey}")
    @Operation(summary = "根据key查询配置")
    public ReturnUtil<SysConfigVO> getByKey(@PathVariable String paramKey) {
        return ReturnUtil.data(sysConfigService.getByParamKey(paramKey));
    }

    @PostMapping
    @Operation(summary = "新增配置")
    public ReturnUtil<Void> create(@Valid @RequestBody SysConfigDTO dto) {
        SysConfig config = new SysConfig();
        config.setParamKey(dto.getParamKey());
        config.setParamValue(dto.getParamValue());
        config.setStatus(dto.getStatus());
        config.setRemark(dto.getRemark());
        sysConfigService.create(config);
        return ReturnUtil.success();
    }

    @PutMapping
    @Operation(summary = "更新配置")
    public ReturnUtil<Void> update(@Validated(SysConfigDTO.Update.class) @RequestBody SysConfigDTO dto) {
        SysConfig config = new SysConfig();
        config.setId(dto.getId());
        config.setParamKey(dto.getParamKey());
        config.setParamValue(dto.getParamValue());
        config.setStatus(dto.getStatus());
        config.setRemark(dto.getRemark());
        sysConfigService.update(config);
        return ReturnUtil.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除配置")
    public ReturnUtil<Void> delete(@PathVariable Long id) {
        sysConfigService.deleteById(id);
        return ReturnUtil.success();
    }
}
