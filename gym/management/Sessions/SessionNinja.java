package gym.management.Sessions;

import gym.customers.ForumType;
import gym.management.Instructor;

import java.time.LocalDateTime;

public class SessionNinja extends Session {
    private static final int MAX_PARTICIPANTS = 5;
    private static final int PRICE = 150;
    public SessionNinja(Instructor instructor, LocalDateTime dateTime, ForumType forum) {
        super(SessionType.Ninja, dateTime, forum ,instructor);
    }
    public int getPrice(){
        return SessionType.Ninja.getPRICE();
    }
    public int getMAX_PARTICIPANTS() {
        return SessionType.Ninja.getMAX_PARTICIPANTS();
    }
    }

