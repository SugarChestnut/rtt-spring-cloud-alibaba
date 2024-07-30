package cn.rentaotao.product.api;

import cn.rentaotao.product.dao.ProductInfoDAO;
import cn.rentaotao.product.domain.dto.ProductInfoDTO;
import cn.rentaotao.product.domain.dto.ProductQueryDTO;
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

    private final ProductInfoDAO productInfoDAO;

    @Override
    public List<ProductInfoDTO> query(List<ProductQueryDTO> productQueryDTOList) {
        if (productQueryDTOList != null) {
            return productInfoDAO.getFromLocal(productQueryDTOList);
        }
        return Collections.emptyList();
    }
}
