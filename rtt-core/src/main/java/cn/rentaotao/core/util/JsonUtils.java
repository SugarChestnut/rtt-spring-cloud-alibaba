package cn.rentaotao.core.util;

import cn.rentaotao.core.json.ObjectMapperImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Json对象转换工具
 */
@Slf4j
public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapperImpl();

    /**
     * 单个对象转json字符串
     */
    public static String object2Json(Object o) {
        if (o == null) {
            return null;
        }

        String s = null;
        try {
            s = objectMapper.writeValueAsString(o);
        } catch (Exception e) {
            log.warn("object to com.ruyuan.eshop.common.json error", e);
        }
        return s;
    }

    /**
     * 集合对象批量转json字符串集合
     */
    public static <T> List<String> listObject2ListJson(List<T> objects) {
        if (objects == null) {
            return null;
        }

        List<String> lists = new ArrayList<String>();
        for (T t : objects) {
            lists.add(object2Json(t));
        }

        return lists;
    }

    /**
     * json字符串集合批量转对象集合
     */
    public static <T> List<T> listJson2ListObject(List<String> jsons, Class<T> clazz) {
        if (jsons == null) {
            return null;
        }

        List<T> ts = new ArrayList<T>();
        for (String j : jsons) {
            ts.add(json2Object(j, clazz));
        }

        return ts;
    }

    /**
     * json字符串转指定类型的对象
     */
    public static <T> T json2Object(String json, Class<T> clazz) {
        if (!StringUtils.hasLength(json)) {
            return null;
        }

        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            log.warn("cn.rentaotao.core.json data process error", e);
        }
        return null;
    }

    /**
     * json字符串转指定类型的对象
     */
    public static <T> T json2Object(String json, TypeReference<T> typeReference) {
        if (!StringUtils.hasLength(json)) {
            return null;
        }
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (Exception e) {
            log.warn("cn.rentaotao.core.json data process error", e);
        }
        return null;
    }

    /**
     * 用于支持获取远程调用返回数据的转换
     * 注意事项：
     * 支持多单词下划线专驼峰（序列化和反序列化）
     */
    public <T> T getData(Object data, Class<T> clazz) {
        if (data == null) {
            return null;
        }
        try {
            String jsonStr = objectMapper.writeValueAsString(data);
            return objectMapper.readValue(jsonStr, clazz);
        } catch (JsonProcessingException e) {
            log.warn("cn.rentaotao.core.json data process error", e);
        }
        return null;
    }

    /**
     * 用于支持获取远程调用返回数据的转换
     * 注意事项：
     * 支持多单词下划线专驼峰（序列化和反序列化）
     */
    public <T> T getData(Object data, TypeReference<T> typeReference) {
        if (data == null) {
            return null;
        }
        try {
            String jsonStr = objectMapper.writeValueAsString(data);
            return objectMapper.readValue(jsonStr, typeReference);
        } catch (JsonProcessingException e) {
            log.warn("cn.rentaotao.core.json data process error", e);
        }
        return null;
    }
}
