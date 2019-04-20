package com.store.frank.config.base;

import com.store.frank.config.rule.DateType;
import com.store.frank.utils.PropertiesUtils;
import lombok.Data;

import java.util.Properties;

/**
 * 全局配置
 * @author hubin
 * @since 2016-12-02
 */
@Data
public class GlobalConfig {

    /**
     * 生成文件的输出目录【默认 D 盘根目录】
     */
    private String outputDir = "D://";

    /**
     * 是否覆盖已有文件
     */
    private boolean fileOverride = false;

    /**
     * 是否打开输出目录
     */
    private boolean open = false;

    /**
     * 是否在xml中添加二级缓存配置
     */
    private boolean enableCache = false;

    /**
     * 开发人员
     */
    private String author;


    /**
     * 开启 swagger2 模式
     */
    private boolean swagger2 = true;



    /**
     * 时间类型对应策略
     */
    private DateType dateType = DateType.TIME_PACK;

    /**
     * 开启 baseColumnList
     */
    private boolean baseColumnList = false;
    /**
     * 各层文件名称方式，例如： %sAction 生成 UserAction
     * %s 为占位符
     */
    private String entityName;
    private String mapperName;
    private String xmlName;
    private String serviceName;
    private String serviceImplName;
    private String controllerName;

    public GlobalConfig() {
        Properties properties=PropertiesUtils.getProperties();
        String author= (String) properties.get("author");
        String outputDir= (String) properties.get("outputDir");
        this.outputDir=outputDir;
        this.author=author;
    }

}
