package cn.rentaotao.product.dao;

import cn.rentaotao.core.dao.BaseDAO;
import cn.rentaotao.product.config.listener.ProductProperties;
import cn.rentaotao.product.domain.dto.ProductInfoDTO;
import cn.rentaotao.product.domain.dto.ProductQueryDTO;
import cn.rentaotao.product.domain.entity.ProductInfo;
import cn.rentaotao.product.mapper.ProductInfoMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author rtt
 * @date 2024/6/20 16:33
 */
@Repository
public class ProductInfoDAO extends BaseDAO<ProductInfoMapper, ProductInfo> {

    private final ProductProperties productProperties;

    public ProductInfoDAO(ProductProperties productProperties) {
        this.productProperties = productProperties;
    }

    public List<ProductInfoDTO> getFromLocal(List<ProductQueryDTO> productQueryList) {
        if (productProperties.getCanSale()) {
            return productQueryList.stream().filter(productQueryDTO -> !productProperties.getExcludes().contains(productQueryDTO.getSkuCode())).map(this::getFromLocal
            ).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Cacheable(cacheNames = "product", key = "#productQueryDTO.skuCode")
    public ProductInfoDTO getFromLocal(ProductQueryDTO productQueryDTO) {
        ProductInfoDTO productInfoDTO = new ProductInfoDTO();
        productQueryDTO.clone(productInfoDTO);
        productInfoDTO.setManufacturers("eshop");
        return productInfoDTO;
    }
}
