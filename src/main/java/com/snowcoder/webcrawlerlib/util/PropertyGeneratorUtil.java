package com.snowcoder.webcrawlerlib.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;

@Service
public class PropertyGeneratorUtil {

    private final Logger LOGGER = LoggerFactory.getLogger(PropertyGeneratorUtil.class);

    public <T> PropertyDescriptor createPropertyInstance(String reflectionName, T className){
        PropertyDescriptor pd = null;
        try {
            pd = new PropertyDescriptor(reflectionName, className.getClass());
        } catch (Exception e) {
            LOGGER.info("Error occured while creating property " + e.getMessage());
        }
        return pd;
    }

    public <T> T readFromProperty(T className, PropertyDescriptor pd){
        T element = null;
        try {
            element = (T) pd.getReadMethod().invoke(className);
        } catch (Exception e) {
            LOGGER.info("Error occured while reading property " + e.getMessage());
        }
        return element;
    }

    public <T> T writeToProperty(T element, T className, PropertyDescriptor pd){
        try {
            pd.getWriteMethod().invoke(className, element);
        } catch (Exception e) {
            LOGGER.info("Error occured while writing to property " + e.getMessage());
        }
        return className;
    }
}
