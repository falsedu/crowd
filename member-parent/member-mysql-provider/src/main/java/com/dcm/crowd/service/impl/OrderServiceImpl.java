package com.dcm.crowd.service.impl;

import com.dcm.crowd.entity.po.Address;
import com.dcm.crowd.entity.po.AddressExample;
import com.dcm.crowd.entity.vo.AddressVO;
import com.dcm.crowd.entity.vo.OrderProjectVO;
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
}
