package sunwou.entity;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

import com.google.gson.reflect.TypeToken;

import sunwou.mongo.util.MongoBaseEntity;
import sunwou.util.Util;

public class Product extends MongoBaseEntity{

	@NotEmpty(message="商品名不能为空")
	private String name;
	@NotEmpty(message="商品图片不能为空")
	private String image;
	
	private BigDecimal discount;
	
	private Integer sales;
	
	private Boolean boxFlag;
	@NotEmpty(message="分类id不能为空")
	private String categoryId;
	private String shopId;
	@NotEmpty(message="属性不能为空")
	@Transient
	private String attributes;
	
	private List<Attribute> attribute;
	
	private Boolean isShow;
	
	

	public Boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public Integer getSales() {
		return sales;
	}

	public void setSales(Integer sales) {
		this.sales = sales;
	}

	public Boolean getBoxFlag() {
		return boxFlag;
	}

	public void setBoxFlag(Boolean boxFlag) {
		this.boxFlag = boxFlag;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	public List<Attribute> getAttribute() {
		return attribute;
	}

	public void setAttribute(List<Attribute> attribute) {
		this.attribute = attribute;
	}

	public void add() {
		this.discount=this.discount.divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_DOWN);
		this.attribute=Util.gson.fromJson(this.attributes,new TypeToken<List<Attribute>>(){}.getType());
	}
	
	public void update() {
		if(this.discount!=null)
		this.discount=this.discount.divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_DOWN);
		if(this.attributes!=null)
		this.attribute=Util.gson.fromJson(this.attributes,new TypeToken<List<Attribute>>(){}.getType());
	}
	
	
	
}
