package cn.rentaotao.core.util;

import cn.rentaotao.core.exception.BaseBizException;
import cn.rentaotao.core.exception.BaseErrorCodeEnum;
import cn.rentaotao.core.exception.CommonErrorCodeEnum;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * <p>
 *     参数校验工具类
 * </p>
 *
 * @author zhonghuashishan
 */
@Slf4j
public class ParamCheckUtils {

    public static void checkObjectNonNull(Object o) throws BaseBizException {
        if (Objects.isNull(o)) {
            throw new BaseBizException(CommonErrorCodeEnum.SERVER_ILLEGAL_ARGUMENT_ERROR);
        }
    }

    public static void checkObjectNonNull(Object o, BaseErrorCodeEnum baseErrorCodeEnum, Object... arguments) throws BaseBizException {
        if (Objects.isNull(o)) {
            throw new BaseBizException(baseErrorCodeEnum.getErrorCode(), baseErrorCodeEnum.getErrorMsg(),arguments);
        }
    }

    public static void checkStringNonEmpty(String s) throws BaseBizException {
        if (!StringUtils.hasText(s)) {
            throw new BaseBizException(CommonErrorCodeEnum.SERVER_ILLEGAL_ARGUMENT_ERROR);
        }
    }

    public static void checkStringNonEmpty(String s, BaseErrorCodeEnum baseErrorCodeEnum, Object... arguments) throws BaseBizException {
        if (!StringUtils.hasText(s)) {
            throw new BaseBizException(baseErrorCodeEnum.getErrorCode(), baseErrorCodeEnum.getErrorMsg(),arguments);
        }
    }

    public static void checkIntAllowableValues(Integer i, Set<Integer> allowableValues, BaseErrorCodeEnum baseErrorCodeEnum,Object... arguments) throws BaseBizException {
        if (Objects.nonNull(i) && !allowableValues.contains(i)) {
            throw new BaseBizException(baseErrorCodeEnum.getErrorCode(), baseErrorCodeEnum.getErrorMsg(),arguments);
        }
    }

    public static void checkIntMin(Integer i, int min, BaseErrorCodeEnum baseErrorCodeEnum,Object... arguments) throws BaseBizException {
        if (Objects.isNull(i) || i<min) {
            throw new BaseBizException(baseErrorCodeEnum.getErrorCode(), baseErrorCodeEnum.getErrorMsg(),arguments);
        }
    }

    public static void checkCollectionNonEmpty(Collection<?> collection) throws BaseBizException {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BaseBizException(CommonErrorCodeEnum.SERVER_ILLEGAL_ARGUMENT_ERROR);
        }
    }

    public static void checkCollectionNonEmpty(Collection<?> collection, BaseErrorCodeEnum baseErrorCodeEnum, Object... arguments) throws BaseBizException {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BaseBizException(baseErrorCodeEnum.getErrorCode(), baseErrorCodeEnum.getErrorMsg(),arguments);
        }
    }

    public static void checkIntSetAllowableValues(Set<Integer> intSet, Set<Integer> allowableValues, BaseErrorCodeEnum baseErrorCodeEnum,Object... arguments) throws BaseBizException {
        if (CollectionUtils.isNotEmpty(intSet) && !diffSet(intSet,allowableValues).isEmpty()) {
            throw new BaseBizException(baseErrorCodeEnum.getErrorCode(), baseErrorCodeEnum.getErrorMsg(),arguments);
        }
    }

    public static void checkSetMaxSize(Set<?> setParam, Integer maxSize, BaseErrorCodeEnum baseErrorCodeEnum,Object... arguments) throws BaseBizException {
        if (CollectionUtils.isNotEmpty(setParam) && setParam.size() > maxSize) {
            throw new BaseBizException(baseErrorCodeEnum.getErrorCode(), baseErrorCodeEnum.getErrorMsg(),arguments);
        }
    }

    /**
     * 求set 差集合
     */
    private static Set<Integer> diffSet(Set<Integer> set1, Set<Integer> set2) {
        Set<Integer> result = new HashSet<>(set1);
        result.removeAll(set2);
        return result;
    }

}
