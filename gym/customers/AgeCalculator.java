package gym.customers;

import java.time.LocalDate;
import java.time.Period;

public class AgeCalculator {
    public static int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        if (birthDate == null || currentDate == null) {
            throw new IllegalArgumentException("Birth date and current date must not be null.");
        }
        if (birthDate.isAfter(currentDate)) {
            throw new IllegalArgumentException("Birth date cannot be after the current date.");
        }

        // חישוב הגיל
        return Period.between(birthDate, currentDate).getYears();
    }
}
