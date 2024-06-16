package cn.rentaotao.order.domain.request;

import cn.rentaotao.core.bean.AbstractObject;
import lombok.Data;

import java.io.Serializable;

@Data
public class GenOrderIdRequest extends AbstractObject implements Serializable {

    private static final long serialVersionUID = -3918194989507931383L;

    /**
     * 业务线标识
     */
    private Integer businessIdentifier;

    /**
     * 用户ID
     */
    private String userId;

}