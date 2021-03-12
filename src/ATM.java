
import java.util.Scanner;

public class ATM {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		Bank theBank = new Bank("Bank of Drausin");
		
		//add a user
		User aUser = theBank.addUser("John", "Doe", "1234");
		
		// add a checking account for our user
		Account newAccount  = new Account("Checking", aUser, theBank);
		aUser.addAccount(newAccount);
		theBank.addAccount(newAccount);
		
		User curUser;
		while (true) {
			
			curUser = ATM.mainMenuPrompt(theBank, sc);
			
			ATM.printUserMenu(curUser,sc);
		}
		
	}
	
	/**
	 * 
	 * @param theBank
	 * @param sc
	 * @return
	 */
	public static User mainMenuPrompt(Bank theBank, Scanner sc) {
		String userID;
		String pin;
		User authUser;
		
		do {
			System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
			System.out.print("Enter user ID: ");
			userID = sc.nextLine();
			System.out.print("Enter pin: ");
			pin = sc.nextLine();
			
			authUser = theBank.userLogin(userID, pin);
			if(authUser == null) {
				System.out.println("Incorrect user ID/pin combination." + "Please try again.");
			}
			
		}while(authUser == null);
		
		return authUser;
	}
	
	public static void printUserMenu(User theUser, Scanner sc) {
		
		theUser.printAccountsSummary();
		
		int choice;
		
		do {
			System.out.printf("Welcome %s, what would you like to do?\n", theUser.getFirstName());
			System.out.println(" 1) Show account transaction history");
			System.out.println(" 2) Withdrawl");
			System.out.println(" 3) Depost");
			System.out.println(" 4) Transfer");
			System.out.println(" 5) Quit");
			System.out.println();
			System.out.print("Enter choice: ");
			choice = sc.nextInt();
			
			if(choice < 1 || choice > 5) {
				System.out.println("Invalid Choice. Please choose  1-5");
			}
		}while(choice < 1 || choice > 5);
		
		switch (choice) {
		
		case 1:
			ATM.showTransHistory(theUser, sc);
			break;
		case 2:
			ATM.withdrawlFunds(theUser, sc);
			break;
		case 3:
			ATM.depositFunds(theUser, sc);
			break;
		case 4:
			ATM.transferFunds(theUser, sc);
			break;
			
		case 5:
			sc.nextLine();
			break;
		}
		
		// redisplay the menu
		if(choice != 5) {
			ATM.printUserMenu(theUser, sc);
		}
	}
	
	public static void showTransHistory(User theUser, Scanner sc) {
		
		int theAcct;
		
		do {
			System.out.printf("Enter number (1-%d) of the account"+
					"whose transactions you want to see: ",theUser.numAccounts());
			theAcct = sc.nextInt()-1;
			if(theAcct < 0 || theAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again");
			}
		}while(theAcct < 0 || theAcct >= theUser.numAccounts());
		
		theUser.printAcctTransHistory(theAcct);
	}
	
	public static void transferFunds(User theUser, Scanner sc) {
		
		int fromAcct;
		int toAcct;
		double amount;
		double acctBal;
		
		do {
			System.out.printf("Enter number (1-%d) of the account"+
					"to transfer from: ",theUser.numAccounts());
			fromAcct = sc.nextInt()-1;
			if(fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again");
			}
		}while(fromAcct < 0 || fromAcct >= theUser.numAccounts());
		
		acctBal = theUser.getAcctBalance(fromAcct);
		
		// get account transfer to
		do {
			System.out.printf("Enter number (1-%d) of the account"+
					"to transfer to: ",theUser.numAccounts());
			toAcct = sc.nextInt()-1;
			if(toAcct < 0 || toAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again");
			}
		}while(toAcct < 0 || toAcct >= theUser.numAccounts());
		
		//get amount to transfer
		do {
			System.out.printf("Enter the amount to transfer (max $%.02f): $",acctBal);
			amount = sc.nextDouble();
			
			if(amount < 0) {
				System.out.println("Amount must be greater than 0");
			}else if(amount > acctBal){
				System.out.printf("Amount must not be greater than\n"+
						"balance of $.02f.\n",acctBal);
			}
		}while(amount < 0 || amount > acctBal);
		
		//make the transfer
		theUser.addAcctTransaction(fromAcct, -1*amount, String.format("Transfer to account %s",
				theUser.getAcctUuid(toAcct)));
		theUser.addAcctTransaction(toAcct, amount, String.format("Transfer to account %s",
				theUser.getAcctUuid(fromAcct)));
	}
	
	public static void withdrawlFunds(User theUser, Scanner sc ) {
		
		int fromAcct;
		double amount;
		double acctBal;
		String memo;
		
		do {
			System.out.printf("Enter number (1-%d) of the account"+
					"to withdraw from: ",theUser.numAccounts());
			fromAcct = sc.nextInt()-1;
			if(fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again");
			}
		}while(fromAcct < 0 || fromAcct >= theUser.numAccounts());
		
		acctBal = theUser.getAcctBalance(fromAcct);
		
		//get amount transfer
		do {
			System.out.printf("Enter the amount to transfer (max $%.02f): $",acctBal);
			amount = sc.nextDouble();
			
			if(amount < 0) {
				System.out.println("Amount must be greater than 0");
			}else if(amount > acctBal){
				System.out.printf("Amount must not be greater than\n"+
						"balance of $.02f.\n",acctBal);
			}
		}while(amount < 0 || amount > acctBal);
		
		sc.nextLine();
		
		System.out.println("Enter a memo: ");
		memo = sc.nextLine();
		
		// do withdrawl
		theUser.addAcctTransaction(fromAcct, -1*amount, memo);
	}
	
	public static void depositFunds(User theUser, Scanner sc) {
		int toAcct;
		double amount;
		double acctBal;
		String memo;
		
		do {
			System.out.printf("Enter number (1-%d) of the account"+
					"to deposit in: ",theUser.numAccounts());
			toAcct = sc.nextInt()-1;
			if(toAcct < 0 || toAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again");
			}
		}while(toAcct < 0 || toAcct >= theUser.numAccounts());
		
		acctBal = theUser.getAcctBalance(toAcct);
		
		//get amount transfer
		do {
			System.out.printf("Enter the amount to deposit: $",acctBal);
			amount = sc.nextDouble();
			
			if(amount < 0) {
				System.out.println("Amount must be greater than 0");
			}
		}while(amount < 0 );
		
		sc.nextLine();
		
		System.out.println("Enter a memo: ");
		memo = sc.nextLine();
		
		// do deposit
		theUser.addAcctTransaction(toAcct, amount, memo);
	}
}
