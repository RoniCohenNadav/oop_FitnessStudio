package gym.management.Sessions;

import gym.customers.ForumType;
import gym.management.Instructor;

import java.time.LocalDateTime;

public class SessionPilates extends Session {
//    private static final int MAX_PARTICIPANTS = 30;
//    private static final int PRICE = 60;

    public SessionPilates(Instructor instructor, LocalDateTime dateTime, ForumType forum) {
        super(SessionType.Pilates, dateTime, forum ,instructor);
    }
    public int getPrice(){
        return SessionType.Pilates.getPRICE();
    }
    public int getMAX_PARTICIPANTS() {
        return SessionType.Pilates.getMAX_PARTICIPANTS();
    }
}
