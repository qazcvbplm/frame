package sunwou.serviceimple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sunwou.entity.Apply;
import sunwou.mongo.dao.IApplyDao;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.service.IApplyService;
import sunwou.valueobject.ApplyParamsObject;

@Component
public class ApplyServiceImple implements IApplyService{
	
	@Autowired	
	private IApplyDao iApplyDao;

	@Override
	public String add(Apply apply) {
		// TODO Auto-generated method stub
		return iApplyDao.add(apply);
	}

	@Override
	public int update(ApplyParamsObject apo) {
		Apply apply=iApplyDao.findById(apo.getApplyId(), MongoBaseDaoImple.APPLY);
		if(apo.getSuccess()!=null){
			//后台审核
			if(apo.getSuccess()){
				apply.setStatus("审核通过");
			}else{
				apply.setStatus("审核未通过");
			}
		}else{
			//前台更新
			apply.setContent(apo.getResult());
		}
		return iApplyDao.updateById(apply, MongoBaseDaoImple.APPLY);
	}

}
