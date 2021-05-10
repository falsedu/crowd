package com.dcm.crowd.handler;

import com.dcm.crowd.api.MySQLRemoteService;
import com.dcm.crowd.constant.CrowdConstant;
import com.dcm.crowd.entity.po.Address;
import com.dcm.crowd.entity.vo.AddressVO;
import com.dcm.crowd.entity.vo.MemberLoginVO;
import com.dcm.crowd.entity.vo.OrderProjectVO;
import com.dcm.crowd.exception.MySqlException;
import com.dcm.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class OrderConsumerHandler
{

@Autowired
private MySQLRemoteService mySQLRemoteService;




    @RequestMapping("/save/address")
    public String saveAddress(AddressVO addressVO,HttpSession session){

      ResultEntity<String> resultEntity=  mySQLRemoteService.saveAddressVORemote(addressVO);
        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute("orderProjectVO");

        Integer returnCount=orderProjectVO.getReturnCount();
        return "redirect:http://192.168.3.54/order/confirm/order/"+returnCount;
    }


    @RequestMapping("/confirm/order/{returnCount}")
    public String showConfirmOrderInfo(@PathVariable ("returnCount")Integer returnCount,HttpSession session){

        OrderProjectVO orderProjectVO= (OrderProjectVO) session.getAttribute("orderProjectVO");

        orderProjectVO.setReturnCount(returnCount);
        session.setAttribute("orderProjectVO",orderProjectVO);

        MemberLoginVO memberLoginVO=(MemberLoginVO) session.getAttribute(CrowdConstant.LOGIN_MEMBER);
        Integer memberId = memberLoginVO.getId();
        ResultEntity<List<AddressVO>> resultEntity= mySQLRemoteService.getAddressVORemote(memberId);


        if(ResultEntity.SUCCESS.equals(resultEntity.getResult())){
            List<AddressVO> list=resultEntity.getData();
            session.setAttribute("addressVOList",list);
        }



        return "confirm-order";


    }


    @RequestMapping("/confirm/return/info/{projectId}/{returnId}")
    public String getOrderPage(HttpSession session,@PathVariable("projectId") Integer projectId, @PathVariable("returnId") Integer returnId){

        ResultEntity<OrderProjectVO> resultEntity= mySQLRemoteService.getOrderProjectVORemote(projectId,returnId);
        if(ResultEntity.SUCCESS.equals(resultEntity.getResult())){
            OrderProjectVO data = resultEntity.getData();
            session.setAttribute("orderProjectVO",data);

        }

        return "confirm-return";

    }
}
