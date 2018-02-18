package com.wsz.utils;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.wsz.utils.GetRSA;
import com.wsz.utils.HttpClientCustomSSL;
import com.wsz.utils.SignUtils;
import com.wsz.utils.StringUtils;
import com.wsz.utils.XMLUtils;
import com.wxenterprisepay.XMLUtil;

public class TestWChatToBank {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void pay(String name,String bankNumber,String bank_code,String amount,String desc,String partner_trade_no) throws Exception {
		// TODO Auto-generated method stub
		//注意 这里的  publicKeyPKCS8  是上一步获取微信支付公钥后经openssl "MIIBCgKCAQEA4rZ4nGjy6pxu77zasM转化成PKCS8格式的公钥
		String publicKeyPKCS8 ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4rZ4nGjy6pxu77zasMlCDme4ulJqK29BbEawxns7AzpXS90Zth6qQ34+/twenhP3FG6ICAP/Hl7kT+UZGKzbaf4Mf/VM0xFXHdzcaDfn9nwf1DcgosnkwQIIlBAxFJzyZlkJlKBdpYeP+RYr6nnDW9tU5WQNgRwwnCuSpqxP1+EqqOVAs/VQCqNCwfRiUzge7EqV0mp4u75OH/X/TFbuWomQLxHzSeKtyzyCYfEer8T8OMTObfG570eiZVceM2dCYzdm2ubaYtZ+iRtnICd/63emUa0h/UFIjLJKQNNFv3AF1c6T1REJbbenfdncYFkFemZD2w9TCqB08VrQ3UwE5QIDAQAB";
		String enc_true_name =GetRSA.getRSA(name,publicKeyPKCS8);
		String enc_bank_no = GetRSA.getRSA(bankNumber,publicKeyPKCS8);
	  
	    String nonce_str1 =  StringUtils.getStrRandom(28);
	    
	    //获取签名
	    SortedMap<Object,Object> parameters1 = new TreeMap<Object,Object>();
		parameters1.put("mch_id", WChatInfo.MCH_ID);
		parameters1.put("partner_trade_no", partner_trade_no);
		parameters1.put("nonce_str", nonce_str1);
		parameters1.put("enc_bank_no", enc_bank_no);
		parameters1.put("enc_true_name", enc_true_name);
		parameters1.put("bank_code", bank_code);
		parameters1.put("amount", amount);
		parameters1.put("desc", desc);
		String sign1 = SignUtils.creatSign(WChatInfo.CHARSET, parameters1);
		//请求企业付款
		 TreeMap<String, String> tmap1 = new TreeMap<String, String>();
		 tmap1.put("mch_id", WChatInfo.MCH_ID);
		 tmap1.put("partner_trade_no", partner_trade_no);
		 tmap1.put("nonce_str", nonce_str1);
		 tmap1.put("enc_bank_no", enc_bank_no);
		 tmap1.put("enc_true_name", enc_true_name);
		 tmap1.put("bank_code", bank_code);
		 tmap1.put("amount", amount);
		 tmap1.put("desc", desc);
		 tmap1.put("sign", sign1);
		String xml2 = XMLUtils.getRequestXml(tmap1);
		String  xml3= HttpClientCustomSSL.httpClientResultPANK(xml2);
		Map<String, Object> resultMap =  XMLUtil.doXMLParse(xml3);
		System.out.println(resultMap.get("return_msg"));
			
		
	}

}
