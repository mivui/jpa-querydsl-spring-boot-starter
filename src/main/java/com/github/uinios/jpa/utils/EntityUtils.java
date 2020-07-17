package com.github.uinios.jpa.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.persistence.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * get the non null field of a single table
 */
public class EntityUtils {

    private EntityUtils() {
    }

    public static <T> Map<String, Object> getProperties(T entity) {
        BeanWrapper wrapper = new BeanWrapperImpl(entity);
        Map<String, Object> map = new HashMap<>();
        Field[] fields = entity.getClass().getDeclaredFields();
        if (fields.length > 0) {
            verify(fields, wrapper, map);
        }
        getSuperClassProperties(entity.getClass(), wrapper, map);
        return map;
    }

    private static void verify(Field[] fields, BeanWrapper wrapper, Map<String, Object> map) {
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            boolean flag = true;
            for (Annotation annotation : annotations) {
                if (annotation instanceof Id) {
                    flag = false;
                    break;
                } else if (annotation instanceof Transient) {
                    flag = false;
                    break;
                } else if (annotation instanceof OneToOne) {
                    flag = false;
                    break;
                } else if (annotation instanceof OneToMany) {
                    flag = false;
                    break;
                } else if (annotation instanceof ManyToOne) {
                    flag = false;
                    break;
                } else if (annotation instanceof ManyToMany) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                Object value = wrapper.getPropertyValue(field.getName());
                if (Objects.nonNull(value)) {
                    map.put(field.getName(), value);
                }
            }
        }
    }

    private static void getSuperClassProperties(Class<?> aClass, BeanWrapper wrapper, Map<String, Object> map) {
        Class<?> superclass = aClass.getSuperclass();
        if (!Objects.equals(superclass.getName(), "java.lang.Object")) {
            Field[] fields = superclass.getDeclaredFields();
            if (fields.length > 0) {
                verify(fields, wrapper, map);
            }
            getSuperClassProperties(superclass, wrapper, map);
        }
    }

}
