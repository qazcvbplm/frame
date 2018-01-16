package sunwou.entity;

import java.math.BigDecimal;

import sunwou.mongo.util.MongoBaseEntity;

public class Shop extends MongoBaseEntity{

	private String schoolId;
	
	private String shopName;
	
	private String shopPhone;
	
	private String shopImage;
	
	private Boolean open;
	
	private String sort;
	
	private int sales;
	
	private int score;
	
	private BigDecimal startPrice;
	
	private BigDecimal boxPrice;
	
	private BigDecimal sendPrice;
	
	private String categoryId;
	
	private String sendTime;
	
	private String topTitle;
	
}
