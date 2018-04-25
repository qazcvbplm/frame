package sunwou.serviceimple;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.mongodb.Mongo;

import sunwou.entity.School;
import sunwou.entity.Shop;
import sunwou.mongo.dao.IShopDao;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.IShopService;
import sunwou.util.ResultUtil;
import sunwou.util.TimeUtil;
import sunwou.valueobject.SchoolLoginParamObject;
import sunwou.valueobject.ShopLoginParamsObject;

@Component
public class ShopServiceImple implements IShopService{

	@Autowired
	private IShopDao iShopDao;

	@Override
	public String add(Shop shop) {
		shop.add();
		iShopDao.add(shop);
		return shop.getSunwouId();
	}

	@Override
	public int update(Shop shop) {
		shop.update();
		return iShopDao.updateById(shop, MongoBaseDaoImple.SHOP);
	}

	@Override
	public List<Shop> find(QueryObject qo) {
		return iShopDao.find(qo);
	}

	@Override
	public int count(QueryObject qo) {
		// TODO Auto-generated method stub
		return iShopDao.count(qo);
	}

	@Override
	public Shop findById(String sunwouId) {
		// TODO Auto-generated method stub
		return iShopDao.findById(sunwouId, MongoBaseDaoImple.SHOP);
	}

	@Override
	public Shop login(ShopLoginParamsObject slpo) {
		Criteria c=new Criteria();
		c.andOperator(Criteria.where("schoolId").is(slpo.getSchoolId()),
				Criteria.where("shopUserName").is(slpo.getShopUserName()),
				Criteria.where("shopPassWord").is(slpo.getShopPassWord()),
				Criteria.where("isDelete").is(false));
		List<Shop> rs=iShopDao.getMongoTemplate().find(new Query(c)	, MongoBaseDaoImple.classes.get(MongoBaseDaoImple.SHOP));
		if(rs.size()>0)
		return rs.get(0);
		else
			return null;
	}

	@Override
	public List<Shop> findBySchool(String schoolId) {
		Criteria c=new Criteria();
		c.andOperator(Criteria.where("schoolId").is(schoolId),Criteria.where("isDelete").is(false));
		return iShopDao.getMongoTemplate().find(new Query(c), MongoBaseDaoImple.classes.get(MongoBaseDaoImple.SHOP));
	}

	@Override
	public int sort(String sunwouId, String type) {
		Shop s=findById(sunwouId);
		Shop temp;
	    if(type.equals("置顶")){
	    	s.setSort(TimeUtil.formatDate(new Date(), TimeUtil.TO_S2));
	    	return update(s);
	    }
		if(type.equals("上移")||type.equals("下移")){
			Criteria c=new Criteria();
			c.andOperator(Criteria.where("schoolId").is(s.getSchoolId()),Criteria.where("isDelete").is(false));
			List<Shop> shops=iShopDao.getMongoTemplate().find(new Query(c).with(new Sort(Direction.DESC, "sort")), Shop.class);
			for(int i=0;i<shops.size();i++){
				  temp=shops.get(i);
				  if(temp.getSunwouId().equals(s.getSunwouId())){
					      String sorttemp=s.getSort();
					     if(type.equals("上移")&&i!=0){
					    	 temp.setSort(shops.get(i-1).getSort());
					    	 shops.get(i-1).setSort(sorttemp);
					    	 update(temp);
					    	 update(shops.get(i-1));
					    	 return 1;
					     }
					     if(type.equals("下移")&&i!=shops.size()-1){
					    	 temp.setSort(shops.get(i+1).getSort());
					    	 shops.get(i+1).setSort(sorttemp);
					    	 update(temp);
					    	 update(shops.get(i+1));
					    	 return 1;
					     }
					     break;
				  }
			}
		}
		return 0;
	}


}
