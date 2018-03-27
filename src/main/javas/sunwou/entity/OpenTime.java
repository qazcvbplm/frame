package sunwou.entity;

import java.util.Date;

import sunwou.mongo.util.MongoBaseEntity;
import sunwou.util.TimeUtil;

public class OpenTime extends MongoBaseEntity{


	private String shopId;
	
	private String start;
	
	private String end;
	
	private Long startL;
	
	private Long  endL;
	
	

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public Long getStartL() {
		return startL;
	}

	public void setStartL(Long startL) {
		this.startL = startL;
	}

	public Long getEndL() {
		return endL;
	}

	public void setEndL(Long endL) {
		this.endL = endL;
	}

	public void add() {
		// TODO Auto-generated method stub
		StringBuilder sb=new StringBuilder();
		StringBuilder sb2=new StringBuilder();
		String temp=TimeUtil.formatDate(new Date(), TimeUtil.TO_DAY);
		long today=TimeUtil.parse(temp, TimeUtil.TO_DAY).getTime();
		String s=sb.append(temp).append(" ").append(this.start).append(":00").toString();
		long start=TimeUtil.parse(s, TimeUtil.TO_S).getTime();
		String e=sb2.append(temp).append(" ").append(this.end).append(":00").toString();
		long end=TimeUtil.parse(e, TimeUtil.TO_S).getTime();
		this.startL=start-today;
		this.endL=end-today;	
		this.setIsDelete(false);
	}
	
	
}
