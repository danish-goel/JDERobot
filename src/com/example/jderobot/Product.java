package com.example.jderobot;

public class Product 
{
	@Override
	public String toString() 
	{
		// TODO Auto-generated method stub
		StringBuilder sb=new StringBuilder();
		sb.append("SKU:\t"+this.getSku());
		sb.append("Amoutn:\t"+this.getAmount());
		sb.append("Currency:\t"+this.getCurrency());
		return sb.toString();
	}

	private String sku;
	private float amount;
	private String currency;
	
	public String getSku() 
	{
		return sku;
	}
	
	public void setSku(String sku) 
	{
		this.sku = sku;
	}
	
	public float getAmount() 
	{
		return amount;
	}
	
	public void setAmount(float amount) 
	{
		this.amount = amount;
	}
	
	public String getCurrency() 
	{
		return currency;
	}
	
	public void setCurrency(String currency) 
	{
		this.currency = currency;
	}
	
}
