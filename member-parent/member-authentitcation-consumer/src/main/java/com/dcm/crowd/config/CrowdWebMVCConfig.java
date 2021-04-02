package com.dcm.crowd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrowdWebMVCConfig  implements WebMvcConfigurer {


    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public void addViewControllers(ViewControllerRegistry registry) {



        //浏览器访问地址
        String urlPath="/auth/member/to/reg/page";


        //目标视图名称
        String viewName="member-reg";
        registry.addViewController(urlPath).setViewName(viewName);


        registry.addViewController("/auth/member/to/login/page").setViewName("member-login");
        registry.addViewController("/auth/member/to/center/page").setViewName("member-center");
        registry.addViewController("/member/my/crowd").setViewName("member-mycrowd");
//        registry.addViewController("/auth/member/logout").setViewName("portal");



    }
}
