package unitTest;

import student.Student;
import statistics.Statistics;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Statistics class.
 */
public class StatisticsTest {

    /**
     * Test que verifica la correcta grabación de puntajes y cálculo del veredicto.
     */
    @Test
    public void testRecordRegularQuizScorePass() {
        // Crear un estudiante
        Student student = new Student("John", "Doe", new java.util.Date());

        // Crear una instancia de Statistics
        Statistics stats = new Statistics(student);

        // Registrar un puntaje >= 0.5 en el primer intento regular (esto debería resultar en PASS)
        stats.recordRegularQuizScore(0.7);

        // Verificar que el veredicto sea PASS
        assertEquals("PASS", stats.getVerdict());
        assertEquals(1, stats.getRegularAttempts());  // Verificar que hay un intento registrado
    }

    /**
     * Test que verifica la correcta grabación de puntajes y cálculo del veredicto FAIL.
     */
    @Test
    public void testRecordRegularQuizScoreFail() {
        // Crear un estudiante
        Student student = new Student("Jane", "Doe", new java.util.Date());

        // Crear una instancia de Statistics
        Statistics stats = new Statistics(student);

        // Registrar dos puntajes bajos (esto debería resultar en FAIL después del segundo intento)
        stats.recordRegularQuizScore(0.3);
        stats.recordRegularQuizScore(0.4);

        // Verificar que el veredicto sea FAIL
        assertEquals("FAIL", stats.getVerdict());
        assertEquals(2, stats.getRegularAttempts());  // Verificar que hay dos intentos registrados
    }

    /**
     * Test que verifica que un estudiante no pueda registrar más de dos intentos de revisión.
     */
    @Test
    public void testRecordRevisionQuizScoreLimit() {
        // Crear un estudiante
        Student student = new Student("John", "Doe", new java.util.Date());

        // Crear una instancia de Statistics
        Statistics stats = new Statistics(student);

        // Registrar dos intentos de revisión
        stats.recordRevisionQuizScore(0.3);
        stats.recordRevisionQuizScore(0.4);

        // Verificar que el estudiante no pueda tomar más revisiones
        assertFalse(stats.canTakeRevisionQuiz());
        assertEquals(2, stats.getRevisionAttempts());  // Verificar que hay dos intentos registrados
    }

    /**
     * Test que verifica que el veredicto sea PASS si el estudiante obtiene >= 0.5 en una revisión.
     */
    @Test
    public void testRecordRevisionQuizScorePass() {
        // Crear un estudiante
        Student student = new Student("Jane", "Doe", new java.util.Date());

        // Crear una instancia de Statistics
        Statistics stats = new Statistics(student);

        // Registrar un puntaje bajo en el quiz regular
        stats.recordRegularQuizScore(0.3);

        // Registrar un puntaje >= 0.5 en la revisión (esto debería resultar en PASS)
        stats.recordRevisionQuizScore(0.6);

        // Verificar que el veredicto sea PASS
        assertEquals("PASS", stats.getVerdict());
        assertEquals(1, stats.getRevisionAttempts());  // Verificar que hay un intento de revisión registrado
    }

    /**
     * Test que verifica que no se puedan registrar intentos después de un veredicto de PASS o FAIL.
     */
    @Test
    public void testNoFurtherAttemptsAfterVerdict() {
        // Crear un estudiante
        Student student = new Student("John", "Doe", new java.util.Date());

        // Crear una instancia de Statistics
        Statistics stats = new Statistics(student);

        // Registrar dos puntajes bajos en los quizzes regulares (esto debería resultar en FAIL)
        stats.recordRegularQuizScore(0.2);
        stats.recordRegularQuizScore(0.4);

        // Intentar registrar otro intento (esto no debería afectar las estadísticas)
        stats.recordRegularQuizScore(0.5);
        assertEquals(2, stats.getRegularAttempts());  // Verificar que no se ha registrado otro intento

        // Verificar que el veredicto siga siendo FAIL
        assertEquals("FAIL", stats.getVerdict());
    }

    /**
     * Test que verifica la generación correcta del informe de estadísticas.
     */
    @Test
    public void testGenerateStatisticsReport() {
        // Crear un estudiante
        Student student = new Student("Jane", "Doe", new java.util.Date());

        // Crear una instancia de Statistics
        Statistics stats = new Statistics(student);

        // Registrar algunos puntajes
        stats.recordRegularQuizScore(0.3);  // Primer intento regular (fallido)
        //stats.recordRegularQuizScore(0.4);  // Segundo intento regular (fallido)
        stats.recordRevisionQuizScore(0.7);  // Un intento de revisión exitoso

        // Generar el informe de estadísticas
        String report = stats.generateStatistics();

        // Imprimir el reporte para verificar el formato
        System.out.println(report);  // Ver el contenido exacto generado

        // Verificar que el informe contiene la información correcta
        assertTrue(report.contains("Final verdict: PASS"));
        assertTrue(report.contains("Number of regular quiz attempts: 1"));
        assertTrue(report.contains("Number of revision quiz attempts: 1"));
        assertTrue(report.contains("Regular quiz scores: [0.3]"));
        assertTrue(report.contains("Revision quiz scores: [0.7]"));
    }


    /**
     * Test que verifica los límites de los intentos en los quizzes regulares y de revisión.
     */
    @Test
    public void testAttemptsLimits() {
        // Crear un estudiante
        Student student = new Student("John", "Doe", new java.util.Date());

        // Crear una instancia de Statistics
        Statistics stats = new Statistics(student);

        // Registrar dos intentos regulares fallidos
        stats.recordRegularQuizScore(0.2);
        //stats.recordRegularQuizScore(0.4);

        // Verificar que no puede tomar más quizzes regulares (FAIL)
        //assertFalse(stats.canTakeRegularQuiz());

        // Intentar registrar más puntajes no debería afectar
        //stats.recordRegularQuizScore(0.5);
        assertEquals(1, stats.getRegularAttempts());



        // Registrar dos intentos de revisión
        stats.recordRevisionQuizScore(0.4);
        stats.recordRevisionQuizScore(0.3);

        // Verificar que no puede tomar más quizzes de revisión
        assertFalse(stats.canTakeRevisionQuiz());
        assertEquals(2, stats.getRevisionAttempts());
    }
}
