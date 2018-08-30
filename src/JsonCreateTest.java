import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonCreateTest {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		//TreeMap<String,Object> tmpShop = new TreeMap<String,Object>();
		JSONObject json = new JSONObject();
		json.put("shopName", "Shop2");
		json.put("customerTypeNew", 1);
		json.put("customerTypeReturn", 0.95);
		json.put("customerTypeVip", 0.85);
		JSONArray list = new JSONArray();
		list.add(10);
		list.add(5);
		list.add(15);
		list.add(10);
		JSONArray list2 = new JSONArray();
		list2.add(35);
		list2.add(25);
		list2.add(50);
		list2.add(80);
		//JSONObject json = new JSONObject();
		//json.putAll(tmpShop);
		json.put("Inventory", list);
		json.put("Price", list2);
		
        JSONParser parser = new JSONParser();
        
        /*try {
 
            Object obj = parser.parse(new FileReader("./files/Shop1.json"));
            JSONObject jsonObject = (JSONObject) obj;
 
            JSONArray list2 = new JSONArray();
            
            list2 = (JSONArray) jsonObject.get("353645654");
 
            System.out.println(list2.get(0));
            System.out.println(list2.get(2));
            System.out.println(list2.get(1));
            Iterator<String> iterator = list2.iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
 
        } catch (Exception e) {
            e.printStackTrace();
        }*/
		
		//System.out.println(json.get("ID")); //Just a test, not log in function
		
		try (FileWriter file = new FileWriter("./files/Shop2.json")) {
			file.write(json.toJSONString());
			System.out.println("Successfully Copied JSON Object to File...");
			System.out.println("\nJSON Object: " + json);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
