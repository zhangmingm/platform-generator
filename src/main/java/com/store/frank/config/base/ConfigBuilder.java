/*
 * Copyright (c) 2011-2019, hubin (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.store.frank.config.base;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.store.frank.config.db.IDbQuery;
import com.store.frank.config.po.TableField;
import com.store.frank.config.po.TableInfo;
import com.store.frank.utils.ConstVal;
import com.store.frank.utils.StringTools;
import lombok.Data;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配置汇总 传递给文件生成工具
 *
 * @author YangHu, tangguo, hubin
 * @since 2016-08-30
 */
@Data
public class ConfigBuilder {

    /**
     * 模板路径配置信息
     */
    private final TemplateConfig template;
    /**
     * 数据库配置
     */
    private final DataSourceConfig dataSourceConfig;
    /**
     * SQL连接
     */
    private Connection connection;
    /**
     * SQL语句类型
     */
    private IDbQuery dbQuery;
    private String superEntityClass;
    private String superMapperClass;
    /**
     * service超类定义
     */
    private String superServiceClass;
    private String superServiceImplClass;
    /**
     * 数据库表信息
     */
    private TableInfo tableInfo;
    private List<TableInfo> tableInfoList;
    /**
     * 包配置详情
     */
    private Map<String, String> packageInfo;
    /**
     * 路径配置信息
     */
    private Map<String, String> pathInfo;
    /**
     * 策略配置
     */
    private StrategyConfig strategyConfig;
    /**
     * 全局配置信息
     */
    private GlobalConfig globalConfig;


    /**
     * 在构造器中处理配置
     *
     * @param packageConfig    包配置
     * @param dataSourceConfig 数据源配置
     * @param strategyConfig   表配置
     * @param globalConfig     全局配置
     */
    public ConfigBuilder(PackageConfig packageConfig, DataSourceConfig dataSourceConfig, StrategyConfig strategyConfig,
                         GlobalConfig globalConfig) {
        // 全局配置
        if (null == globalConfig) {
            this.globalConfig = new GlobalConfig();
        } else {
            this.globalConfig = globalConfig;
        }

        this.template = new TemplateConfig();

        // 包配置
        if (null == packageConfig) {
            handlerPackage(this.template, this.globalConfig.getOutputDir(), new PackageConfig());
        } else {
            handlerPackage(this.template, this.globalConfig.getOutputDir(), packageConfig);
        }
        this.dataSourceConfig = dataSourceConfig;
        handlerDataSource(dataSourceConfig);
        // 策略配置
        if (null == strategyConfig) {
            this.strategyConfig = new StrategyConfig();
        } else {
            this.strategyConfig = strategyConfig;
        }
        handlerStrategy(this.strategyConfig);
    }

    // ************************ 曝露方法 BEGIN*****************************

    /**
     * 所有包配置信息
     *
     * @return 包配置
     */
    public Map<String, String> getPackageInfo() {
        return packageInfo;
    }


    /**
     * 所有路径配置
     *
     * @return 路径配置
     */
    public Map<String, String> getPathInfo() {
        return pathInfo;
    }


    public String getSuperEntityClass() {
        return superEntityClass;
    }


    public String getSuperMapperClass() {
        return superMapperClass;
    }


    /**
     * 获取超类定义
     *
     * @return 完整超类名称
     */
    public String getSuperServiceClass() {
        return superServiceClass;
    }


    public String getSuperServiceImplClass() {
        return superServiceImplClass;
    }




    /**
     * 模板路径配置信息
     *
     * @return 所以模板路径配置信息
     */
    public TemplateConfig getTemplate() {
        return template == null ? new TemplateConfig() : template;
    }

    // ****************************** 曝露方法 END**********************************

