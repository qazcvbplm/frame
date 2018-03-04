package sunwou.serviceimple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sunwou.entity.Article;
import sunwou.mongo.dao.IArticleDao;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.IArticleService;

@Component
public class AricleServiceImple implements IArticleService{

	@Autowired
	private IArticleDao iArticleDao;

	@Override
	public String add(Article article) {
		// TODO Auto-generated method stub
		article.setVisitor(0);
		article.setIsShow(true);
		return iArticleDao.add(article);
	}

	@Override
	public List<Article> find(QueryObject qo) {
		// TODO Auto-generated method stub
		return iArticleDao.find(qo);
	}

	@Override
	public int update(Article article) {
		// TODO Auto-generated method stub
		return iArticleDao.updateById(article, MongoBaseDaoImple.ARTICLE);
	}
}
