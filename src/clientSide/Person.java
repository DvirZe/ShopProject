package clientSide;

public class Person {

	private String id;
	private String name;
	private String phoneNr;
	
	public Person (String id, String name, String phoneNr) {
		this.id = id;
		this.name = name;
		this.phoneNr = phoneNr;
	}
	
	public String getId() { return id;}
	public String getName() { return name;}
	public String getPhoneNr() { return phoneNr;}
	
	public void setName (String name) { this.name = name; }
	public void setPhoneNr (String phoneNr) { this.phoneNr = phoneNr; }
	
}
