package com.syslog.webapp.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * 身份验证失败时执行的操作</br>
 * 返回统一状态码 401</br>
 * 返回格式示例：</br> {"error_description":"用户名密码错误","error":"Unauthorized"}
 * 
 * @author zhigongzhang
 * 
 * @version 2020年10月28日16:04:50
 *
 */
public class SyslogAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		JSONObject json = new JSONObject();
		try {
			json.put("error", "Unauthorized");
			json.put("error_description", exception.getMessage());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		PrintWriter writer = response.getWriter();
		writer.write(json.toString());
		writer.flush();
		writer.close();
	}

}
