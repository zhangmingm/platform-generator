<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package.Mapper}.${table.mapperName}">

<#if enableCache>
    <!-- 开启二级缓存 -->
    <cache type="org.mybatis.caches.ehcache.LoggingEhcache"/>
</#if>



    <select id="select${entity}ListBySql" parameterType="${package.Entity}.${entity}"
            resultType="${package.Entity}.${entity}">
        select
    <#list table.fields as field>
        <#if field_index gte 0>
            <#if field_index != tableFieldSize>
                ${field.name} ${field.propertyName},
            <#else>
                ${field.name} ${field.propertyName}
            </#if>
        </#if>
    </#list>
        from ${tableName} where 1=1
    </select>


    <insert id="insert${entity}BySql" parameterType="${package.Entity}.${entity}"
            useGeneratedKeys="true" keyProperty="id" >
            INSERT INTO ${tableName} (
        <#list table.fields as field>
            <#if field_index gte 0 >
                <#if field_index != tableFieldSize>
                    ${field.name},
                <#else>
                    ${field.name}
                </#if>
            </#if>
        </#list>
             ) VALUES (
         <#list table.fields as field>
             <#if field_index gte 0 >
                 <#if field_index != tableFieldSize>
                     @{${field.propertyName}},
                 <#else>
                     @{${field.propertyName}}
                 </#if>
             </#if>
         </#list>
              )

    </insert>


    <delete id="delete${entity}BySql" parameterType="${package.Entity}.${entity}">
        delete from ${tableName} where 1=1

    </delete>


    <update id="update${entity}BySql" parameterType="${package.Entity}.${entity}">
            UPDATE ${tableName} SET
        <#list table.fields as field>
            <#if field_index gte 0 >
                <#if field_index != tableFieldSize>
                    ${field.name} = @{${field.propertyName}},
                <#else>
                    ${field.name} = @{${field.propertyName}}
                </#if>
            </#if>
        </#list>
             WHERE id = @{id}
    </update>



</mapper>
