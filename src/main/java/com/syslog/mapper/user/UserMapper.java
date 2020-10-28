package com.syslog.mapper.user;

import org.apache.ibatis.annotations.Param;

import com.syslog.module.user.User;

public interface UserMapper {

	public User login(@Param("username") String username, @Param("password") String password);
	
	public User loadUserByUsername(@Param("username") String username);
}
