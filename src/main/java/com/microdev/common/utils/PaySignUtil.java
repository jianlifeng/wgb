package com.microdev.common.utils;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
import java.util.Map.Entry;


public class PaySignUtil {
	
	
	private static final Logger log = LoggerFactory.getLogger(PaySignUtil.class);
	
	public static final String SIGN_ALGORITHMS = "SHA1WithRSA";
	
	private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCANZVinfVHVSRG2Rzchgz2XvXElutqxifnVeJN5+VxQTeob5TcOIRw0bQq7zjM5Tuq2OHl4Lg7ogu+a7Jb+gJetvJwPrsFrYJsBTIIwkBjb6q8xBSyo5F8fDp5/Zm2SJgFam3FbfLYB3vqhz1kbcqbbgWZ1dbvCWxUreczP0WPEQIDAQAB";
	
	private static String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIA1lWKd9UdVJEbZHNyGDPZe9cSW62rGJ+dV4k3n5XFBN6hvlNw4hHDRtCrvOMzlO6rY4eXguDuiC75rslv6Al628nA+uwWtgmwFMgjCQGNvqrzEFLKjkXx8Onn9mbZImAVqbcVt8tgHe+qHPWRtyptuBZnV1u8JbFSt5zM/RY8RAgMBAAECgYBqsKx8oWgAkVib4IbE+ISG7STmEJUdiIKiXvTw0b48jgIMF7avwBRucgPVCregwk3x8YOisWt+rG3La4HESnt3MfusAAGNUioO5I5MFUEyRDoMq3hh+zySvnzw/h3aONNAgYV6d/0qyWCmMWST7NyPfsODB3inJY71oHpuZmuBcQJBAOXVU9oLDHvqg2vLpaCwAYV8noNvAlOEgEq/GDV9X6+TU/H4k9c+BsijC6rSeYeRtSKiRZ84qA9ZAPZaM/vrmS0CQQCOzlg64lpthn+AQfaICx2a1IvtRDDigyAWpvWY7nFelZ26dzshKMtptOIZhqhxhChlDOcjNbbSPb8So4URaDP1AkEAsIVjLKG1yeq5W26C3GKyGHM5T9tP3xNycXZJwrNzbWdrXvo7mmKSVUEc8etL2froMxyM+phKQ9dpLMzlBkTSVQJAeLDz6HA9NKujPmaBUOD0GFLiH5iuKAHiMMLSdnmu3t1b/KegFZpAsAnrvN8NpUKoUR7iieNvq6f7wGjqoZeLsQJBANprDC/OPq9YgFgthOzg0B3ujT4xV14T4S8WOGpqIwHuIm3x0a0/zsb2rB8o+QnFurAtQWZmQ15SaJIwUJUjXI8=";
	
