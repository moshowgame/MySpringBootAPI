package com.softdev.system.service.impl;

import com.softdev.system.entity.SysConfig;
import com.softdev.system.exception.BusinessException;
import com.softdev.system.mapper.SysConfigMapper;
import com.softdev.system.service.SysConfigService;
import com.softdev.system.vo.SysConfigVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysConfigServiceImpl implements SysConfigService {

    private final SysConfigMapper sysConfigMapper;

    public SysConfigServiceImpl(SysConfigMapper sysConfigMapper) {
        this.sysConfigMapper = sysConfigMapper;
    }

    @Override
    public SysConfigVO getById(Long id) {
        SysConfig config = sysConfigMapper.selectById(id);
        return toVO(config);
    }

    @Override
    public List<SysConfigVO> list() {
        return sysConfigMapper.selectList().stream()
                .map(this::toVO).collect(Collectors.toList());
    }

    @Override
    public SysConfigVO getByParamKey(String paramKey) {
        SysConfig config = sysConfigMapper.selectByParamKey(paramKey);
        return toVO(config);
    }

    @Override
    public String getValue(String key) {
        return sysConfigMapper.value(key);
    }

    @Override
    @Transactional
    public void create(SysConfig config) {
        SysConfig existing = sysConfigMapper.selectByParamKey(config.getParamKey());
        if (existing != null) {
            throw new BusinessException("配置键已存在: " + config.getParamKey());
        }
        sysConfigMapper.insert(config);
    }

    @Override
    @Transactional
    public void update(SysConfig config) {
        int rows = sysConfigMapper.updateById(config);
        if (rows == 0) {
            throw new BusinessException("配置不存在");
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        int rows = sysConfigMapper.deleteById(id);
        if (rows == 0) {
            throw new BusinessException("配置不存在");
        }
    }

    private SysConfigVO toVO(SysConfig entity) {
        if (entity == null) {
            return null;
        }
        SysConfigVO vo = new SysConfigVO();
        vo.setId(entity.getId());
        vo.setParamKey(entity.getParamKey());
        vo.setParamValue(entity.getParamValue());
        vo.setStatus(entity.getStatus());
        vo.setRemark(entity.getRemark());
        return vo;
    }
}
