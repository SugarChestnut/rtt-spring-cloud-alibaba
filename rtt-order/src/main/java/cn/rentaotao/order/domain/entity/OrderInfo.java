package cn.rentaotao.order.domain.entity;

import cn.rentaotao.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author rtt
 * @date 2023/9/6 15:52
 */
@Data
@TableName("order_info")
public class OrderInfo extends BaseEntity implements Serializable {


    private String orderId;

    private String userId;

    private Integer payAmount;

}
