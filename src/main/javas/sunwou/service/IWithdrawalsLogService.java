package sunwou.service;

import java.util.List;

import sunwou.entity.WithdrawalsLog;
import sunwou.mongo.util.QueryObject;

public interface IWithdrawalsLogService {

	List<WithdrawalsLog> find(QueryObject qo);

	String add(WithdrawalsLog log);

}
