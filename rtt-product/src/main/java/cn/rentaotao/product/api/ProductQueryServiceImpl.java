package cn.rentaotao.product.api;

import cn.rentaotao.core.dao.BaseDAO;
import cn.rentaotao.product.domain.dto.ProductInfoDTO;
import cn.rentaotao.product.domain.dto.ProductQueryDTO;
import cn.rentaotao.product.domain.entity.ProductInfo;
import cn.rentaotao.product.mapper.ProductInfoMapper;
import lombok.AllArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author rtt
 * @date 2024/6/20 15:05
 */
@AllArgsConstructor
@DubboService
public class ProductQueryServiceImpl implements ProductQueryService {

    private final BaseDAO<ProductInfoMapper, ProductInfo> productInfoDTO;

    @Override
    public List<ProductInfoDTO> query(List<ProductQueryDTO> productQueryDTOList) {
        if (productQueryDTOList != null) {
            return productQueryDTOList.stream().map(productQueryDTO -> {
                ProductInfoDTO productInfoDTO = new ProductInfoDTO();
                productQueryDTO.clone(productInfoDTO);
                productInfoDTO.setProducer("eshop");
                return productInfoDTO;
            }).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
