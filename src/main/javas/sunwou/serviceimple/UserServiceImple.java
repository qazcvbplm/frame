package sunwou.serviceimple;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.wx.towallet.WeChatPayUtil;

import sunwou.entity.App;
import sunwou.entity.User;
import sunwou.exception.MyException;
import sunwou.mongo.dao.IUserDao;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.IAppService;
import sunwou.service.IUserService;
import sunwou.util.TimeUtil;
@Component
public class UserServiceImple implements IUserService{
	@Autowired
	private IUserDao iUserDao;
	@Autowired
	private IAppService iAppService;

	@Override
	public User findByOpenId(String openid) {
        List<User> users=iUserDao.getMongoTemplate().find(new Query(Criteria.where("openid").is(openid)),
              MongoBaseDaoImple.classes.get(MongoBaseDaoImple.USER));
        if(users.size()==0)
		return null;
        else
        return users.get(0);
	}

	@Override
	public User add(String openid) {
		User user=new User(openid);
		iUserDao.add(user);
		return user;
	}

	@Override
	public int update(User user) {
		return iUserDao.updateById(user, MongoBaseDaoImple.USER);
	}

	@Override
	public List<User> findUser(QueryObject qo) {
		// TODO Auto-generated method stub
		return iUserDao.find(qo);
	}

	@Override
	public User findById(String sunwouId) {
		// TODO Auto-generated method stub
		return iUserDao.findById(sunwouId, MongoBaseDaoImple.USER);
	}

	@Override
	public int count(QueryObject qo) {
		// TODO Auto-generated method stub
		return iUserDao.count(qo);
	}

	@Override
	public int addSource(String userId, int intValue, String c) {
		 User user=iUserDao.findById(userId, MongoBaseDaoImple.USER);
		 if(c.equals("加"))
		 user.setSource(user.getSource()+intValue);
		 if(c.equals("减")){
			 if(user.getSource()>=intValue){
				 user.setSource(user.getSource()-intValue);
			 }
			 else{
				 throw new MyException("积分不足");
			 }
		 }
		return iUserDao.updateById(user, MongoBaseDaoImple.USER);
	}

	@Override
	public int activeCount(String schoolId) {
		Criteria c=new Criteria();
		c.andOperator(
				Criteria.where("schoolId").is(schoolId),
				Criteria.where("lastLoginTime").is(TimeUtil.formatDate(new Date(), TimeUtil.TO_DAY)));
		return (int) iUserDao.getMongoTemplate().count(new Query(c), MongoBaseDaoImple.classes.get(MongoBaseDaoImple.USER));
	}

	@Override
	public int Money(String userId, BigDecimal amount, boolean add) {
		User user=null;
		user=iUserDao.findById(userId, MongoBaseDaoImple.USER);
		 if(add){
			 user.setMoney(user.getMoney().add(amount));
		 }
		 else{
			 synchronized (userId) {
				 user=iUserDao.findById(userId, MongoBaseDaoImple.USER);
				 if(user.getMoney().compareTo(amount)==-1){
					 throw new MyException("余额不足");
				 }
				 else{
					 user.setMoney(user.getMoney().subtract(amount));
				 }
			}
		 }
		return iUserDao.updateById(user, MongoBaseDaoImple.USER);
	}

	@Override
	public int detailMoney(HttpServletRequest request) {
		App app=iAppService.find();
		Criteria c=new Criteria();
		c.andOperator(Criteria.where("money").gte("1.0"));
		List<User> user=iUserDao.getMongoTemplate().find(new Query(c), User.class);
		for(User temp:user){
			try {
				if(WeChatPayUtil.transfers(request, app, "", temp.getMoney(), temp.getOpenid()).equals("支付成功")){
					Money(temp.getSunwouId(), temp.getMoney(), false);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}


}
