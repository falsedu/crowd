package com.dcm.test;

import com.dcm.crowd.entity.Admin;
import com.dcm.crowd.entity.Role;
import com.dcm.crowd.mapper.AdminMapper;


import com.dcm.crowd.mapper.RoleMapper;
import com.dcm.crowd.service.api.AdminService;
import com.dcm.crowd.service.impl.AdminServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml","classpath:spring-persist-tx.xml"})
public class MyTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private    AdminService adminService;
    @Autowired
    private RoleMapper roleMapper;
    @Test
    public void test6(){
        Role role=new Role();
        for(int i=0;i<200;i++){
            role.setName("role"+(i+1));
            roleMapper.insert(role);
        }
    }

    @Test
    public void test3(){
        Admin admin=adminMapper.selectByPrimaryKey(1);

Logger logger= LoggerFactory.getLogger(MyTest.class);
logger.debug(admin.toString());
//        System.out.println(admin.toString());

    }
    @Test
    public void test2(){
        Admin admin = new Admin();
        admin.setUserName("dcm");
        admin.setUserPswd("1207");
        admin.setLoginAcct("falsedu");
        admin.setEmail("2294513765@qq.com");
        System.out.println("受影响的行数="+adminMapper.insert(admin));
    }
    @Test
    public void test5(){
        Admin admin = new Admin();
         int count=0;
        for (int i=1; i <= 200; i++) {
            admin.setUserName("name"+i);
            admin.setUserPswd("pswd"+i);
            admin.setLoginAcct("loginAct"+i);
            admin.setEmail("email"+i);
            count+=adminMapper.insert(admin);
        }

        System.out.println("受影响的行数="+count);
    }

    @Test
    public void test1() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    @Test
    public void test4() throws Exception {


        Admin admin=new Admin();
        admin.setUserName("lyl2");
        admin.setLoginAcct("lll");
        admin.setEmail("15195@qq.com");
        admin.setUserPswd("123");
        adminService.saveAdmin(admin);
    }



}
