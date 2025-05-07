package gym.management;

import gym.customers.Client;
import gym.customers.Gender;
import gym.customers.Person;
import gym.management.Sessions.Session;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static gym.management.Sessions.SessionType.Pilates;

public class Gym {
    protected static int gymBalance = 0;
    private static Gym instance;
    protected String name;
    protected Secretary secretary; // המזכירה הנוכחית
    protected List<Client> clients;  // רשימה לשמירת השמות של הלקוחות
    protected List<Instructor> instructors;

    protected List<Session> sessions; // רשימה לשמירת השיעורים
    protected List<String> gymInformation;
    protected List<String> actionsHistory;




    //private List<customers> cus;
    private Gym(){
        clients = new ArrayList<>();
        sessions = new ArrayList<>();
        name = "Default Gym Name";
        this.secretary = null;
        this.gymInformation = new ArrayList<>();
        actionsHistory = new ArrayList<>();
        instructors = new ArrayList<>();
    }
    public static Gym getInstance() {
        if (instance == null) {
            instance = new Gym();
        }
        return instance;
    }
    public List<String> getActionsHistory() {
        return actionsHistory;
    }
    public  String getName(){
        return name;
    }
    public void setName(String newName){
        this.name = newName;
        gymInformation.add("Gym Name: " + newName);
    }
    public Secretary getSecretary(){
        if (secretary != null) {
            gymInformation.add("Gym Secretary: ID: " + secretary.getId() +
                    " | Name: " + secretary.getName() +
                    " | Gender: " + secretary.getGender() +
                    " | Birthday: " + secretary.getBirthDate() +
                    " | Age: " + secretary.getAge() +
                    " | Balance: " + secretary.getPayment() +
                    " | Role: Secretary | Salary per Month: " + secretary.getSalary());
        }
        return secretary;
    }
    public  List<Session> getSession(){
        if (sessions != null) {
            for (Session s : sessions) {
                gymInformation.add("Session Type: " + s.getSessionType() +
                        " | Date: " + s.getDateTime() +
                        " | Forum: " + s.getForumType() +
                        " | Insructor: " + s.getInstructor() +
                        " | Participants: " + s.getRegisteredClients().size() +
                        "/" + s.getSessionType().getMAX_PARTICIPANTS());
            }
        }
        return sessions;

    }
    public void setSecretary(Person person, int salary ) {
        if (secretary == null) {
            this.secretary = new Secretary(person, salary, this);

        }
        else{
            // כאשר המזכיר עוזב את העבודה
            getSecretary().deactivateSecretary();
            this.secretary = new Secretary(person, salary, this);
    }
        if(secretary != null) {
            this.actionsHistory.add("A new secretary has started working at the gym: " + this.secretary.getName());
        }
        }
    // מתודה להוספת כסף ליתרה
    public static void addToBalance(int amount) {
        gymBalance += amount;
    }
    // מתודה להוספת כסף ליתרה
    public static void declienBalance(int amount) {
        gymBalance -= amount;
    }
    // מתודה להחזרת היתרה הנוכחית
    public static int getGymBalance() {
        return gymBalance;
    }
    public List<Client> getClients() {
        return clients;
    }

    public List<Session> getSessions(){return sessions;}
    public List<Instructor> getInstructors(){return instructors;}
    public String toString() {
        StringBuilder sb = new StringBuilder();
// יצירת פורמט מותאם אישית לתאריך
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedBirthday = secretary.getBirthDate().format(formatter);

        sb.append("Gym Name: ").append(name).append("\n");

        if (secretary != null) {
            sb.append("Gym Secretary: ").append("ID: " + secretary.getId() +
                    " | Name: " + secretary.getName() +
                    " | Gender: " + secretary.getGender() +
                    " | Birthday: " + formattedBirthday +
                    " | Age: " + secretary.getAge() +
                    " | Balance: " + secretary.getPayment() +
                    " | Role: Secretary | Salary per Month: " + secretary.getSalary()).append("\n");
        }

        sb.append("Gym Balance: ").append(gymBalance).append("\n\n");

        // Clients Data
        sb.append("Clients Data:\n");
        for (Client client : secretary.getClients()) {
            sb.append(client).append("\n");
        }

        sb.append("\n");

        // Employees Data
        sb.append("Employees Data:\n");
        for (Instructor instructor : secretary.getInstructors()) {
            sb.append(instructor).append("\n");
        }

        if (secretary != null) {
            sb.append("ID: " + secretary.getId() +
                    " | Name: " + secretary.getName() +
                    " | Gender: " + secretary.getGender() +
                    " | Birthday: " + formattedBirthday +
                    " | Age: " + secretary.getAge() +
                    " | Balance: " + secretary.getPayment() +
                    " | Role: Secretary | Salary per Month: " + secretary.getSalary()).append("\n");
        }


        sb.append("\n");

        // Sessions Data
        sb.append("Sessions Data:\n");
        for (Session session : secretary.getSessions()) {
            sb.append(session).append("\n");
        }

        return sb.toString();
    }





    public void printGymInformation() {
        System.out.println(this.toString());
    }
}

