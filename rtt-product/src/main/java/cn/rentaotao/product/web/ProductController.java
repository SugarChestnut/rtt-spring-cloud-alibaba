package cn.rentaotao.product.web;

import cn.rentaotao.product.dao.ProductInfoDAO;
import cn.rentaotao.product.domain.dto.ProductInfoDTO;
import cn.rentaotao.product.domain.dto.ProductQueryDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author rtt
 * @date 2024/7/25 10:11
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductInfoDAO productInfoDAO;

    public ProductController(ProductInfoDAO productInfoDAO) {
        this.productInfoDAO = productInfoDAO;
    }

    @RequestMapping("/query")
    public List<ProductInfoDTO> queryProduct(@RequestBody List<ProductQueryDTO> productQueryDTOList) {
        return productInfoDAO.getFromLocal(productQueryDTOList);
    }
}
