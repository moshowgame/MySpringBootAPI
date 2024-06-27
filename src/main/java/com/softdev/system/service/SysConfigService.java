package com.softdev.system.service;

import com.softdev.system.mapper.SysConfigMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SysConfigService {
    @Autowired
    SysConfigMapper sysConfigMapper;


}
