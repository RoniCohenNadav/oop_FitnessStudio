package gym.management;

import gym.Exception.ClientNotRegisteredException;
import gym.Exception.DuplicateClientException;
import gym.Exception.InstructorNotQualifiedException;
import gym.Exception.InvalidAgeException;
import gym.customers.*;
import gym.management.Sessions.Session;
import gym.management.Sessions.SessionFactory;
import gym.management.Sessions.SessionType;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static gym.management.Gym.declienBalance;


public class Secretary extends Person implements Subject {
    private int salary;

    protected Gym gym;
    private boolean isActive;



    public Secretary(Person person, int salary, Gym gym ) {
        super(person);
        this.salary = salary;
        this.isActive = true;
        this.gym = gym;
    }

    public int getSalary() {
        return salary;
    }
    public void setSalary(int salary){
        this.salary = salary;
    }

    public List<Client> getClients() {
        return gym.getClients();
    }

    public List<String> getActionsHistory() {
        return gym.getActionsHistory();
    }
    public List<Session> getSessions(){
        return gym.getSessions();
    }
    public List<Instructor> getInstructors(){
        return gym.getInstructors();
    }

    public void deactivateSecretary() {
        this.isActive = false;
    }

    public Client registerClient(Person person) throws InvalidAgeException, DuplicateClientException {
        if (!isActive) {
            throw new NullPointerException("Error: Former secretaries are not permitted to perform actions.");
        }
        try {
            // בדיקה האם ה-birthDate חוקי
            if (person.getBirthDate() == null) {
                throw new InvalidAgeException("Birth date cannot be null.");
            }
            LocalDate birthDate;
            try {
                birthDate = person.getBirthDate();
            } catch (DateTimeParseException e) {
                throw new InvalidAgeException("Invalid birth date format. Expected format: dd-MM-yyyy.");
            }

            // חישוב הגיל
            LocalDate currentDate = LocalDate.now();
            int age = AgeCalculator.calculateAge(birthDate, currentDate);

            // בדיקה האם הגיל קטן מ-18
            if (age < 18) {
                throw new InvalidAgeException("Error: Client must be at least 18 years old to register");
            }

            // יצירת הלקוח והוספתו לרשימת הלקוחות
            Client client= new Client(person);
            // בדיקת כפילות לפני הוספה
            for (Client c : getClients()) {
                if (c.equals(client)) {
                    throw new DuplicateClientException("Error: The client is already registered");
                }
            }

            // הוספת הלקוח לרשימה
            gym.getClients().add(client);
            getActionsHistory().add("Registered new client: " + client.getName());
            return client;
        } catch (InvalidAgeException | DuplicateClientException e) {
            throw e; // החריגה מטופלת ברמת הקורא
        }
    }


    public Instructor hireInstructor(Person person, double hourlyRate, List<SessionType> specialties) {
        if (!isActive) {
            throw new NullPointerException("Error: Former secretaries are not permitted to perform actions.");
        }
        // בדיקות תקינות לפרמטרים
        if (person == null) {
            throw new IllegalArgumentException("Person cannot be null.");
        }
        if (hourlyRate <= 0) {
            throw new IllegalArgumentException("Hourly rate must be greater than 0.");
        }
        if (specialties == null || specialties.isEmpty()) {
            throw new IllegalArgumentException("Specialties cannot be null or empty.");
        }


        // יצירת אובייקט המדריך
        Instructor instructor = new Instructor(person, (int) hourlyRate, specialties);
        // הוספת המדריך לרשימת המדריכים
        getInstructors().add(instructor);
        getActionsHistory().add("Hired new instructor: " + instructor.getName() + " with salary per hour: " + instructor.getHourlyRate());
        // החזרת אובייקט המדריך
        return instructor;
    }


