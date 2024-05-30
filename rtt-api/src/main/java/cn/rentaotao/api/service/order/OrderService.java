package cn.rentaotao.api.service.order;

/**
 * @author rtt
 * @date 2023/9/6 15:55
 */
public interface OrderService {

    void create(Integer userId, Integer amount);
}
