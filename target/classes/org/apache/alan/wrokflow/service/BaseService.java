package org.apache.alan.wrokflow.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.alan.wrokflow.utils.MapperUtils;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 基础Service，所有Service的父类
 *
 **/
public interface BaseService<T> extends IService<T> {


    default <V> List<V> list(Wrapper<T> queryWrapper, Function<T, V> mapper) {
        return getBaseMapper().selectList(queryWrapper).stream().filter(Objects::nonNull).map(mapper).collect(Collectors.toList());
    }

    default <V> List<V> list(Wrapper<T> queryWrapper, Class<V> destinationClass) {
        return list(queryWrapper, o -> MapperUtils.map(o, destinationClass));
    }
}
