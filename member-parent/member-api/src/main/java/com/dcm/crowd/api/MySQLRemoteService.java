package com.dcm.crowd.api;



import com.dcm.crowd.entity.po.MemberPO;
import com.dcm.crowd.entity.vo.*;
import com.dcm.crowd.util.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient("mysqlProvider")
public interface MySQLRemoteService {

    @RequestMapping("/get/memberpo/by/login/acct/remote")
    public ResultEntity<MemberPO>  getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct);



    @RequestMapping("/save/member/remote")

    public ResultEntity<String > saveMember(@RequestBody MemberPO memberPO);



    @RequestMapping("/save/project/vo/remote")
    ResultEntity<String> saveProjectVORemote(@RequestBody ProjectVO projectVO,@RequestParam("memberId") Integer memberId);


    @RequestMapping("/get/portal/type/project/data/remote")

    public ResultEntity<List<PortalTypeVO>>getPortalTypeProjectDataRemote();


    @RequestMapping("/get/project/detail/remote/{projectId}")
    public ResultEntity<DetailProjectVO> getDetailProjectVORemote(@PathVariable("projectId") Integer projectId);


    @RequestMapping("/get/order/project/vo/remote/{projectId}/{returnId}")
   public  ResultEntity<OrderProjectVO> getOrderProjectVORemote(@PathVariable("projectId") Integer projectId, @PathVariable("returnId") Integer returnId);

    @RequestMapping("/get/address/vo/remote")
    ResultEntity<List<AddressVO>> getAddressVORemote(@RequestParam("memberId") Integer memberId);


    @RequestMapping("/save/address/vo/remote")
    ResultEntity<String> saveAddressVORemote(@RequestBody AddressVO addressVO);


    @RequestMapping("/save/order/vo/remote")
    ResultEntity<String> saveOrderRemote(@RequestBody OrderVO orderVO);
}
