package com.dcm.crowd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrowdWebMVCConfig  implements WebMvcConfigurer {


    public void addViewControllers(ViewControllerRegistry registry) {


        //浏览器访问地址
        String urlPath="/auth/member/to/reg/page.html";


        //目标视图名称
        String viewName="member-reg";
        registry.addViewController(urlPath).setViewName(viewName);
    }
}
