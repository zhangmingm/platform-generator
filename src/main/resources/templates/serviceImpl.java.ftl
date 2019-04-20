package ${package.ServiceImpl};

import ${package.Mapper}.${table.mapperName};
import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.sstech.framework.token.token.helper.LoginTokenHelper;


/**
 * @author ${author}
 * @since ${date}
 * @Description: ${table.comment!} service 接口实现类
 */
@Service
public class ${table.serviceImplName}  implements ${table.serviceName} {

     @Autowired
     private ${table.mapperName} mapper;


   /**
    * @author ${author}
    * @since ${date}
    * @Description: 新增${entity}对象。
    */
    public int insert${entity}(${entity} ${entityInstanceName}){
        long userId=LoginTokenHelper.getLoginUserFromRequest().getId();
        ${entityInstanceName}.setCreateBy(userId);
        return mapper.insert(${entityInstanceName});
    }

   /**
    * @author ${author}
    * @since ${date}
    * @Description: 根据SQL新增${entity}对象。
    */
    public int insert${entity}BySql(${entity} ${entityInstanceName}){
        long userId=LoginTokenHelper.getLoginUserFromRequest().getId();
        ${entityInstanceName}.setCreateBy(userId);
        return mapper.insert${entity}BySql(${entityInstanceName});
    }

   /**
    * @author ${author}
    * @since ${date}
    * @Description: 删除${entity}对象。
    */
    public int delete${entity}ById(${entity} ${entityInstanceName}){
        return mapper.deleteById(${entityInstanceName}.getId());
    }

   /**
    * @author ${author}
    * @since ${date}
    * @Description: 根据SQL删除${entity}对象。
    */
    public int delete${entity}BySql(${entity} ${entityInstanceName}){
        return mapper.delete${entity}BySql(${entityInstanceName});
    }

   /**
    * @author ${author}
    * @since ${date}
    * @Description: 修改${entity}对象。
    */
    public int update${entity}(${entity} ${entityInstanceName}){
        long userId=LoginTokenHelper.getLoginUserFromRequest().getId();
        ${entityInstanceName}.setUpdateBy(userId);
        return mapper.updateById(${entityInstanceName});
    }

   /**
    * @author ${author}
    * @since ${date}
    * @Description: 根据SQL修改${entity}对象。
    */
    public int update${entity}BySql(${entity} ${entityInstanceName}){
        long userId=LoginTokenHelper.getLoginUserFromRequest().getId();
        ${entityInstanceName}.setUpdateBy(userId);
       return mapper.update${entity}BySql(${entityInstanceName});
    }


   /**
    * @author ${author}
    * @since ${date}
    * @Description: 根据id查询${entity}对象。
    */
    public ${entity} selectSingle${entity}ById(${entity} ${entityInstanceName}){
        return mapper.selectById(${entityInstanceName}.getId());
    }


   /**
    * @author ${author}
    * @since ${date}
    * @Description: 根据SQL修改${entity}对象。
    */
    public List<${entity}> select${entity}ListBySql(${entity} ${entityInstanceName}){
        return mapper.select${entity}ListBySql(${entityInstanceName});
    }


    /**
    * @author ${author}
    * @since ${date}
    * @Description: 分页查询${entity}对象集合。
    */
    public PageInfo<${entity}> get${entity}Page(int current,int pageSize,${entity} entity){
        PageHelper.startPage(current,pageSize);
        List<${entity}> list=mapper.select${entity}ListBySql(entity);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

}
