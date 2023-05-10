import java.util.ArrayList;
import java.util.Scanner;

//create a class call client to record the details of the client
class Client{
    private int client_id;
    private String name_Of_the_customer;
    private String nationality_of_the_customer;
    private String occupation;
    private String address;
    private int client_age;
    private String gender_of_the_customer;
    private int pin;
    private ArrayList<Account> accounts_list;
    private String currency;
    static ArrayList<Client> clientList = new ArrayList<>();

    //constructor of Client class
    public Client(int client_id,String name_Of_the_customer,String nationality_of_the_customer,String occupation,String address,int client_age,String gender_of_the_customer,int pin,String currency){
        this.client_id = client_id;
        this.name_Of_the_customer = name_Of_the_customer;
        this.nationality_of_the_customer = nationality_of_the_customer ;
        this.occupation = occupation;
        this.address = address;
        this.client_age = client_age;
        this.gender_of_the_customer = gender_of_the_customer;
        this.pin = pin;
        this.accounts_list = new ArrayList<>();
        this.currency = currency;
        clientList.add(this);
    }

    public int get_The_ClientId() {
        return client_id;
    }

    public int get_the_correct_Pin() {
        return pin;
    }

    public ArrayList<Account> getAccounts() {
        return accounts_list;
    }

    public void addAnAccount(Account account){
        this.accounts_list.add(account);
    }
    public String get_currency(){
        return currency;
    }
    public ArrayList<Client> getClientList(){
        return clientList;
    }
}
//create a class called Account to save the details of an account
class Account{
    private int accountNumber;
    private String branch_of_the_bank;
    private String currency;
    private double balance;
    private Client client;

    //create a constructor for class called Account
    public Account(Client client,int accountNumber, String branch_of_the_bank, double balance) {
        this.accountNumber = accountNumber;
        this.currency = client.get_currency();
        this.branch_of_the_bank = branch_of_the_bank;
        this.balance = balance;
        this.client =client;
        client.addAnAccount(this);

    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
//create a class called SavingAccount to save the details of an account
//this class extends by Account class
class SavingAccount extends Account{
    private double interestRate;
    private double accountBalance;
    //create constructor for Saving Account class
    public SavingAccount(Client client,int accountNumber, String branch, double balance) {
        super(client,accountNumber,branch, balance);
    }
    //create a method called payInterest to calculate and return the interest in given rate
    public double payInterest(double interestRate,double accountBalance){
        return accountBalance*interestRate/100;
    }
}
//create a class called CurrentAccount
//extends by Account class
class CurrentAccount extends Account{
    public CurrentAccount(Client client,int accountNumber, String branch, double balance) {
        super(client,accountNumber, branch, balance);
    }
}
//create a class called Request_loan
class Request_Loan{
    private double loan_Amount;
    private double interest;
    private int duration;
    private String paymentMethod;
    private Account account;

    //create a constructor for Request_loan class
    public Request_Loan(double loan_Amount, double interest, int duration, String paymentMethod, Account account) {
        this.loan_Amount = loan_Amount;
        this.interest = interest;
        this.duration = duration;
        this.paymentMethod = paymentMethod;
        this.account = account;
    }
}
//create a class called Transactions to do a transaction
class Transactions{
    private int transaction_Id;
    private int bankAccount_Id;
    private String date;
    private String status;

    //create a constructor for Request_loan class
    public Transactions(int transaction_Id, int bankAccount_Id, String date,String status) {
        this.transaction_Id = transaction_Id;
        this.bankAccount_Id = bankAccount_Id;
        this.date = date;
        this.status =status;
    }
}
public class Main {
    public static void main(String[] args) {
        Client client1 = new Client(1,"Kamal","Sinhalese","Business","Matara",28,"male",123,"lkr");
        Account account1 = new Account(client1,123,"Matara",12344);
        Account account2 = new Account(client1,243,"Colombo",6572);
        Account account3 = new Account(client1,549,"Galle",1243);

        Scanner obj = new Scanner(System.in);
        int transactionCount = 100;
        String date = "24-04-2023"; //initialize the date
        String statusList[] = {"Success", "Failure", "Cancellation"}; //create the list of states

        //display welcome massage
        System.out.println("Welcome!");
        System.out.println("Please Enter Your Client Id: ");
        int client_id = obj.nextInt();
        System.out.println("Please enter your PIN: ");
        int pin = obj.nextInt();
        //validate the user's pin
        Client client = null;
        for (Client person : Client.clientList) {
            if (person.get_The_ClientId() == client_id) {
                if (person.get_the_correct_Pin() == pin) {
                    client = person;

                    //Display accounts of user
                    ArrayList<Account> accountList = client.getAccounts();
                    int i = 0;
                    System.out.println("Select a index of an account: ");
                    for (Account account : accountList) {
                        System.out.println(i + ") " + account.getAccountNumber());
                        i++;

                    }

                    //
                    int index = obj.nextInt();
                    //print options that user can choose
                    System.out.println("1. View Balance");
                    System.out.println("2. Withdraw Money");
                    System.out.println("3. Deposit Money");
                    System.out.println("4. Exit");
                    //get the choice from user
                    System.out.println("Select the option you want: ");
                    int choice = obj.nextInt();

                    if (choice == 1) {
                        //show account balance
                        System.out.println("Account Balance: " + accountList.get(index).getBalance());

                    }
                    if (choice == 2) {
                        System.out.println("Enter amount to withdraw: ");

                        double amount = obj.nextDouble();

                        //get money from account
                        if (amount < accountList.get(index).getBalance()) {
                            System.out.println("Withdrow Sucessful");
                            accountList.get(index).setBalance(accountList.get(index).getBalance() - amount);
                            System.out.println("Remaining Balance: " + accountList.get(index).getBalance());
                            //save transaction details
                            Transactions transaction = new Transactions(++transactionCount, accountList.get(index).getAccountNumber(), date, statusList[0]);
                        } else {
                            Transactions transaction = new Transactions(++transactionCount, accountList.get(index).getAccountNumber(), date, statusList[1]);
                        }
                    }
                    //deposit money to the account
                    if (choice == 3) {
                        System.out.println("Enter amount to deposit: ");

                        double amount = obj.nextDouble();
                        accountList.get(index).setBalance(accountList.get(index).getBalance() + amount);
                        System.out.println("Remaining Balance: " + accountList.get(index).getBalance());
                        Transactions transaction = new Transactions(++transactionCount, accountList.get(index).getAccountNumber(), date, statusList[0]);
                    }
                    if (choice == 4) {
                        System.out.println("Exit");
                    }
                }
                else
                    System.out.println("Your entered pin is wrong");
                break;
            }
        }
    }
}

