package cn.rentaotao.product.domain.dto;

import cn.rentaotao.core.bean.AbstractObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author rtt
 * @date 2024/6/20 14:50
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class ProductQueryDTO extends AbstractObject implements Serializable {

    private static final long serialVersionUID = 1936530823289930871L;

    /**
     * 商品编号
     */
    private String skuCode;

    /**
     * 商品分类
     */
    private String productType;
}

