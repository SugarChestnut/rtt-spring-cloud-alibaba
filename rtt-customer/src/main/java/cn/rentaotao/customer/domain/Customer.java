package cn.rentaotao.customer.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author rtt
 * @date 2023/9/5 15:16
 */
@Data
@TableName("customer")
public class Customer {

    private Integer id;

    private Integer age;

    private String username;

    private Integer account;

}
