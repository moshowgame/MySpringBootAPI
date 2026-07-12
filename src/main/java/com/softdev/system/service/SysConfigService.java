package com.softdev.system.service;

import com.softdev.system.vo.SysConfigVO;

import java.util.List;

public interface SysConfigService {

    SysConfigVO getById(Long id);

    List<SysConfigVO> list();

    SysConfigVO getByParamKey(String paramKey);

    String getValue(String key);

    void create(com.softdev.system.entity.SysConfig config);

    void update(com.softdev.system.entity.SysConfig config);

    void deleteById(Long id);
}
