package student;
import java.util.Date;
import java.util.Objects;

public final class Student {
    private final String firstName;
    private final String lastName;
    private final Date birthDate;

    public Student(String firstName, String lastName, Date birthDate) {
        if (firstName == null || lastName == null || birthDate == null) {
            throw new IllegalArgumentException("Arguments can't be null.");
        }
        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
        this.birthDate = new Date(birthDate.getTime());
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public Date getBirthDate() {
        return new Date(birthDate.getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return firstName.equals(student.firstName) &&
                lastName.equals(student.lastName) &&
                birthDate.equals(student.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, birthDate);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (born " + birthDate + ")";
    }
}

