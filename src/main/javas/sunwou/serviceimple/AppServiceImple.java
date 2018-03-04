package sunwou.serviceimple;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.wx.tobank.WChatToBank;
import com.wx.towallet.WeChatPayUtil;
import com.wx.towallet.WeChatUtil;
import com.wx.towallet.XMLUtil;

import sunwou.entity.App;
import sunwou.entity.School;
import sunwou.entity.Sender;
import sunwou.entity.WithdrawalsLog;
import sunwou.exception.MyException;
import sunwou.mongo.dao.IAppDao;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.service.IAppService;
import sunwou.service.ISchoolService;
import sunwou.service.ISenderService;
import sunwou.service.IWithdrawalsLogService;
import sunwou.util.Util;
import sunwou.valueobject.WithdrawwalsObject;

@Component
public class AppServiceImple implements IAppService{

	@Autowired
	private IAppDao iAppDao;
	@Autowired
	private ISchoolService iSchoolService;
	@Autowired
	private ISenderService iSenderService;
	@Autowired
	private IWithdrawalsLogService iWithdrawalsLogService;

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
		App app=find();
		wo.setSxf(new BigDecimal(0));
		synchronized (wo.getSchoolId()) {
       	 School school=iSchoolService.findById(wo.getSchoolId());
	       	 if(school.getMoney().compareTo(wo.getAmount())!=-1){
	       		 if(wo.getAmount().compareTo(new BigDecimal(1))!=-1){
	       			 	 String rs="提现失败";
		       			 if(wo.getType().equals("配送员提现")||wo.getType().equals("代理零钱提现")){
		       				 rs=WeChatPayUtil.transfers(wo.getRequest(),app,wo.getPayId(), wo.getAmount(), wo.getOpenid());
		       			 }
		       			 if(wo.getType().equals("代理银行卡提现")){
		       				 if(wo.getAmount().compareTo(new BigDecimal(1000))==-1){
		       					 wo.setSxf(new BigDecimal(1));
		       					 wo.setAmount(wo.getAmount().subtract(wo.getSxf()));
		       				 }else{
		       					 wo.setSxf(wo.getAmount().multiply(new BigDecimal(0.001)));
		       					 wo.setAmount(wo.getAmount().subtract(wo.getSxf()).setScale(2, BigDecimal.ROUND_HALF_DOWN));
		       				 }
		       				 String amount=WeChatUtil.bigDecimalToPoint(wo.getAmount());
		       				 StringBuilder sb=new StringBuilder();
		       				 sb.append("name=").append(wo.getName()).append("&bankNumber=").append(wo.getBankNumber())
		       				 .append("&bank_code=").append(wo.getBamk_code()).append("&desc=").append(wo.getDesc()).append("&partner_trade_no=")
		       				 .append(wo.getPayId()).append("&amount=").append(amount);
		       				 String temp=Util.httpRequest("http://www.wojush.com/WXEnterprisePaymentToBank/paymentForBankCard.do", "POST", sb.toString());
		       				 Map<String, Object> resultMap =  XMLUtil.doXMLParse(temp);
		       				 rs=(String) resultMap.get("return_msg");
		       			 }
	       			 //判断支付结果
	       			 if(rs.equals("支付成功")){
	       				 WithdrawalsLog log=new WithdrawalsLog(wo.getSchoolId(), "", wo.getType(), wo.getAmount(),school.getSchoolName());
	       				 iWithdrawalsLogService.add(log);
       					 if(wo.getType().equals("配送员提现")){
       						 Sender sender=iSenderService.findById(wo.getBz());
       						 log.setSenderId(wo.getBz());
       						 log.setName(sender.getRealName());
       						 iSenderService.money(wo.getBz(), wo.getAmount(),false);
       					 }
       					 if(wo.getType().equals("代理零钱提现")||wo.getType().equals("代理银行卡提现")){
       						iSchoolService.money(wo.getSchoolId(), wo.getAmount().add(wo.getSxf()), false);
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
