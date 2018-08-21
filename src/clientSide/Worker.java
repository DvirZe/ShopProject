package clientSide;

public class Worker extends Person {

	private static int workerCounter = 1;
	private int workerID;
	private String bankAcc;
	private String shop;
	private String job;
	
	public Worker(String id, String name, String phoneNr, String bankAcc, String shop, String job) {
		super(id, name, phoneNr);
		workerID = workerCounter++;
		this.bankAcc = bankAcc;
		this.shop = shop;
		this.job = job;
	}
	
	public int getWorkerId() { return workerID; }
	public String getJob() { return job; }
	public void setJob(String job) { this.job = job; }

}
