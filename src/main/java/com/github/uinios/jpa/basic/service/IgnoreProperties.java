package com.github.uinios.jpa.basic.service;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyDescriptor;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Jingle-Cat
 */
public class IgnoreProperties {
    private IgnoreProperties() {
    }

    /**
     * 获取对象需要更新的字段
     *
     * @return 将目标源中不为空的字段取出
     */
    public static String[] ignoreProperties(Object object) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(requestAttributes).getRequest();
        final Enumeration<String> parameterNames = request.getParameterNames();
        //需要更新的字段
        Set<String> params = new HashSet<>();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            params.add(paramName);
        }
        //获取后台进行设置参数的值
        BeanWrapper bean = new BeanWrapperImpl(object);
        //获取属性（字段）的描述
        PropertyDescriptor[] descriptors = bean.getPropertyDescriptors();
        for (PropertyDescriptor descriptor : descriptors) {
            //获取字段的值
            Object value = bean.getPropertyValue(descriptor.getName());
            if (Objects.nonNull(value)) {
                params.add(descriptor.getName());
            }
        }
        return params.toArray(new String[0]);
    }

}
