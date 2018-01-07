package sunwou.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.ZhimaCreditScoreBriefGetRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.ZhimaCreditScoreBriefGetResponse;




public class MYUtil {

	    //appid
		public static final String appid="2017100609157970";
		//私钥
		public static final String privatekey="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCQ3rwnBlS40L2vqat1r3ZwW0lKIkt7mMJfw3WSJjnnCK+5MN+fvy94HIooaN5c/LgqaMiJqCRn86QgjTYHJ2RvLT/VcD7RWeEpQtjYM8A+9J3lqst4QoX8Yf8zgSEmSMeA3G41T9R7fxJeJg0ssEAyhNz14ek9wEN8Q0nJEzVHtkRJDW9kpyvISyTCltJ2M0jSQecQEYo2Jlksodl5NChaLraL73+3xwzDjrChDLg7zgFSM/itcvxArjeDXOtC/I3Jy0z5K1pceIHjmvf3dUY7PGQ3tB0JhYWuJdKtuts+C35ugxAc9/Oe2kntu1EnYsxFdWX8WB9KonZBhz5dMdVJAgMBAAECggEAA6HB38yV1ShOiRfc1491ja2MMdKYR9n1jYcN7IMO0o7yMNdb09psKzuz7v1qDbS8PTvxQxo3B3AhPjusDhPbvbnJruUofgEd1vX5D7qEaE+job1dan9ocoJWbokfNqF+dumPzcBZmFsAL+IOVH96rImOLI/kVAr85iGduO/FVy3dt1RJU91YHckhHEEOcmEcVOwH04+8d7lnqNUQ1W3ZhE70nZOh4cCRugHdFcxwUN7T5Qy7Gz4J6csmJYdDUj4rbjGG0yFZGK7eUDNUBLnjbDgC6b+tRpweYbv9r/go734C3hmuoLjGF/nQjq/Q8T03ELmgZi4Suuy33XqmChhC3QKBgQDvjmij7j9I/lyuJGft0TOm5HaLUuuAV6mtt7QIbYgv4UtNXLIHdWlYGTnWKF4gvEFkFeNFlh0EzzFRfhtStIZhxonTj4FowOCCaLzHtisy5XMSGj4d/KArzJ0wQ4aq9fUt8H1VNGTqah+f+Empd8vY4MPfPkQ5sRArQkDnuCfPlwKBgQCa0HUDiBBSfHRSkMHe96Xt9dcVYAp0h2hatXppWmgLdOtegaBW2v32VBM97rTOc69loS8s8NPWv7IyG3k+kudXvU14EFs5HvL+OQPca1+N8WAgcsEYF24AAAPRAkz6RNLKocYLjnO/FAXWmUzw6L+SBRKUBEaMyX3qDQ/hC9QeHwKBgQDYVoJXcQRD9jpapdQZqDD+ShpoThURzZkVoe37Ine3zb1Eey7nn3foMLfTmJcQ9xISwdWCdUN1oWuHjdw8/3zQdgzoOxQCNxVJkC7UKMawaxhVQ8/PIuK/S0S1XnYSlJa7yxBUz0FHlVlEDWTwn5g/HxSk2kWg2C+i8W1OFjgVtwKBgElvXkIJlax66naLwdqw4B2LQrYZ83upC2ATu3gj8TyV/lu640Kv6E2jPelfxJY1m9p6ZbVMmz+/eJh6Hb4H0wL0gb3VDGCBBMSAaQtyR0Y+W9modV2nJ3+KgkTvcXI5mT+TEy1CKjdWGRrdZvjTfqOWEmTxzkFUBo0yGvkmMj0DAoGBANBn+DBAZwhQD6Odl3zRWNDRNKvda/sh2uL/9tG9fBB/SHNtMWms+MdRtqwpT/eL5H5Idp5vuvjW1HuvEHfjQtIAaz0A02Z6+9Zi46Y30m7vM0ctiQcKG2asbwEPEdOC2PT4DHxH1SLKcqMwP0eeJeWLDjNCw4ZM+1TEsN16OH89";
		//公钥
		public static final String publickey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqazEaMzc/z/aiP7UKzt9ZYsNtYk4avuKFYsTrqJqfhkv0QEMk2Q+K278V8LzOehPpDuwjxgBuP3aXmk8aDksJqQO6hVWoPtjeiibdAjGqolQER7oinOHfT6yoQi3m8nGw0yq8FBOLgqdEJsU0IOLHvPNdYgJrlUkRN5e3ZlG2bdZBfTHT5lGCV0/nQjnB4bS9SFxDLsKz9lsK4PCp2QEJpYJh6uqTO/k5CuQynTdZId1VwE27XjilCU6giZR/3fqXHKAIBM5cUsaPV3I1g+y+2s2vPMrO4Z40Otk5gPLlmAMA1NNWfC2/RP9VTqEBdupnld6gCVm9FcuiZXhmvg2ZQIDAQAB";
		
