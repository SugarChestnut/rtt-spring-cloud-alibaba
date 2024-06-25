package cn.rentaotao.product.api;

import cn.rentaotao.product.domain.dto.ProductInfoDTO;
import cn.rentaotao.product.domain.dto.ProductQueryDTO;

import java.util.List;

/**
 * @author rtt
 * @date 2024/6/19 14:39
 */
public interface ProductQueryService {

    List<ProductInfoDTO> query(List<ProductQueryDTO> productQueryDTOList);
}
