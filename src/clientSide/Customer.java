package clientSide;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Customer extends Person {

	private String type;
	
	public Customer(String id, String name, String phoneNr, String type) {
		super(id, name, phoneNr);
		this.type = type;
	}

	public String getType() { return type; }
	public void changeType(String type) { this.type = type; }
	
}
