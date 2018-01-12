package sunwou.serviceimple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import sunwou.entity.App;
import sunwou.mongo.dao.IAppDao;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.service.IAppService;

@Component
public class AppServiceImple implements IAppService{

	@Autowired
	private IAppDao iAppDao;

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
	public App findById(String sunwouId) {
		return iAppDao.findById(sunwouId, MongoBaseDaoImple.APP);
	}
}
