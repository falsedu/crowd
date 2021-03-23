package com.dcm.crowd.service.api;

import com.dcm.crowd.entity.po.MemberPO;
import com.dcm.crowd.util.ResultEntity;

import java.util.List;

public interface MemberService {
    MemberPO getMemberPOByLoginAcct(String loginacct);

    void saveMember(MemberPO memberPO);
}
