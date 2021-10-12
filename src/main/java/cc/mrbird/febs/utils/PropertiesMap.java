package cc.mrbird.febs.utils;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertiesMap {
    public static void main(String[] args) {
//        Map<String,String> map  = getProperties();

    }
    public static Map<String,String>  getProperties(){

        Map<String, String> map = new HashMap();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        //处理映射配置信息
        Properties mappingProperties = new Properties();
        InputStream inputStream =  loader.getResourceAsStream("courier.properties");
        BufferedReader bf = new BufferedReader(new  InputStreamReader(inputStream));
        try {
            mappingProperties.load(bf);
//                mappingProperties.load(mappingPropertiesInStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Enumeration mappingPropertiesEenumeration = mappingProperties.propertyNames();
        while (mappingPropertiesEenumeration.hasMoreElements()) {
            String key = (String) mappingPropertiesEenumeration.nextElement();
            String value = mappingProperties.getProperty(key);
            map.put(key, value);
            System.out.println(key +"---" +value);
        }
        return  map;
    }
    }

