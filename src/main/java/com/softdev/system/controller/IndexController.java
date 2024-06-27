package com.softdev.system.controller;

import com.softdev.system.mapper.SysConfigMapper;
import com.softdev.system.util.ReturnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @Autowired
    SysConfigMapper sysConfigMapper;
    /**
     * 首页
     */
    @GetMapping("/")
    public Object indexPage(){
        return "hello world - https://zhengkai.blog.csdn.net/";
    }

    /**
     * http://localhost:12666/api/author
     */
    @GetMapping("/author")
    public Object author(){
        return ReturnUtil.DATA(sysConfigMapper.value("developer"));
    }
}
