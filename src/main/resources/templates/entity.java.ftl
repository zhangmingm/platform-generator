package ${package.Entity};

<#list table.importPackages as pkg>
import ${pkg};
</#list>
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* @author ${author}
* @since ${date}
* @Description: ${table.comment!} 实体对象
*/
@Data
@ApiModel(value="${entity}对象", description="${table.comment!}")
public class ${entity}  implements Serializable{

<#list table.fields as field>
    <#if field.comment!?length gt 0>
    @ApiModelProperty(value = "${field.comment}")
    private ${field.propertyType} ${field.propertyName};

    </#if>
</#list>

}
