package sunwou.serviceimple;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import sunwou.entity.SignLog;
import sunwou.entity.User;
import sunwou.mongo.daoimple.SignLogDaoImple;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.service.ISignLogService;
import sunwou.service.IUserService;
import sunwou.util.TimeUtil;

@Component
public class SignLogServiceImple implements ISignLogService{

	@Autowired
	private SignLogDaoImple iSignLogDao;
	@Autowired
	private IUserService iUserService;

	@Override
	public String sign(SignLog sign) {
		User user=iUserService.findById(sign.getUserId());
		Criteria c=new Criteria();
		c.andOperator(Criteria.where("userId").is(sign.getUserId()));
		Query query=new Query(c);
		query.with(new Sort(Direction.DESC, "createDate"));
		List<SignLog> signLog=iSignLogDao.getMongoTemplate().find(query, iSignLogDao.getCl());
		if(signLog.size()==0){
			sign.setCount(1);
			sign.setNumber(sign.getCount()*2);
			iSignLogDao.add(sign);
		}else{
			SignLog last=signLog.get(0);
			if(last.getCreateDate().equals(TimeUtil.formatDate(new Date(), TimeUtil.TO_DAY))){
				sign.setCount(last.getCount());
				sign.setNumber(last.getNumber());
				//今天签到过
				return null;
			}
			if(last.getCreateDate().equals(TimeUtil.getYesterday())){
				//昨天签到过
				if(sign.getCount()>=7){
					//达到最大值
					sign.setCount(1);
					sign.setNumber(sign.getCount()*2);
					iSignLogDao.add(sign);
				}else{
					sign.setCount(last.getCount()+1);
					sign.setNumber(sign.getCount()*2);
					iSignLogDao.add(sign);
				}
			}else{
				//不是连续签到
				sign.setCount(1);
				sign.setNumber(sign.getCount()*2);
				iSignLogDao.add(sign);
			}
		}
		user.setSource(user.getSource()+sign.getNumber());
		iUserService.update(user);
		return null;
	}
}
