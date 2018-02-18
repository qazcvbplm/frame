package sunwou.service;

import java.util.List;

import sunwou.entity.Carousel;
import sunwou.mongo.util.QueryObject;

public interface ICarouselService {

	String add(Carousel carousel);

	List<Carousel> find(QueryObject qo);

	int update(Carousel carousel);

}
