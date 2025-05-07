package gym.customers;
import java.time.format.DateTimeFormatter;
import java.util.*;
import gym.management.Observer;

public class Client extends Person implements Observer {
    private List<String> notifications;

    public Client(String firstName, Integer payment, Gender gender, String birthDate) {
        super(firstName, payment, gender, birthDate);
        this.notifications = new ArrayList<>();  // אתחול רשימה חדשה
    }
    public Client(Person person) {
        super(person);
        this.notifications = new ArrayList<>();  // אתחול רשימה חדשה
    }

    public List<String> getNotifications() {
        return notifications;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Client client = (Client) obj;
        return this.getId()==client.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }


    @Override
    public String toString() {
        // יצירת פורמט מותאם אישית לתאריך
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedBirthday = getBirthDate().format(formatter);

        // החזרת המחרוזת עם התאריך המעוצב
        return "ID: " + getId() + " | Name: " + getName() + " | Gender: " + getGender() +
                " | Birthday: " + formattedBirthday + " | Age: " + getAge() + " | Balance: " + getPayment();
    }

    @Override
    public void update(String message) {
            getNotifications().add(message);
    }
}