    /**
     * 处理包配置
     *
     * @param template  TemplateConfig
     * @param outputDir
     * @param config    PackageConfig
     */
    private void handlerPackage(TemplateConfig template, String outputDir, PackageConfig config) {
        // 包信息
        packageInfo = new HashMap<>(8);
        packageInfo.put(ConstVal.MODULE_NAME, config.getModuleName());
        packageInfo.put(ConstVal.ENTITY, joinPackage(config.getParent(), config.getEntity()));
        packageInfo.put(ConstVal.MAPPER, joinPackage(config.getParent(), config.getMapper()));
        packageInfo.put(ConstVal.XML, joinPackage(config.getParent(), config.getXml()));
        packageInfo.put(ConstVal.SERVICE, joinPackage(config.getParent(), config.getService()));
        packageInfo.put(ConstVal.SERVICE_IMPL, joinPackage(config.getParent(), config.getServiceImpl()));
        packageInfo.put(ConstVal.CONTROLLER, joinPackage(config.getParent(), config.getController()));

        // 生成路径信息
        pathInfo = new HashMap<>(6);
        setPathInfo(pathInfo, template.getEntity(), outputDir, ConstVal.ENTITY_PATH, ConstVal.ENTITY);
        setPathInfo(pathInfo, template.getMapper(), outputDir, ConstVal.MAPPER_PATH, ConstVal.MAPPER);
        String xmlPathTemp=outputDir.replace("java","resources")+"//mapper";
        setPathInfo(pathInfo, template.getXml(), xmlPathTemp, ConstVal.XML_PATH, ConstVal.XML);
        setPathInfo(pathInfo, template.getService(), outputDir, ConstVal.SERVICE_PATH, ConstVal.SERVICE);
        setPathInfo(pathInfo, template.getServiceImpl(), outputDir, ConstVal.SERVICE_IMPL_PATH, ConstVal.SERVICE_IMPL);
        setPathInfo(pathInfo, template.getController(), outputDir, ConstVal.CONTROLLER_PATH, ConstVal.CONTROLLER);

    }

    private void setPathInfo(Map<String, String> pathInfo, String template, String outputDir, String path, String module) {
        if (StringUtils.isNotEmpty(template)) {
            if(ConstVal.XML.equals(module)){
                pathInfo.put(path, outputDir);
            }else{
                pathInfo.put(path, joinPath(outputDir, packageInfo.get(module)));
            }
        }
    }

    /**
     * 处理数据源配置
     *
     * @param config DataSourceConfig
     */
    private void handlerDataSource(DataSourceConfig config) {
        connection = config.getConn();
        dbQuery = config.getDbQuery();
    }


    /**
     * 处理数据库表 加载数据库表、列、注释相关数据集
     *
     * @param config StrategyConfig
     */
    private void handlerStrategy(StrategyConfig config) {
        processTypes(config);
        tableInfo = getTablesInfo(config);
    }


    /**
     * 处理superClassName,IdClassType,IdStrategy配置
     *
     * @param config 策略配置
     */
    private void processTypes(StrategyConfig config) {
        if (StringUtils.isEmpty(config.getSuperServiceClass())) {
            superServiceClass = ConstVal.SUPER_SERVICE_CLASS;
        } else {
            superServiceClass = config.getSuperServiceClass();
        }
        if (StringUtils.isEmpty(config.getSuperServiceImplClass())) {
            superServiceImplClass = ConstVal.SUPER_SERVICE_IMPL_CLASS;
        } else {
            superServiceImplClass = config.getSuperServiceImplClass();
        }
        if (StringUtils.isEmpty(config.getSuperMapperClass())) {
            superMapperClass = ConstVal.SUPER_MAPPER_CLASS;
        } else {
            superMapperClass = config.getSuperMapperClass();
        }
        superEntityClass = config.getSuperEntityClass();
    }


    /**
     * 处理表对应的类名称
     *
//     * @param tableList 表名称
     * @return 补充完整信息后的表
     */
    private TableInfo processTable(TableInfo tableInfo) {
            String entityName = tableInfo.getEntityName(); // NamingStrategy.capitalFirst(tableInfo.getName());
            tableInfo.setMapperName(entityName + ConstVal.MAPPER);
            tableInfo.setXmlName(entityName + ConstVal.MAPPER);
            tableInfo.setServiceName("I" + entityName + ConstVal.SERVICE);
            tableInfo.setServiceImplName(entityName + ConstVal.SERVICE_IMPL);
            tableInfo.setControllerName(entityName + ConstVal.CONTROLLER);
            // 检测导入包
            checkImportPackages(tableInfo);
        return tableInfo;
    }

