package com.wxenterprisepay;

public class WeChatConfig {
	//请求接口
	public static final String  POST_URL ="https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
	public static final String  CHARSET = "UTF-8";//编码格式
	//证书存放目录
	public static final String  CA_LICENSE = "/home/cert/apiclient_cert.p12";//"C:/Users/Administrator.hi/Desktop/WeChat/cert/apiclient_cert.p12";

	//注意商户平台需要与微信公众号有关联。通常申请流程是从公众号->微信支付，进入申请
	//商户apikey
	public static final String  API_KEY = "csB18857818257332522199510208595";
	//商户平台：https://pay.weixin.qq.com/index.php/home/login
	//商户平台号
	public static final String  MCHID = "1480274402";
	
	//微信公众平台appid
	public static final String  MCH_APPID = "wx46197a2f1c92edf0";
	//公众号网址：https://mp.weixin.qq.com/
	
}
