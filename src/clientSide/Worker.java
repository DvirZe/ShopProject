package clientSide;

public class Worker extends Person {
	private int workerID;
	private String bankAcc;
	private String shop;
	private String job;
	private String password;
	private boolean login;
	
	public Worker(String id, int workerID, String name, String phoneNr, String bankAcc, String shop, String job, String password) {
		super(id, name, phoneNr);
		this.workerID = workerID;
		this.bankAcc = bankAcc;
		this.shop = shop;
		this.job = job;
		this.password = password;
		login = false;
	}
	
	public int getWorkerId() { return workerID; }
	
	public String getPassword() { return password; }
	
	public String getBankAcc() { return bankAcc; }
	
	public void setJob(String job) { this.job = job; }
	
	public void changeLogin() { login = !login; }
	
	public boolean isLogin() { return login; }
	
	public String getShopName() {return shop; }
	
	public int getLoginStatus() { return login?1:0; }
	
	public String getJob() {
		return job;
	}

}
