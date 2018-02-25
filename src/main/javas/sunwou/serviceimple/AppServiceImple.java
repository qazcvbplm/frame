package sunwou.serviceimple;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.wxenterprisepay.WeChatPayUtil;

import sunwou.entity.App;
import sunwou.entity.School;
import sunwou.entity.Sender;
import sunwou.exception.MyException;
import sunwou.mongo.dao.IAppDao;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.service.IAppService;
import sunwou.service.ISchoolService;
import sunwou.service.ISenderService;
import sunwou.valueobject.WithdrawwalsObject;

@Component
public class AppServiceImple implements IAppService{

	@Autowired
	private IAppDao iAppDao;
	@Autowired
	private ISchoolService iSchoolService;
	@Autowired
	private ISenderService iSenderService;

	@Override
	public App add(App app) {
		iAppDao.add(app);
		return app;
	}

	@Override
	public int count(App app) {
		return (int) iAppDao.getMongoTemplate().count(new Query(), MongoBaseDaoImple.classes.get(MongoBaseDaoImple.APP));
	}

	@Override
	public void updateById(App app) {
		iAppDao.updateById(app, MongoBaseDaoImple.APP);
	}

	@Override
	public App find() {
		List<App> rs=iAppDao.getMongoTemplate().find(new Query(), MongoBaseDaoImple.classes.get(MongoBaseDaoImple.APP));
		return rs.get(0);
	}

	@Override
	public int money(BigDecimal amount, boolean add) {
		App app=find();
		if(add){
			app.setMoney(app.getMoney().add(amount));
		}else{
			app.setMoney(app.getMoney().subtract(amount));
		}
		return iAppDao.updateById(app, MongoBaseDaoImple.APP);
	}
	
	
	@Override
	public int total(BigDecimal amount, boolean add) {
		App app=find();
		if(add){
			app.setTotal(app.getTotal().add(amount));
		}else{
			app.setTotal(app.getTotal().subtract(amount));
		}
		return iAppDao.updateById(app, MongoBaseDaoImple.APP);
	}

	@Override
	public String withdrawals(WithdrawwalsObject wo) throws Exception {
		synchronized (wo.getSchoolId()) {
       	 School school=iSchoolService.findById(wo.getSchoolId());
	       	 if(school.getMoney().compareTo(wo.getAmount())!=-1){
	       		 if(wo.getAmount().compareTo(new BigDecimal(1))!=-1){
	       			 String rs="";
	       			 if(wo.getType().equals("配送员提现")||wo.getType().equals("代理零钱提现")){
	       				 rs=WeChatPayUtil.transfers(wo.getRequest(),wo.getPayId(), wo.getAmount(), wo.getPhone(), wo.getOpenid());
	       				 if(rs.equals("支付成功")){
	       					 if(wo.getType().equals("配送员提现")){
	       						 Sender sender=iSenderService.findById(wo.getBz());
	       						 iSenderService.money(sender, wo.getAmount(),false);
	       					 }
	       					if(wo.getType().equals("代理零钱提现")){
	       						iSchoolService.money(wo.getSchoolId(), wo.getAmount(), false);
	       					 }
	       				 }
	       			 }
	       	
	       			 return rs;
	       		 }else{
	       			 throw new MyException("余额至少为1");
	       		 }
	       	 }else{
	       		 throw new MyException("平台余额不足");
	       	 }	
		 }
	}
}
