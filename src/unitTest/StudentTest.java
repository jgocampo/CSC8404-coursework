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
     * Test para verificar la correcta creación de un objeto Student y los getters de los atributos.
     */
    @Test
    public void testStudentCreationAndGetters() {
        // Crear fecha de nacimiento
        Calendar cal = Calendar.getInstance();
        cal.set(1990, Calendar.JANUARY, 1);
        Date birthDate = cal.getTime();

        // Crear un estudiante
        Student student = new Student("John", "Doe", birthDate);

        // Verificar que los atributos se hayan asignado correctamente
        assertEquals("John", student.getFirstName());
        assertEquals("Doe", student.getLastName());
        assertEquals("John Doe", student.getName());
        assertEquals(birthDate, student.getDateOfBirth());
    }

    /**
     * Test para verificar que el método getDateOfBirth retorna una copia y no la referencia directa.
     */
    @Test
    public void testGetDateOfBirthImmutability() {
        // Crear fecha de nacimiento
        Calendar cal = Calendar.getInstance();
        cal.set(1990, Calendar.JANUARY, 1);
        Date birthDate = cal.getTime();

        // Crear un estudiante
        Student student = new Student("Jane", "Doe", birthDate);

        // Obtener la fecha de nacimiento a través del getter y modificarla
        Date retrievedDate = student.getDateOfBirth();
        retrievedDate.setTime(System.currentTimeMillis());

        // Verificar que la fecha en el objeto student no haya cambiado
        assertEquals(birthDate, student.getDateOfBirth());
    }

    /**
     * Test para verificar que los nombres se trimen correctamente y se almacenan sin espacios extra.
     */
    @Test
    public void testNameTrimming() {
        // Crear fecha de nacimiento
        Calendar cal = Calendar.getInstance();
        cal.set(1990, Calendar.JANUARY, 1);
        Date birthDate = cal.getTime();

        // Crear un estudiante con nombres con espacios
        Student student = new Student("   John   ", "   Doe   ", birthDate);

        // Verificar que los espacios se hayan eliminado
        assertEquals("John", student.getFirstName());
        assertEquals("Doe", student.getLastName());
        assertEquals("John Doe", student.getName());
    }

    /**
     * Test para verificar que dos estudiantes con el mismo nombre y fecha de nacimiento son considerados iguales.
     */
    @Test
    public void testEqualsSameStudent() {
        // Crear fecha de nacimiento
        Calendar cal = Calendar.getInstance();
        cal.set(1990, Calendar.JANUARY, 1);
        Date birthDate = cal.getTime();

        // Crear dos estudiantes con los mismos nombres y fecha de nacimiento
        Student student1 = new Student("John", "Doe", birthDate);
        Student student2 = new Student("john", "doe", birthDate);  // Case-insensitive

        // Verificar que sean iguales
        assertEquals(student1, student2);
        assertEquals(student1.hashCode(), student2.hashCode());  // El hashCode también debería ser el mismo
    }

    /**
     * Test para verificar que dos estudiantes con diferente nombre o fecha de nacimiento no son iguales.
     */
    @Test
    public void testNotEqualsDifferentStudent() {
        // Crear dos fechas de nacimiento diferentes
        Calendar cal1 = Calendar.getInstance();
        cal1.set(1990, Calendar.JANUARY, 1);
        Date birthDate1 = cal1.getTime();

        Calendar cal2 = Calendar.getInstance();
        cal2.set(1991, Calendar.FEBRUARY, 1);
        Date birthDate2 = cal2.getTime();

        // Crear dos estudiantes con nombres o fechas de nacimiento diferentes
        Student student1 = new Student("John", "Doe", birthDate1);
        Student student2 = new Student("John", "Smith", birthDate1);
        Student student3 = new Student("Jane", "Doe", birthDate2);

        // Verificar que no son iguales
        assertNotEquals(student1, student2);  // Nombres diferentes
        assertNotEquals(student1, student3);  // Fecha de nacimiento diferente
    }

    /**
     * Test para verificar el método toString.
     */
    @Test
    public void testToString() {
        // Crear fecha de nacimiento
        Calendar cal = Calendar.getInstance();
        cal.set(1990, Calendar.JANUARY, 1);
        Date birthDate = cal.getTime();

        // Crear un estudiante
        Student student = new Student("John", "Doe", birthDate);

        // Verificar que el método toString retorna el formato correcto
        String expected = "John Doe (born " + birthDate + ")";
        assertEquals(expected, student.toString());
    }

    /**
     * Test para verificar que el método getStatistics retorna un objeto Statistics asociado con el estudiante.
     */
    @Test
    public void testGetStatistics() {
        // Crear fecha de nacimiento
        Calendar cal = Calendar.getInstance();
        cal.set(1990, Calendar.JANUARY, 1);
        Date birthDate = cal.getTime();

        // Crear un estudiante
        Student student = new Student("John", "Doe", birthDate);

        // Verificar que getStatistics retorna un objeto Statistics
        Statistics stats = student.getStatistics();
        assertNotNull(stats);

        // Verificar que las estadísticas están asociadas con el estudiante correcto
        assertEquals(student, stats.getStudent());
    }
}

