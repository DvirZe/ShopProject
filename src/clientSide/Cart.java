package clientSide;

import java.util.Vector;

public class Cart {
	private int[] cart;
	private Customer customer;
	
	public Cart() { 
		cart = new int[4];
		customer = null;
	}
	
	public void cartUpdate(int itemNr, int sum) { cart[itemNr] = sum; }
	
	public void emptyCart() { 
		for (int i=0;i<4;++i)
			cart[i]=0;
	}
	
	public void setCustomer(Customer customer)
	{
		this.customer = customer;
	}
	
	public Customer getCustomer() { return customer; }
}
