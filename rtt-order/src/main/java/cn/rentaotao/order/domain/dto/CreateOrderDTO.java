package cn.rentaotao.order.domain.dto;

import cn.rentaotao.core.bean.AbstractObject;
import lombok.Data;

import java.io.Serializable;

/**
 * 创建订单返回结果
 */
@Data
public class CreateOrderDTO extends AbstractObject implements Serializable {

    /**
     * 订单ID
     */
    private String orderId;

    // 库存不足的商品列表
}