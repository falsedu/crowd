package com.dcm.crowd.mvc.config;

import com.dcm.crowd.constant.CrowdConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Configuration
@EnableWebSecurity

@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Autowired
    private PasswordEncoder passwordEncoder;



    @Autowired
    private MyUserDetailService myUserDetailService;

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {


//        builder.inMemoryAuthentication()
//                .withUser("false").password("1207")
//                .roles("ADMIN")
//        ;



        //正式的基于数据库的认证
        builder.userDetailsService(myUserDetailService).passwordEncoder(passwordEncoder);




    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security.authorizeRequests()
                .antMatchers("/index.jsp","/bootstrap/**","/css/**",
                        "/fonts/**","/img/**","/jquery/**","/layer/**","/script/**",
                        "/ztree/**","/admin/to/login/page.html")
                .permitAll()
                .antMatchers("/admin/get/page.html")
//                .hasRole("经理")

                .access("hasRole('经理') or hasAnyAuthority('user:get')")
//                .antMatchers("/role/to/page.html")
//                .hasRole("部长")//试一试使用注解来实现权限控制
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
                        httpServletRequest.setAttribute(CrowdConstant.ATTR_NAME_EXCEPTION,new Exception("访问被拒绝"));
                        httpServletRequest.getRequestDispatcher("/WEB-INF/system-error.jsp").forward(httpServletRequest,httpServletResponse);

                    }
                })
                .and()


        .formLogin()//开启表单登录
                .loginPage("/admin/to/login/page.html")//指定登录页面
                .loginProcessingUrl("/admin/security/login.html")//指定处理登录请求的地址
                .usernameParameter("loginAcct")//
                .passwordParameter("userPswd")
        .defaultSuccessUrl("/admin/to/main/page.html")

        .and()
        .logout()
        .logoutUrl("/admin/security/logout.html")
        .logoutSuccessUrl("/index.jsp")
        .and()
                .csrf()
                .disable()

        ;
    }
}
