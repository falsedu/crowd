package com.dcm.crowd.service.impl;

import com.dcm.crowd.constant.CrowdConstant;
import com.dcm.crowd.entity.po.MemberPO;
import com.dcm.crowd.entity.po.MemberPOExample;
import com.dcm.crowd.mapper.MemberPOMapper;
import com.dcm.crowd.service.api.MemberService;
import com.dcm.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService {



    @Autowired
    private MemberPOMapper memberPOMapper;


    public MemberPO getMemberPOByLoginAcct(String loginacct) {
        MemberPOExample example=new MemberPOExample();
        example.createCriteria().andLoginacctEqualTo(loginacct);
        List<MemberPO> memberPOs = memberPOMapper.selectByExample(example);


//        System.out.println(memberPOs==null);

        return memberPOs.get(0);

    }



    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class,readOnly = false)
    @Override
    public void saveMember(MemberPO memberPO) {


            memberPOMapper.insertSelective(memberPO);


    }
}
