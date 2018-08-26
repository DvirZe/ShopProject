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
		TreeMap<String,Object> tmpUser = new TreeMap<String,Object>();
		tmpUser.put("Name", "UserTest");
		tmpUser.put("Phone", "0502133427");
		tmpUser.put("Shop", "Shop1");
		tmpUser.put("BankAcc", "123");
		JSONArray list = new JSONArray();
		list.add("UserTest");
		list.add("0502133427");
		list.add("Shop1");
		list.add("123");
		//users.put("ID", tmpUser);
		JSONObject json = new JSONObject();
		json.put(353645654, list);
		
		
        JSONParser parser = new JSONParser();
        
        try {
 
            Object obj = parser.parse(new FileReader("./files/Workers.json"));
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
        }
		
		//System.out.println(json.get("ID")); //Just a test, not log in function
		
		/*try (FileWriter file = new FileWriter("./files/users.json")) {
			file.write(json.toJSONString());
			System.out.println("Successfully Copied JSON Object to File...");
			System.out.println("\nJSON Object: " + json);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
	}

}
