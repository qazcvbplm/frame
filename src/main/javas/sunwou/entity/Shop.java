package sunwou.entity;

import java.math.BigDecimal;
import java.util.List;


import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotEmpty;

import sunwou.mongo.util.MongoBaseEntity;

public class Shop extends MongoBaseEntity{

	public interface add{};
	
	@NotEmpty(message="学校id不能为空",groups=add.class)
	private String schoolId;//学校id
	@NotEmpty(message="学校名字不能为空",groups=add.class)
	private String shopName;//店铺名字
	@NotEmpty(message="手机号码不能为空",groups=add.class)
	private String shopPhone;//店铺手机号码
	@NotEmpty(message="图片不能为空",groups=add.class)
	private String shopImage;//店铺图片
	private Boolean open;//店是否开业
	
	private String sort;//排序
	
	private Boolean sendModel;//配送模式
	
	private Boolean getModel;//自取模式
	
	private BigDecimal fullCutRate;//满减优惠商家承担比重
	
	private BigDecimal productDiscountRate;//商品折扣商家承担比重
	
	private Integer sales;//销量
	
	private BigDecimal score;//评分
	
	private BigDecimal startPrice;//配送费
	
	private BigDecimal boxPrice;//餐盒费
	
	private BigDecimal sendPrice;//配送费
	@NotEmpty(message="分类id不能为空",groups=add.class)
	private String categoryId;//分类id
	@NotEmpty(message="配送时间不能为空",groups=add.class)
	private String sendTime;//平均配送时间
	
	private String topTitle;//店铺横幅
	
	private List<FullCut> fullCut;//满减优惠
	
	private List<OpenTime> OpenTime;//营业时间
	
	private BigDecimal money;//店铺营业额
	private String bankNumber;//银行卡号
	private String bankCode;//银行卡号
	
	@NotEmpty(message="账号不能为空",groups=add.class)
	private String shopUserName;
	@NotEmpty(message="密码不能为空",groups=add.class)
	private String shopPassWord;
	
	@NotEmpty(message="店铺地址不能为空",groups=add.class)
	private String address;
	
	
	private BigDecimal rate;//对商家抽成比率
	
	
	private String minDiscount;
	
	private String realName;
	
	@NotEmpty(message="经度不能为空")
	private String lng;
	@NotEmpty(message="纬度不能为空")
	private String lat;
	
	
	
	
	
	public String getLng() {
		return lng;
	}


	public void setLng(String lng) {
		this.lng = lng;
	}


	public String getLat() {
		return lat;
	}


	public void setLat(String lat) {
		this.lat = lat;
	}


	public String getRealName() {
		return realName;
	}


	public void setRealName(String realName) {
		this.realName = realName;
	}


	public String getBankCode() {
		return bankCode;
	}


	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}


	public String getMinDiscount() {
		return minDiscount;
	}


	public void setMinDiscount(String minDiscount) {
		this.minDiscount = minDiscount;
	}


	public BigDecimal getRate() {
		return rate;
	}


	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}


	public List<OpenTime> getOpenTime() {
		return OpenTime;
	}


	public void setOpenTime(List<OpenTime> openTime) {
		OpenTime = openTime;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getShopUserName() {
		return shopUserName;
	}


	public void setShopUserName(String shopUserName) {
		this.shopUserName = shopUserName;
	}


	public String getShopPassWord() {
		return shopPassWord;
	}


	public void setShopPassWord(String shopPassWord) {
		this.shopPassWord = shopPassWord;
	}


	public String getBankNumber() {
		return bankNumber;
	}


	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}


	public void add() {
		this.fullCutRate=this.fullCutRate.divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_DOWN);
		this.productDiscountRate=this.productDiscountRate.divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_DOWN);
		this.rate=this.rate.divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_DOWN);
		this.money = new BigDecimal(0);
	}
	

	public void update() {
		this.schoolId = null;
		this.money = null;
		this.fullCut=null;
		if(this.fullCutRate!=null)
		this.fullCutRate=this.fullCutRate.divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_DOWN);
		if(this.productDiscountRate!=null)
		this.productDiscountRate=this.productDiscountRate.divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_DOWN);
		if(this.rate!=null)
		this.rate=this.rate.divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_DOWN);
	}







	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopPhone() {
		return shopPhone;
	}

	public void setShopPhone(String shopPhone) {
		this.shopPhone = shopPhone;
	}

	public String getShopImage() {
		return shopImage;
	}

	public void setShopImage(String shopImage) {
		this.shopImage = shopImage;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Boolean getSendModel() {
		return sendModel;
	}

	public void setSendModel(Boolean sendModel) {
		this.sendModel = sendModel;
	}

	public Boolean getGetModel() {
		return getModel;
	}

	public void setGetModel(Boolean getModel) {
		this.getModel = getModel;
	}

	public BigDecimal getFullCutRate() {
		return fullCutRate;
	}

	public void setFullCutRate(BigDecimal fullCutRate) {
		this.fullCutRate = fullCutRate;
	}


	public BigDecimal getProductDiscountRate() {
		return productDiscountRate;
	}


	public void setProductDiscountRate(BigDecimal productDiscountRate) {
		this.productDiscountRate = productDiscountRate;
	}


	public void setSales(Integer sales) {
		this.sales = sales;
	}

	
	


	public BigDecimal getScore() {
		return score;
	}


	public void setScore(BigDecimal score) {
		this.score = score;
	}


	public int getSales() {
		return sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}

	

	public BigDecimal getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(BigDecimal startPrice) {
		this.startPrice = startPrice;
	}

	public BigDecimal getBoxPrice() {
		return boxPrice;
	}

	public void setBoxPrice(BigDecimal boxPrice) {
		this.boxPrice = boxPrice;
	}

	public BigDecimal getSendPrice() {
		return sendPrice;
	}

	public void setSendPrice(BigDecimal sendPrice) {
		this.sendPrice = sendPrice;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getTopTitle() {
		return topTitle;
	}

	public void setTopTitle(String topTitle) {
		this.topTitle = topTitle;
	}

	public List<FullCut> getFullCut() {
		return fullCut;
	}

	public void setFullCut(List<FullCut> fullCut) {
		this.fullCut = fullCut;
	}


	public BigDecimal getMoney() {
		return money;
	}


	public void setMoney(BigDecimal money) {
		this.money = money;
	}


	public void findById() {
		this.money = null;
		this.bankNumber = null;
		this.shopUserName = null;
		this.shopPassWord = null;
	}



	
	
	
	
}
