package gym.management;

public class Balance {
    int balance = 0;
    public Balance (int balance){
        this.balance = balance;
    }
    public int getBalance(){
        return balance;
    }

    public void setBalance(int newBalance) {
        if (newBalance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative.");
        }
        this.balance = newBalance;
    }

}
