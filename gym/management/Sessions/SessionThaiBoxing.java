package gym.management.Sessions;

import gym.customers.ForumType;
import gym.management.Instructor;

import java.time.LocalDateTime;

public class SessionThaiBoxing extends Session {
    private static final int MAX_PARTICIPANTS = 20;
    private static final int PRICE = 100;
    public SessionThaiBoxing(Instructor instructor, LocalDateTime dateTime, ForumType forum) {
        super(SessionType.ThaiBoxing, dateTime, forum ,instructor);    }
    public int getPrice(){
        return SessionType.ThaiBoxing.getPRICE();
    }
    public int getMAX_PARTICIPANTS() {
        return SessionType.ThaiBoxing.getMAX_PARTICIPANTS();
    }
    }

