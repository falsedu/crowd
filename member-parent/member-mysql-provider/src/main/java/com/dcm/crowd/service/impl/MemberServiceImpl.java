package com.dcm.crowd.service.impl;

import com.dcm.crowd.entity.po.MemberPO;
import com.dcm.crowd.entity.po.MemberPOExample;
import com.dcm.crowd.mapper.MemberPOMapper;
import com.dcm.crowd.service.api.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService {



    @Autowired
    private MemberPOMapper memberPOMapper;
    public MemberPO getMemberPOByLoginAcct(String loginacct) {
        MemberPOExample example=new MemberPOExample();
        example.createCriteria().andLoginacctLike(loginacct);
        List<MemberPO> memberPOs = memberPOMapper.selectByExample(example);


        System.out.println(memberPOs==null);

        return memberPOs.get(0);

    }
}
