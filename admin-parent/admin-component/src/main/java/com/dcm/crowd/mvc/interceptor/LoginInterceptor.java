package com.dcm.crowd.mvc.interceptor;

import com.dcm.crowd.constant.CrowdConstant;
import com.dcm.crowd.entity.Admin;
import com.dcm.crowd.exception.AccessFailedException;
import com.dcm.crowd.exception.LoginFailedException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
在spring security之后就不用了
 */

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HttpSession session=httpServletRequest.getSession();
        Admin admin=(Admin) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN);
        if(admin==null){
            throw new AccessFailedException(CrowdConstant.MESSAGE_ACCESS_FORBIDEN);
        }
        return true;

    }


}
