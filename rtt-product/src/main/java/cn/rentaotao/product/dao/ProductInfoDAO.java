package cn.rentaotao.product.dao;

import cn.rentaotao.core.dao.BaseDAO;
import cn.rentaotao.product.domain.entity.ProductInfo;
import cn.rentaotao.product.mapper.ProductInfoMapper;
import org.springframework.stereotype.Repository;

/**
 * @author rtt
 * @date 2024/6/20 16:33
 */
@Repository
public class ProductInfoDAO extends BaseDAO<ProductInfoMapper, ProductInfo> {
}
