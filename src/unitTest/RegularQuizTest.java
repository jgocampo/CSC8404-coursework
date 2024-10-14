package unitTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import question.FreeResponseQuestion;
import question.MultipleChoiceQuestion;
import question.Question;
import quiz.RegularQuiz;
import student.Student;
import statistics.Statistics;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class RegularQuizTest {

    private List<Question> questionPool;
    private RegularQuiz regularQuiz;
    private Student student;

    @BeforeEach
    public void setup() {
        // Crear banco de preguntas mixtas
        questionPool = new ArrayList<>();
        questionPool.add(new FreeResponseQuestion("What is the capital of France?", "Paris"));
        questionPool.add(new FreeResponseQuestion("Who wrote '1984'?", "George Orwell"));
        questionPool.add(new MultipleChoiceQuestion("Which are fruits?", new String[]{"Apple", "Banana", "Orange"}));
        questionPool.add(new MultipleChoiceQuestion("Which are colors?", new String[]{"Red", "Green", "Blue"}));

        // Crear un quiz regular
        regularQuiz = new RegularQuiz(questionPool);

        // Crear un estudiante
        Calendar cal = Calendar.getInstance();
        cal.set(1995, Calendar.JANUARY, 1);
        Date birthDate = cal.getTime();
        student = new Student("John", "Doe", birthDate);
    }

    @Test
    public void testTakeQuizWithCorrectAnswers() {
        List<String> answers = List.of("Paris", "George Orwell", "Apple,Banana,Orange", "Red,Green,Blue");
        double score = regularQuiz.takeQuiz(student, questionPool, answers);
        assertEquals(1.0, score);  // 100% correct answers

        Statistics stats = student.getStatistics();
        assertEquals("PASS", stats.getVerdict());  // Student should pass
    }

    @Test
    public void testTakeQuizWithIncorrectAnswers() {
        List<String> answers = List.of("Lyon", "Orwell", "Apple", "Red,Green");
        double score = regularQuiz.takeQuiz(student, questionPool, answers);
        assertEquals(0.0, score);  // 0% correct answers

        Statistics stats = student.getStatistics();
        assertEquals("TBD", stats.getVerdict());  // Still TBD after the first failed quiz
    }

    @Test
    public void testTakeQuizAfterFailingTwice() {
        // Failing two regular quizzes
        student.getStatistics().recordRegularQuizScore(0.3);  // First quiz failed
        student.getStatistics().recordRegularQuizScore(0.2);  // Second quiz failed

        List<String> answers = List.of("Paris", "George Orwell", "Apple,Banana,Orange", "Red,Green,Blue");

        // Trying to take a third regular quiz after failing twice should throw an exception
        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                regularQuiz.takeQuiz(student, questionPool, answers)
        );

        assertEquals("Cannot take more regular quizzes. Final verdict: FAIL", exception.getMessage());
    }

    @Test
    public void testQuestionsTrackingAfterQuiz() {
        List<String> answers = List.of("Paris", "George Orwell", "Apple,Banana,Orange", "Red,Green,Blue");
        regularQuiz.takeQuiz(student, questionPool, answers);

        // Verificar que las preguntas vistas se registraron correctamente en las estadísticas
        Statistics stats = student.getStatistics();
        assertTrue(stats.hasSeenQuestion("What is the capital of France?"));
        assertTrue(stats.hasSeenQuestion("Who wrote '1984'?"));
        assertTrue(stats.hasSeenQuestion("Which are fruits?"));
        assertTrue(stats.hasSeenQuestion("Which are colors?"));
    }
}

