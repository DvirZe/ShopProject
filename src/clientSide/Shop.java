package clientSide;

import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeMap;
import java.util.Vector;

import org.json.JSONObject;


public class Shop {
	private String shopName;
	private TreeMap<Integer, Vector<Integer>> Inventory;
	private Cart shopCart; 
	
	public Shop(String name) {
		shopName = name;
		Inventory = new TreeMap<Integer, Vector<Integer>>();
		shopCart = new Cart();
		Inventory.put(1,new Vector<Integer>()); //Shirt 1
		Inventory.get(1).add(10); //Inventory
		Inventory.get(1).add(35); //price
		Inventory.put(2, new Vector<Integer>()); //Shirt 2
		Inventory.get(2).add(5); //Inventory
		Inventory.get(2).add(25); //price
		Inventory.put(3, new Vector<Integer>()); //Pants 1
		Inventory.get(3).add(15); //Inventory
		Inventory.get(3).add(50); //price
		Inventory.put(4, new Vector<Integer>()); //Pants 2
		Inventory.get(4).add(10); //Inventory
		Inventory.get(4).add(80); //price
	}
	
	public String getShopName() { return shopName; }
	
	public void endSell() { 
		int size = shopCart.getSize();
		for (int i = 0; i < size; ++i)
		{
			int updatedItem = shopCart.deleteFromCart(0);
			Inventory.get(updatedItem).set(0, Inventory.get(updatedItem).get(0)-1);
		}
	}
	
	public void buyInventory(int item, int sum)
	{
		Inventory.get(item).set(0, Inventory.get(item).get(0)+sum);
	}
	
	public int getInventory(int item) { return Inventory.get(item).get(0); }
	
	public void saveInfo() { 
		JSONObject obj = new JSONObject();
		obj.append("shopName", shopName);
		
		try (FileWriter file = new FileWriter("c:\\employees.json")) {
			 
            file.write(obj.toString());
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
	
	
	public static void main(String[] args)
	{
		Shop sp = new Shop("Store 1");
		sp.shopCart.addToCart(1);
		sp.shopCart.addToCart(1);
		sp.shopCart.addToCart(1);
		sp.shopCart.addToCart(1);
		sp.shopCart.addToCart(1);
		System.out.println(sp.getInventory(1));
		sp.shopCart.addToCart(1);
		sp.shopCart.addToCart(1);
		sp.shopCart.addToCart(1);
		sp.shopCart.addToCart(1);
		sp.shopCart.addToCart(1);
		sp.shopCart.deleteFromCart(1);
		sp.endSell();
		System.out.println(sp.getInventory(1));
		Client c1 = new Client("54646465", "fdgfd", "0502133427", "R");
		System.out.println(c1.getId()+ " " + c1.getName()+ " " + c1.getPhoneNr() + " " + c1.getType());
		sp.saveInfo();
		
	}
	
}
