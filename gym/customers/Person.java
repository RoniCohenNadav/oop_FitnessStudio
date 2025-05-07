package gym.customers;

import gym.management.Balance;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class Person {
    private LocalDate birthDate;
    private String name;
    private Gender gender;
    private Balance payment; // שדה לתשלום
    private static int nextId = 1111;
    private int id;

    public Person(String firstName, int paymentAmount, Gender gender, String birthDate) {
        this.name = firstName;
        this.gender = gender;
        this.payment = new Balance(paymentAmount); // יצירת מופע Balance
        setid();
        try {
            // ניסיון לנתח לפי הפורמט yyyy-MM-dd
            this.birthDate = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e1) {
            try {
                // אם נכשל, ניסיון לנתח לפי הפורמט dd-MM-yyyy
                this.birthDate = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            } catch (DateTimeParseException e2) {
                // אם לא הצלחנו לנתח את התאריך בשום פורמט, זרוק שגיאה
                System.out.println("Error: Invalid date format. Please use yyyy-MM-dd or dd-MM-yyyy.");
                e2.printStackTrace();
            }
        }
    }
    public Person(Person person) {
        this.name = person.getName();
        this.payment = person.payment;
        this.gender = person.getGender();
        this.birthDate = person.birthDate;
        this.id = person.getId();
    }
    private void setid() {
        id = nextId++;
    }
    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public int getPayment() {
        return payment.getBalance();
    }

    public int getId() {
        return id;
    }

    public static int getNextId() {
        return nextId;
    }

    public int getAge() {
        return AgeCalculator.calculateAge(this.birthDate, LocalDate.now());
    }

    public void setPayment(int newPayment) {
        if (newPayment < 0) {
            throw new IllegalArgumentException("Payment cannot be null or negative.");
        }
        this.payment.setBalance(newPayment); // מניחים שמתודת setBalance קיימת ב-Balance
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Client client = (Client) obj;
//        return Objects.equals(getName(), client.getName()) &&
//                Objects.equals(getGender(), client.getGender());
        return this.getId()==client.getId();
    }


    @Override
    public int hashCode() {
        return Objects.hash(id); // hash מבוסס על ID
    }

    @Override
    public String toString() {
        return "Person{" +
                "birthDate=" + birthDate +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", payment=" + payment.getBalance() + // הצגת יתרת התשלום
                ", id=" + id +
                ", age=" + getAge() +
                '}';
    }
}
