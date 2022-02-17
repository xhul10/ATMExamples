package atm.bank;

import java.util.ArrayList;

public class Account {
    private String name;
    //private double balance;
    private String uuId;
    private User holder;
    private ArrayList<Transaction> transactions;

    public Account(String name , User holder , Bank theBank){
        this.name = name;
        this.holder = holder;

        this.uuId = theBank.getNewAccountUUID();

        //init transation
        this.transactions = new ArrayList<Transaction>();

    }

    public String getUUID() {
        return this.uuId;
    }

    public String getSummaryLine() {
        //get the balance
        double balance = this.getBalance();

        //format the summary line, depending on the wether the balance is negative
        if(balance >= 0){
            return String.format("%s : $%.02f : %s", this.uuId, balance, this.name);
        }else {
            return String.format("%s : $(%.02f) : %s",this.uuId, balance, this.name);
        }
    }
    public double getBalance(){
        double balance = 0;
        for (Transaction t:this.transactions) {
            balance += t.getAmount();
        }
        return balance;
    }

    public void printTransactionHistory() {
        System.out.printf("\nTransaction history for account %s\n", this.uuId);
        for (int t = this.transactions.size() - 1 ; t >= 0; t--){
            System.out.println(this.transactions.get(t).getSummaryLine());
        }
        System.out.println();
    }

    public void addTransation(double amount, String memo) {

        //create new transation object and add it to our list
        Transaction newTrans = new Transaction(amount, memo ,this);
        this.transactions.add(newTrans);
    }
}
