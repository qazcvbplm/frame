package sunwou.mongo.daoimple;

import org.springframework.stereotype.Component;

import sunwou.entity.Article;
import sunwou.mongo.dao.IArticleDao;
import sunwou.mongo.util.MongoBaseDaoImple;

@Component
public class ArticleDaoImple extends MongoBaseDaoImple<Article> implements IArticleDao{

}
