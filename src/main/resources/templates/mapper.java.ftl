package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author ${author}
 * @since ${date}
 * @Description: ${table.comment!} Mapper 接口
 */
@Repository
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

   /**
    * @author ${author}
    * @since ${date}
    * @Description: 根据SQL添加${entity}对象。
    */
    public int insert${entity}BySql(${entity} entity);

   /**
    * @author ${author}
    * @since ${date}
    * @Description: 根据SQL删除${entity}对象。
    */
    public int delete${entity}BySql(${entity} entity);


   /**
    * @author ${author}
    * @since ${date}
    * @Description: 根据SQL修改${entity}对象
    */
    public int update${entity}BySql(${entity} entity);



   /**
    * @author ${author}
    * @since ${date}
    * @Description: 多条件查询${entity}对象
    */
    public List<${entity}> select${entity}ListBySql(${entity} entity);





}
