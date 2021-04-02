package com.dcm.crowd.handler;

import com.dcm.crowd.entity.vo.AddressVO;
import com.dcm.crowd.entity.vo.OrderProjectVO;
import com.dcm.crowd.service.api.OrderService;
import com.dcm.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class OrderProviderHandler {

    @Autowired
    private OrderService orderService;





    @RequestMapping("/get/order/project/vo/remote/{projectId}/{returnId}")
    public  ResultEntity<OrderProjectVO> getOrderProjectVORemote(@PathVariable("projectId") Integer projectId, @PathVariable("returnId") Integer returnId){

        try{
            OrderProjectVO orderProjectVO= orderService.getOrderPrjectVORemote(projectId,returnId);


            return ResultEntity.successWithData(orderProjectVO);
        }catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }





    }

    @RequestMapping("/get/address/vo/remote")
    ResultEntity<List<AddressVO>> getAddressVORemote(@RequestParam("memberId") Integer memberId){
       try{
           List<AddressVO> addressVOs= orderService.getAddress(memberId);
           return ResultEntity.successWithData(addressVOs);
       }catch (Exception e){
           e.printStackTrace();
           return ResultEntity.failed(e.getMessage());
       }
    }


    @RequestMapping("/save/address/vO/remote")
    ResultEntity<String> saveAddressVORemote(@RequestBody AddressVO addressVO){

        try {
            orderService.saveAddress(addressVO);
            return ResultEntity.successWithoutData();
        }catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }

    }

}
