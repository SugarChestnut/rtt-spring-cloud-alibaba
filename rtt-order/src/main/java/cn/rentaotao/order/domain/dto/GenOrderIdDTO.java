package cn.rentaotao.order.domain.dto;

import cn.rentaotao.core.bean.AbstractObject;
import lombok.Data;

import java.io.Serializable;

@Data
public class GenOrderIdDTO extends AbstractObject implements Serializable {

    /**
     * 订单号
     */
    private String orderId;

}