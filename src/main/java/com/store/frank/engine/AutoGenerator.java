package com.store.frank.engine;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.store.frank.config.base.ConfigBuilder;
import com.store.frank.config.base.DataSourceConfig;
import com.store.frank.config.base.GlobalConfig;
import com.store.frank.config.base.PackageConfig;
import com.store.frank.config.base.StrategyConfig;
import com.store.frank.config.po.TableInfo;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * 生成文件
 *
 * @author YangHu, tangguo, hubin
 * @since 2016-08-30
 */
@Data
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
     * 预处理配置
     * @param config 总配置信息
     * @return 解析数据结果集
     */
    protected ConfigBuilder pretreatmentConfigBuilder(ConfigBuilder config) {
        /*
         * 表信息列表
         */
        TableInfo tableInfo=config.getTableInfo();
            boolean importSerializable = true;
            if (StringUtils.isNotEmpty(config.getSuperEntityClass())) {
                // 父实体
                tableInfo.setImportPackages(config.getSuperEntityClass());
                importSerializable = false;
            }
            if (importSerializable) {
                tableInfo.setImportPackages(Serializable.class.getCanonicalName());
            }
         config.setTableInfo(tableInfo);
        return config;
    }

}
