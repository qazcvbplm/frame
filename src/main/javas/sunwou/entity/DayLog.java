package sunwou.entity;

import java.math.BigDecimal;

import sunwou.mongo.util.MongoBaseEntity;

public class DayLog extends MongoBaseEntity{

	
	private String parentId;
	
	private String name;
	
	private BigDecimal totalIn;
	
	private BigDecimal appGet;
	
	private BigDecimal agentGet;
	
	private BigDecimal selfGet;
	
	private BigDecimal fullCut;
	
	private BigDecimal discount;
	
	private BigDecimal sendPrice;
	
	private BigDecimal boxPrice;
	
	private BigDecimal productPrice;
	
	private BigDecimal senderGet;
	
	private Integer totalOrderNumber;
	
	private Integer takeOutCanelNumber;
	
	private Integer tScanelOrderNumber;
	
	private Integer takeOutNumber;
	
	private Integer tSNumber;

	private String type;
	
	private Boolean settlement;
	
	
	private String time;
	
	private String shopId;
	
	

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public BigDecimal getSenderGet() {
		return senderGet;
	}

	public void setSenderGet(BigDecimal senderGet) {
		this.senderGet = senderGet;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public DayLog() {
		super();
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public BigDecimal getSendPrice() {
		return sendPrice;
	}

	public void setSendPrice(BigDecimal sendPrice) {
		this.sendPrice = sendPrice;
	}

	public BigDecimal getBoxPrice() {
		return boxPrice;
	}

	public void setBoxPrice(BigDecimal boxPrice) {
		this.boxPrice = boxPrice;
	}

	public BigDecimal getFullCut() {
		return fullCut;
	}

	public void setFullCut(BigDecimal fullCut) {
		this.fullCut = fullCut;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getSettlement() {
		return settlement;
	}

	public void setSettlement(Boolean settlement) {
		this.settlement = settlement;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}



	public BigDecimal getTotalIn() {
		return totalIn;
	}

	public void setTotalIn(BigDecimal totalIn) {
		this.totalIn = totalIn;
	}

	public BigDecimal getAppGet() {
		return appGet;
	}

	public void setAppGet(BigDecimal appGet) {
		this.appGet = appGet;
	}

	public BigDecimal getAgentGet() {
		return agentGet;
	}

	public void setAgentGet(BigDecimal agentGet) {
		this.agentGet = agentGet;
	}

	public Integer getTotalOrderNumber() {
		return totalOrderNumber;
	}

	public void setTotalOrderNumber(Integer totalOrderNumber) {
		this.totalOrderNumber = totalOrderNumber;
	}

	


	public Integer getTakeOutCanelNumber() {
		return takeOutCanelNumber;
	}

	public void setTakeOutCanelNumber(Integer takeOutCanelNumber) {
		this.takeOutCanelNumber = takeOutCanelNumber;
	}


	public Integer getTakeOutNumber() {
		return takeOutNumber;
	}

	public void setTakeOutNumber(Integer takeOutNumber) {
		this.takeOutNumber = takeOutNumber;
	}

	public BigDecimal getSelfGet() {
		return selfGet;
	}

	public void setSelfGet(BigDecimal selfGet) {
		this.selfGet = selfGet;
	}

	public Integer gettScanelOrderNumber() {
		return tScanelOrderNumber;
	}

	public void settScanelOrderNumber(Integer tScanelOrderNumber) {
		this.tScanelOrderNumber = tScanelOrderNumber;
	}

	public Integer gettSNumber() {
		return tSNumber;
	}

	public void settSNumber(Integer tSNumber) {
		this.tSNumber = tSNumber;
	}

	public DayLog(String parentId, String name,String type, Boolean settlement,String time) {
		super();
		this.parentId = parentId;
		this.name = name;
		this.totalIn = new BigDecimal(0);
		this.appGet = new BigDecimal(0);
		this.agentGet = new BigDecimal(0);
		this.selfGet = new BigDecimal(0);
		this.fullCut = new BigDecimal(0);
		this.discount = new BigDecimal(0);
		this.sendPrice = new BigDecimal(0);
		this.boxPrice = new BigDecimal(0);
		this.productPrice = new BigDecimal(0);
		this.senderGet=new BigDecimal(0);
		this.totalOrderNumber = 0;
		this.takeOutCanelNumber = 0;
		this.tScanelOrderNumber = 0;
		this.takeOutNumber = 0;
		this.tSNumber = 0;
		this.type = type;
		this.settlement = settlement;
		this.time=time;
	}
	
	public DayLog(String parentId,String shopId, String name,String type, Boolean settlement,String time) {
		super();
		this.shopId=shopId;
		this.parentId = parentId;
		this.name = name;
		this.totalIn = new BigDecimal(0);
		this.appGet = new BigDecimal(0);
		this.agentGet = new BigDecimal(0);
		this.selfGet = new BigDecimal(0);
		this.fullCut = new BigDecimal(0);
		this.discount = new BigDecimal(0);
		this.sendPrice = new BigDecimal(0);
		this.boxPrice = new BigDecimal(0);
		this.productPrice = new BigDecimal(0);
		this.senderGet=new BigDecimal(0);
		this.totalOrderNumber = 0;
		this.takeOutCanelNumber = 0;
		this.tScanelOrderNumber = 0;
		this.takeOutNumber = 0;
		this.tSNumber = 0;
		this.type = type;
		this.settlement = settlement;
		this.time=time;
	}

	public void addOrder(Order temp) {
		this.totalOrderNumber+=1;
		if(temp.getType().equals("外卖订单")){
			this.takeOutNumber+=1;
			if(temp.getStatus().equals("已取消"))
				this.takeOutCanelNumber+=1;
		}
		else{
			this.tSNumber+=1;
			if(temp.getStatus().equals("已取消"))
				this.tScanelOrderNumber+=1;
		}
		if(!temp.getStatus().equals("已取消")){
			if(temp.getDiscountType()!=null){
				if(temp.getDiscountType().equals("商品折扣")){
					this.discount=this.discount.add(temp.getShopdiscount()).add(temp.getAppdiscount());
				}else{
					this.fullCut=this.fullCut.add(temp.getShopdiscount()).add(temp.getAppdiscount());
				}
			}
			if(temp.getSenderId()!=null){
				this.senderGet=this.senderGet.add(temp.getSenderGet());
			}
			this.totalIn=this.totalIn.add(temp.getTotal());
			this.appGet=this.appGet.add(temp.getAppGet());
			this.agentGet=this.agentGet.add(temp.getAgentGet());
			this.selfGet=this.selfGet.add(temp.getShopGet());
			this.sendPrice=this.sendPrice.add(temp.getSendPrice());
			this.boxPrice=this.boxPrice.add(temp.getBoxPrice());
			this.productPrice=this.productPrice.add(temp.getProductPrice());
		}else{
		}
	}

	public void addDayLog(DayLog dayLogshop) {
		this.takeOutCanelNumber+=dayLogshop.getTakeOutCanelNumber();
		this.tScanelOrderNumber+=dayLogshop.gettScanelOrderNumber();
		this.totalOrderNumber+=dayLogshop.getTotalOrderNumber();
		this.takeOutNumber+=dayLogshop.getTakeOutNumber();
		this.tSNumber+=dayLogshop.gettSNumber();
		this.discount=this.discount.add(dayLogshop.getDiscount());
		this.senderGet=this.senderGet.add(dayLogshop.getSenderGet());
		this.fullCut=this.fullCut.add(dayLogshop.getFullCut());
		this.totalIn=this.totalIn.add(dayLogshop.getTotalIn());
		this.appGet=this.appGet.add(dayLogshop.getAppGet());
		this.agentGet=this.agentGet.add(dayLogshop.getAgentGet());
		this.selfGet=this.selfGet.add(dayLogshop.getSelfGet());
		this.sendPrice=this.sendPrice.add(dayLogshop.getSendPrice());
		this.boxPrice=this.boxPrice.add(dayLogshop.getBoxPrice());
		this.productPrice=this.productPrice.add(dayLogshop.getProductPrice());
		
	}


	
}