    public Session addSession(SessionType sessionType, String dateTime, ForumType forumType, Instructor instructor) throws InstructorNotQualifiedException {
        // בדיקה אם המזכירה פעילה
        if (!isActive) {
            throw new NullPointerException("Error: Former secretaries are not permitted to perform actions.");
        }

        // בדיקה האם המדריך מוסמך ללמד את השיעור
        if (!instructor.getSpecialties().contains(sessionType)) {
            throw new InstructorNotQualifiedException("Error: Instructor is not qualified to conduct this session type.");
        }

        // בדיקה האם השיעור כבר נמצא ברשימת השיעורים
        for (Session existingSession : getSessions()) {
            if (existingSession.getSessionType().equals(sessionType) &&
                    existingSession.getDateTime().equals(dateTime) &&
                    existingSession.getForumType().equals(forumType) &&
                    existingSession.getInstructor().equals(instructor)) {
                return existingSession; // אם השיעור כבר קיים, מחזירים אותו ולא מוסיפים חדש
            }
        }

        // ניסיון להמיר את dateTime ל-LocalDateTime
        LocalDateTime sessionDateTime;
        try {
            // שימוש בפורמט מותאם אישית
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            sessionDateTime = LocalDateTime.parse(dateTime, formatter);
        } catch (Exception e) {
            try {
                // שימוש בפורמט ברירת מחדל של LocalDateTime
                sessionDateTime = LocalDateTime.parse(dateTime);
            } catch (Exception ex) {
                throw new IllegalArgumentException("Error: Invalid dateTime format. Expected 'dd-MM-yyyy HH:mm' or ISO format.", ex);
            }
        }

        // יצירת שיעור חדש
        Session session = SessionFactory.createSession(sessionType, instructor, sessionDateTime, forumType);

        // הוספת השיעור לרשימה
        getSessions().add(session);
        instructor.addSession(session);

        // הוספה להיסטוריית הפעולות
        getActionsHistory().add(String.format("Created new session: %s on %s with instructor: %s",
                sessionType, sessionDateTime, instructor.getName()));

        return session;
    }


    public void unregisterClient(Client c2) throws ClientNotRegisteredException {
        if (!isActive) {
            throw new NullPointerException("Error: Former secretaries are not permitted to perform actions.");
        }
        if (c2 == null) {
            throw new IllegalArgumentException("Client cannot be null.");
        }

        if (!getClients().contains(c2)) {
            throw new ClientNotRegisteredException(
                    "Error: Registration is required before attempting to unregister"
            );
        }

        // הסרה של הלקוח מהרשימה
        getClients().remove(c2);
        removeClientFromAllSessions(c2);
        getActionsHistory().add("Unregistered client: "+ c2.getName());
    }
    public void removeClientFromAllSessions(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null.");
        }

