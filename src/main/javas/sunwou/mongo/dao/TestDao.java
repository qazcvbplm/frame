package sunwou.mongo.dao;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;

import sunwou.entity.App;
import sunwou.mongo.util.QueryObject;

public interface TestDao {

	/**
	 * 添加一条记录
	 * @param t
	 * @return
	 */
	String add(App t);
	
	/**
	 * 按属性查询
	 * @param query  属性类
	 * @param skip
	 * @param limit
	 * @param className
	 * @return
	 */
	List<App> find(QueryObject qo) ;
	
	/**
	 * 按id查询
	 * @param id
	 * @param className
	 * @return
	 */
	App findById(String id,String className);
	/**
	 * 查询总条数
	 * @param query
	 * @param className
	 * @return
	 */
	int count(QueryObject qo);
	
	/**
	 * 更新记录
	 * @param query  条件
	 * @param update  更新内容
	 * @param className
	 * @return
	 */
	int update(QueryObject qo,App update);
	/**
	 * 更新记录
	 * @param query  条件
	 * @param update  更新内容
	 * @param className
	 * @return
	 */
	int updateById(App updateo,String className);
	/**
	 * 删除记录
	 * @param imageAndText
	 * @param className
	 * @return
	 */
	int remove(QueryObject qo);
	
	MongoTemplate getMongoTemplate();
}
