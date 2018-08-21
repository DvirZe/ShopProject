package clientSide;

public class Worker extends Person {

	private static int workerCounter = 1;
	private int workerID;
	private String bankAcc;
	private String shop;
	private String job;
	private String password;
	private boolean login;
	
	public Worker(String id, String name, String phoneNr, String bankAcc, String shop, String job, String password) {
		super(id, name, phoneNr);
		workerID = workerCounter++;
		this.bankAcc = bankAcc;
		this.shop = shop;
		this.job = job;
		login = false;
	}
	
	public int getWorkerId() { return workerID; }
	public String getJob() { return job; }
	public String getPassword() { return password; }
	
	public void setJob(String job) { this.job = job; }
	
	public void changeLogin() { login = !login; }
	public boolean isLogin() { return login; }

}
