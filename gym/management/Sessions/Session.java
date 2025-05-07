package gym.management.Sessions;

import gym.customers.Client;
import gym.customers.ForumType;
import gym.management.Instructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Session {
    protected SessionType sessionType;
    protected LocalDateTime dateTime;
    protected ForumType forumType;
    protected Instructor instructor;
    protected List<Client> clients;
    protected int price;
    protected int maxParticipants;

    public Session(SessionType sessionType, LocalDateTime dateTime,ForumType forumType, Instructor instructor) {
        this.sessionType = sessionType;
        this.dateTime = dateTime;
        this.forumType = forumType;
        this.instructor =instructor;
        this.clients = new ArrayList<>();
    }
    public SessionType getSessionType() {
        return sessionType;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public ForumType getForumType() {
        return forumType;
    }

    public Instructor getInstructor() {
        return instructor;
    }
    public boolean isClientRegistered(Client client) {
        return con(client,clients);//lients.contains(client);
    }
    private boolean con(Client c, List<Client> l){
        for (Client cl: l){
            if(cl.equals(c))return true;
        }
        return false;
    }
    public List<Client> getRegisteredClients() {
        return clients; // מחזיר את הרשימה המקורית ישירות
    }


    public void addClient(Client client) {
        clients.add(client);
    }


    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"); // פורמט הכולל גם תאריך וגם שעה
        String formattedDate = dateTime.format(formatter); // המרת התאריך ושעה לפורמט הרצוי

        return "Session Type: " + sessionType + " | Date: " + formattedDate + " | Forum: " + forumType + " | Instructor: " + instructor.getName() +
                " | Participants: " + clients.size() + "/" + sessionType.getMAX_PARTICIPANTS();
    }

}

