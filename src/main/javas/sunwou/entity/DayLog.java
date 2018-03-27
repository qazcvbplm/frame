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
	
	private String selfId;
	
    private BigDecimal takeOutGet;
    
    private BigDecimal runGet;
	

    
	
	public BigDecimal getTakeOutGet() {
		return takeOutGet;
	}

	public void setTakeOutGet(BigDecimal takeOutGet) {
		this.takeOutGet = takeOutGet;
	}

	public BigDecimal getRunGet() {
		return runGet;
	}

	public void setRunGet(BigDecimal runGet) {
		this.runGet = runGet;
	}

	public String getSelfId() {
		return selfId;
	}

	public void setSelfId(String selfId) {
		this.selfId = selfId;
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

	
	/**
	 * 初始化日志
	 * @param parentId 父id
	 * @param selfId  自己的id
	 * @param name   自己的名字
	 * @param type   日志类型
	 * @param settlement 
	 * @param time  统计的日子
	 */
	public DayLog(String parentId,String selfId, String name,String type, Boolean settlement,String time) {
		super();
		this.parentId = parentId;
		this.selfId=selfId;
		this.name = name;
		this.totalOrderNumber = 0;
		this.takeOutCanelNumber = 0;
		this.tScanelOrderNumber = 0;
		this.takeOutNumber = 0;
		this.tSNumber = 0;
		this.type = type;
		this.time=time;
		this.totalIn = new BigDecimal(0);
		this.appGet = new BigDecimal(0);
		this.agentGet = new BigDecimal(0);
		this.selfGet = new BigDecimal(0);
		if(type.contains("商铺")){
			this.selfGet = new BigDecimal(0);
			this.fullCut = new BigDecimal(0);
			this.discount = new BigDecimal(0);
			this.sendPrice = new BigDecimal(0);
			this.boxPrice = new BigDecimal(0);
			this.productPrice = new BigDecimal(0);
			this.senderGet=new BigDecimal(0);
		}
		if(type.contains("跑腿")){
			this.takeOutGet=new BigDecimal(0);
			this.runGet=new BigDecimal(0);
		}
	}

	void totalCount(Order temp){
		this.totalOrderNumber+=1;
		if(temp.getType().equals("外卖订单")){
			this.takeOutNumber+=1;
			if(temp.getStatus().equals("已取消"))
				this.takeOutCanelNumber+=1;
		}
		if(temp.getType().equals("堂食订单")){
			this.tSNumber+=1;
			if(temp.getStatus().equals("已取消"))
				this.tScanelOrderNumber+=1;
		}
		if(temp.getType().equals("跑腿订单")){
			this.tSNumber+=1;
			if(temp.getStatus().equals("已取消"))
				this.tScanelOrderNumber+=1;
		}
	}
	
	public void addRunOrder(Order temp) {
		this.totalCount(temp);
		if(!temp.getStatus().equals("已取消")){
			this.selfGet=this.selfGet.add(temp.getSenderGet());
			if(temp.getType().equals("外卖订单")){
				 BigDecimal rate =temp.getAppGet().divide(temp.getTotal(),2,BigDecimal.ROUND_HALF_DOWN);
				this.appGet=this.appGet.add(temp.getSendPrice().multiply(rate));
				this.totalIn=this.totalIn.add(temp.getSendPrice());
				this.takeOutGet=this.takeOutGet.add(temp.getSenderGet());
				this.agentGet=this.agentGet.add(temp.getSendPrice().subtract(temp.getSenderGet()).subtract(temp.getSendPrice().multiply(rate)));
			}
			if(temp.getType().equals("跑腿订单")){
				this.appGet=this.appGet.add(temp.getAppGet());
				this.totalIn=this.totalIn.add(temp.getTotal());
				this.runGet=this.runGet.add(temp.getSenderGet());
				this.agentGet=this.agentGet.add(temp.getAgentGet());
			}
		}
	}
	public void addRunDayLog(DayLog dayLogshop) {
		this.takeOutCanelNumber+=dayLogshop.getTakeOutCanelNumber();
		this.tScanelOrderNumber+=dayLogshop.gettScanelOrderNumber();
		this.totalOrderNumber+=dayLogshop.getTotalOrderNumber();
		this.takeOutNumber+=dayLogshop.getTakeOutNumber();
		this.tSNumber+=dayLogshop.gettSNumber();
		this.totalIn=this.totalIn.add(dayLogshop.getTotalIn());
		this.appGet=this.appGet.add(dayLogshop.getAppGet());
		this.agentGet=this.agentGet.add(dayLogshop.getAgentGet());
		this.selfGet=this.selfGet.add(dayLogshop.getSelfGet());
		this.takeOutGet=this.takeOutGet.add(dayLogshop.getTakeOutGet());
		this.runGet=this.runGet.add(dayLogshop.getRunGet());
	}
	public void addOrder(Order temp) {
		this.totalCount(temp);
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
