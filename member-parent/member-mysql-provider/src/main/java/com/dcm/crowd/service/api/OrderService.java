package com.dcm.crowd.service.api;

import com.dcm.crowd.entity.vo.AddressVO;
import com.dcm.crowd.entity.vo.OrderProjectVO;
import com.dcm.crowd.entity.vo.OrderVO;

import java.util.List;

public interface OrderService {

    OrderProjectVO getOrderPrjectVORemote(Integer projectId, Integer returnId);

    List<AddressVO> getAddress(Integer memberId);

    void saveAddress(AddressVO addressVO);

    void saveOrder(OrderVO orderVO);
}
