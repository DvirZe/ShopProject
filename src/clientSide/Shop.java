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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class Shop {
	private String shopName;
	private TreeMap<Integer, Vector<Integer>> Inventory;
	private Cart shopCart; 
	private CustomerType CustomerTypeNew;
	private CustomerType CustomerTypeReturn;
	private CustomerType CustomerTypeVip;
	
	public Shop(JSONObject json) {
		shopName = json.get("shopName").toString();
		Inventory = new TreeMap<Integer, Vector<Integer>>();
		JSONArray inv, prices;
		inv = (JSONArray) json.get("Inventory");
		prices = (JSONArray) json.get("Price");
		for (int i = 0; i < 4; ++i)
		{
			Inventory.put(i+1,new Vector<Integer>());
			Inventory.get(i+1).addElement(Integer.parseInt(inv.get(i).toString()));
			Inventory.get(i+1).addElement(Integer.parseInt(prices.get(i).toString()));
		}
		shopCart = new Cart();
		CustomerTypeNew = new CustomerTypeNew();
		CustomerTypeReturn = new CustomerTypeReturn(Double.parseDouble(json.get("customerTypeReturn").toString()));
		CustomerTypeVip = new CustomerTypeVip(Double.parseDouble(json.get("customerTypeVip").toString()));
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
	
	
	/*public static void main(String[] args)
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
	}*/
	
}
