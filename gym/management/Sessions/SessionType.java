package gym.management.Sessions;

public enum SessionType {
    Pilates(60, 30),
    MachinePilates(80, 10),
    ThaiBoxing(100, 20),
    Ninja(150, 5);
    private final int PRICE;
    private int MAX_PARTICIPANTS;

    SessionType(int PRICE, int MAX_PARTICIPANTS){
        this.PRICE = PRICE;
        this.MAX_PARTICIPANTS = MAX_PARTICIPANTS;
    }
    public int getPRICE(){
        return PRICE;
    }

    public int getMAX_PARTICIPANTS() {
        return MAX_PARTICIPANTS;
    }
}
