package com.syslog.service.user;

import com.syslog.module.user.User;

public interface UserService {

	public User login(String username, String password);

}
