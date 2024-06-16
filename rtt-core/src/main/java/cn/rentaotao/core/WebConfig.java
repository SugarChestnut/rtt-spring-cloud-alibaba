package cn.rentaotao.core;

import cn.rentaotao.core.exception.GlobalExceptionHandler;
import cn.rentaotao.core.web.GlobalResponseBodyAdvice;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * web相关bean组件配置
 *
 * @author zhonghuashishan
 * @version 1.0
 */
@Configuration
@Import(value = {GlobalExceptionHandler.class, GlobalResponseBodyAdvice.class})
public class WebConfig {

}