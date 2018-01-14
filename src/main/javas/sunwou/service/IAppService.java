package sunwou.service;

import sunwou.entity.App;

public interface IAppService {

	App add(App app);

	int count(App app);

	void updateById(App app);

	App find();

}
