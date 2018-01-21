package sunwou.serviceimple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sunwou.mongo.dao.IFullCutDao;
import sunwou.service.IFullCutService;

@Component
public class FullCutServiceImple implements IFullCutService{
	
	@Autowired
	private IFullCutDao iFullCutDao;
}
