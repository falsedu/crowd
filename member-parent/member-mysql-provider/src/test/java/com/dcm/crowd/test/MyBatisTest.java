package com.dcm.crowd.test;


import com.dcm.crowd.entity.po.MemberPO;
import com.dcm.crowd.entity.vo.PortalTypeVO;
import com.dcm.crowd.mapper.MemberPOMapper;
import com.dcm.crowd.mapper.ProjectPOMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MyBatisTest {


    @Autowired
    private DataSource dataSource;


    @Autowired
    private MemberPOMapper memberPOMapper;

    @Autowired
    private ProjectPOMapper projectPOMapper;


    private Logger logger= LoggerFactory.getLogger(MyBatisTest.class);

    @Test
    public void test() throws SQLException {

        Connection connection = dataSource.getConnection();
        logger.debug("connection:"+connection);





    }

    @Test
    public void testMapper(){
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        String code="123123";
        String encode=bCryptPasswordEncoder.encode(code);
        MemberPO memberPO=new MemberPO(null,"jack", encode, " 杰 克 ",
                        "jack@qq.com", 1, 1, "杰克", "123123", 2);

        memberPOMapper.insert(memberPO);

    }



}
