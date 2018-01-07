package sunwou.mongo.dao;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;

import sunwou.entity.Test;

public interface TestDao {

	/**
	 * 添加一条记录
	 * @param t
	 * @return
	 */
	String add(Test t);
	
	/**
	 * 按属性查询
	 * @param query  属性类
	 * @param skip
	 * @param limit
	 * @param className
	 * @return
	 */
	List<Test> find(Test query,String className) ;
	
	/**
	 * 按id查询
	 * @param id
	 * @param className
	 * @return
	 */
	Test findById(String id,String className);
	/**
	 * 查询总条数
	 * @param query
	 * @param className
	 * @return
	 */
	int count(Test query,String className);
	
	/**
	 * 更新记录
	 * @param query  条件
	 * @param update  更新内容
	 * @param className
	 * @return
	 */
	int update(Test query,Test update,String className);
	
	/**
	 * 删除记录
	 * @param imageAndText
	 * @param className
	 * @return
	 */
	int remove(Test imageAndText,String className);
	
	MongoTemplate getMongoTemplate();
}
