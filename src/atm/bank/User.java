package atm.bank;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
    private String firstName;
    private String lastName;
    //Universal Unique ID
    private String uuId;
    private byte pinHash[];
    private ArrayList<Account> accounts;

    public User(String firstName, String lastName, String pin , Bank theBank) {
        this.firstName = firstName;
        this.lastName = lastName;

        //store the pin's MDS pin
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("error !!");
            e.printStackTrace();
            System.exit(1);
        }

        //get a new uu id for the user
        this.uuId = theBank.getNewUserUUID();

        //create empty list of Accounts
        this.accounts = new ArrayList<Account>();

        //Print Log In Message
        System.out.printf("New user %s, %s whth ID %s created.\n"
        ,lastName , firstName , this.uuId);
    }

    public void addAccount(Account anAcc) {
        this.accounts.add(anAcc);
    }

    public String getUUID() {
        return this.uuId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public boolean validatePin(String aPin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()),this.pinHash);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("error !!");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    public void printAccountsSummary() {
        System.out.printf("\n\n%s's account summary\n", this.firstName);
        for (int a = 0 ; a < this.accounts.size(); a++){
            System.out.printf("  %d) %s\n", a+1, this.accounts.get(a).getSummaryLine());
        }
        System.out.println();
    }
    public int numAccounts(){
        return this.accounts.size();
    }

    public void printAccountTransactionHistory(int accIndex) {
        this.accounts.get(accIndex).printTransactionHistory();
    }
    public double getAccountBalance(int accIndex){
        return this.accounts.get(accIndex).getBalance();
    }
    public String getAccountUUID(int accIndex){
        return this.accounts.get(accIndex).getUUID();
    }

    public void addAccountTrasaction(int accIndex, double amount, String memo) {
        this.accounts.get(accIndex).addTransation(amount, memo);
    }
}
