package ${package.Service};

import ${package.Entity}.${entity};
import java.util.List;
import com.github.pagehelper.PageInfo;


/**
 * @author ${author}
 * @since ${date}
 * @Description: ${table.comment!} service 接口
 */
public interface ${table.serviceName}{


   /**
    * @author ${author}
    * @since ${date}
    * @Description: 新增${entity}对象。
    */
    public int insert${entity}(${entity} ${entityInstanceName});

   /**
    * @author ${author}
    * @since ${date}
    * @Description: 根据SQL新增${entity}对象。
    */
    public int insert${entity}BySql(${entity} ${entityInstanceName});

   /**
    * @author ${author}
    * @since ${date}
    * @Description: 删除${entity}对象。
    */
    public int delete${entity}ById(${entity} ${entityInstanceName});

   /**
    * @author ${author}
    * @since ${date}
    * @Description: 根据SQL删除${entity}对象。
    */
    public int delete${entity}BySql(${entity} ${entityInstanceName});

   /**
    * @author ${author}
    * @since ${date}
    * @Description: 修改${entity}对象。
    */
    public int update${entity}(${entity} ${entityInstanceName});

   /**
    * @author ${author}
    * @since ${date}
    * @Description: 根据SQL修改${entity}对象。
    */
    public int update${entity}BySql(${entity} ${entityInstanceName});


   /**
    * @author ${author}
    * @since ${date}
    * @Description: 根据id查询${entity}对象。
    */
    public ${entity} selectSingle${entity}ById(${entity} entity);


   /**
    * @author ${author}
    * @since ${date}
    * @Description: 根据SQL查询${entity}对象集合。
    */
    public List<${entity}> select${entity}ListBySql(${entity} entity);

    /**
     * @author zhangmingming  braveheart1115@163.com
     * @since 2019-04-19 01:04:24
     * @Description: 分页查询User对象。
     */


   /**
    * @author ${author}
    * @since ${date}
    * @Description: 分页查询${entity}对象集合。
    */
    public PageInfo<${entity}> get${entity}Page(int current,int pageSize,${entity} entity);





}
