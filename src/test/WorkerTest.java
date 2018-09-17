package test;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;
import clientSide.*;

public class WorkerTest {

	
	@Test
	public void workerTest() {
		Worker emp = new Worker(" ", 3, "Dvir", "0502223355", "123", "Shop1", "Cashier", "ABC123!");
		assertNotEquals("there cant be person with no ID",  "" ,emp.getId());
	}

}
