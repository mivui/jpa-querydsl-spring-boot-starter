package com.github.uinio.jpa.utils;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.Assert;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Optional;

/**
 * reflection get id
 */
public class KeyUtils {

    private KeyUtils() {
    }

    public static <T> Object fieldValue(T entity) {
        Object value = null;
        BeanWrapperImpl wrapper = new BeanWrapperImpl(entity);
        String fieldName = fieldName(entity.getClass());
        if (Objects.nonNull(fieldName)) {
            value = wrapper.getPropertyValue(fieldName);
        }
        return value;
    }

    public static String fieldName(Class<?> entity) {
        Optional<String> optional = findAnnotationFieldName(entity);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            Optional<String> fieldName = findSuperclassFieldName(entity);
            return fieldName.orElse(null);
        }
    }

    private static Optional<String> findAnnotationFieldName(Class<?> entity) {
        String fieldName = null;
        Field[] fields = entity.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                fieldName = field.getName();
                break;
            }
        }
        return Optional.ofNullable(fieldName);
    }

    private static Optional<String> findSuperclassFieldName(Class<?> entity) {
        String fieldName = null;
        Class<?> superParent = entity.getSuperclass();
        String completePackage = superParent.getName();
        //Parent class to find primary key
        boolean flag = !Objects.equals(completePackage, "java.lang.Object");
        Assert.isTrue(flag, "Table " + entity.getName() + " Not Found javax.persistence.Id");
        Optional<String> optional = findAnnotationFieldName(superParent);
        if (optional.isPresent()) {
            fieldName = optional.get();
        } else {
            findSuperclassFieldName(superParent);
        }
        return Optional.ofNullable(fieldName);
    }

}
