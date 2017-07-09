package com.ziyang.mapper;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Component;

@Component
public class DbResultConversionService extends DefaultConversionService implements ApplicationContextAware, InitializingBean {

	private ApplicationContext applicationContext;

	@Override
	public void afterPropertiesSet() {
		for (Converter<?, ?> converter : applicationContext.getBeansOfType(Converter.class).values()) {
			addConverter(converter);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
}
