package com.microdev.common;

public class PayCallbackForm {
	
	
	private String orderNo;
	
	private String tradeNo;
	
	private Integer totalFee;
	
	private String serviceCode;
	
	private Integer payType;
	
	private String orderId;
	
	private String userId;
	
	private String buyer;// 购买者账号
	
	public String getOrderNo() {
		return orderNo;
	}
	
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getTradeNo() {
		return tradeNo;
	}
	
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	
	public Integer getTotalFee() {
		return totalFee;
	}
	
	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}
	
	public String getServiceCode() {
		return serviceCode;
	}
	
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	
	public Integer getPayType() {
		return payType;
	}
	
	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBuyer() {
		return buyer;
	}
	
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	
}
