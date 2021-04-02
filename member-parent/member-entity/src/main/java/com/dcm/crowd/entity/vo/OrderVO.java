package com.dcm.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVO implements Serializable {

    private static final long serialVersionUID=1L;


    private Integer id;
    private String orderNum;
    private String payOrderNum;
    private Double orderAmount;
    private Integer invoice;
    private String invoiceTitle;
    private Integer addressId;

    private String orderRemark;
    private OrderProjectVO orderProjectVO;




}
