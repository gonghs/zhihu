package ice.maple.springbootrest.dao;

import ice.maple.springbootrest.model.Attach;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

//指定访问的路径名  默认为类名小写加s/es（英语复数形式） 保持驼峰式  例如 UserVo --- userVoes
//@RepositoryRestResource(path = "attach")
public interface AttachRepository extends MongoRepository<Attach,String> {
    //未指定路径则访问方法名
    @RestResource(path = "urlStartsWith")
    Attach findByUrlStartsWith(@Param("url")String url);
}
