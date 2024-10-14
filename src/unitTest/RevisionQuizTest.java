package unitTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import question.FreeResponseQuestion;
import question.MultipleChoiceQuestion;
import question.Question;
import quiz.RevisionQuiz;
import student.Student;
import statistics.Statistics;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class RevisionQuizTest {

    private List<Question> questionPool;
    private RevisionQuiz revisionQuiz;
    private Student student;

    @BeforeEach
    public void setup() {
        // Crear banco de preguntas mixtas
        questionPool = new ArrayList<>();
        questionPool.add(new FreeResponseQuestion("What is the capital of Spain?", "Madrid"));
        questionPool.add(new FreeResponseQuestion("What is the chemical formula for water?", "H2O"));
        questionPool.add(new MultipleChoiceQuestion("Which are vegetables?", new String[]{"Carrot", "Broccoli", "Spinach"}));
        questionPool.add(new MultipleChoiceQuestion("Which are primary colors?", new String[]{"Red", "Blue", "Yellow"}));

        // Crear un quiz de revisión
        revisionQuiz = new RevisionQuiz(questionPool);

        // Crear un estudiante
        Calendar cal = Calendar.getInstance();
        cal.set(1995, Calendar.JANUARY, 1);
        Date birthDate = cal.getTime();
        student = new Student("Jane", "Doe", birthDate);
    }

    @Test
    public void testTakeRevisionQuizWithoutAffectingVerdict() {
        List<String> answers = List.of("Madrid", "H2O", "Carrot,Broccoli,Spinach", "Red,Blue,Yellow");
        double score = revisionQuiz.takeQuiz(student, questionPool, answers);
        assertEquals(1.0, score);  // All answers correct

        Statistics stats = student.getStatistics();
        assertEquals("TBD", stats.getVerdict());  // Revision quiz should not affect verdict
    }

    @Test
    public void testMaxTwoRevisionQuizzes() {
        List<String> answers = List.of("Madrid", "H2O", "Carrot,Broccoli,Spinach", "Red,Blue,Yellow");

        revisionQuiz.takeQuiz(student, questionPool, answers);  // First attempt
        revisionQuiz.takeQuiz(student, questionPool, answers);  // Second attempt

        Statistics stats = student.getStatistics();
        assertFalse(stats.canTakeRevisionQuiz());  // Max attempts reached

        // Trying to take a third revision quiz should throw an exception
        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                revisionQuiz.takeQuiz(student, questionPool, answers)
        );

        assertEquals("Cannot take more revision quizzes. Final verdict: TBD", exception.getMessage());
    }

    @Test
    public void testQuestionsTrackingAfterRevisionQuiz() {
        List<String> answers = List.of("Madrid", "H2O", "Carrot,Broccoli,Spinach", "Red,Blue,Yellow");
        revisionQuiz.takeQuiz(student, questionPool, answers);

        // Verificar que las preguntas vistas se registraron correctamente en las estadísticas
        Statistics stats = student.getStatistics();
        assertTrue(stats.hasSeenQuestion("What is the capital of Spain?"));
        assertTrue(stats.hasSeenQuestion("What is the chemical formula for water?"));
        assertTrue(stats.hasSeenQuestion("Which are vegetables?"));
        assertTrue(stats.hasSeenQuestion("Which are primary colors?"));
    }
}


