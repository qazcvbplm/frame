package sunwou.mongo.daoimple;

import org.springframework.stereotype.Component;

import sunwou.entity.Carousel;
import sunwou.mongo.dao.ICarouselDao;
import sunwou.mongo.util.MongoBaseDaoImple;
@Component
public class CarouselDaoImple extends MongoBaseDaoImple<Carousel> implements ICarouselDao{

}
