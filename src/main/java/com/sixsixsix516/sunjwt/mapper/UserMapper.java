package com.sixsixsix516.sunjwt.mapper;

import com.sixsixsix516.sunjwt.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    User findUser(@Param("user") User user);

    User findUserById(@Param("Id") String id);
}
