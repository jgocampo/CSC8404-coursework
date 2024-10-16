package student;

import statistics.Statistics;
import java.util.Date;
import java.util.Objects;

/**
 * The Student class represents a student with a first name, last name, and date of birth.
 * Each student is linked to a Statistics object to track their performance.
 */

public final class Student {
    private final String firstName;
    private final String lastName;
    private final Date dateOfBirth;
    private final Statistics statistics;

    /**
     * Constructs a new Student with the specified first name, last name, and date of birth.
     *
     * @param firstName   The student first name.
     * @param lastName    The student last name.
     * @param dateOfBirth The student date of birth.
     */
    public Student(String firstName, String lastName, Date dateOfBirth) {
        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
        this.dateOfBirth = new Date(dateOfBirth.getTime());
        this.statistics = new Statistics(this);
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
        return new Date(dateOfBirth.getTime());
    }

    public Statistics getStatistics() {
        return statistics;
    }

    /**
     * Returns a string representation of the student, including their full name and date of birth.
     *
     * @return A string representation of the student.
     */

    @Override
    public String toString() {
        return firstName + " " + lastName + " (born " + dateOfBirth + ")";
    }

    /**
     * Determines if two students are equal. Two students are  equal if they have the same
     * first name, last name, and date of birth, ignoring case sensitivity.
     *
     * @param o The object to compare with this student.
     * @return True if the students are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return firstName.equalsIgnoreCase(student.firstName) &&
                lastName.equalsIgnoreCase(student.lastName) &&
                dateOfBirth.equals(student.dateOfBirth);
    }

    /**
     * The method hashCode is overwritten in order to create an accurate hashcode,
     * using the names a date of birth of the student
     *
     * @return A hash code value for the student.
     */

    @Override
    public int hashCode() {
        return Objects.hash(firstName.toLowerCase(), lastName.toLowerCase(), dateOfBirth);
    }
}


