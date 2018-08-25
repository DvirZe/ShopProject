package clientSide;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.TreeMap;
import java.util.Vector;

import javax.net.ssl.SSLSocketFactory;

import org.json.simple.JSONObject;


public class Shop {
	private String shopName;
	private TreeMap<Integer, Vector<Integer>> Inventory;
	private Cart shopCart; 
	private CustomerType CustomerTypeNew;
	private CustomerType CustomerTypeReturn;
	private CustomerType CustomerTypeVip;
	
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
		CustomerTypeNew = new CustomerTypeNew();
		CustomerTypeReturn = new CustomerTypeReturn(0.95);
		CustomerTypeVip = new CustomerTypeVip(0.85);
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
	
	public void saveInfo(PrintWriter printWriter, BufferedReader socketBufferedReader) throws IOException { 
		JSONObject obj = null;
		obj.put("shopName", shopName);
		printWriter.println(obj.toString());
		System.out.println(socketBufferedReader.readLine());
		/*
		try (FileWriter file = new FileWriter("c:\\employees.json")) {
			 
            file.write(obj.toString());
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
        }*/
		
	}
	
	
	public static void main(String[] args)
	{
		JSONObject obj = null;
		obj.put("ShopName", "shopName");
		Shop sp = new Shop(obj.escape("ShopName"));
		Customer c1 = new Customer("54646465", "fdgfd", "0502133427", sp.CustomerTypeReturn);
		System.out.println(c1.getId()+ " " + c1.getName()+ " " + c1.getPhoneNr() + " " + c1.getType().getCustomerTypeDiscount());
		Customer c2 = new Customer("54646465", "fdgfd", "0502133427", sp.CustomerTypeReturn);
		System.out.println(c2.getId()+ " " + c2.getName()+ " " + c2.getPhoneNr() + " " + c2.getType().getCustomerTypeDiscount());
		sp.CustomerTypeReturn.setCustomerTypeDiscount(0.90);
		System.out.println(c1.getId()+ " " + c1.getName()+ " " + c1.getPhoneNr() + " " + c1.getType().getCustomerTypeDiscount());
		System.out.println(c2.getId()+ " " + c2.getName()+ " " + c2.getPhoneNr() + " " + c2.getType().getCustomerTypeDiscount());
		System.out.println(sp.shopName);

	}
	
}
