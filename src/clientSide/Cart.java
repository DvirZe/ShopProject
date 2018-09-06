package clientSide;

import java.util.Vector;

public class Cart {
	private Vector<Integer> cart;
	private Customer customer;
	
	public Cart() { 
		cart = new Vector<Integer>();
		customer = null;
	}
	
	public void addToCart(int itemNr) { cart.addElement(itemNr); }
	
	public int deleteFromCart(int place) { 
		int item = cart.get(place);
		cart.remove(place); 
		return item;
	}
	
	public int getSize() { return cart.size(); }
	
	public void setCustomer(Customer customer)
	{
		this.customer = customer;
	}
	
	public Customer getCustomer() { return customer; }
}
