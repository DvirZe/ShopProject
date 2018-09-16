package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import clientSide.CustomerType;

public class CustomerTest {
	
	
	@Test
	public void customerTest() {
		CustomerType CusCheck = new CustomerType("Shlomo",1);
		assertEquals("Can't get customer name ", CusCheck.getCustomerTypeName(), "Shlomo");

	}

}
