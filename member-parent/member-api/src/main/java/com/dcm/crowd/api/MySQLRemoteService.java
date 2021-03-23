package com.dcm.crowd.api;



import com.dcm.crowd.entity.po.MemberPO;
import com.dcm.crowd.util.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient("mysqlProvider")
public interface MySQLRemoteService {

    @RequestMapping("/get/memberpo/by/login/acct/remote")
    public ResultEntity<MemberPO>  getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct);



    @RequestMapping("/save/member/remote")

    public ResultEntity<String > saveMember(@RequestBody MemberPO memberPO);
}