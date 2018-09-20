package test;

import static org.junit.Assert.assertEquals;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import clientSide.Shop;

public class ShopTest {	

	@Test
	public void shopNameTest() {
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		array.add("10");
		array.add("5");
		array.add("15");
		array.add("10");
		json.put("Inventory", array.clone());
		array.clear();
		array.add("20");
		array.add("25");
		array.add("50");
		array.add("80");
		json.put("Price", array.clone());
		array.clear();
		array.add("0");
		array.add("0");
		array.add("0");
		array.add("0");
		json.put("sellesHistory", array.clone());
		array.clear();
		json.put("customerTypeVip", "0.15");
		json.put("customerTypeReturn", "0.05");
		json.put("shopName", "HIT-Shirt");
		json.put("customerTypeNew", "0");
		json.put("personalID", "1");
		Shop shop = new Shop(json);
		assertEquals("The shop names are different!", shop.getShopName(), "HIT-Shirt");
	}
	
	@Test
	public void shopWorkerTest() {
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		array.add("10");
		array.add("5");
		array.add("15");
		array.add("10");
		json.put("Inventory", array.clone());
		array.clear();
		array.add("20");
		array.add("25");
		array.add("50");
		array.add("80");
		json.put("Price", array.clone());
		array.clear();
		array.add("0");
		array.add("0");
		array.add("0");
		array.add("0");
		json.put("sellesHistory", array.clone());
		array.clear();
		json.put("customerTypeVip", "0.15");
		json.put("customerTypeReturn", "0.05");
		json.put("shopName", "HIT-Shirt");
		json.put("customerTypeNew", "0");
		json.put("personalID", "201070620");
		Shop shop = new Shop(json);
		assertEquals("Wrong worker online ID!", shop.getWorkerOnline(), 201070620);
	}

}
