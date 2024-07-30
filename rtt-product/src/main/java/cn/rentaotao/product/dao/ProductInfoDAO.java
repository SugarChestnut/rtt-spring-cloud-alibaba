package cn.rentaotao.product.dao;

import cn.rentaotao.core.dao.BaseDAO;
import cn.rentaotao.core.exception.OrderCreateException;
import cn.rentaotao.product.config.listener.ProductProperties;
import cn.rentaotao.product.domain.dto.ProductInfoDTO;
import cn.rentaotao.product.domain.dto.ProductQueryDTO;
import cn.rentaotao.product.domain.entity.ProductInfo;
import cn.rentaotao.product.mapper.ProductInfoMapper;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author rtt
 * @date 2024/6/20 16:33
 */
@Repository
public class ProductInfoDAO extends BaseDAO<ProductInfoMapper, ProductInfo> {

    private final ProductProperties productProperties;

    private final ConcurrentHashMap<String, Integer> warehouse = new ConcurrentHashMap<>();

    {
        warehouse.put("sw001", 100);
        warehouse.put("ggx001", 200);
        warehouse.put("lyq001", 99);
    }

    public ProductInfoDAO(ProductProperties productProperties) {
        this.productProperties = productProperties;
    }

    @SentinelResource(value = "ProductInfoDAO#getFromLocal", entryType = EntryType.IN)
    public List<ProductInfoDTO> getFromLocal(List<ProductQueryDTO> productQueryList) {
        if (productProperties.getCanSale()) {
            return productQueryList.stream().filter(productQueryDTO -> !productProperties.getExcludes().contains(productQueryDTO.getSkuCode())).map(this::getFromLocal
            ).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * 内部调用（即类内方法之间的调用）无法缓存
     */
    @Cacheable(cacheNames = "product", key = "#productQueryDTO.skuCode")
    public ProductInfoDTO getFromLocal(ProductQueryDTO productQueryDTO) {
        Integer num = warehouse.get(productQueryDTO.getSkuCode());
        if (num == null) {
            throw new OrderCreateException(productQueryDTO.getSkuCode() + "商品不存在");
        }
        ProductInfoDTO productInfoDTO = new ProductInfoDTO();
        productQueryDTO.clone(productInfoDTO);
        productInfoDTO.setManufacturers("eshop");
        productInfoDTO.setNum(num);
        return productInfoDTO;
    }
}
