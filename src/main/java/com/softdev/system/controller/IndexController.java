package com.softdev.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.softdev.system.entity.SysConfig;
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
     * <a href="http://localhost:12666/api/author">view api</a>
     */
    @GetMapping("/author")
    public Object author(){
        return ReturnUtil.DATA(sysConfigMapper.value("developer"));
    }

    /**
     * <a href="http://localhost:12666/api/author2">view api</a>
     */
    @GetMapping("/author2")
    public Object author2(){
        return ReturnUtil.DATA(
                sysConfigMapper.selectOne(
                        new QueryWrapper<SysConfig>()
                                .eq("param_key","developer")));
    }
}