        // לולאה על כל השיעורים
        for (Session session : getSessions()) {
            if (session.getRegisteredClients().contains(client)) {
                getSessions().remove(client); // הסרת הלקוח מהשיעור
            }
        }

    }



    public void registerClientToLesson(Client c1, Session s2) throws ClientNotRegisteredException, IllegalArgumentException, DuplicateClientException {

        if (!isActive) {
            throw new NullPointerException("Error: Former secretaries are not permitted to perform actions.");
        }

        // בדיקה אם הלקוח הוא null
        if (c1 == null) {
            throw new IllegalArgumentException("Client cannot be null.");
        }
        // בדיקה אם הלקוח כבר רשום לשיעור
        if (s2.isClientRegistered(c1)) {
            throw new DuplicateClientException("Error: The client is already registered for this lesson");
        }


        // בדיקה אם הלקוח לא רשום במערכת
        if (!getClients().contains(c1)) {
            throw new ClientNotRegisteredException(
                    "Error: The client is not registered with the gym and cannot enroll in lessons"
            );
        }

        boolean hasIssues = false;

        // המרת תאריך לפורמט LocalDateTime
        try {
            // פורמט תאריך מותאם אישית כמו 23-12-2024 10:00
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        } catch (Exception e) {
            throw new IllegalArgumentException("Session date string is invalid: " + s2.getDateTime());
        }

        // בדיקת האם התאריך חלף
        if (!s2.getDateTime().isAfter(LocalDateTime.now())) {
            getActionsHistory().add("Failed registration: Session is not in the future");
            hasIssues = true;
        }

        // בדיקת פורום השיעור
        ForumType sessionForum = s2.getForumType();
        Person clientPerson = c1;
        int clientAge = clientPerson.getAge();
        Gender clientGender = clientPerson.getGender();

        if (sessionForum == ForumType.Male && clientGender != Gender.Male) {
            getActionsHistory().add("Failed registration: Client's gender doesn't match the session's gender requirements");
            hasIssues = true;
        }

        if (sessionForum == ForumType.Female && clientGender != Gender.Female) {
            getActionsHistory().add("Failed registration: Client's gender doesn't match the session's gender requirements");
            hasIssues = true;
        }

        if (sessionForum == ForumType.Seniors && clientAge < 65) {
            getActionsHistory().add("Failed registration: Client doesn't meet the age requirements for this session (Seniors)");
            hasIssues = true;
        }

        // בדיקת מקום בשיעור
        if (s2.getRegisteredClients().size() >= s2.getSessionType().getMAX_PARTICIPANTS()) {
            getActionsHistory().add("Failed registration: No available spots for session");
            hasIssues = true;
        }

        // בדיקת התשלום אם יש מספיק כסף לשלם על השיעור
        int sessionCost = s2.getSessionType().getPRICE();
        int clientBalance = c1.getPayment();
        if (clientBalance < sessionCost) {
            getActionsHistory().add("Failed registration: Client doesn't have enough balance");
            hasIssues = true;
        }

        // אם יש בעיות, עצור את התהליך
        if (hasIssues) {
            return;
        }

        // אם כל התנאים עברו, נבצע את ההרשמה והתשלום
        c1.setPayment(clientBalance - sessionCost);
        Gym.addToBalance(sessionCost);

        // הוספת הלקוח לשיעור
        s2.addClient(c1);

        // הוספת הפעולה להיסטוריית הפעולות
        getActionsHistory().add("Registered client: " + c1.getName() + " to session: " + s2.getSessionType() +
                " on " + s2.getDateTime() + " for price: " + sessionCost);

    }




    public void paySalaries() {
        if (!isActive) {
            throw new NullPointerException("Error: Former secretaries are not permitted to perform actions.");}
        for (Session s : getSessions()) {
           int pay =  s.getInstructor().getHourlyRate();
           s.getInstructor().setPayment(s.getInstructor().getPayment() + pay);
           declienBalance(pay);
        }
        setPayment(salary + getPayment());
        declienBalance(salary);
        getActionsHistory().add("Salaries have been paid to all employees");
    }


    public void printActions() {
            if (!isActive) {
                throw new NullPointerException("Error: Former secretaries are not permitted to perform actions.");
            }
        for (String his: getActionsHistory()){
            System.out.println(his);
        }
    }
@Override
    public void notify(Session session, String message) {
    if (!isActive) {
        throw new NullPointerException("Error: Former secretaries are not permitted to perform actions.");
    }
        for (Client client: session.getRegisteredClients()){
            client.update(message);
        }
    getActionsHistory().add("A message was sent to everyone registered for session " + session.getSessionType() + " on " +session.getDateTime()+ " : The instructor will be a few minutes late for the session");
}
    @Override
    public void notify(String date, String message) {
        if (!isActive) {
            throw new NullPointerException("Error: Former secretaries are not permitted to perform actions.");
        }
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate targetDate = LocalDate.parse(date, inputFormatter);
        for(Session session: getSessions()){
            LocalDate sessionDate = session.getDateTime().toLocalDate();
            if(sessionDate.equals(targetDate)){
                for(Client client: session.getRegisteredClients()){
                    if(!client.getNotifications().contains(message)){
                        client.update(message);
                    }
                }
            }
        }
        getActionsHistory().add("A message was sent to everyone registered for a session on "+ targetDate + " : Heavy traffic reported around the gym today. Plan ahead to avoid missing your session!");
    }

   @Override
    public void notify(String message) {
       if (!isActive) {
           throw new NullPointerException("Error: Former secretaries are not permitted to perform actions.");
       }
       for (Client client: getClients()){
           client.update(message);
       }
       getActionsHistory().add("A message was sent to all gym clients: Happy New Year to all our valued clients!");
   }
}











