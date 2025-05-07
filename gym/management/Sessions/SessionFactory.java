package gym.management.Sessions;

import gym.customers.ForumType;
import gym.management.Instructor;

import java.time.LocalDateTime;

public class SessionFactory {
    public static Session createSession(SessionType sessionType, Instructor instructor, LocalDateTime dateTime, ForumType forum) {
        switch (sessionType) {
            case Pilates:
                return new SessionPilates(instructor, dateTime, forum);
            case MachinePilates:
                return new SessionMachinePilates(instructor, dateTime, forum);
            case ThaiBoxing:
                return new SessionThaiBoxing(instructor, dateTime, forum);
            case Ninja:
                return new SessionNinja(instructor, dateTime, forum);
            default:
                throw new IllegalArgumentException("Unknown session type: " + sessionType);
        }
    }
}
