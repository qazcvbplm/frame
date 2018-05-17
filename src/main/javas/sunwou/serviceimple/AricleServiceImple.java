package sunwou.serviceimple;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import sunwou.entity.Article;
import sunwou.mongo.daoimple.ArticleDaoImple;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.IArticleService;

@Component
public class AricleServiceImple implements IArticleService{

	@Autowired
	private ArticleDaoImple iArticleDao;

	@Override
	public String add(Article article) {
		// TODO Auto-generated method stub
		article.setVisitor(0);
		article.setIsShow(true);
		article.setSort(new Date().getTime()+"");
		return iArticleDao.add(article);
	}

	@Override
	public List<Article> find(QueryObject qo,boolean admin) {
		String schoolId=qo.getWheres()[0].getOpertionValue().toString();
		Criteria c=new Criteria();
		if(admin){
			c.andOperator(Criteria.where("schoolId").is(schoolId),Criteria.where("isDelete").is(false));
		}else{
			c.andOperator(Criteria.where("schoolId").is(schoolId),Criteria.where("isShow").is(true),Criteria.where("isDelete").is(false));
		}
		Query query=new Query(c);
		query.fields().exclude("richText");
		query.with(new Sort(Direction.DESC, "sort"));
		return iArticleDao.getMongoTemplate().find(query, Article.class);
	}

	@Override
	public int update(Article article) {
		// TODO Auto-generated method stub
		return iArticleDao.updateById(article);
	}

	@Override
	public Article findById(String sunwouId) {
		Article rs=iArticleDao.findById(sunwouId);
		rs.setVisitor(rs.getVisitor()+3);
		iArticleDao.updateById(rs);
		return rs;
	}
}