		private static final String notify_name="https://www.tongzhuhe.com/electric/notify";
		
		  public static Map<String,NotifyImple> notifyimple=new HashMap<>();
		
		public static String login(String code) throws AlipayApiException{
			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",appid,privatekey,"json","utf-8",publickey,"RSA2");
			AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
			request.setGrantType("authorization_code");
			request.setCode(code);
			AlipaySystemOauthTokenResponse response = alipayClient.execute(request);
			if(response.isSuccess()){
					return response.getUserId();
			} else {
				return null;
			}
		}
		
		public static Boolean checkZhima(String userId) throws AlipayApiException{
			//判断芝麻信用分
			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
					appid, privatekey, "json", "utf-8", publickey, "RSA2");
			ZhimaCreditScoreBriefGetRequest request = new ZhimaCreditScoreBriefGetRequest();
			request.setBizContent("{" +
			"    \"transaction_id\":\""+"sunwo_zhima"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"\"," +
			"    \"product_code\":\"w1010100000000002733\"," +
			"    \"cert_type\":\"ALIPAY_USER_ID\"," +
			"    \"cert_no\":\""+userId+"\"," +
			"    \"admittance_score\":650" +
			"  }");
			ZhimaCreditScoreBriefGetResponse response = alipayClient.execute(request);
			if(response.isSuccess()){
				//添加充电记录
				if(response.getIsAdmittance().equals("Y"))
				{
					//可以使用
					return true;
				}else
				{
					//芝麻信用分不够
					return false;
				}
			} else {
				return null;
			}
		}
		
		
		
		public static Object pay(String subject,String Out_trade_no,String total_amount,String notify,NotifyImple  notifyImple){
			//实例化客户端
			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", appid, 
					privatekey, "json", "utf-8", publickey, "RSA2");
			//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
			AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
			//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
			AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
			model.setBody(notify);
			model.setSubject(subject);
			model.setOutTradeNo(Out_trade_no);
			model.setTimeoutExpress("60m");
			model.setTotalAmount(total_amount);
			model.setProductCode("QUICK_MSECURITY_PAY");
			request.setBizModel(model);
			request.setNotifyUrl(notify_name);
			try {
			    //这里和普通的接口调用不同，使用的是sdkExecute
			    AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
			    if(!notifyimple.containsKey(notify))
	        	{
	        		notifyimple.put(notify, notifyImple);
	        	}
			    return null;
			} catch (AlipayApiException e) {
			   return null;
			}
		}
		
		
		 /**
		 * 回调方法封装
		 * @throws AlipayApiException 
		 */
		public static void notify(HttpServletRequest req,HttpServletResponse rep) throws AlipayApiException {
			//获取支付宝POST过来反馈信息
			Map<String,String> params = new HashMap<String,String>();
			Map requestParams = req.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			    String name = (String) iter.next();
			    String[] values = (String[]) requestParams.get(name);
			    String valueStr = "";
			    for (int i = 0; i < values.length; i++) {
			        valueStr = (i == values.length - 1) ? valueStr + values[i]
			                    : valueStr + values[i] + ",";
			  	}
			    //乱码解决，这段代码在出现乱码时使用。
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				params.put(name, valueStr);
			}
			//切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
			//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
			boolean flag = AlipaySignature.rsaCheckV1(params, publickey, "utf-8","RSA2");
			if(flag)
			{
				//处理业务
				String ordernumber=params.get("out_trade_no");
				String notifyActionName=params.get("body");
				if(notifyimple.containsKey(notifyActionName))
				{
					if(notifyimple.get(notifyActionName).notifcation(params))
					{
						//业务处理成功
						
					}else
					{
						//业务处理失败
						Util.outLog(ordernumber+",业务处理失败");
					}
				}else
				{
					//未找到处理方法
					Util.outLog(ordernumber+",未找到处理方法");
				}
			}
		}
		
}
