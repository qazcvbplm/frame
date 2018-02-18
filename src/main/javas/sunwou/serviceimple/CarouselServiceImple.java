package sunwou.serviceimple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sunwou.entity.Carousel;
import sunwou.mongo.dao.ICarouselDao;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.ICarouselService;

@Component
public class CarouselServiceImple implements ICarouselService{

	@Autowired
	private ICarouselDao iCarouselDao;

	@Override
	public String add(Carousel carousel) {
		// TODO Auto-generated method stub
		return iCarouselDao.add(carousel);
	}

	@Override
	public List<Carousel> find(QueryObject qo) {
		// TODO Auto-generated method stub
		return iCarouselDao.find(qo);
	}

	@Override
	public int update(Carousel carousel) {
		// TODO Auto-generated method stub
		return iCarouselDao.updateById(carousel, MongoBaseDaoImple.CAROUSEL);
	}
	
	
	
}
