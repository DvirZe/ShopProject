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
	private CustomerType customerTypeNew;
	private CustomerType customerTypeReturn;
	private CustomerType customerTypeVip;
	private int totalSales;
	private int workerOnline;
	
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
		customerTypeNew = new CustomerTypeNew();
		customerTypeReturn = new CustomerTypeReturn(Double.parseDouble(json.get("customerTypeReturn").toString()));
		customerTypeVip = new CustomerTypeVip(Double.parseDouble(json.get("customerTypeVip").toString()));
		
		totalSales = Integer.parseInt(json.get("totalSales").toString());
		workerOnline = Integer.parseInt(json.get("personalID").toString());
	}
	
	public String getShopName() { return shopName; }
	
	public JSONObject endSell() { 
		for (int i = 0; i < 4 ; ++i)
		{
			int curInv = Inventory.get(i+1).get(0);
			Inventory.get(i+1).set(0, curInv-shopCart.getCartItems(i));
		}
		JSONObject sell = new JSONObject();
		sell.put("shopName", this.shopName);
		sell.put("customerId", shopCart.getCustomer());
		sell.put("shirt1", shopCart.getCartItems(0));
		sell.put("shirt2", shopCart.getCartItems(1));
		sell.put("pants1", shopCart.getCartItems(2));
		sell.put("pants2", shopCart.getCartItems(3));
		sell.put("totalPrice", shopCart.getTotalPrice());
		sell.put("workerId", workerOnline);
		shopCart.emptyCart();
		return sell;
	}
	
	public void buyInventory(int item, int sum)
	{
		Inventory.get(item).set(0, Inventory.get(item).get(0)+sum);
	}
	
	public void UpdateInventory(int item, int sum)
	{
		Inventory.get(item).set(0, sum);
	}
	
	public int getInventory(int item) { return Inventory.get(item).get(0); }
	public int getPrices(int item) { return Inventory.get(item).get(1); }
	public Cart getCart() { return shopCart; }
	
	
	public Double getDiscountForCustomer(String type)
	{		
		switch(type)
		{
			case "Return":
				return customerTypeReturn.getCustomerTypeDiscount();
			case "VIP":
				return customerTypeVip.getCustomerTypeDiscount();
			default:
				return 0.0;	
		}
	}
	
	public void setDiscount(double vip, double returned)
	{
		customerTypeReturn.setCustomerTypeDiscount(returned);
		customerTypeVip.setCustomerTypeDiscount(vip);
	}
	
	public void setPrices(int newPriceArr[])
	{ 
		for (int i = 1; i<= 4; ++i)
		{
			Inventory.get(i).set(1, newPriceArr[i-1]);
		}
		
	}
	
	public int getWorkerOnline() { return workerOnline; }
	
	public void saveInfo(PrintWriter printWriter, BufferedReader socketBufferedReader) throws IOException { 
		JSONObject obj = null;
		obj.put("shopName", shopName);
		printWriter.println(obj.toString());
		System.out.println(socketBufferedReader.readLine());
	}
}
