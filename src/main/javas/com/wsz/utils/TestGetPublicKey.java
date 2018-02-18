package com.wsz.utils;

import com.wsz.utils.GetPublicKey;
/**
 * 获取的公钥需要转化后才能使用
 * */
public class TestGetPublicKey {

	public static void main(String[] args) throws Exception {
		System.out.println(new GetPublicKey().getPublicKey());
	}
}
