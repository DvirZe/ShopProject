import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonCreateTest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//TreeMap<String,Object> tmpShop = new TreeMap<String,Object>();
		////JSONObject json = new JSONObject();
		/*json.put("id", "123");
		json.put("name", "alex");
		json.put("phoneNr", "0505554564");
		json.put("CustomerType", 0);*/
		/*JSONArray list = new JSONArray();
		list.add("alex");
		list.add("0505554564");
		list.add("VIP");*/
		//list.add(10);
		/*JSONArray list2 = new JSONArray();
		list2.add(35);
		list2.add(25);
		list2.add(50);
		list2.add(80);*/
		//JSONObject json = new JSONObject();
		//json.putAll(tmpShop);
		///json.put("123", list);
		//json.put("Price", list2);
		
        ///JSONParser parser = new JSONParser();
        
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
		
		/*try (FileWriter file = new FileWriter("./files/Customers.json")) {
			file.write(json.toJSONString());
			System.out.println("Successfully Copied JSON Object to File...");
			System.out.println("\nJSON Object: " + json);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/

		FileWriter log = new FileWriter("./files/sales.log",true);
		log.write(
				 new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())
		+ "\n"
				
				
				);
		log.close();
	}

}
