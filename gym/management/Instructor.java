package gym.management;
import gym.Exception.InstructorNotQualifiedException;
import gym.customers.Gender;
import gym.customers.Person;
import gym.management.Sessions.Session;
import gym.management.Sessions.SessionType;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Instructor extends Person {

    private int hourlyRate;
    private List<SessionType> specialties;
    private List<Session> sessionsI;

    public Instructor(Person person, int hourlyRate, List<SessionType>specialties) {
        super(person);
        this.hourlyRate = hourlyRate;
        this.specialties = specialties;
        sessionsI = new ArrayList<>();
    }
    public int getHourlyRate(){
        return hourlyRate;
    }
    public List<SessionType> getSpecialties(){
        return  specialties;
    }
    public List<Session> getSessionsI(){
        return sessionsI;
    }
    public void setHourlyRate(int hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public void validateQualification(SessionType sessionType) throws InstructorNotQualifiedException {
        if (!sessionsI.contains(sessionType)) {
            throw new InstructorNotQualifiedException(
                    "Instructor " + getName() + " is not qualified to teach " + sessionType
            );
        }
    }
    public void addSession(Session session){
        sessionsI.add(session);
    }
    @Override
    public String toString() {
        // יצירת פורמט מותאם אישית לתאריך
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedBirthday = getBirthDate().format(formatter);
        StringBuilder specialtiesStringBuilder = new StringBuilder();
        for (int i = 0; i < specialties.size(); i++) {
            specialtiesStringBuilder.append(specialties.get(i));
            if (i < specialties.size() - 1) {
                specialtiesStringBuilder.append(", "); // הוספת פסיק בין תחומי ההתמחות
            }
        }
        return "ID: " + getId() + " | Name: " + getName() + " | Gender: " + getGender() +
                " | Birthday: " + formattedBirthday + " | Age: " + getAge() + " | Balance: " + getPayment() +
                " | Role: Instructor | Salary per Hour: " + hourlyRate + " | Certified Classes: " + specialtiesStringBuilder.toString();
    }
}


