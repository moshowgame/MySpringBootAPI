package com.softdev.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.softdev.system.entity.SysConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


/**
 * @author moshowgame
 */
@Mapper
public interface SysConfigMapper extends BaseMapper<SysConfig> {

    @Select("select t.param_value from sys_config t where t.param_key=#{name} ")
    String value(String name);

    @Select("select t.param_value from sys_config t where t.param_key=#{name} ")
    Integer valueInt(String name);
}
