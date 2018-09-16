package clientSide;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CustomerType {
	private String typeName;
	private double discountValue;
	
	public CustomerType (String typeName, double discountValue)
	{
		this.typeName = typeName;
		this.discountValue = discountValue;
	}
	
	public String getCustomerTypeName() { return typeName; }
	public void setCustomerTypeDiscount(double value) { discountValue = value; }
	public double getCustomerTypeDiscount() { return discountValue; };
	
}
