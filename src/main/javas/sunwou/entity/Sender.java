package sunwou.entity;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.NotEmpty;

import sunwou.mongo.util.MongoBaseEntity;

public class Sender extends MongoBaseEntity{
	
	@NotEmpty(message="用户id不能为空")
	private String userId;
	@NotEmpty(message="学校id不能为空")
	private String schoolId;
	private String type;
	private String status;
	@NotEmpty(message="模板id不能为空")
	private String formid;
	@NotEmpty(message="姓名不能为空")
	private String realName;
	private String gender;
	@NotEmpty(message="手机号不能为空")
	private String phone;
	@NotEmpty(message="专业不能为空")
	private String classes;
	@NotEmpty(message="学号不能为空")
	private String classesNumber;
	
	private String floors[];
	
	private String shops[];
	
	private BigDecimal rate;
	
	private BigDecimal money;
	
	private String floorsId[];
	
	private String shopsId[];
	

	
	
	public String[] getShops() {
		return shops;
	}

	public void setShops(String[] shops) {
		this.shops = shops;
	}

	public String[] getShopsId() {
		return shopsId;
	}

	public void setShopsId(String[] shopsId) {
		this.shopsId = shopsId;
	}

	public String[] getFloorsId() {
		return floorsId;
	}

	public void setFloorsId(String[] floorsId) {
		this.floorsId = floorsId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getClasses() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	public String getClassesNumber() {
		return classesNumber;
	}

	public void setClassesNumber(String classesNumber) {
		this.classesNumber = classesNumber;
	}


	public String[] getFloors() {
		return floors;
	}

	public void setFloors(String[] floors) {
		this.floors = floors;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFormid() {
		return formid;
	}

	public void setFormid(String formid) {
		this.formid = formid;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void update() {
		this.money=null;
	}

	public Sender() {
		super();
		this.money=new BigDecimal(0);
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	
	
}
