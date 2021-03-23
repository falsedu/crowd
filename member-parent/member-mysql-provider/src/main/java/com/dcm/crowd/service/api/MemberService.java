package com.dcm.crowd.service.api;

import com.dcm.crowd.entity.po.MemberPO;

import java.util.List;

public interface MemberService {
    MemberPO getMemberPOByLoginAcct(String loginacct);
}
