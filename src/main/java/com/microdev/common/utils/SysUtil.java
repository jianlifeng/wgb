package com.microdev.common.utils;

import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Date;
import java.util.Random;

public class SysUtil {
	
	
	public static String createOrderNo() {
		Random rad = new Random();
		// 创建商户订单号
		String timestamp = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
		// 补三位随机数
		String radNum = String.format("%03d", rad.nextInt(1000));
		return "Y" + timestamp + radNum;
	}
}
