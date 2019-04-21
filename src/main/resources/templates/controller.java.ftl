package ${package.Controller};


import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ${author}
 * @since ${date}
 * @Description: ${table.comment!} controller
 */
@RestController
@Api(description = "${table.comment!}")
public class ${table.controllerName} {

     @Autowired
     private ${table.serviceName} service;

   /**
    * @author ${author}
    * @since ${date}
    * @Description: 分页查询${entity}对象集合。
    */
    @GetMapping("/${entityInstanceName}/get${entity}List")
    @ApiOperation(notes = "分页查询${entity}对象集合", value = "分页查询${entity}对象集合")
    public PageInfo<${entity}> get${entity}Page(
                @RequestHeader(value = "X-Token") String token,
                @ApiParam(value="Call-Source",defaultValue="WEB") @RequestHeader(value = "Call-Source") String callSource,
                @ApiParam(value="当前页") @RequestParam int current,
                @ApiParam(value="每页页数") @RequestParam int pageSize,
                @ApiParam(value="${table.comment!}对象") @RequestBody ${entity} ${entityInstanceName}){
         return service.get${entity}Page(current,pageSize,${entityInstanceName});
    }


   /**
    * @author ${author}
    * @since ${date}
    * @Description: 新增${entity}对象。
    */
    @PostMapping("/${entityInstanceName}/insert${entity}")
    @ApiOperation(value = "新增${entity}对象", notes = "新增${entity}对象")
    public long insert${entity}(
        @RequestHeader(value = "X-Token") String token,
        @ApiParam(value="Call-Source",defaultValue="WEB") @RequestHeader(value = "Call-Source") String callSource,
        @ApiParam(value="${table.comment!}对象",required = true) @RequestBody ${entity} ${entityInstanceName}){
        return service.insert${entity}(${entityInstanceName});
    }


   /**
    * @author ${author}
    * @since ${date}
    * @Description: 根据id删除${entity}对象。
    */
    @GetMapping("/${entityInstanceName}/delete${entity}ById")
    @ApiOperation(value = "根据id删除${entity}对象", notes = "根据id删除${entity}对象")
    public String delete${entity}ById(
                @RequestHeader(value = "X-Token") String token,
                @ApiParam(value="Call-Source",defaultValue="WEB") @RequestHeader(value = "Call-Source") String callSource,
                @ApiParam(value="${table.comment!}对象") @RequestBody ${entity} ${entityInstanceName}){
            service.delete${entity}ById(${entityInstanceName});
            return "删除成功";
    }


   /**
    * @author ${author}
    * @since ${date}
    * @Description: 更新${entity}对象。
    */
    @PostMapping("/${entityInstanceName}/update${entity}")
    @ApiOperation(value = "更新${entity}对象", notes = "更新${entity}对象")
    public String update${entity}(
                @RequestHeader(value = "X-Token") String token,
                @ApiParam(value="Call-Source",defaultValue="WEB") @RequestHeader(value = "Call-Source") String callSource,
                @ApiParam(value="${table.comment!}对象") @RequestBody  ${entity} ${entityInstanceName}){
            service.update${entity}BySql(${entityInstanceName});
            return "修改成功";
    }


}
