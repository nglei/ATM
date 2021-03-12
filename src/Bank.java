import java.util.ArrayList;
import java.util.Random;

public class Bank {

	private String name;
	
	private ArrayList<User> users;
	
	private ArrayList<Account> accounts;
	
	public Bank(String name) {
		this.name = name;
		this.users = new ArrayList<User>();
		this.accounts = new ArrayList<Account>();
	}
	
	public String getNewUserUUID() {
		
		String uuid;
		
		Random rng = new Random();
		int len = 6;
		boolean nonUnique;
		
		//keep generate until get unique id
		do {
			
			uuid = "";
			for (int c = 0; c < len; c++) {
				uuid += ((Integer)rng.nextInt(10)).toString();
			}
			
			nonUnique = false;
			for (User u: this.users) {
				if(uuid.compareTo(u.getUuid()) == 0) {
					nonUnique = true;
					break;
				}
			}
			
		}while(nonUnique);
		
		return uuid;
	}
	
	public String getNewAccountUUID() {
		
		String uuid;
		
		Random rng = new Random();
		int len = 10;
		boolean nonUnique;
		
		//keep generate until get unique id
		do {
			
			uuid = "";
			for (int c = 0; c < len; c++) {
				uuid += ((Integer)rng.nextInt(10)).toString();
			}
			
			nonUnique = false;
			for (Account a : this.accounts) {
				if(uuid.compareTo(a.getUuid()) == 0) {
					nonUnique = true;
					break;
				}
			}
			
		}while(nonUnique);
		
		return uuid;
		
	}
	
	public void addAccount(Account newAcc) {
		this.accounts.add(newAcc);
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User addUser(String firstName, String lastName, String pin) {
		
		User newUser = new User(firstName, lastName,pin, this);
		this.users.add(newUser);
		
		Account newAccount = new Account("Savings", newUser, this);
		newUser.addAccount(newAccount);
		this.accounts.add(newAccount);
		
		return newUser;
	}
	
	public User userLogin(String userID, String pin) {
		for(User u: this.users) {
			
			if(u.getUuid().compareTo(userID)==0  && u.validatePin(pin)){
				return u;
			}
		}
		
		return null;
	}

}
