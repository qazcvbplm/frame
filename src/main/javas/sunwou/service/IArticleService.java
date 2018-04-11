package sunwou.service;

import java.util.List;

import sunwou.entity.Article;
import sunwou.mongo.util.QueryObject;

public interface IArticleService {

	String add(Article article);

	List<Article> find(QueryObject qo, boolean admin);

	int update(Article article);

	Article findById(String sunwouId);

}
