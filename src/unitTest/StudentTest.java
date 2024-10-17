package unitTest;
import student.Student;
import statistics.Statistics;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Student class.
 */
public class StudentTest {

    /**
     * Test to verify the correct creation of a Student object and the attribute getters.
     */
    @Test
    public void testStudentCreationAndGetters() {

        Calendar cal = Calendar.getInstance();
        cal.set(1998, Calendar.DECEMBER, 10);
        Date birthDate = cal.getTime();

        Student student = new Student("Jhostin", "Ocampo", birthDate);

        assertEquals("Jhostin", student.getFirstName());
        assertEquals("Ocampo", student.getLastName());
        assertEquals("Jhostin Ocampo", student.getName());
        assertEquals(birthDate, student.getDateOfBirth());
    }

    /**
     * Test to verify that the getDateOfBirth method returns a copy and not the direct reference.
     */
    @Test
    public void testGetDateOfBirth() {
        Calendar cal = Calendar.getInstance();
        cal.set(1994, Calendar.JANUARY, 29);
        Date birthDate = cal.getTime();

        Student student = new Student("Alixon", "Ocampo", birthDate);

        Date retrievedDate = student.getDateOfBirth();
        retrievedDate.setTime(System.currentTimeMillis());

        assertEquals(birthDate, student.getDateOfBirth());
    }

    /**
     * Test to verify that names are trimmed correctly and stored without extra spaces.
     */
    @Test
    public void testNameTrimming() {

        Calendar cal = Calendar.getInstance();
        cal.set(1990, Calendar.JANUARY, 1);
        Date birthDate = cal.getTime();

        Student student = new Student("   John   ", "   Ocampo   ", birthDate);

        assertEquals("John", student.getFirstName());
        assertEquals("Ocampo", student.getLastName());
        assertEquals("John Ocampo", student.getName());
    }

    /**
     * Test to verify that two students with the same name and date of birth are considered equal.
     */
    @Test
    public void testEqualsStudent() {

        Calendar cal = Calendar.getInstance();
        cal.set(1990, Calendar.JANUARY, 1);
        Date birthDate = cal.getTime();

        Student student1 = new Student("John", "Doe", birthDate);
        Student student2 = new Student("john", "doe", birthDate);

        assertEquals(student1, student2);
        assertEquals(student1.hashCode(), student2.hashCode());
    }

    /**
     * Test to verify that two students with different names or dates of birth are not the same.
     */
    @Test
    public void testNotEqualsStudent() {

        Calendar cal1 = Calendar.getInstance();
        cal1.set(1990, Calendar.JANUARY, 1);
        Date birthDate1 = cal1.getTime();

        Calendar cal2 = Calendar.getInstance();
        cal2.set(1991, Calendar.FEBRUARY, 1);
        Date birthDate2 = cal2.getTime();

        Student student1 = new Student("John", "Ocampo", birthDate1);
        Student student2 = new Student("John", "Smith", birthDate1);
        Student student3 = new Student("Jane", "Ocampo", birthDate2);

        assertNotEquals(student1, student2);
        assertNotEquals(student1, student3);
    }

    /**
     * Test for the toString method.
     */
    @Test
    public void testToString() {

        Calendar cal = Calendar.getInstance();
        cal.set(1995, Calendar.JANUARY, 1);
        Date birthDate = cal.getTime();

        Student student = new Student("John", "Ocampo", birthDate);

        String expected = "John Ocampo (born " + birthDate + ")";
        assertEquals(expected, student.toString());
    }

    /**
     * Test to verify that the getStatistics method returns a Statistics object associated with the student.
     */
    @Test
    public void testGetStatistics() {

        Calendar cal = Calendar.getInstance();
        cal.set(1990, Calendar.JANUARY, 1);
        Date birthDate = cal.getTime();

        Student student = new Student("John", "Doe", birthDate);

        Statistics stats = student.getStatistics();
        assertNotNull(stats);

        assertEquals(student, stats.getStudent());
    }
}

