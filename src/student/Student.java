package student;

import statistics.Statistics;
import java.util.Date;
import java.util.Objects;

public final class Student {
    private final String firstName;
    private final String lastName;
    private final Date dateOfBirth;
    private final Statistics statistics;

    // Constructor
    public Student(String firstName, String lastName, Date dateOfBirth) {
        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
        this.dateOfBirth = new Date(dateOfBirth.getTime());  // Hacer una copia de la fecha para proteger la inmutabilidad
        this.statistics = new Statistics(this);  // Cada estudiante tiene un objeto de estadísticas
    }

    // Getters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public Date getDateOfBirth() {
        return new Date(dateOfBirth.getTime());  // Retorna una copia para proteger la inmutabilidad
    }

    public Statistics getStatistics() {
        return statistics;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (born " + dateOfBirth + ")";
    }

    // Igualdad de estudiantes basada en nombre y fecha de nacimiento
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return firstName.equalsIgnoreCase(student.firstName) &&
                lastName.equalsIgnoreCase(student.lastName) &&
                dateOfBirth.equals(student.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName.toLowerCase(), lastName.toLowerCase(), dateOfBirth);
    }
}


