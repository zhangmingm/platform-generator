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

import com.store.frank.config.base.StrategyConfig;
import com.store.frank.config.rule.IColumnType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * 表字段信息
 *
 * @author YangHu
 * @since 2016-12-03
 */
@Data
@Accessors(chain = true)
public class TableField {

//    private boolean convert;
//    private boolean keyFlag;
    /**
     * 主键是否为自增类型
     */
//    private boolean keyIdentityFlag;


    private String name; // 字段名称
    private String type; // 字段类型
    private String propertyName;  // java 属性
    private IColumnType columnType; // 字段类型
    private String comment; // 字段注释

    private String fill;
    /**
     * 自定义查询字段列表
     */
    private Map<String, Object> customMap;


    public TableField setPropertyName(StrategyConfig strategyConfig, String propertyName) {
        this.propertyName = propertyName;
        return this;
    }

    public String getPropertyType() {
        if (null != columnType) {
            return columnType.getType();
        }
        return null;
    }

}
