package gym.management.Sessions;

import gym.customers.ForumType;
import gym.management.Instructor;

import java.time.LocalDateTime;

public class SessionMachinePilates extends Session {
    private static final int MAX_PARTICIPANTS = 10;
    private static final int PRICE = 80;
    public SessionMachinePilates(Instructor instructor, LocalDateTime dateTime, ForumType forum) {
        super(SessionType.MachinePilates, dateTime, forum ,instructor);    }
    public int getPrice(){
        return SessionType.MachinePilates.getPRICE();
    }
    public int getMAX_PARTICIPANTS() {
        return SessionType.MachinePilates.getMAX_PARTICIPANTS();
    }
}
