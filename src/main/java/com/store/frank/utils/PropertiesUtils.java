package com.store.frank.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @author zhangmingming  braveheart1115@163.com
 * @date 2019年04月18日 14:40
 * @Description:
 */
public class PropertiesUtils {

    protected static final Logger log = LoggerFactory.getLogger(PropertiesUtils.class);


    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2019/4/20 0020 下午 1:18
     * @Description: 加载 resources 目录下的固定文件。
     */
    public static Properties getProperties(){
        Properties properties=new Properties();
        FileInputStream in = null;
        String projectPath = System.getProperty("user.dir");
        String propertyPath=projectPath+"/src/main/resources/mybatis-mysql.properties";
        try {
            in = new FileInputStream(propertyPath);
        } catch (FileNotFoundException e) {
            log.error("缺少配置文件: mybatis-mysql.properties");
            e.printStackTrace();
        }
        try {
            properties.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }


    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2019/4/20 0020 下午 1:18
     * @Description: 加载 resources 目录下的指定名称的文件。
     */
    public static Properties getProperties(String fileName){
        Properties properties=new Properties();
        FileInputStream in = null;
        String projectPath = System.getProperty("user.dir");
        String propertyPath=projectPath+"/src/main/resources/"+fileName;
        try {
            in = new FileInputStream(propertyPath);
        } catch (FileNotFoundException e) {
            log.error("缺少配置文件: mybatis-mysql.properties");
            e.printStackTrace();
        }
        try {
            properties.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

}
