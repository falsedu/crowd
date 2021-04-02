package com.dcm.crowd.mapper;

import com.dcm.crowd.entity.po.OrderProjectPO;
import com.dcm.crowd.entity.po.OrderProjectPOExample;
import com.dcm.crowd.entity.vo.OrderProjectVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderProjectPOMapper {
    long countByExample(OrderProjectPOExample example);

    int deleteByExample(OrderProjectPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrderProjectPO record);

    int insertSelective(OrderProjectPO record);

    List<OrderProjectPO> selectByExample(OrderProjectPOExample example);

    OrderProjectPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrderProjectPO record, @Param("example") OrderProjectPOExample example);

    int updateByExample(@Param("record") OrderProjectPO record, @Param("example") OrderProjectPOExample example);

    int updateByPrimaryKeySelective(OrderProjectPO record);

    int updateByPrimaryKey(OrderProjectPO record);

    OrderProjectVO selectOrderProjectVOByReturnId( Integer returnId);
}