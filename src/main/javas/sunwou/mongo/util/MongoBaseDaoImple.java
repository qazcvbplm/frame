package sunwou.mongo.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import sunwou.entity.Test;
import sunwou.util.StringUtil;
import sunwou.util.TimeUtil;
import sunwou.util.Util;

/**
 * 
 * @author onepieces
 * mongodb数据库操作类的父类
 * 定义了所有类的公共属性
 * 实现了所有数据的增删改查的基础方法
 * @param <T>
 */
@Component("mongoBaseDao")
public class MongoBaseDaoImple<T extends MongoBaseEntity> implements MongoBaseDao<T>{
	
	@Autowired
	public  MongoTemplate mongoTemplate;
	
	//默认从0开始取
	private static final int MIN_COUNT=0;
	//默认取的条数
	private static final int MAX_COUNT=20;
	
    public static Map<String, Class> classes = new HashMap<String, Class>();
	  
	  
	  public static final String ENTITYBASE="mongoBaseEntity";

	  
	  static{
		  classes.put(ENTITYBASE, new MongoBaseEntity().getClass());
		  classes.put("test", new Test().getClass());
	  }
	
	/**
	 * 增加记录的方法
	 * @param add
	 * @return  返回唯一ID
	 */
	public String add(T add)
	{
		String time=TimeUtil.sdfCommon.format(new Date());
		add.setCreateTime(time);
		add.setCreateDate(time.substring(0, 10));
		mongoTemplate.save(add);
		return add.getSunwouId();
	}

	/**
	 * 根据实体类的属性来查询
	 * @param queryo
	 * @param skip 从第skip条数据开始
	 * @param limit 取limit条数据  
	 * @return  返回查询结果
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public List<T> find(T queryo,String className) 
	{
			  Query	query = mongoutilQ(queryo, className);
			return (List<T>) mongoTemplate.find(query,classes.get(className),className);
	}
	
	
	/**
	 * 获取数据量的总数
	 * @param queryo
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public int count(T queryo,String className)
	{
			    Query query = mongoutilQ(queryo, className);
				return  (int)mongoTemplate.count(query, className);
	}
	
	
	/**
	 * 更新数据
	 * @param queryo
	 * @param updateo
	 * @return  更新数量
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public int update(T queryo,T updateo,String className)
	{
		    Query query = mongoutilQ(queryo, className);
			updateo.setLastUpdateTime(TimeUtil.sdfCommon.format(new Date()));
			Update update=mongoutilU(updateo, className);
			return mongoTemplate.updateMulti(query, update, className).getN();
	}
	
	
	/**
	 * 更新一条数据
	 * @param queryo
	 * @param updateo
	 * @return  更新数量
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public int updateById(T updateo,String className)
	{
		    Query query = new Query(Criteria.where("_id").is(updateo.getSunwouId()));
			updateo.setLastUpdateTime(TimeUtil.sdfCommon.format(new Date()));
			Update update=mongoutilU(updateo, className);
			return mongoTemplate.updateFirst(query, update, className).getN();
	}
	
	
	/**
	 * 删除一条数据
	 * @param queryo
	 * @return 删除数量
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public int remove(T queryo,String className)
	{
			Query query = mongoutilQ(queryo, className);
			Update update=new Update();
			update.set("isDelete", true);
			update.set("deleteTime", TimeUtil.sdfCommon.format(new Date()));
			return mongoTemplate.updateMulti(query, update, className).getN();
	}

	@Override
	public T findById(String id,String className) {
		Criteria c=new Criteria();
		c.andOperator(Criteria.where("_id").is(id),Criteria.where("isDelete").is(false));
		List<MongoBaseEntity> rs=mongoTemplate.find(new Query(c).limit(1), classes.get(className));
		if(rs.size()==0)
		return  null;
		else
		return (T) rs.get(0);
	}

	@Override
	public MongoTemplate getMongoTemplate() {
		return this.mongoTemplate;
	}
	
	
	
	/**
     * 返回query查询对象 mongo
     * @param ob
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    private  Query mongoutilQ(Object ob, String classname) {
    	Query query=new Query();
        Criteria c = new Criteria();
        List<Criteria> param = new ArrayList<Criteria>();
        Object value=null;
        Class cl=classes.get(classname);
        Class base=classes.get(ENTITYBASE);
        //判断取的条件
        for (Field f : base.getDeclaredFields()) {
            f.setAccessible(true);
                try {
					value = f.get(ob);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					value=null;
				}
                if (check(value)){
                	if(f.getName().equals("sunwouId"))
                      param.add(Criteria.where("_id").is(value));
                	else if(f.getName().equals("where"))
                	{
							JsonObject where = null;
							try {
								where = Util.gson.fromJson(value.toString(), JsonObject.class);
							} catch (JsonSyntaxException e) {
							}
							if(where!=null)
							{
								if(where.get("fields")!=null)
								{
									JsonArray fields=where.get("fields").getAsJsonArray();
									int size=fields.size();
									for(int i=0;i<size;i++)
									{
										query.fields().include(fields.get(i).getAsString());
									}
								}
								if(where.get("sort")!=null)
								{
									JsonArray sort=where.get("sort").getAsJsonArray();
									int size=sort.size();
									JsonObject temp;
									for(int i=0;i<size;i++)
									{
										temp=sort.get(i).getAsJsonObject();
										if(temp.get("value").getAsString().equals("asc"))
										{
											query.with(new Sort(Direction.ASC, temp.get("name").getAsString()));
										}else
										{
											query.with(new Sort(Direction.DESC, temp.get("name").getAsString()));
										}
									}
								}
								if(where.get("page")!=null)
								{
									JsonObject page=where.get("page").getAsJsonObject();
									int currentPage=page.get("currentPage").getAsInt();
									int size=page.get("size").getAsInt();
									query.skip((currentPage-1)*size).limit(size);
								}else
								{
									query.limit(MAX_COUNT);
								}
							}
                	}
                	else
                	  param.add(Criteria.where(f.getName()).is(value));
                }
        }
        for (Field f : cl.getDeclaredFields()) {
            f.setAccessible(true);
                try {
					value = f.get(ob);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					value=null;
				}
                if (check(value)){
               param.add(Criteria.where(f.getName()).is(value));
                }
        }
        if (param.size() > 0){
            c.andOperator(param.toArray(new Criteria[param.size()]));
        }
        //添加条件
        query.addCriteria(c);
        return query;
    }

    /**
     * 返回update查询对象 mongo
     * @param ob
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    private  Update mongoutilU(Object ob, String classname)  {
        Update update = new Update();
        Object value=null;
        Class cl=classes.get(classname);
        Class base=classes.get(ENTITYBASE);
        for (Field f : base.getDeclaredFields()) {
            f.setAccessible(true);
                try {
					value = f.get(ob);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					value=null;
				}
                if (check(value)){
	                update.set(f.getName(), value);
	                }
        }
        for (Field f : cl.getDeclaredFields()) {
        		f.setAccessible(true);
                try {
					value = f.get(ob);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					value=null;
				}
                if (check(value)){
                update.set(f.getName(), value);
                }
        }
        return update;
    }
    
    private  boolean check(Object value){
    	if(value==null)
    		return false;
    	if(value instanceof String)
    		if(!StringUtil.isEmpty(value.toString()))
    			return true;
    		else
    			return false;
    	else
    		return true;
    }


	
}
