package com.softdev.system.mapper;

import com.softdev.system.entity.SysUser;
import org.apache.ibatis.annotations.*;

@Mapper
public interface SysUserMapper {

    @Select("SELECT * FROM sys_user WHERE username = #{username}")
    SysUser selectByUsername(String username);

    @Select("SELECT * FROM sys_user WHERE id = #{id}")
    SysUser selectById(Long id);

    @Select("SELECT * FROM sys_user WHERE token = #{token}")
    SysUser selectByToken(String token);

    @Insert("INSERT INTO sys_user(username, password, nickname, email, token, status) " +
            "VALUES(#{username}, #{password}, #{nickname}, #{email}, #{token}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SysUser user);

    @Update("UPDATE sys_user SET nickname=#{nickname}, email=#{email}, " +
            "token=#{token}, status=#{status}, update_time=CURRENT_TIMESTAMP WHERE id=#{id}")
    int updateById(SysUser user);
}
