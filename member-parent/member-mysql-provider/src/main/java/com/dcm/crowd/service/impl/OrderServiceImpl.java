package com.dcm.crowd.service.impl;

import com.dcm.crowd.entity.po.Address;
import com.dcm.crowd.entity.po.AddressExample;
import com.dcm.crowd.entity.po.OrderPO;
import com.dcm.crowd.entity.po.OrderProjectPO;
import com.dcm.crowd.entity.vo.AddressVO;
import com.dcm.crowd.entity.vo.OrderProjectVO;
import com.dcm.crowd.entity.vo.OrderVO;
import com.dcm.crowd.mapper.AddressMapper;
import com.dcm.crowd.mapper.OrderPOMapper;
import com.dcm.crowd.mapper.OrderProjectPOMapper;
import com.dcm.crowd.service.api.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderProjectPOMapper orderProjectPOMapper;

    @Autowired
    private OrderPOMapper orderPOMapper;


    @Autowired
    private AddressMapper addressMapper;

    @Override
    public OrderProjectVO getOrderPrjectVORemote(Integer projectId, Integer returnId) {
        OrderProjectVO orderProjectVO=orderProjectPOMapper.selectOrderProjectVOByReturnId(returnId);

        return orderProjectVO;
    }

    @Override
    public List<AddressVO> getAddress(Integer memberId) {




       return  addressMapper.selectByMemberId(memberId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW ,rollbackFor = Exception.class,readOnly = false)
    public void saveAddress(AddressVO addressVO) {
        Address address=new Address();
        BeanUtils.copyProperties(addressVO,address);
        addressMapper.insert(address);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public void saveOrder(OrderVO orderVO) {

        OrderPO orderPO = new OrderPO();

        BeanUtils.copyProperties(orderVO, orderPO);

        OrderProjectPO orderProjectPO = new OrderProjectPO();

        BeanUtils.copyProperties(orderVO.getOrderProjectVO(), orderProjectPO);

        // 保存orderPO自动生成的主键是orderProjectPO需要用到的外键
        orderPOMapper.insert(orderPO);

        // 从orderPO中获取orderId
        Integer id = orderPO.getId();

        // 将orderId设置到orderProjectPO
        orderProjectPO.setOrderId(id);

        orderProjectPOMapper.insert(orderProjectPO);
    }

}
