package clientSide;

import java.util.Vector;

public class Cart {
	private Vector<Integer> cart;
	
	public Cart() { cart = new Vector<Integer>(); }
	
	public void addToCart(int itemNr) { cart.addElement(itemNr); }
	
	public int deleteFromCart(int place) { 
		int item = cart.get(place);
		cart.remove(place); 
		return item;
	}
	
	public int getSize() { return cart.size(); }
}
