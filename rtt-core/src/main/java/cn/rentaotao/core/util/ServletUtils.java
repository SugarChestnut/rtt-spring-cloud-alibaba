package cn.rentaotao.core.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 获取Servlet相关组件的工具类
 */
@Slf4j
public class ServletUtils {

    /**
     * 从应用上下文获取HttpServletRequest组件
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        return attributes.getRequest();
    }

    /**
     * 从应用上下文获取HttpServletResponse组件
     */
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        return attributes.getResponse();
    }

    /**
     * 响应json结果
     */
    public static void writeJsonMessage(HttpServletResponse response, Object obj) {
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.print(JsonUtils.object2Json(obj));

            response.flushBuffer();

        } catch (IOException e) {
            log.warn("响应json结果错误", e);
        }
    }

}