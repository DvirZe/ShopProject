package clientSide;

import java.util.Vector;

public class Cart {
	private int[] cart;
	private String customer;
	private double totalPrice;
	
	public Cart() { //creates cart for a new customer
		cart = new int[4];
		customer = new String("New Customer - ID not saved");
	}
	
	public void cartUpdate(int itemNr, int sum) { cart[itemNr] = sum; }
	
	public int getCartItems(int item)
	{
		return cart[item];
	}
	
	public void emptyCart() { 
		for (int i=0;i<4;++i)
			cart[i]=0;
		customer = "New Customer - ID not saved";
		totalPrice = 0;
	}
	
	public void setTotalPrice(double price)
	{
		totalPrice = price;
	}
	
	public double getTotalPrice() { return totalPrice; }
	
	public void setCustomer(String customer)
	{
		this.customer = customer;
	}
	
	public String getCustomer() { return customer; }
}
