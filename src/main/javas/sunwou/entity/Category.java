package sunwou.entity;

import org.hibernate.validator.constraints.NotEmpty;

import sunwou.mongo.util.MongoBaseEntity;

public class Category extends MongoBaseEntity{
	
	public interface ShopCategory{}
	
	public interface ProductCategory{}
	
	public interface SecondCategory{}

	@NotEmpty(message="学校id不能为空",groups={ShopCategory.class,SecondCategory.class})
	private String schoolId;
	@NotEmpty(message="商店id不能为空",groups=ProductCategory.class)
	private String shopId;
	
	private String type;//店铺分类    商品分类  二手分类
	
	private String sort;
	@NotEmpty(message="分类名字不能为空")
	private String name;

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
}