	/**
	 * RSA签名
	 * 
	 * @param content 待签名数据
	 * @param privateKey 商户私钥
	 * @param input_charset 编码格式
	 * @return 签名值
	 */
	public static String sign(String content, String privateKey, String input_charset) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);
			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
			signature.initSign(priKey);
			signature.update(content.getBytes(input_charset));
			byte[] signed = signature.sign();
			return Base64.encode(signed);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * RSA验签名检查
	 * 
	 * @param content 待签名数据
	 * @param sign 签名值
	 * @param ali_public_key 支付宝公钥
	 * @param input_charset 编码格式
	 * @return 布尔值
	 */
	public static boolean verify(String content, String sign, String ali_public_key, String input_charset) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = Base64.decode(ali_public_key);
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
			signature.initVerify(pubKey);
			signature.update(content.getBytes(input_charset));
			boolean bverify = signature.verify(Base64.decode(sign));
			return bverify;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * 解密
	 * 
	 * @param content 密文
	 * @param private_key 商户私钥
	 * @param input_charset 编码格式
	 * @return 解密后的字符串
	 */
	public static String decrypt(String content, String private_key, String input_charset)
		throws Exception {
		PrivateKey prikey = getPrivateKey(private_key);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, prikey);
		InputStream ins = new ByteArrayInputStream(Base64.decode(content));
		ByteArrayOutputStream writer = new ByteArrayOutputStream();
		// rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
		byte[] buf = new byte[128];
		int bufl;
		while ((bufl = ins.read(buf)) != -1) {
			byte[] block = null;
			if (buf.length == bufl) {
				block = buf;
			} else {
				block = new byte[bufl];
				for (int i = 0; i < bufl; i++) {
					block[i] = buf[i];
				}
			}
			writer.write(cipher.doFinal(block));
		}
		return new String(writer.toByteArray(), input_charset);
	}
	
	/**
	 * 得到私钥
	 * 
	 * @param key 密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String key)
		throws Exception {
		byte[] keyBytes;
		keyBytes = Base64.decode(key);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}
	
	/**
	 * 
	 * signSuccess: 请求验签 . <br/>
	 *
	 * @param request
	 * @param sign
	 * @return
	 */
	public static boolean requestSign(HttpServletRequest request, String sign) {
		// 按字母升序排序
		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		Map<String, String[]> map = request.getParameterMap();
		Set<Entry<String, String[]>> set = map.entrySet();
		Iterator<Entry<String, String[]>> its = set.iterator();
		while (its.hasNext()) {
			Entry<String, String[]> entry = its.next();
			String key = entry.getKey();
			String[] values = entry.getValue();
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			parameters.put(key, valueStr);
		}
		StringBuffer sb = new StringBuffer();
		Set<Entry<Object, Object>> es = parameters.entrySet();
		Iterator<Entry<Object, Object>> it = es.iterator();
		while (it.hasNext()) {
			Entry<Object, Object> entry = it.next();
			String k = (String) entry.getKey();
			// 排除字段
			if (k.equalsIgnoreCase("sign")) {
				continue;
			}
			Object v = entry.getValue();
			if (null != v) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("AppSecret=" + Const.AppSecret);
		log.debug("验签拼接：" + sb.toString());
		return PaySignUtil.verify(sb.toString(), sign, publicKey, "UTF-8");
	}
	
	/**
	 * 
	 * verifySign: 异步通知回调验签 . <br/>
	 *
	 * @param data
	 * @param sign
	 * @return
	 */
	public static boolean verifyNotifySign(String data, String sign) {
		Map<String, Object> params = new HashMap<>();
		if (StringUtils.isNotBlank(data)) {
			params = JSONObject.parseObject(data);
		}
		// 按字母升序排序
		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		for (Entry<String, Object> entry : params.entrySet()) {
			parameters.put(entry.getKey(), entry.getValue());
		}
		StringBuffer sb = new StringBuffer();
		Set<Entry<Object, Object>> es = parameters.entrySet();
		Iterator<Entry<Object, Object>> it = es.iterator();
		while (it.hasNext()) {
			Entry<Object, Object> entry = it.next();
			String k = (String) entry.getKey();
			// 排除字段
			if (k.equalsIgnoreCase("sign")) {
				continue;
			}
			Object v = entry.getValue();
			if (null != v) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("AppSecret=" + Const.AppSecret);
		log.debug("验签拼接：" + sb.toString());
		return PaySignUtil.verify(sb.toString(), sign, publicKey, "UTF-8");
	}

	public static String createSign(String data) {
		Map<String, Object> params = new HashMap<>();
		if (StringUtils.isNotBlank(data)) {
			params = JSONObject.parseObject(data);
		}
		SortedMap<Object, Object> treeMap = new TreeMap<Object, Object>();
		for (Entry<String, Object> entry : params.entrySet()) {
			treeMap.put(entry.getKey(), entry.getValue());
		}
		StringBuffer sb = new StringBuffer();
		Set<Entry<Object, Object>> es = treeMap.entrySet();
		Iterator<Entry<Object, Object>> it = es.iterator();
		while (it.hasNext()) {
			Entry<Object, Object> entry = it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			sb.append(k + "=" + v + "&");
		}
		sb.append("AppSecret=" + Const.AppSecret);
		log.debug("签名拼接：" + sb.toString());
		return PaySignUtil.sign(sb.toString(), privateKey, "UTF-8");
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(UUID.random());
			String sign = PaySignUtil.sign("AAAAAAAA", privateKey, "UTF-8");
			System.out.println(sign);
			boolean a = PaySignUtil.verify("AAAAAAAA", sign, publicKey, "UTF-8");
			System.out.println(a);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
