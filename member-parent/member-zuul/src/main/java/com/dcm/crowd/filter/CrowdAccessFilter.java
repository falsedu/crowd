package com.dcm.crowd.filter;

import com.dcm.crowd.constant.AccessPassResources;
import com.dcm.crowd.constant.CrowdConstant;
import com.dcm.crowd.entity.vo.MemberLoginVO;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class CrowdAccessFilter extends ZuulFilter {
    public String filterType() {
        return "pre";
    }

    public int filterOrder() {
        return 0;
    }

    public boolean shouldFilter() {
        RequestContext requestContext= RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String servletPath = request.getServletPath();
        boolean containsResult=AccessPassResources.PASS_RES_SET.contains(servletPath);
        if(containsResult){
                return false;

        }

        return !AccessPassResources.judgeCurrentServletPathWhetherStaticResource(servletPath);
    }

    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        HttpSession session = request.getSession();
        MemberLoginVO loginMember = (MemberLoginVO) session.getAttribute(CrowdConstant.LOGIN_MEMBER);
        if(loginMember==null){
            HttpServletResponse response = requestContext.getResponse();
            session.setAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_ACCESS_FORBIDEN);
            try{
                response.sendRedirect("/auth/member/to/login/page");
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return null;
    }
}
