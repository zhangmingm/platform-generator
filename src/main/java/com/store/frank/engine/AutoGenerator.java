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
package com.store.frank.engine;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.store.frank.config.base.ConfigBuilder;
import com.store.frank.config.base.DataSourceConfig;
import com.store.frank.config.base.GlobalConfig;
import com.store.frank.config.base.PackageConfig;
import com.store.frank.config.base.StrategyConfig;
import com.store.frank.config.po.TableInfo;
import lombok.Data;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

/**
 * 生成文件
 *
 * @author YangHu, tangguo, hubin
 * @since 2016-08-30
 */
@Data
@Accessors(chain = true)
public class AutoGenerator {
    private static final Logger logger = LoggerFactory.getLogger(AutoGenerator.class);

    /**
     * 生成代码
     */
    public void execute() {
        logger.debug("==========================准备生成文件...==========================");
        // 初始化配置
        PackageConfig packageInfo=new PackageConfig();
        DataSourceConfig dataSource=new DataSourceConfig();
        StrategyConfig strategy=new StrategyConfig();
        GlobalConfig globalConfig=new GlobalConfig();
        ConfigBuilder config = new ConfigBuilder(packageInfo, dataSource, strategy,globalConfig);
        AbstractTemplateEngine templateEngine = new FreemarkerTemplateEngine();
        // 模板引擎初始化执行文件输出
        ConfigBuilder configBuilder=pretreatmentConfigBuilder(config);
        AbstractTemplateEngine engine= templateEngine.init(configBuilder);
        engine.mkdirs();
        engine.batchOutput();
        logger.debug("==========================文件生成完成！！！==========================");
    }

    /**
     * 开放表信息、预留子类重写
     *
     * @param config 配置信息
     * @return ignore
     */
    protected List<TableInfo> getAllTableInfoList(ConfigBuilder config) {
        return config.getTableInfoList();
    }

    /**
     * 预处理配置
     * @param config 总配置信息
     * @return 解析数据结果集
     */
    protected ConfigBuilder pretreatmentConfigBuilder(ConfigBuilder config) {
        /*
         * 表信息列表
         */
        List<TableInfo> tableList = this.getAllTableInfoList(config);
        for (TableInfo tableInfo : tableList) {
            /* ---------- 添加导入包 ---------- */
            boolean importSerializable = true;
            if (StringUtils.isNotEmpty(config.getSuperEntityClass())) {
                // 父实体
                tableInfo.setImportPackages(config.getSuperEntityClass());
                importSerializable = false;
            }
            if (importSerializable) {
                tableInfo.setImportPackages(Serializable.class.getCanonicalName());
            }
        }
        return config.setTableInfoList(tableList);
    }

}
