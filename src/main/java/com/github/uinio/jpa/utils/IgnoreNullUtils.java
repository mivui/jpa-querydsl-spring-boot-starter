package com.github.uinio.jpa.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Get non-null attributes
 */
public class IgnoreNullUtils {

    private IgnoreNullUtils() {
    }

    /**
     * get non-null attributes
     */
    public static <T> String[] notNullProperties(T entity) {
        Set<String> params = new HashSet<>();
        BeanWrapper wrapper = new BeanWrapperImpl(entity);
        PropertyDescriptor[] descriptors = wrapper.getPropertyDescriptors();
        Stream.of(descriptors).forEach(descriptor -> {
            Object value = wrapper.getPropertyValue(descriptor.getName());
            if (Objects.nonNull(value)) {
                String descriptorName = descriptor.getName();
                if (!Objects.equals(descriptorName, "class")) {
                    params.add(descriptorName);
                }
            }
        });
        return params.toArray(new String[0]);
    }

    /**
     * copy non-null attributes
     */
    public static <T> void copyNotNullProperties(T source, T target) {
        //Copy the attributes of source to target, and the non-null attributes of target will not be overwritten when copy.
        BeanUtils.copyProperties(source, target, notNullProperties(target));
    }
}
