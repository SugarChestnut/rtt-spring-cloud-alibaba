package cn.rentaotao.order.service.feign;

import cn.rentaotao.product.domain.dto.ProductInfoDTO;
import cn.rentaotao.product.domain.dto.ProductQueryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author rtt
 * @date 2024/7/25 16:24
 */
@FeignClient("rtt-product")
public interface ProductService {

    @PostMapping("/product/query")
    List<ProductInfoDTO> query(List<ProductQueryDTO> productQueryDTOList);
}
