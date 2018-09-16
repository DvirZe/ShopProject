package test;

import static org.junit.Assert.assertEquals;


import org.junit.Test;

import clientSide.Cart;
import clientSide.Worker;

public class CartTest {
	private Cart cart;

	@Test
	public void cartTest() {
		Cart cart = new Cart();
		cart.cartUpdate(0,8);
		assertEquals("The number of items is not similar!", cart.getCartItems(0), 8);
	}
}
