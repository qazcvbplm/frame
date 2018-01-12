package sunwou.entity;

import sunwou.mongo.util.MongoBaseEntity;


public class School extends MongoBaseEntity{

    private Boolean settledFlag;//入驻功能是否开启

    private Boolean signFlag;//签到功能是否开启
    
    private Boolean sourceShopFlag;//积分商城是否开启
    
    private Boolean vipFlag;//会员卡购买是否开启
    
    private String indexTopTitle;//首页头部文字
    
    private Integer indexTopDay;//首页头部天数
}
