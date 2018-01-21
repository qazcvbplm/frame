package sunwou.entity;

import java.math.BigDecimal;
import java.util.List;

import com.github.qcloudsms.SmsSingleSender;

import sunwou.mongo.util.MongoBaseEntity;

public class Shop extends MongoBaseEntity{

	private String schoolId;//学校id
	
	private String shopName;//店铺名字
	
	private String shopPhone;//店铺手机号码
	
	private String shopImage;//店铺图片
	
	private Boolean open;//店是否开业
	
	private String sort;//排序
	
	private Boolean sendModel;//配送模式
	
	private Boolean getModel;//自取模式
	
	private BigDecimal fullCutRate;//满减优惠商家承担比重
	
	private BigDecimal ProductDiscount;//商品折扣商家承担比重
	
	private int sales;//销量
	
	private int score;//评分
	
	private BigDecimal startPrice;//配送费
	
	private BigDecimal boxPrice;//餐盒费
	
	private BigDecimal sendPrice;//配送费
	
	private String categoryId;//分类id
	
	private String sendTime;//平均配送时间
	
	private String topTitle;//店铺横幅
	
	private List<FullCut> fullCut;//满减优惠
	
	
}
