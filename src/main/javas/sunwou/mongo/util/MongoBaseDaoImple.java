package sunwou.mongo.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import sunwou.entity.Address;
import sunwou.entity.App;
import sunwou.entity.Apply;
import sunwou.entity.Article;
import sunwou.entity.Carousel;
import sunwou.entity.Category;
import sunwou.entity.DayLog;
import sunwou.entity.Floor;
import sunwou.entity.FullCut;
import sunwou.entity.OpenTime;
import sunwou.entity.Order;
import sunwou.entity.Product;
import sunwou.entity.School;
import sunwou.entity.Sender;
import sunwou.entity.Shop;
import sunwou.entity.ShopApply;
import sunwou.entity.SignLog;
import sunwou.entity.User;
import sunwou.entity.WithdrawalsLog;
import sunwou.util.StringUtil;
import sunwou.util.TimeUtil;

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
	  public static final String APP="app";
	  public static final String USER="user";
	  public static final String FLOOR="floor";
	  public static final String SCHOOL = "school";
	  public static final String ADDRESS = "address";
	  public static final String CATEGORY = "category";
	  public static final String FULLCUT = "fullCut";
	  public static final String SHOP = "shop";
	  public static final String OPENTIME = "openTime";
	  public static final String PRODUCT = "product";
	  public static final String ORDER = "order";
	  public static final String APPLY = "apply";
	  public static final String DAYLOG = "dayLog";
	  public static final String SENDER = "sender";
	  public static final String SHOPAPPLY = "shopApply";
	  public static final String CAROUSEL = "carousel";
	  public static final String WITHDRAWALSLOG = "withdrawalsLog";
	  public static final String SIGNLOG = "signLog";
	  public static final String ARTICLE = "article";

	  
	  static{
		  classes.put(ENTITYBASE, new MongoBaseEntity().getClass());
		  classes.put(CAROUSEL, new Carousel().getClass());
		  classes.put(APP, new App().getClass());
		  classes.put(USER, new User().getClass());
		  classes.put(SCHOOL, new School().getClass());
		  classes.put(FLOOR, new Floor().getClass());
		  classes.put(ADDRESS, new Address().getClass());
		  classes.put(CATEGORY, new Category().getClass());
		  classes.put(FULLCUT, new FullCut().getClass());
		  classes.put(SHOP, new Shop().getClass());
		  classes.put(OPENTIME, new OpenTime().getClass());
		  classes.put(PRODUCT, new Product().getClass());
		  classes.put(ORDER, new Order().getClass());
		  classes.put(APPLY, new Apply().getClass());
		  classes.put(DAYLOG, new DayLog().getClass());
		  classes.put(SENDER, new Sender().getClass());
		  classes.put(SHOPAPPLY, new ShopApply().getClass());
		  classes.put(WITHDRAWALSLOG, new WithdrawalsLog().getClass());
		  classes.put(SIGNLOG, new SignLog().getClass());
		  classes.put(ARTICLE, new Article().getClass());
	  }
	
	/**
	 * 增加记录的方法
	 * @param add
	 * @return  返回唯一ID
	 */
	public String add(T add)
	{
		String time=TimeUtil.formatDate(new Date(), TimeUtil.TO_S);
		add.setCreateTime(time);
		add.setCreateDate(time.substring(0, 10));
		add.setIsDelete(false);
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
	public List<T> find(QueryObject qo) 
	{
			  Query	query = mongoutilQ(qo);
			return (List<T>) mongoTemplate.find(query,classes.get(qo.getTableName()));
	}
	
	
	/**
	 * 获取数据量的总数
	 * @param queryo
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public int count(QueryObject qo)
	{
			    Query query = mongoutilQ(qo);
				return  (int)mongoTemplate.count(query, qo.getTableName());
	}
	
	
	/**
	 * 更新数据
	 * @param queryo
	 * @param updateo
	 * @return  更新数量
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public int update(QueryObject qo,T updateo)
	{
		    Query query = mongoutilQ(qo);
			Update update=mongoutilU(updateo, qo.getTableName());
			return mongoTemplate.updateMulti(query, update, qo.getTableName()).getN();
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
	public int remove(QueryObject qo)
	{
			Query query = mongoutilQ(qo);
			Update update=new Update();
			update.set("isDelete", true);
			update.set("deleteTime", TimeUtil.formatDate(new Date(), TimeUtil.TO_S));
			return mongoTemplate.updateMulti(query, update, qo.getTableName()).getN();
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
    private  Query mongoutilQ(QueryObject qo) {
    	Query query=new Query();
    	Criteria c=new Criteria();
    	List<Criteria> andparam = new ArrayList<Criteria>();
    	List<Criteria> orparam = new ArrayList<Criteria>();
    	if(qo.getFields()!=null){
    		String[] fields=qo.getFields();
    		for(String temp:fields){
    			if(temp.endsWith("-")){
    				query.fields().exclude(temp);
    			}else{
    				query.fields().include(temp);
    			}
    		}
    	}
    	if(qo.getWheres()!=null){
    		WhereObject[] wheres=qo.getWheres();
    		for(WhereObject temp:wheres){
    			where(temp,andparam,orparam);
    		}
    		if(andparam.size()>0)
    		c.andOperator(andparam.toArray(new Criteria[andparam.size()]));
    		if(orparam.size()>0)
    		c.orOperator(orparam.toArray(new Criteria[orparam.size()]));
    	}
    	if(qo.getSorts()!=null){
    		SortObject[] sorts=qo.getSorts();
    		for(SortObject temp:sorts){
    			if(temp.isAsc()){
    				query.with(new Sort(Direction.ASC, temp.getValue()));
    			}else
    			{
    				query.with(new Sort(Direction.DESC, temp.getValue()));
    			}
    		}
    	}
    	if(qo.getPages()!=null){
    		PageObejct pages=qo.getPages();
    		query.skip((pages.getCurrentPage()-1)*pages.getSize()).limit(pages.getSize());
    	}else
    	{
    		query.limit(MAX_COUNT);
    	}
    	query.addCriteria(c);	
    	return query;
    }
    
    
    public static void where(WhereObject where,List<Criteria> andparam,List<Criteria> orparam){
    	if(where.isAnd()){
    		switch (where.getOpertionType()) {
    		case "equal":
    			andparam.add(Criteria.where(where.getValue()).is(where.getOpertionValue()));
    			break;
    		case "lt":
    			andparam.add(Criteria.where(where.getValue()).lt(where.getOpertionValue()));
    			break;
    		case "lte":
    			andparam.add(Criteria.where(where.getValue()).lte(where.getOpertionValue()));
    			break;
    		case "gte":
    			andparam.add(Criteria.where(where.getValue()).gte(where.getOpertionValue()));
    			break;
    		case "gt":
    			andparam.add(Criteria.where(where.getValue()).gt(where.getOpertionValue()));
    			break;
    		case "ne":
    			andparam.add(Criteria.where(where.getValue()).ne(where.getOpertionValue()));
    			break;
    		case "like":
    			char[] chars=where.getOpertionValue().toString().toCharArray();
    			StringBuffer sb=new StringBuffer();
    			sb.append(".*");
    			for(char temp:chars)
    			{
    				sb.append(temp).append(".*");
    			}
    			andparam.add(Criteria.where(where.getValue()).regex(sb.toString()));
    			break;
    		default:
    			break;
    	}
		}else
		{
			switch (where.getOpertionType()) {
    		case "equal":
    			orparam.add(Criteria.where(where.getValue()).is(where.getOpertionValue()));
    			break;
    		case "lt":
    			orparam.add(Criteria.where(where.getValue()).lt(where.getOpertionValue()));
    			break;
    		case "lte":
    			orparam.add(Criteria.where(where.getValue()).lte(where.getOpertionValue()));
    			break;
    		case "gte":
    			orparam.add(Criteria.where(where.getValue()).gte(where.getOpertionValue()));
    			break;
    		case "gt":
    			orparam.add(Criteria.where(where.getValue()).gt(where.getOpertionValue()));
    			break;
    		case "ne":
    			orparam.add(Criteria.where(where.getValue()).ne(where.getOpertionValue()));
    			break;
    		case "like":
    			char[] chars=where.getOpertionValue().toString().toCharArray();
    			StringBuffer sb=new StringBuffer();
    			sb.append(".*");
    			for(char temp:chars)
    			{
    				sb.append(temp).append(".*");
    			}
    			orparam.add(Criteria.where(where.getValue()).regex(sb.toString()));
    			break;
    		default:
    			break;
			}
		}
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
