package com.dcm.crowd.handler;


import com.dcm.crowd.constant.CrowdConstant;
import com.dcm.crowd.entity.po.MemberPO;
import com.dcm.crowd.service.api.MemberService;

import com.dcm.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;




@RestController
public class MemberProviderHandler {


    @Autowired
    private MemberService memberService;

    @RequestMapping("/get/memberpo/by/login/acct/remote")
    public ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct){


       try{
           MemberPO memberPOByLoginAcct =
                   memberService.getMemberPOByLoginAcct(loginacct);

        return ResultEntity.successWithData(memberPOByLoginAcct);
    }catch (Exception e){
           e.printStackTrace();
           return ResultEntity.failed("没有找到该账户名");
       }



    }



    @RequestMapping("/save/member/remote")

    public ResultEntity<String > saveMember(@RequestBody MemberPO memberPO){

        try{
            memberService.saveMember(memberPO);
            return ResultEntity.successWithoutData();
        }catch (Exception e){
            if(e instanceof DuplicateKeyException){
                return ResultEntity.failed(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }


            return ResultEntity.failed(e.getMessage());
        }



    }

}
