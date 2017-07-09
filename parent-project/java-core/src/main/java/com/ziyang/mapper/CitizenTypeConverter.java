package com.ziyang.mapper;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.ziyang.entity.CitizenType;
import com.ziyang.utils.AppServiceUtil;

@Component
public class CitizenTypeConverter implements Converter<String, CitizenType> {

	@Override
	public CitizenType convert(String source) {
		return AppServiceUtil.getCitizenType(source);
	}

}