    /**
     * 检测导入包
     *
     * @param tableInfo ignore
     */
    private void checkImportPackages(TableInfo tableInfo) {
        if (StringUtils.isNotEmpty(strategyConfig.getSuperEntityClass())) {
            // 自定义父类
            tableInfo.getImportPackages().add(strategyConfig.getSuperEntityClass());
        }
    }


    /**
     * 获取所有的数据库表信息
     */
    private TableInfo getTablesInfo(StrategyConfig config) {
        String tableName=config.getTableName();
        TableInfo tableInfo=new TableInfo();
        try {
            String tablesSql = dbQuery.tablesSql(tableName);
            try (PreparedStatement preparedStatement = connection.prepareStatement(tablesSql);
                 ResultSet results = preparedStatement.executeQuery()) {
                while (results.next()) {
                    if (StringUtils.isNotEmpty(tableName)) {
                        String tableComment = results.getString(dbQuery.tableComment());
                        tableInfo.setName(tableName);
                        tableInfo.setEntityName(StringTools.dbField2UpperCamel(tableName));
                        tableInfo.setComment(tableComment);
                    } else {
                        System.err.println("当前数据库为空！！！");
                    }
                }
            }
            convertTableFields(tableInfo);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return processTable(tableInfo);
    }



    /**
     * 将字段信息与表信息关联
     *
     * @param tableInfo 表信息
     * @return ignore
     */
    private TableInfo convertTableFields(TableInfo tableInfo/*, NamingStrategy strategy*/) {
        List<TableField> fieldList = new ArrayList<>();
        List<TableField> commonFieldList = new ArrayList<>();
        String tableName = tableInfo.getName();
        try {
            String tableFieldsSql = dbQuery.tableFieldsSql();
            tableFieldsSql = String.format(tableFieldsSql, tableName);
            try (
                PreparedStatement preparedStatement = connection.prepareStatement(tableFieldsSql);
                ResultSet results = preparedStatement.executeQuery()) {
                while (results.next()) {
                    TableField field = new TableField();
                    String columnName = results.getString(dbQuery.fieldName());

                    // 处理其它信息
                    field.setName(columnName);
                    field.setType(results.getString(dbQuery.fieldType()));
                    field.setPropertyName(StringTools.dbField2LowerCamel(field.getName()));
                    field.setColumnType(dataSourceConfig.getTypeConvert().processTypeConvert(globalConfig, field.getType()));
                    field.setComment(results.getString(dbQuery.fieldComment()));
                    if (strategyConfig.includeSuperEntityColumns(field.getName())) {
                        // 跳过公共字段
                        commonFieldList.add(field);
                        continue;
                    }
                    fieldList.add(field);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception：" + e.getMessage());
        }
        tableInfo.setFields(fieldList);
        tableInfo.setCommonFields(commonFieldList);
        return tableInfo;
    }


    /**
     * 连接路径字符串
     *
     * @param parentDir   路径常量字符串
     * @param packageName 包名
     * @return 连接后的路径
     */
    private String joinPath(String parentDir, String packageName) {
        if (StringUtils.isEmpty(parentDir)) {
            parentDir = System.getProperty(ConstVal.JAVA_TMPDIR);
        }
        if (!StringUtils.endsWith(parentDir, File.separator)) {
            parentDir += File.separator;
        }
        packageName = packageName.replaceAll("\\.", StringPool.BACK_SLASH + File.separator);
        String result=parentDir + packageName;
//        result=result.replace("\\","//");
        return result;
    }


    /**
     * 连接父子包名
     *
     * @param parent     父包名
     * @param subPackage 子包名
     * @return 连接后的包名
     */
    private String joinPackage(String parent, String subPackage) {
        if (StringUtils.isEmpty(parent)) {
            return subPackage;
        }
        return parent + StringPool.DOT + subPackage;
    }


    /**
     * 处理字段名称
     *
     * @return 根据策略返回处理后的名称
     */
    /*private String processName(String name) {
        return name;
    }*/



    public StrategyConfig getStrategyConfig() {
        return strategyConfig;
    }



    public GlobalConfig getGlobalConfig() {
        return globalConfig;
    }


}
