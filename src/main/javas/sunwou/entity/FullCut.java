package sunwou.entity;

import java.math.BigDecimal;

import sunwou.mongo.util.MongoBaseEntity;

public class FullCut extends MongoBaseEntity{
	
	private String shopId;

	private BigDecimal full;
	
	private BigDecimal cut;

	
	
	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public BigDecimal getFull() {
		return full;
	}

	public void setFull(BigDecimal full) {
		this.full = full;
	}

	public BigDecimal getCut() {
		return cut;
	}

	public void setCut(BigDecimal cut) {
		this.cut = cut;
	}

}
