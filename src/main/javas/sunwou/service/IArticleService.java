package sunwou.service;

import java.util.List;

import sunwou.entity.Article;
import sunwou.mongo.util.QueryObject;

public interface IArticleService {

	String add(Article article);

	List<Article> find(QueryObject qo);

	int update(Article article);

}
