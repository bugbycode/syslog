package com.syslog.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.syslog.mapper.user.UserMapper;
import com.syslog.module.user.User;
import com.syslog.service.user.UserService;

@Service("userService")
public class UserServiceImpl implements UserService,UserDetailsService {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public User login(String username, String password) {
		try {
			return userMapper.login(username, password);
		}catch (Exception e) {
			throw new RuntimeException("根据账号和密码查询单一用户信息时连接数据库超时，" + e.getMessage());
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			return userMapper.loadUserByUsername(username);
		}catch (Exception e) {
			throw new RuntimeException("根据账号查询单一用户信息时连接数据库超时，" + e.getMessage());
		}
	}

}
