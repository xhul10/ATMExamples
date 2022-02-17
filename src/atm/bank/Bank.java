package atm.bank;

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
        Random randomNumberGenerator = new Random();
        int lengh = 6;
        boolean nonUnique ;
        do {
            uuid = "";
            for (int c = 0 ; c < lengh ; c++) {
                uuid += ((Integer) randomNumberGenerator.nextInt(10)).toString();
            }
            nonUnique= false;
                for (User u :this.users) {
                    if (uuid.compareTo(u.getUUID())==0){
                        nonUnique = true;
                        break;
                    }
                }

        }while (nonUnique);

        return uuid;
    }

    public String getNewAccountUUID() {
        String uuid;
        Random randomNumberGenerator = new Random();
        int lengh = 10;
        boolean nonUnique ;
        do {
            uuid = "";
            for (int c = 0 ; c < lengh ; c++) {
                uuid += ((Integer) randomNumberGenerator.nextInt(10)).toString();
            }
            nonUnique= false;
            for (Account a :this.accounts) {
                if (uuid.compareTo(a.getUUID())==0){
                    nonUnique = true;
                    break;
                }
            }

        }while (nonUnique);

        return uuid;
    }

    public void addAccount(Account anAcc) {
        this.accounts.add(anAcc);
    }

    public User addUser(String firstName , String lastName, String pin){
        User newUser = new User(firstName, lastName, pin,this);
        this.users.add(newUser);

        //Savings Acccount
        Account newAccount = new Account("Savings", newUser,this);
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);
        return newUser;
    }

    public User userLogIn(String userId, String pin){
        for (User u :this.users) {
            if (u.getUUID().compareTo(userId) == 0 && u.validatePin(pin)){
                return u;
            }
        }
            return null;
    }

    public String getName() {
        return this.name;
    }
}
