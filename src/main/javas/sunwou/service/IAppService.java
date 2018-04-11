package sunwou.service;

import java.math.BigDecimal;

import sunwou.entity.App;
import sunwou.valueobject.WithdrawwalsObject;

public interface IAppService {

	App add(App app);

	int count(App app);

	void updateById(App app);

	App find();

	int money(BigDecimal amount, boolean add);

	public int total(BigDecimal amount, boolean add);

	String withdrawals(WithdrawwalsObject wo) throws Exception;
	
	/**
	 * 按照楼栋和店铺计算配送费
	 * @param fid
	 * @param sid
	 * @return
	 */
	BigDecimal completeSenderMoney(String fid,String sid);
}
