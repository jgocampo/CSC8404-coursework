package unitTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import statistics.Statistics;
import student.Student;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StatisticsTest {

    private Student student;
    private Statistics statistics;

    @BeforeEach
    public void setup() {
        Calendar cal = Calendar.getInstance();
        cal.set(1995, Calendar.JANUARY, 1);
        Date birthDate = cal.getTime();
        student = new Student("John", "Doe", birthDate);
        statistics = student.getStatistics();
    }

    @Test
    public void testRegularQuizPass() {
        statistics.recordRegularQuizScore(0.7);  // PASSED regular quiz
        assertEquals("PASS", statistics.getVerdict());
    }

    @Test
    public void testRegularQuizFail() {
        statistics.recordRegularQuizScore(0.3);  // Failed first quiz
        assertEquals("TBD", statistics.getVerdict());  // Still pending

        statistics.recordRegularQuizScore(0.4);  // Failed second quiz
        assertEquals("FAIL", statistics.getVerdict());  // Verdict: FAIL
    }

    @Test
    public void testRevisionQuizWithoutAffectingVerdict() {
        statistics.recordRegularQuizScore(0.3);  // Fail first regular quiz
        statistics.recordRevisionQuizScore(0.6);  // PASS revision quiz, but no effect on verdict
        assertEquals("TBD", statistics.getVerdict());  // Still TBD

        statistics.recordRegularQuizScore(0.4);  // Fail second regular quiz
        assertEquals("FAIL", statistics.getVerdict());  // Final verdict: FAIL
    }

    @Test
    public void testMaxTwoRevisionAttempts() {
        statistics.recordRevisionQuizScore(0.6);  // First attempt
        statistics.recordRevisionQuizScore(0.7);  // Second attempt
        assertFalse(statistics.canTakeRevisionQuiz());  // Max attempts reached

        statistics.recordRevisionQuizScore(0.8);  // Should not register this score
        assertEquals(2, statistics.getRevisionAttempts());
    }

    @Test
    public void testSeenQuestionsTracking() {
        // Record some seen questions
        statistics.recordSeenQuestions(List.of("Question 1", "Question 2"));
        assertTrue(statistics.hasSeenQuestion("Question 1"));
        assertFalse(statistics.hasSeenQuestion("Question 3"));
    }

    /**
     * Prueba para verificar el método generateStatistics.
     */
    @Test
    public void testGenerateStatisticsReport() {

        // Registrar puntajes de quizzes de revisión
        statistics.recordRevisionQuizScore(0.4);  // First revision quiz
        statistics.recordRevisionQuizScore(0.5);  // Second revision quiz

        // Registrar puntajes de quizzes regulares
        statistics.recordRegularQuizScore(0.3);  // Failed first regular quiz
        statistics.recordRegularQuizScore(0.6);  // PASSED second regular quiz (should mark as PASS)



        // Generar el informe de estadísticas
        String report = statistics.generateStatistics();

        // Verificar que el informe contiene toda la información correcta
        assertTrue(report.contains("Final verdict: PASS"));
        assertTrue(report.contains("Number of regular quiz attempts: 2"));
        assertTrue(report.contains("Number of revision quiz attempts: 2"));
        assertTrue(report.contains("Regular quiz scores: [0.3, 0.6]"));
        assertTrue(report.contains("Revision quiz scores: [0.4, 0.5]"));
    }
}
