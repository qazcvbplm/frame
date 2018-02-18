package sunwou.entity;

import org.hibernate.validator.constraints.NotEmpty;

import sunwou.mongo.util.MongoBaseEntity;

public class ShopApply extends MongoBaseEntity{

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
	@NotEmpty(message="店铺名字不能为空")
   	private String shopName;
	@NotEmpty(message="店铺图片不能为空")
   	private String[] image;
    @NotEmpty(message="店铺位置不能为空")
    private String shopAddress;
	
	
    
	
	

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String[] getImage() {
		return image;
	}

	public void setImage(String[] image) {
		this.image = image;
	}

	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	
   	
   	
}
