package atm.bank;

import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Bank theBank = new Bank("Bank Of WAVE");

        User aUser = theBank.addUser("Xhuljo", "Dede", "1234");

        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUser;
        while (true) {
            //until we successfull Log In
            curUser = ATM.mainMenuPrompt(theBank, input);

            //Main menu until user quit
            ATM.printUserMenu(curUser, input);
        }


    }


    private static User mainMenuPrompt(Bank theBank, Scanner input) {
        String userId;
        String pin;
        User authUser;

        //prompt the user for user ID/PIN combo until a correct one is reached
        do {
            System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
            System.out.print("Enter user ID: ");
            userId = input.nextLine();
            System.out.print("Enter pin: ");
            pin = input.nextLine();

            //try to get the user object corresponding to the ID and Pin combo
            authUser = theBank.userLogIn(userId, pin);
            if (authUser == null) {
                System.out.println("Incorrect user ID/PIN combination. " +
                        "Please try again!");
            }

        } while (authUser == null);
        return authUser;
    }


    private static void printUserMenu(User theUser, Scanner input) {
        //print a summary of the user's accounts
        theUser.printAccountsSummary();

        int choice;

        do {
            System.out.printf("Welcome %s, what would you like to do?\n",
                    theUser.getFirstName());
            System.out.println(" 1) Show account transaction history!!");
            System.out.println(" 2) Withdrawl");
            System.out.println(" 3) Deposit");
            System.out.println(" 4) Transfer");
            System.out.println(" 5) Quit");
            System.out.println();
            System.out.print("Enter a choice: ");
            choice = input.nextInt();
            if (choice < 1 || choice > 5) {
                System.out.println("Invalid choice. Please choose 1-5");
            }
        } while (choice < 1 || choice > 5);

        switch (choice) {
            case 1:
                ATM.showTransactionHistory(theUser, input);
                break;
            case 2:
                ATM.withdrawlFunds(theUser, input);
                break;
            case 3:
                ATM.depositFunds(theUser, input);
                break;
            case 4:
                ATM.transferFunds(theUser, input);
                break;
        }

        //redisaplay this menu unless the user wants to quits
        if (choice != 5) {
            ATM.printUserMenu(theUser, input);
        }
    }




    private static void showTransactionHistory(User theUser, Scanner input) {
        int theAcct;

        //get account whose transaction history to look at
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "whose transations you want to see: ", theUser.numAccounts());
            theAcct = input.nextInt() - 1;
            if (theAcct < 0 || theAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again. ");
            }
        } while (theAcct < 0 || theAcct >= theUser.numAccounts());

        theUser.printAccountTransactionHistory(theAcct);
    }

    private static void transferFunds(User theUser, Scanner input) {

        int fromAcct;
        int toAcct;
        double amount;
        double acctBalance;
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to transer from: ", theUser.numAccounts());
            fromAcct = input.nextInt() - 1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again. ");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBalance = theUser.getAccountBalance(fromAcct);

        //get the account to transfer to
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to transer to: ", theUser.numAccounts());
            toAcct = input.nextInt() - 1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again. ");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccounts());

        do {
            System.out.printf("Enter the amount to trnasfer( max $%f): $",
                    acctBalance);
            amount = input.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero.");
            } else if (amount > acctBalance) {
                System.out.printf("Amount must not be greater than\n" +
                        "balance of $%.02f.\n", acctBalance);
            }
        } while (amount < 0 || amount > acctBalance);

        //do the trnasfer
        theUser.addAccountTrasaction(fromAcct, -1 * amount, String.format(
                "Trnasfer to account %s", theUser.getAccountUUID(toAcct)));
        theUser.addAccountTrasaction(toAcct, amount, String.format(
                "Trnasfer to account %s", theUser.getAccountUUID(fromAcct)));
    }

    private static void withdrawlFunds(User theUser, Scanner input) {
        int fromAcct;
        double amount;
        double acctBalance;
        String memo;
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to withdraw from: ", theUser.numAccounts());
            fromAcct = input.nextInt() - 1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again. ");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBalance = theUser.getAccountBalance(fromAcct);

        do {
            System.out.printf("Enter the amount to trnasfer( max $%f): $",
                    acctBalance);
            amount = input.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero.");
            } else if (amount > acctBalance) {
                System.out.printf("Amount must not be greater than\n" +
                        "balance of $%.02f.\n", acctBalance);
            }
        } while (amount < 0 || amount > acctBalance);

        input.nextLine();

        System.out.print("Enter a memo: ");
        memo = input.nextLine();

        theUser.addAccountTrasaction(fromAcct, -1*amount, memo);

    }
    private static void depositFunds(User theUser, Scanner input) {
        int toAcct;
        double amount;
        double acctBalance;
        String memo;
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to deposit in: ", theUser.numAccounts());
            toAcct = input.nextInt() - 1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again. ");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccounts());
        acctBalance = theUser.getAccountBalance(toAcct);

        do {
            System.out.printf("Enter the amount to trnasfer: $",
                    acctBalance);
            amount = input.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero.");
            }
        } while (amount < 0);

        input.nextLine();

        System.out.print("Enter a memo: ");
        memo = input.nextLine();

        theUser.addAccountTrasaction(toAcct, amount, memo);
    }
}
