package clientSide;

public class Client extends Person {

	private String type;
	
	public Client(String id, String name, String phoneNr, String type) {
		super(id, name, phoneNr);
		this.type = type;
	}

	public String getType() { return type; }
	public void changeType(String type) { this.type = type; }
	
}
