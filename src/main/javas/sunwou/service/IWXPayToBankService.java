package sunwou.service;

public interface IWXPayToBankService {

	public String pay(String name,String bankNumber,String bank_code,String amount,String desc,String partner_trade_no) throws Exception;
}
