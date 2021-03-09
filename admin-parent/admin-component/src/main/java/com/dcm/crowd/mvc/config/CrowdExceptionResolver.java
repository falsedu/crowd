package com.dcm.crowd.mvc.config;


import com.dcm.crowd.CrowdUtil;
import com.dcm.crowd.constant.CrowdConstant;
import com.dcm.crowd.exception.*;
import com.dcm.crowd.util.ResultEntity;

import com.google.gson.Gson;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;




@ControllerAdvice
public class CrowdExceptionResolver {


    @ExceptionHandler(value = LoginFailedException.class)
    public ModelAndView resolveLoginFailedException(LoginFailedException exception,HttpServletRequest request,HttpServletResponse response) throws IOException {
        String viewName="admin-login";

        return commonResolve(viewName,exception,request,response);

    }
    @ExceptionHandler(value = AccessFailedException.class)
    public ModelAndView resolveAccessException(AccessFailedException exception,HttpServletRequest request,HttpServletResponse response) throws IOException {
        String viewName="admin-login";

        return commonResolve(viewName,exception,request,response);

    }


    @ExceptionHandler(value = Exception.class)
    public ModelAndView resolveException(Exception exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName="system-error";
       return  commonResolve(viewName,exception,request,response);

    }
    @ExceptionHandler(value = MySqlException1.class)
    public ModelAndView resolveException1(MySqlException1 exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName="admin-add";
        return  commonResolve(viewName,exception,request,response);

    }
    @ExceptionHandler(value = MySqlException2.class)
    public ModelAndView resolveException2(MySqlException2 exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName="system-error";
        return  commonResolve(viewName,exception,request,response);

    }
    @ExceptionHandler(value = RoleNAmeException.class)
    public ModelAndView resolveException3(RoleNAmeException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName="";
        return  commonResolve(viewName,exception,request,response);

    }


    private ModelAndView commonResolve(String viewName,Exception exception,HttpServletRequest request,HttpServletResponse response) throws IOException {
        boolean judgeResult = CrowdUtil.judgeRequestType(request);

        if(judgeResult){
            ResultEntity<Object> failed = ResultEntity.failed(exception.getMessage());

            Gson gson=new Gson();
            String json=gson.toJson(failed);

            response.getWriter().write(json);

            //是ajax不需要返回页面，直接在当前页面报错
            return null;
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(CrowdConstant.ATTR_NAME_EXCEPTION,exception);
        modelAndView.setViewName(viewName);


        return modelAndView;

    }

}
