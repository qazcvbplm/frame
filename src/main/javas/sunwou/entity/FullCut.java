package sunwou.entity;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import sunwou.mongo.util.MongoBaseEntity;

public class FullCut extends MongoBaseEntity{
	
	@NotEmpty(message="商店id不能为空")
	private String shopId;
	@NotNull(message="参数空")
	private Integer full;
	@NotNull(message="参数空")
	private Integer cut;

	
	
	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public Integer getFull() {
		return full;
	}

	public void setFull(Integer full) {
		this.full = full;
	}

	public Integer getCut() {
		return cut;
	}

	public void setCut(Integer cut) {
		this.cut = cut;
	}

}
