package com.softdev.system.mapper;

import com.softdev.system.entity.SysConfig;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SysConfigMapper {

    @Select("SELECT * FROM sys_config WHERE id = #{id}")
    SysConfig selectById(Long id);

    @Select("SELECT * FROM sys_config")
    List<SysConfig> selectList();

    @Select("SELECT * FROM sys_config WHERE param_key = #{paramKey}")
    SysConfig selectByParamKey(String paramKey);

    @Select("SELECT t.param_value FROM sys_config t WHERE t.param_key = #{name}")
    String value(String name);

    @Insert("INSERT INTO sys_config(id, param_key, param_value, status, remark) " +
            "VALUES(#{id}, #{paramKey}, #{paramValue}, #{status}, #{remark})")
    int insert(SysConfig sysConfig);

    @Update("UPDATE sys_config SET param_key=#{paramKey}, param_value=#{paramValue}, " +
            "status=#{status}, remark=#{remark} WHERE id=#{id}")
    int updateById(SysConfig sysConfig);

    @Delete("DELETE FROM sys_config WHERE id = #{id}")
    int deleteById(Long id);
}
