package com.oopsmails.test.config;

import com.oopsmails.annotation.CustomTestAnnotation;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

/**
 * Created by liu on 2017-07-11.
 */
public class OopsRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
//    private RequestMappingInfo.BuilderConfiguration config = new RequestMappingInfo.BuilderConfiguration();

    @Override
    protected boolean isHandler(Class<?> beanType) {
        return (AnnotatedElementUtils.hasAnnotation(beanType, CustomTestAnnotation.class) ||
                super.isHandler(beanType));
    }

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo info = createRequestMappingInfo(method);
        if (info != null) {
            RequestMappingInfo typeInfo = createRequestMappingInfo(handlerType);
            if (typeInfo != null) {
                info = typeInfo.combine(info);
            }
        }
        return info;
    }

    private RequestMappingInfo createRequestMappingInfo(AnnotatedElement element) {
        RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(element, RequestMapping.class);
        RequestCondition<?> condition = (element instanceof Class<?> ?
                getCustomTypeCondition((Class<?>) element) : getCustomMethodCondition((Method) element));
        RequestMappingInfo result = (requestMapping != null ? createRequestMappingInfo(requestMapping, condition) : null);

        if (result == null) {
            CustomTestAnnotation customTestAnnotation = AnnotatedElementUtils.findMergedAnnotation(element, CustomTestAnnotation.class);
            result = (customTestAnnotation != null ? createRequestMappingInfo(customTestAnnotation, condition) : null);
        }

        return result;
    }

    protected RequestMappingInfo createRequestMappingInfo(
            CustomTestAnnotation customTestAnnotation, RequestCondition<?> customCondition) {

        return RequestMappingInfo
                .paths(resolveEmbeddedValuesInPatterns(customTestAnnotation.value()))
                .methods(customTestAnnotation.method())
                .params(customTestAnnotation.params())
                .headers(customTestAnnotation.headers())
                .consumes(customTestAnnotation.consumes())
                .produces(customTestAnnotation.produces())
                .mappingName(customTestAnnotation.name())
                .customCondition(customCondition)
//                .options(this.config)
                .build();
    }
}
