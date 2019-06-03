package com.demo.cms.authorization.interceptor;


import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.demo.cms.authorization.config.AuthorizationConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author 戴俊明
 * @version 1.0
 * @className AuthorizationInterceptor
 * @description 拦截所有请求，解析请求头中的authorization字段，验证token
 * @date 2019/5/20 15:20
 **/
@Slf4j
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    /**
     * @param request  请求
     * @param response 响应
     * @param handler  处理者
     * @return boolean
     * @author 戴俊明
     * @description 拦截请求，验证token
     * @date 2019/5/20 15:45
     **/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果不是映射到方法直接通过
        log.info("AuthorizationInterceptor::preHandle::request = [{}], response = [{}], handler = [{}]", request, response, handler);
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //从header中得到token
        String authorization = request.getHeader(AuthorizationConstants.AUTHORIZATION);
        log.info("{}:{}", AuthorizationConstants.AUTHORIZATION, authorization);
        //验证token
        HttpResponse res = HttpRequest.get("http://localhost:9500/rbac/user/token")
                .header("authorization", authorization)
                .execute();
        if (res.getStatus() == 401) {
            //如果验证token失败，返回401错误
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        } else {
            //如果token验证成功，将token对应的用户username存在response中，便于之后注入
            log.info("{}:{}", AuthorizationConstants.CURRENT_USER, res.header(AuthorizationConstants.CURRENT_USER));
            response.setHeader(AuthorizationConstants.CURRENT_USER, res.header(AuthorizationConstants.CURRENT_USER));
            return true;
        }
    }
}
