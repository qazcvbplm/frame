package com.wxenterprisepay;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.annotation.ObjectIdGenerators.UUIDGenerator;



//@Controller(controllerKey = "/advapplyforWeChat")
public class WeChatPayUtil  {
	

	/**
	 * 企业付款到零钱
	 * @throws Exception 
	 */
	public static String transfers(HttpServletRequest request,String payId,BigDecimal amount,String tel,String openId) throws Exception{
		String[] uiids = null;
		//List<Record> record = null;//这个是jfinal的通用对象，跟可以理解为实体。
		// 提现转账
		String nonceStr    = WeChatUtil.getNonceStr();// 随机字符
		Map<String, String> map = null;
		List<Map<String, String>> list =  new ArrayList<>();

			
			TreeMap<String, String> parms = new TreeMap<String, String>(); 
			parms.put("mch_appid", WeChatConfig.MCH_APPID);//企业公众号appid
			parms.put("mchid", WeChatConfig.MCHID);//微信支付分配的商户号
			parms.put("nonce_str", nonceStr);//随机字符串，不长于32位
			parms.put("partner_trade_no",  WeChatUtil.getPartnerTradeNo(payId));//商户订单号，需保持唯一性
			parms.put("amount", WeChatUtil.bigDecimalToPoint(amount));//企业付款金额，单位为分
			parms.put("desc", "提现款额");//企业付款描述信息
			parms.put("spbill_create_ip", WeChatUtil.getIpAddr(request));//调用接口的机器Ip地址
			parms.put("openid", openId);//用户openid
			parms.put("check_name", "NO_CHECK");//NO_CHECK：不校验真实姓名 FORCE_CHECK：强校验真实姓名,OPTION_CHECK：针对已实名认证的用户才校验真实姓名
//		    parms.put("re_user_name", "mch_appid");//如果check_name设置为FORCE_CHECK或OPTION_CHECK，则必填用户真实姓名
			parms.put("sign", SignTools.buildRequestMysign(parms));//签名
			
			String resultXML = HttpClientCustomSSL.httpClientResult(parms);//转账
			//交易结果处理
			Map<String, Object> resultMap =  XMLUtil.doXMLParse(resultXML);
            String return_code = (String) resultMap.get("return_code");
            String result_code = (String) resultMap.get("result_code");
            
            if (return_code.equalsIgnoreCase("SUCCESS") && result_code.equalsIgnoreCase("SUCCESS")){
            	//交易成功
            	// TODO: handle exception
            	//map.put("result", "1");
            	System.out.println("交易成功");
            	
            	return "提现成功";
            }else{ 
            	//转账失败
            	// TODO: handle exception
            	return (String)resultMap.get("return_msg");
            }
//		renderJson(list);
	}
	
	
	

}
