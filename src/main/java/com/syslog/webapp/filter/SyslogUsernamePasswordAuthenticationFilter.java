package com.syslog.webapp.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.syslog.module.user.User;
import com.syslog.service.user.UserService;
import com.util.StringUtil;

public class SyslogUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	@Autowired
	private UserService userService;
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		HttpSession session = request.getSession();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String imgCode = request.getParameter("imgCode");
		Object randObj = session.getAttribute("rand");
		String rand = randObj == null ? "" : randObj.toString();
		if(StringUtil.isBlank(username)) {
			throw new BadCredentialsException("请输入账号");
		}else if(StringUtil.isBlank(password)) {
			throw new BadCredentialsException("请输入密码");
		}else if(StringUtil.isBlank(imgCode)) {
			throw new BadCredentialsException("请输入验证码");
		}else if(!imgCode.toUpperCase().equals(rand)) {
			throw new BadCredentialsException("验证码错误");
		}
		
		try {
			User user = userService.login(username, password);
			
			if(user == null) {
				throw new BadCredentialsException("用户名密码错误");
			}
			
			if(user.getStatus() == 0) {
				throw new BadCredentialsException("您的账号已被锁定");
			}
		}catch (Exception e) {
			throw new BadCredentialsException(e.getMessage());
		}
		
		
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, "");
		return super.getAuthenticationManager().authenticate(token);
	}

}
