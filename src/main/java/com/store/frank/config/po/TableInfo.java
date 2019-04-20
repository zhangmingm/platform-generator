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
package com.store.frank.config.po;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 表信息，关联到当前字段信息
 *
 * @author YangHu
 * @since 2016/8/30
 */
@Data
public class TableInfo {

    private final Set<String> importPackages = new HashSet<>();
    private String name; // 表字段
    private String comment; // 注释
    private String entityName; // 实体名称
    private String mapperName; // mapper名称
    private String xmlName; // xml名称
    private String serviceName; // service 接口名称
    private String serviceImplName; // service 接口实现类名称
    private String controllerName; // controller名称
    private List<TableField> fields;
    /**
     * 公共字段
     */
    private List<TableField> commonFields;


    private String fieldNames;


    public TableInfo setFields(List<TableField> fields) {
        if (CollectionUtils.isNotEmpty(fields)) {
            this.fields = fields;
            // 收集导入包信息
            for (TableField field : fields) {
                if (null != field.getColumnType() && null != field.getColumnType().getPkg()) {
                    importPackages.add(field.getColumnType().getPkg());
                }
                importPackages.add(com.baomidou.mybatisplus.annotation.TableField.class.getCanonicalName());
                if (null != field.getFill()) {
                    // 填充字段
                    importPackages.add(com.baomidou.mybatisplus.annotation.TableField.class.getCanonicalName());
                    importPackages.add(com.baomidou.mybatisplus.annotation.FieldFill.class.getCanonicalName());
                }
            }
        }
        return this;
    }

    public TableInfo setImportPackages(String pkg) {
        importPackages.add(pkg);
        return this;
    }

}
