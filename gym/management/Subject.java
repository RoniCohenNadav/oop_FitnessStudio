package gym.management;

import gym.management.Sessions.Session;

import java.util.Observer;

public interface Subject {
    void notify(String str); // הוספת צופה
    void notify(String date, String mess); // הסרת צופה
    void notify(Session session, String str); // הודעה לצופים
}

