package com.hcx.asclepiusmanager.sysmgr.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName JWTAuthenticationEntryPoint
 * @Description 没有携带token或者token无效
 * @Author hcx
 * @Date 2022/2/9 15:26
 * @Version 1.0
 **/
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        String reason = "统一处理，原因：" + authException.getMessage();
        response.getWriter().write(new ObjectMapper().writeValueAsString(reason));
    }
}
