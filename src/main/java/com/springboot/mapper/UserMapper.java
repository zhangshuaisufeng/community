package com.springboot.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    void addUser(@Param("account_id") String account_id,
                  @Param("name") String name,
                  @Param("token") String token);
}
