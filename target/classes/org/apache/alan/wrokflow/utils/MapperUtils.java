package org.apache.alan.wrokflow.utils;

import org.dozer.Mapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class MapperUtils {
    public static <T> T map(Object source,Class<T> destinationClass){
        return source == null ? null : SpringContextHolder.getBean(Mapper.class).map(source, destinationClass);
    }

    public static <T> List<T> mapList(List<?> source, Class<T> destinationClass){
        if (source == null) {
            return null;
        }
        List<T> result = new ArrayList<>();
        for (Object object : source) {
            T destination = map(object, destinationClass);
            result.add(destination);
        }
        return result;
    }

    public static <T> List<T> mapList(List<?> source, Class<T> destinationClass, Function<T,T> addField){
        if (source == null) {
            return null;
        }
        List<T> result = new ArrayList<>();
        for (Object object : source) {
            T destination = map(object, destinationClass);
            addField.apply(destination);
            result.add(destination);
        }
        return result;
    }

    //java对象转map
    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }
        return map;
    }
}
