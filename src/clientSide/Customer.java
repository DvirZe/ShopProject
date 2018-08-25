package clientSide;

public class Customer extends Person {

	private CustomerType type;
	
	public Customer(String id, String name, String phoneNr, CustomerType type) {
		super(id, name, phoneNr);
		this.type = type;
	}

	public CustomerType getType() { return type; }
	public void changeType(CustomerType type) { this.type = type; }
	
}
