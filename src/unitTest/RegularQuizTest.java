package unitTest;
import question.Question;
import quiz.RegularQuiz;
import question.FreeResponseQuestion;
import question.MultipleChoiceQuestion;
import student.Student;
import statistics.Statistics;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the RegularQuiz class without using assertThrows.
 */
public class RegularQuizTest {

    /**
     * Test generating a quiz and taking it with correct answers.
     */
    @Test
    public void testTakeQuizWithCorrectAnswers() {
        List<Question> questions = Arrays.asList(
                new FreeResponseQuestion("What is the capital of France?", "Paris"),
                new FreeResponseQuestion("What is the chemical symbol for water?", "H2O")
        );
        RegularQuiz quiz = new RegularQuiz(questions);

        Student student = new Student("John", "Doe", new java.util.Date());

        List<String> answers = Arrays.asList("Paris", "H2O");
        double score = quiz.takeQuiz(student, questions, answers);

        assertEquals(1.0, score);  // 100% correct answers
    }

    /**
     * Test taking quiz with incorrect answers.
     */
    @Test
    public void testTakeQuizWithIncorrectAnswers() {
        List<Question> questions = Arrays.asList(
                new FreeResponseQuestion("What is the capital of France?", "Paris"),
                new FreeResponseQuestion("What is the chemical symbol for water?", "H2O")
        );
        RegularQuiz quiz = new RegularQuiz(questions);

        Student student = new Student("John", "Doe", new java.util.Date());

        List<String> answers = Arrays.asList("London", "CO2");
        double score = quiz.takeQuiz(student, questions, answers);

        assertEquals(0.0, score);  // 0% correct answers
    }

    /**
     * Test attempting to generate quiz with invalid number of questions.
     */
    @Test
    public void testGenerateQuizWithInvalidNumber() {
        List<Question> questions = Arrays.asList(
                new FreeResponseQuestion("What is the capital of France?", "Paris")
        );
        RegularQuiz quiz = new RegularQuiz(questions);

        try {
            quiz.generateQuiz(5);  // Más preguntas de las que existen
            fail("Should have thrown an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Excepción esperada
            assertTrue(true);  // Afirmamos que la excepción fue lanzada
        }
    }

    /**
     * Test student with PASS verdict should not take another quiz.
     */
    @Test
    public void testStudentWithPassCannotTakeAnotherQuiz() {
        List<Question> questions = Arrays.asList(
                new FreeResponseQuestion("What is the capital of France?", "Paris"),
                new FreeResponseQuestion("What is the chemical symbol for water?", "H2O")
        );
        RegularQuiz quiz = new RegularQuiz(questions);

        Student student = new Student("John", "Doe", new java.util.Date());
        Statistics stats = student.getStatistics();
        stats.recordRegularQuizScore(1.0);  // Estudiante pasó

        try {
            quiz.takeQuiz(student, questions, Arrays.asList("Paris", "H2O"));
            fail("Should have thrown an IllegalStateException");
        } catch (IllegalStateException e) {
            assertTrue(true);  // Excepción esperada
        }
    }

    /**
     * Test student with FAIL verdict should not take another quiz.
     */
    @Test
    public void testStudentWithFailCannotTakeAnotherQuiz() {
        List<Question> questions = Arrays.asList(
                new FreeResponseQuestion("What is the capital of France?", "Paris"),
                new FreeResponseQuestion("What is the chemical symbol for water?", "H2O")
        );
        RegularQuiz quiz = new RegularQuiz(questions);

        Student student = new Student("John", "Doe", new java.util.Date());
        Statistics stats = student.getStatistics();
        stats.recordRegularQuizScore(0.3);  // Primer intento fallido
        stats.recordRegularQuizScore(0.2);  // Segundo intento fallido -> FAIL

        try {
            quiz.takeQuiz(student, questions, Arrays.asList("Paris", "H2O"));
            fail("Should have thrown an IllegalStateException");
        } catch (IllegalStateException e) {
            assertTrue(true);  // Excepción esperada
        }
    }

    /**
     * Test quiz with both FreeResponseQuestion and MultipleChoiceQuestion.
     */
    @Test
    public void testQuizWithMultipleTypesOfQuestions() {
        List<Question> questions = Arrays.asList(
                new FreeResponseQuestion("What is the capital of France?", "Paris"),
                new MultipleChoiceQuestion("Which of these are programming languages?", new String[]{"Java", "Python", "C++"})
        );
        RegularQuiz quiz = new RegularQuiz(questions);

        Student student = new Student("John", "Doe", new java.util.Date());

        List<String> answers = Arrays.asList("Paris", "Java,Python,C++");
        double score = quiz.takeQuiz(student, questions, answers);

        assertEquals(1.0, score);  // Respuestas correctas para ambos tipos de preguntas
    }
}

